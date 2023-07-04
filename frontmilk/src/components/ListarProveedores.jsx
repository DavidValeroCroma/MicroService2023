import React, { useEffect, useState } from 'react';
import { obtenerProveedores } from '../services/proveedorService';

const ListarProveedores = () => {
  const [proveedores, setProveedores] = useState([]);

  useEffect(() => {
    obtenerProveedores()
      .then((data) => {
        setProveedores(data);
      })
      .catch((error) => {
        console.error('Error al obtener los proveedores:', error);
      });
  }, []);

  return (
    <div>
      <h2>Listar Proveedores</h2>
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Categoría</th>
            <th>Retención</th>
          </tr>
        </thead>
        <tbody>
          {proveedores.map((proveedor) => (
            <tr key={proveedor.id}>
              <td>{proveedor.id}</td>
              <td>{proveedor.nombre}</td>
              <td>{proveedor.categoria}</td>
              <td>{proveedor.retencion ? 'Sí' : 'No'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ListarProveedores;
