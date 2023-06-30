package com.example.pagoservice.Controllers;

import com.example.pagoservice.Entities.PagoEntity;
import com.example.pagoservice.Models.ReporteModel;
import com.example.pagoservice.Services.PagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pago")
public class PagoController {
    @Autowired
    PagoService pagoService;

    @GetMapping
    public ResponseEntity<List<PagoEntity>> findAll(){
        List<PagoEntity> pagos = pagoService.obtenerPagos();
        if (pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @PostMapping("/upload/{idReporte}")
    public void crearPago(@RequestParam ReporteModel reporte, @RequestParam("idReporte") String idReporte){
        pagoService.generarPago(reporte, idReporte);
    }
}
