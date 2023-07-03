import React, { useState } from 'react';
import reporteService from '../services/reporteService';

const ReportesComponent = () => {
  const [reportes, setReportes] = useState([]);

  const handleFileUpload = async (event) => {
    const file = event.target.files[0];
    await reporteService.subirArchivo(file);
    obtenerReportes();
  };

  const obtenerReportes = async () => {
    const response = await reporteService.obtenerReportes();
    setReportes(response.data);
  };

  return (
    <div>
      <h1>Reportes</h1>
      <input type="file" onChange={handleFileUpload} />
      <ul>
        {reportes.map((reporte) => (
          <li key={reporte.id}>{/* Mostrar información del reporte */}</li>
        ))}
      </ul>
    </div>
  );
};

export default ReportesComponent;
