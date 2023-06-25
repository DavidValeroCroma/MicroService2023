package com.example.proveedorservice.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorEntity {
    @Id
    @Column(nullable = false, length = 5, unique = true)
    private String id;
    private String nombre;
    private Character categoria;
    private Boolean retencion;
}
