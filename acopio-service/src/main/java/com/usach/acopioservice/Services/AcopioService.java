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

    public ArrayList<AcopioEntity> obtenerAcopios(){
        return (ArrayList<AcopioEntity>) acopioRepository.findAll();
    }

    public ArrayList<AcopioEntity> obtenerAcopiosPorProveedor(Long id){
        return (ArrayList<AcopioEntity>) acopioRepository.findByIdProveedor(id);
    }

    public void crearAcopio(Long idProveedor, Date fecha, Character turno, Double leche){
        AcopioEntity acopioAux = new AcopioEntity();
        acopioAux.setIdProveedor(idProveedor);
        acopioAux.setFecha(fecha);
        acopioAux.setLeche(leche);
        if (turno == 'M' || turno == 'T'){
            acopioAux.setTurno(turno);
        }else{
            turno = 'M';
            acopioAux.setTurno(turno);
        }
        acopioRepository.save(acopioAux);
    }

    public ArrayList<AcopioEntity> obtenerAcopiosPorQuincena(Integer quincena, Integer mes, Integer año, Long idProveedor){
        ArrayList<AcopioEntity> acopiosQuincena = new ArrayList<>();
        ArrayList<AcopioEntity> totalAcopios = obtenerAcopiosPorProveedor(idProveedor);

        for (AcopioEntity acopio: totalAcopios){
            if (quincena == 1 && acopio.getFecha().getDay() < 15  && acopio.getFecha().getDay() > 0 && acopio.getFecha().getMonth() == mes && acopio.getFecha().getYear()== año){
                acopiosQuincena.add(acopio);
            }
            if (quincena == 2 && acopio.getFecha().getDay() <= 31  && acopio.getFecha().getDay() >= 15  && acopio.getFecha().getMonth() == mes && acopio.getFecha().getYear()== año){
                acopiosQuincena.add(acopio);
            }
        }

        return acopiosQuincena;
    }

    public double totalLecheQuincena(Integer quincena, Integer mes, Integer año, Long idProveedor){
        ArrayList<AcopioEntity> acopiosQuincena = obtenerAcopiosPorQuincena(quincena, mes, año, idProveedor);
        double acum = 0 ;

        for (AcopioEntity acopio:acopiosQuincena){
            acum = acum + acopio.getLeche();
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
    public void guardarData(AcopioEntity data){
        acopioRepository.save(data);
    }


    public void guardarDataDB(String fecha, String turno, String proveedor, String kls_leche){
        AcopioEntity newData = new AcopioEntity();

        SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
        Date fechaAux = null;
        try {
            fechaAux = formato.parse(fecha);
            newData.setFecha(fechaAux);
            newData.setTurno(turno.charAt(0));
            newData.setIdProveedor(Long.parseLong(proveedor));
            newData.setLeche(Double.parseDouble(kls_leche));
            guardarData(newData);
        } catch (ParseException e) {
            System.out.println("Error al parsear la fecha.");
            e.printStackTrace();
        }

    }
    public void eliminarData(ArrayList<AcopioEntity> datas){
        acopioRepository.deleteAll(datas);
    }

}
