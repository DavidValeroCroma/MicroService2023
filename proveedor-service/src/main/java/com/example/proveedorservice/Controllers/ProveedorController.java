package com.example.proveedorservice.Controllers;

import com.example.proveedorservice.Entities.ProveedorEntity;
import com.example.proveedorservice.Services.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedor/fileInformation")
    public String proveedores(Model model){
        ArrayList<ProveedorEntity> proveedores = proveedorService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "ProveedorInfoView";
    }

    @GetMapping("/proveedor/fileUpload")
    public String accessToProviderView(){
        return "ProveedorView";}

    @PostMapping("/proveedor/fileUpload")
    public String guardarProveedor(@RequestParam("id") String id,
                                   @RequestParam("nombre") String nombre,
                                   @RequestParam("categoria") String categoria,
                                   @RequestParam("retencion") Boolean retencion) {
        ProveedorEntity proveedor = new ProveedorEntity(id, nombre, categoria.charAt(0), retencion);
        proveedorService.guardarProveedor(proveedor);

        return "redirect:/proveedor/fileInformation";
    }
}
