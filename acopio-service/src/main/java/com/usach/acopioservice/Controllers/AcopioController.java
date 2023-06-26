package com.usach.acopioservice.Controllers;

import com.usach.acopioservice.Entities.AcopioEntity;
import com.usach.acopioservice.Services.AcopioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/acopio")
public class AcopioController {
    @Autowired
    AcopioService acopioService;

    @GetMapping
    public ResponseEntity<List<AcopioEntity>> findAll(){
        List<AcopioEntity> acopios = acopioService.obtenerAcopios();
        if (acopios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios);
    }

    @GetMapping("/{idProveedor}")
    public ResponseEntity<List<AcopioEntity>> getByIdProveedor(@PathVariable("idProveedor") String idProveedor){
        List<AcopioEntity> acopios = acopioService.obtenerAcopioPorProveedor(idProveedor);
        if (acopios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios);
    }

    @PostMapping
    public void guardarData(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws FileNotFoundException, ParseException {
        acopioService.guardar(file);
        acopioService.leerCsv("Acopio.csv");
    }
}
