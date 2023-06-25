package com.example.reporteservice.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcopioModel {
    private String idProveedor;
    private Date fecha;
    private Character turno;
    private Double leche;

}
