import React, { useEffect, useState } from 'react';
import { obtenerAcopios } from '../services/acopioService';

const ListarAcopios = () => {
  const [acopios, setAcopios] = useState([]);

  useEffect(() => {
    obtenerAcopios()
      .then((data) => {
        setAcopios(data);
      })
      .catch((error) => {
        console.error('Error al obtener los acopios:', error);
      });
  }, []);

  return (
    <div>
      <h2>Listar Acopios</h2>
      <table>
        <thead>
          <tr>
            <th>Id Acopio</th>
            <th>id Proveedor</th>
            <th>fecha</th>
            <th>turno</th>
            <th>leche</th>
          </tr>
        </thead>
        <tbody>
          {acopios.map((acopio) => (
            <tr key={acopio.id}>
              <td>{acopio.id}</td>
              <td>{acopio.idProveedor}</td>
              <td>{acopio.fecha}</td>
              <td>{acopio.turno}</td>
              <td>{acopio.leche}</td>
              {/* Agregar más celdas según las propiedades del objeto Acopio */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ListarAcopios;
