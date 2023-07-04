import React, { useEffect, useState } from 'react';
import { obtenerReportes } from '../services/reporteService';

const ListarReportes = () => {
  const [reportes, setReportes] = useState([]);

  useEffect(() => {
    obtenerReportes()
      .then((data) => {
        console.log(data);
        setReportes(data);
      })
      .catch((error) => {
        console.error('Error al obtener los reportes:', error);
      });
  }, []);

  return (
    <div>
      <h2>Listar Reportes</h2>
      <table>
        <thead>
          <tr>
            <th>id de reporte</th>
            <th>Quincena</th>
            <th>id Proveedor</th>
            <th>Leche total</th>
            <th>Nro Dias</th>
            <th>Leche promedio</th>
            <th>Grasa total</th>
            <th>Solido total</th>
            <th>% grasa</th>
            <th>% solidos</th>
            <th>%Var de leche</th>
            <th>%Var Grasa</th>
            <th>%Var Solidos</th>

            {/* Agregar más columnas según las propiedades del objeto Reporte */}
          </tr>
        </thead>
        <tbody>
          { reportes.map((reporte) => (
            <tr key={reporte.id}>
              <td>{reporte.id}</td>
              <td>{reporte.quincena}</td>
              <td>{reporte.idProveedor}</td>
              <td>{reporte.leche}</td>
              <td>{reporte.nroDias}</td>
              <td>{reporte.promedioLeche}</td>
              <td>{reporte.grasa}</td>
              <td>{reporte.solidos}</td>
              <td>{reporte.porGrasa}</td>
              <td>{reporte.porSolidos}</td>
              <td>{reporte.varCantLeche}</td>
              <td>{reporte.varGrasa}</td>
              <td>{reporte.varSolidos}</td>

            </tr>
          )) }
        </tbody>
      </table>
    </div>
  );
};

export default ListarReportes;
