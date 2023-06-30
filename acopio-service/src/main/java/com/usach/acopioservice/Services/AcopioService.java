package com.usach.acopioservice.Services;
import com.usach.acopioservice.Entities.AcopioEntity;
import com.usach.acopioservice.Repostiories.AcopioRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.Generated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.io.IOException;
import java.text.ParseException;


@Service
public class AcopioService {
    @Autowired
    AcopioRepository acopioRepository;

    private final Logger logg = LoggerFactory.getLogger(AcopioService.class);

    public ArrayList<AcopioEntity> obtenerAcopios() {
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }

    public ArrayList<AcopioEntity> obtenerAcopioPorProveedor(String idProveedor){
        return acopioRepository.findByIdProveedor(idProveedor);
    }

    public boolean exiteAlguno(String idProvedor){
        return acopioRepository.existsAny(idProvedor);
    }

    public Double totalLecheQuincena(String idProveedor){

        ArrayList<AcopioEntity> acopioQuincena = obtenerAcopioPorProveedor(idProveedor);
        double acum = 0;

        for (AcopioEntity acopio:acopioQuincena){
            acum = acum + acopio.getLeche();
        }

        return acum;
    }

    public Date conseguirFechaAcopios(String proveedor){
        AcopioEntity acopioAux = acopioRepository.getTopByIdProveedor(proveedor);
        return acopioAux.getFecha();
    }

    public int cantDias(String idProveedor){
        ArrayList<AcopioEntity> acopios = obtenerAcopioPorProveedor(idProveedor);
        int acum = 0;
        Date fechaAnt = new Date();
        for (AcopioEntity acopio: acopios){
            if (acopio.getFecha() != fechaAnt){
                acum = acum + 1;
            }
            fechaAnt = acopio.getFecha();
        }
        return acum;
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
    public void guardarData(AcopioEntity data){
        acopioRepository.save(data);
    }

    public void guardarDataDB(String fecha, String turno, String proveedor, String klsLeche){
        AcopioEntity newAcopio = new AcopioEntity();
        System.out.println(fecha);
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaAux = null;
        try{
            fechaAux = formato.parse(fecha);

            newAcopio.setFecha(fechaAux);
            newAcopio.setIdProveedor(proveedor);
            newAcopio.setTurno(turno.charAt(0));
            newAcopio.setLeche(Double.parseDouble(klsLeche));
            guardarData(newAcopio);
        }catch(ParseException e){
            System.out.println("Error al parsear la fecha.");
            e.printStackTrace();
        }
    }
    public void eliminarData(ArrayList<AcopioEntity> datas){
        acopioRepository.deleteAll(datas);
    }

    @Generated
    public void leerCsv(String direccion){
        String texto = "";
        BufferedReader bf = null;
        acopioRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarDataDB(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], bfRead.split(";")[3]);
                    temp = temp + "\n" + bfRead;
                }
            }
            texto = temp;
            System.out.println("Archivo leido exitosamente");
        }catch(Exception e){
            System.err.println("No se encontro el archivo");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }

    }
}
