import React, { useState } from 'react';
import { crearProveedor } from '../services/proveedorService';

const CrearProveedor = () => {
  const [id, setId] = useState('');
  const [nombre, setNombre] = useState('');
  const [categoria, setCategoria] = useState('');
  const [retencion, setRetencion] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const proveedor = {
        id,
        nombre,
        categoria,
        retencion,
      };

      await crearProveedor(proveedor);
      console.log('Proveedor creado exitosamente');
      // Realizar alguna acción adicional después de crear el proveedor

      // Reiniciar los campos del formulario
      setId('');
      setNombre('');
      setCategoria('');
      setRetencion(false);
    } catch (error) {
      console.error('Error al crear el proveedor:', error);
    }
  };

  return (
    <div>
      <h2>Crear Proveedor</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>ID:</label>
          <input type="text" value={id} onChange={(e) => setId(e.target.value)} required />
        </div>
        <div>
          <label>Nombre:</label>
          <input type="text" value={nombre} onChange={(e) => setNombre(e.target.value)} required />
        </div>
        <div>
          <label>Categoría:</label>
          <input type="text" value={categoria} onChange={(e) => setCategoria(e.target.value)} required />
        </div>
        <div>
          <label>Retención:</label>
          <input type="checkbox" checked={retencion} onChange={(e) => setRetencion(e.target.checked)} />
        </div>
        <button type="submit">Guardar</button>
      </form>
    </div>
  );
};

export default CrearProveedor;
