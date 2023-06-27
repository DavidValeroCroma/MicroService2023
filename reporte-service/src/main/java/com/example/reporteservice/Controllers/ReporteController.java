package com.example.reporteservice.Controllers;

import com.example.reporteservice.Entities.ReporteEntity;
import com.example.reporteservice.Models.AcopioModel;
import com.example.reporteservice.Services.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reporte")
public class ReporteController {
    @Autowired
    ReporteService reporteService;

    @PostMapping("/fileUpload")
    public void upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        reporteService.guardar(file);
        redirectAttributes.addFlashAttribute(file);
        reporteService.leerCsv("Quincena.csv");
    }

    @GetMapping("/fileInformation")
    public ResponseEntity<ArrayList<ReporteEntity>> listar(Model model){
        ArrayList<ReporteEntity> reporte = reporteService.obtenerQuincenas();
        if (reporte.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reporte);
    }

    /*
    @GetMapping("/{proveedorId}")
    public ResponseEntity<List<AcopioModel>> obtenerAcopioPorProveedor(@PathVariable("proveedorId") String proveedorId) {
        List<AcopioModel> aux = reporteService.consultaAcopio(proveedorId);
        if (aux.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(aux);
    }
    */
}
