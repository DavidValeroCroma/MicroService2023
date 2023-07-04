import React, { useState } from 'react';
import { subirArchivoReportes } from '../services/reporteService';

const SubirReporte = () => {
  const [file, setFile] = useState(null);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      if (!file) {
        console.error('No se ha seleccionado un archivo');
        return;
      }

      await subirArchivoReportes(file);
      // Realizar alguna acción adicional después de subir el archivo

      // Reiniciar el campo del formulario
      setFile(null);
    } catch (error) {
      console.error('Error al subir el archivo de reportes:', error);
    }
  };

  return (
    <div>
      <h2>Subir Reporte</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Seleccionar archivo CSV:</label>
          <input type="file" accept=".csv" onChange={handleFileChange} required />
        </div>
        <button type="submit">Subir</button>
      </form>
    </div>
  );
};

export default SubirReporte;
