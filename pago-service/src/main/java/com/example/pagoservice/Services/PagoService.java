package com.example.pagoservice.Services;

import com.example.pagoservice.Entities.PagoEntity;
import com.example.pagoservice.Models.AcopioModel;
import com.example.pagoservice.Models.ProveedorModel;
import com.example.pagoservice.Models.ReporteModel;
import com.example.pagoservice.Repositories.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PagoService {
    @Autowired
    PagoRepository pagoRepository;

    @Autowired
    RestTemplate restTemplate;

    public ArrayList<PagoEntity> obtenerPagos(){
        return (ArrayList<PagoEntity>) pagoRepository.findAll();
    }

    public ProveedorModel getProveedor(String idProveedor){
        ResponseEntity<ProveedorModel> response = restTemplate.exchange(
                "http://proveedor-service/proveedor/" + idProveedor,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ProveedorModel>() {}
        );
        ProveedorModel proveedor = response.getBody();
        return proveedor;
    }

    public List<AcopioModel> consultaAcopio(String proveedorId){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<AcopioModel>> response = restTemplate.exchange("http://acopio-service/acopio/" + proveedorId, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AcopioModel>>() {});

        List<AcopioModel> acopio = response.getBody();
        return acopio;
    }

    public Double bonificacionCons(List<AcopioModel> acopios){

        int diasSeguidos = 0;
        int diasTardeSeguidos = 0;
        int diasMañanaSeguidos = 0;
        Character turnoPrevio = 'O';
        Character turnoDiaPrevio = 'O';
        int diaPrevio = 200;

        if(!acopios.isEmpty()) {

            //calculamos los dias y/o turnos seguidos
            for (AcopioModel acopio : acopios) {

                if (acopio.getFecha().getDay() == diaPrevio + 1) {
                    if ((turnoPrevio == 'M' || turnoDiaPrevio == 'M') && acopio.getTurno() == 'M') {
                        diasMañanaSeguidos = diasMañanaSeguidos + 1;
                    } else if ((turnoPrevio == 'T' || turnoDiaPrevio == 'T') && acopio.getTurno() == 'T') {
                        diasTardeSeguidos = diasTardeSeguidos + 1;
                    } else {
                        diasMañanaSeguidos = 0;
                        diasTardeSeguidos = 0;
                    }

                } else if (acopio.getFecha().getDay() == diaPrevio) {
                    if (turnoPrevio == 'M' && acopio.getTurno() == 'T') {
                        diasSeguidos = diasSeguidos + 1;
                    }
                } else {
                    diasSeguidos = 0;
                    diasMañanaSeguidos = 0;
                    diasTardeSeguidos = 0;
                }

                turnoDiaPrevio = turnoPrevio;
                turnoPrevio = acopio.getTurno();
                diaPrevio = acopio.getFecha().getDay();

            }
        }
        //Vemos cual es la bonificación que le corresponde

        if(diasSeguidos >= 10){
            return 0.2;

        }else if(diasMañanaSeguidos >= 10){
            return 0.12;

        }else if(diasTardeSeguidos >= 10){
            return 0.08;
        }
        return  0.0;

    }


    public void generarPago(ReporteModel reporte, String idRep){
        ProveedorModel proveedorAux = getProveedor(reporte.getIdProveedor());
        PagoEntity nuevoPago = new PagoEntity();
        nuevoPago.setIdReporte(Long.parseLong(idRep));
        nuevoPago.setIdProveedor(reporte.getIdProveedor());
        nuevoPago.setQuincena(reporte.getQuincena());
        nuevoPago.setMes(reporte.getMes());
        nuevoPago.setAnio(reporte.getAnio());
        Double leche = reporte.getLeche();
        Double varSolidos = reporte.getVarSolidos();
        Double varGrasa = reporte.getVarGrasa();
        Double varCantLeche = reporte.getVarCantLeche();
        Double porGrasa = reporte.getPorGrasa();
        Double porSolidos = reporte.getPorSolidos();


        //calculamos el pago total de leche
        double pagoLeche = 0;
        if(proveedorAux.getCategoria().equals('A')){
            pagoLeche = leche * 700;

        } else if (proveedorAux.getCategoria().equals('B')) {
            pagoLeche = leche * 550;

        } else if (proveedorAux.getCategoria().equals('C')) {
            pagoLeche = leche * 400;

        } else if (proveedorAux.getCategoria().equals('D')) {
            pagoLeche = leche * 250;

        }
        System.out.println( pagoLeche);

        nuevoPago.setPagoLeche(pagoLeche);

        //calculamos el pago por grasa

        double pagoGrasa = 0;

        if (porGrasa<=20.0){
            pagoGrasa = leche * 30;
        } else if (porGrasa<=45.0  && porGrasa>20.0) {
            pagoGrasa = leche * 80;
        } else if (porGrasa<100.0 && porGrasa>45.0) {
            pagoGrasa = leche * 120;
        }

        nuevoPago.setPagoGrasa(pagoGrasa);
        //calculamos el pago por solidos

        double pagoSolidos = 0.0;

        if (porSolidos < 7.0){
            pagoSolidos = leche * -130;
        } else if (porSolidos < 18.0 && porSolidos > 7.0) {
            pagoSolidos = leche * -90;
        } else if (porSolidos < 35.0 && porSolidos > 18.0){
            pagoSolidos = leche * 95;
        } else if (porSolidos < 100.0 && porSolidos > 35.0){
            pagoSolidos = leche * 150;
        }

        nuevoPago.setPagoSolidos(pagoSolidos);

        //Sacamos el pago por acopio de leche

        //hay que conectarlo con el controlador en vez de service

        List<AcopioModel> acopios = consultaAcopio(reporte.getIdProveedor());

        double bonificacion = pagoLeche * bonificacionCons(acopios);

        nuevoPago.setBonoFrec(bonificacion);

        double pagoAcopioLeche = pagoLeche + pagoGrasa + pagoSolidos + bonificacion;

        //sacamos los descuentos

        double descVarLeche = 0;
        double descVarGrasa = 0;
        double descVarSolido = 0;

        if (varCantLeche <= 25.0 && varCantLeche > 8.0) {
            descVarLeche = pagoAcopioLeche * 0.07;
        } else if (varCantLeche < 40.0 && varCantLeche > 25.0) {
            descVarLeche = pagoAcopioLeche - pagoAcopioLeche * 0.15;
        } else if (varCantLeche < 100.0 && varCantLeche > 40.0) {
            descVarLeche = pagoAcopioLeche - pagoAcopioLeche * 0.3;
        }

        if(varGrasa <= 25 && varGrasa >= 15){
            descVarGrasa = pagoAcopioLeche * 0.12;

        } else if (varGrasa <= 45 && varGrasa > 25) {
            descVarGrasa = pagoAcopioLeche * 0.20;

        } else if (varGrasa <= 100 && varGrasa > 45) {
            descVarGrasa = pagoAcopioLeche * 0.30;
        }

        if (varSolidos <= 12 && varSolidos >= 6){
            descVarSolido = pagoAcopioLeche * 0.18;

        } else if (varSolidos <= 35 && varSolidos > 12) {
            descVarSolido = pagoAcopioLeche * 0.27;

        } else if (varSolidos <= 100 && varSolidos > 35) {
            descVarSolido = pagoAcopioLeche * 0.45;
        }

        double descuentosTotales = descVarSolido + descVarGrasa + descVarLeche;

        nuevoPago.setDescVarGrasa(descVarGrasa);
        nuevoPago.setDescVarSol(descVarSolido);
        nuevoPago.setDescVarLeche(descVarLeche);

        double pagoTotal = pagoAcopioLeche - descuentosTotales;

        nuevoPago.setPagoTotal(pagoTotal);

        double pagoFinal = pagoTotal;
        double retencion = 0;

        if (proveedorAux.getRetencion() == true && pagoTotal > 950000){
            retencion = 0.13*pagoTotal;
            pagoFinal = pagoTotal - retencion;
        }

        nuevoPago.setMontoRet(retencion);

        nuevoPago.setPagoFinal(pagoFinal);

        pagoRepository.save(nuevoPago);

    }

}
