package com.example.proveedorservice.Controllers;

import com.example.proveedorservice.Entities.ProveedorEntity;
import com.example.proveedorservice.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/proveedor")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<ProveedorEntity>> findAllProveedores(){
        List<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        if (proveedores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("/{idProveedor}")
    public ResponseEntity<ProveedorEntity> getProveedorById(@PathVariable("proveedorId") String idProveedor){
        ProveedorEntity proveedores = proveedorService.obtenerProveedorPorId(idProveedor);
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<ProveedorEntity> guardarProveedor(@RequestParam("id") String id,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("categoria") String categoria,
                                   @RequestParam("retencion") Boolean retencion) {
        ProveedorEntity proveedor = new ProveedorEntity(id, nombre, categoria.charAt(0), retencion);
        proveedorService.guardarProveedor(proveedor);

        return ResponseEntity.ok(proveedor);
    }

}
