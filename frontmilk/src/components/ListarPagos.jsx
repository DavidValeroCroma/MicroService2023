import React, { useEffect, useState } from 'react';
import { obtenerPagos } from '../services/pagoService';

const ListarPagos = () => {
  const [pagos, setPagos] = useState([]);

  useEffect(() => {
    obtenerPagos()
      .then((data) => {
        setPagos(data);
      })
      .catch((error) => {
        console.error('Error al obtener los pagos:', error);
      });
  }, []);

  return (
    <div>
      <h2>Listar Pagos</h2>
      <table>
        <thead>
          <tr>
            <th>id Pago</th>
            <th>Pago Leche</th>
            <th>Pago Grasa</th>
            <th>Pago Solidos</th>
            <th>Bonif frec</th>
            <th>Dcto. VarLeche</th>
            <th>Dcto. VarGrasa</th>
            <th>Dcto. VarSol</th>
            <th>Pago Total</th>
            <th>Pago Monto Ret</th>
            <th>Pago Final</th>
          </tr>
        </thead>
        <tbody>
          {pagos.map((pago) => (
            <tr key={pago.id}>
              <td>{pago.id}</td>
              <td>{pago.pagoLeche}</td>
              <td>{pago.pagoGrasa}</td>
              <td>{pago.pagoSolidos}</td>
              <td>{pago.bonoFrec}</td>
              <td>{pago.descVarLeche}</td>
              <td>{pago.descVarGrasa}</td>
              <td>{pago.descVarSol}</td>
              <td>{pago.pagoTotal}</td>
              <td>{pago.montoRet}</td>
              <td>{pago.pagoFinal}</td>
              {/* Agregar más celdas según las propiedades del objeto Pago */}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default ListarPagos;
