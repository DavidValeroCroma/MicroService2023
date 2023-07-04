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
            <th>ID Acopio</th>
            <th>Nombre Proveedor</th>
            {/* Agregar más columnas según las propiedades del objeto Acopio */}
          </tr>
        </thead>
        <tbody>
          {acopios.map((acopio) => (
            <tr key={acopio.id}>
              <td>{acopio.id}</td>
              <td>{acopio.nombreProveedor}</td>
              {/* Agregar más celdas según las propiedades del objeto Acopio */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ListarAcopios;
