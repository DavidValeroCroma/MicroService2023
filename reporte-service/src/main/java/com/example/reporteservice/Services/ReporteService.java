package com.example.reporteservice.Services;

import com.example.reporteservice.Config.RestTemplateConfig;
import com.example.reporteservice.Entities.ReporteEntity;
import com.example.reporteservice.Models.AcopioModel;
import com.example.reporteservice.Repositories.ReporteRepository;
import lombok.Generated;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReporteService{

    @Autowired
    ReporteRepository reporteRepository;

    @Autowired
    RestTemplate restTemplate;

    private final Logger logg = LoggerFactory.getLogger(ReporteService.class);

    public ArrayList<ReporteEntity> obtenerQuincenas(){
        return (ArrayList<ReporteEntity>) reporteRepository.findAll();
    }

    public ArrayList<ReporteEntity> obtenerQuincenasPorProveedor(String idProveedor){
        return reporteRepository.findByIdProveedor(idProveedor);
    }

    public ReporteEntity obtenerUltimoReporte(String proveedor){
        ArrayList<ReporteEntity> reportes = obtenerQuincenasPorProveedor(proveedor);
        return reportes.get(reportes.size()-1);
    }

    public boolean existeAlgunRep(String idProveedor){
        return reporteRepository.existsAny(idProveedor);
    }

    @Generated
    public String guardar(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
            return "Archivo guardado con exito!";
        }
        else{
            return "No se pudo guardar el archivo";
        }
    }

    @Generated
    public void guardarData(ReporteEntity data){
        reporteRepository.save(data);
    }

    public void eliminarData(ArrayList<ReporteEntity> datas){
        reporteRepository.deleteAll(datas);
    }

    public List<AcopioModel> consultaAcopio(String proveedorId){
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List<AcopioModel>> response = restTemplate.exchange("http://acopio-service/acopio/" + proveedorId, HttpMethod.GET, entity, new ParameterizedTypeReference<List<AcopioModel>>() {});

        List<AcopioModel> acopio = response.getBody();
        return acopio;
    }

    public void generarPago(ReporteEntity reporte){
        restTemplate.postForObject("http://pago-service/pago/upload", reporte, Void.class);
    }

    public Double lecheQuincena(ArrayList<AcopioModel> acopios){
        double acum = 0;

        for (AcopioModel acopio:acopios){
            acum = acum + acopio.getLeche();
        }

        return acum;
    }

    public Integer cantidadDias(ArrayList<AcopioModel> acopios){
        int acum = 0;
        Date fechaAnt = new Date();
        for (AcopioModel acopio: acopios){
            if (acopio.getFecha() != fechaAnt){
                acum = acum + 1;
            }
            fechaAnt = acopio.getFecha();
        }
        return acum;
    }

    public void guardarDataDB(String proveedor, String grasa, String solido){

        Double cantLeche = 0.0;
        Integer cantDias = 0;
        Double promLeche = 0.0;
        Double solidos = 0.0;
        Double  grasas= 0.0;
        ReporteEntity newReporte = new ReporteEntity();

        newReporte.setIdProveedor(proveedor);
        newReporte.setPorGrasa(Double.parseDouble(grasa));
        newReporte.setPorSolidos(Double.parseDouble(solido));

        ArrayList<AcopioModel> acopiosProveedor = (ArrayList<AcopioModel>) consultaAcopio(proveedor);

        //sacamos la info de los acopios
        if(!acopiosProveedor.isEmpty()) {

            Date fechaAux = acopiosProveedor.get(acopiosProveedor.size()-1).getFecha();
            cantLeche = lecheQuincena(acopiosProveedor);
            cantDias = cantidadDias(acopiosProveedor);
            promLeche = cantLeche/cantDias;
            solidos = (cantLeche * newReporte.getPorSolidos())/100;
            grasas = (cantLeche * newReporte.getPorGrasa())/100;


            if (fechaAux.getDate() > 15) {
                newReporte.setQuincena(2);
            } else {
                newReporte.setQuincena(1);
            }

            newReporte.setMes(fechaAux.getMonth()+1);
            newReporte.setAnio(fechaAux.getYear()+1900);

        }else {

            Date fechaActual = new Date();
            if (fechaActual.getDate() > 15) {
                newReporte.setQuincena(2);
            } else {
                newReporte.setQuincena(1);
            }

            newReporte.setMes(fechaActual.getMonth()+1);
            newReporte.setAnio(fechaActual.getYear()+1900);

        }
        newReporte.setSolidos(solidos);
        newReporte.setGrasa(grasas);

        newReporte.setLeche(cantLeche);
        newReporte.setNroDias(cantDias);
        newReporte.setPromedioLeche(promLeche);

        //eliminamos los reportes repetidos de haber alguno
        if(existeAlgunRep(proveedor)){
            ArrayList<ReporteEntity> reportes = obtenerQuincenasPorProveedor(proveedor);
            for (ReporteEntity reporte: reportes){

                if(reporte.getAnio().equals(newReporte.getAnio())  &&   (reporte.getMes().equals(newReporte.getMes()))  && (reporte.getQuincena().equals(newReporte.getQuincena()))){
                    reporteRepository.delete(reporte);
                }
            }
        }

        //comparamos con el reporte anterior
        if (existeAlgunRep(proveedor)) {

            ReporteEntity reporteAnterior = obtenerUltimoReporte(proveedor);
            newReporte.setVarGrasa((Double.parseDouble(grasa) - reporteAnterior.getGrasa()) / reporteAnterior.getGrasa());
            newReporte.setVarSolidos((Double.parseDouble(solido) - reporteAnterior.getSolidos()) / reporteAnterior.getSolidos());
            newReporte.setVarCantLeche((cantLeche - reporteAnterior.getLeche())/reporteAnterior.getLeche());
        }else {
            newReporte.setVarGrasa(0.0);
            newReporte.setVarSolidos(0.0);
            newReporte.setVarCantLeche(0.0);
        }

        guardarData(newReporte);


        //debemos conectarlo con un controlador
        generarPago(newReporte);
        //tal vez con lo siguiente se arregle
        //return newReporte;


    }



    @Generated
    public void leerCsv(String direccion) {
        String texto = "";
        BufferedReader bf = null;
        //reporteRepository.deleteAll();
        try {
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while ((bfRead = bf.readLine()) != null) {
                if (count == 1) {
                    count = 0;
                } else {
                    //guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        } catch (Exception e) {
            System.err.println("No se encontro el archivo");
        } finally {
            if (bf != null) {
                try {
                    bf.close();
                } catch (IOException e) {
                    logg.error("ERROR", e);
                }
            }
        }
    }
}
