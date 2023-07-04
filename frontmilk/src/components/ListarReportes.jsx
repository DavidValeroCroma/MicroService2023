import React, { useEffect, useState } from 'react';
import { obtenerReportes } from '../services/reporteService';

const ListarReportes = () => {
  const [reportes, setReportes] = useState([]);

  useEffect(() => {
    obtenerReportes()
      .then((data) => {
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
            <th>Quincena</th>
            <th>Código proveedor</th>
            {/* Agregar más columnas según las propiedades del objeto Reporte */}
          </tr>
        </thead>
        <tbody>
          {reportes.map((reporte) => (
            <tr key={reporte.id}>
              <td>{reporte.quincena}</td>
              <td>{reporte.codigoProveedor}</td>
              {/* Agregar más celdas según las propiedades del objeto Reporte */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ListarReportes;
