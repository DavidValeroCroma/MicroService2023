import React, { useEffect, useState } from 'react';
import proveedorService from '../services/proveedorService';

const ProveedoresComponent = () => {
  const [proveedores, setProveedores] = useState([]);

  useEffect(() => {
    const obtenerDatos = async () => {
      const response = await proveedorService.obtenerProveedores();
      setProveedores(response.data);
    };

    obtenerDatos();
  }, []);

  return (
    <div>
      <h1>Lista de Proveedores</h1>
      <ul>
        {proveedores.map((proveedor) => (
          <li key={proveedor.id}>{proveedor.nombre}</li>
        ))}
      </ul>
    </div>
  );
};

export default ProveedoresComponent;
