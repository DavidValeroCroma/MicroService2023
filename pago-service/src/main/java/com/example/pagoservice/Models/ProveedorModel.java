package com.example.pagoservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorModel {
    private String nombre;
    private Character categoria;
    private Boolean retencion;
}
