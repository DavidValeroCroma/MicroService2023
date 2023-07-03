import React, { useState } from 'react';
import acopioService from '../services/acopioService';

const AcopioComponent = () => {
  const [acopios, setAcopios] = useState([]);

  const handleFileUpload = async (event) => {
    const file = event.target.files[0];
    await acopioService.subirArchivo(file);
    obtenerAcopios();
  };

  const obtenerAcopios = async () => {
    const response = await acopioService.obtenerAcopios();
    setAcopios(response.data);
  };

  return (
    <div>
      <h1>Acopios</h1>
      <input type="file" onChange={handleFileUpload} />
      <ul>
        {acopios.map((acopio) => (
          <li key={acopio.id}>{/* Mostrar información del acopio */}</li>
        ))}
      </ul>
    </div>
  );
};

export default AcopioComponent;
