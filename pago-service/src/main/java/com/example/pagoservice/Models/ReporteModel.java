package com.example.pagoservice.Models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReporteModel {
    private String idProveedor;
    private Double leche;
    private Integer quincena;
    private Integer mes;
    private Integer anio;
    private Double solidos;
    private Double grasa;
    private Double porSolidos;
    private Double porGrasa;
    private Double varSolidos;
    private Double varGrasa;
    private Double varCantLeche;
    private Double promedioLeche;
    private Integer nroDias;
}
