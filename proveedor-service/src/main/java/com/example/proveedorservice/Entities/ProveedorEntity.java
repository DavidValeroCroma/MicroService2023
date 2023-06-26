package com.example.proveedorservice.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "proveedor")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorEntity {
    @Id
    //@Column(nullable = false, length = 5, unique = true)
    @NotNull
    private String id;
    private String nombre;
    private Character categoria;
    private Boolean retencion;
}
