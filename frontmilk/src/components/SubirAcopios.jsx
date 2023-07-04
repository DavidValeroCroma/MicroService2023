import React, { useState } from 'react';
import { subirArchivoAcopios } from '../services/acopioService';

const SubirAcopio = () => {
  const [archivo, setArchivo] = useState(null);

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    setArchivo(file);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (archivo) {
        subirArchivoAcopios(archivo)
        .then(() => {
          console.log('Archivo de acopio subido exitosamente');
        })
        .catch((error) => {
          console.error('Error al subir el archivo de acopio:', error);
        });
    }
  };

  return (
    <div>
      <h2>Subir Acopio</h2>
      <form onSubmit={handleSubmit}>
        <input type="file" accept=".csv" onChange={handleFileChange} />
        <button type="submit">Subir</button>
      </form>
    </div>
  );
};

export default SubirAcopio;
