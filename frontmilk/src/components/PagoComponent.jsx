import React, { useState } from 'react';
import pagoService from '../services/pagoService';

const PagoComponent = () => {
  const [pagos, setPagos] = useState([]);

  const handleCrearPago = async () => {
    const reporte = {/* Datos del reporte para generar el pago */};
    const idReporte = {/* ID del reporte asociado al pago */};
    await pagoService.crearPago(reporte, idReporte);
    obtenerPagos();
  };

  const obtenerPagos = async () => {
    const response = await pagoService.obtenerPagos();
    setPagos(response.data);
  };

  return (
    <div>
      <h1>Pagos</h1>
      {/* Formulario para ingresar los datos del pago */}
      <button onClick={handleCrearPago}>Crear Pago</button>
      <ul>
        {pagos.map((pago) => (
          <li key={pago.id}>{/* Mostrar información del pago */}</li>
        ))}
      </ul>
    </div>
  );
};

export default PagoComponent;
