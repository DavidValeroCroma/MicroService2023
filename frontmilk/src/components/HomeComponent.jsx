import React from 'react';
import { Link } from 'react-router-dom';

const HomeComponent = () => {
  return (
    <div>
      <h1>Bienvenido a la página de inicio</h1>
      <p>Elige una opción:</p>
      <ul>
        <li>
          <Link to="/crearproveedores">Crear Proveedor</Link>
        </li>
        <li>
          <Link to="/listarproveedores">Listar Proveedores</Link>
        </li>
        <li>
          <Link to="/subiracopio">Subir Acopio</Link>
        </li>
        <li>
          <Link to="/listaracopio">Listar Acopios</Link>
        </li>
        <li>
          <Link to="/subirreportes">Subir Reporte</Link>
        </li>
        <li>
          <Link to="/listarreportes">Listar Reportes</Link>
        </li>
        <li>
          <Link to="/listarpagos">Listar Pagos</Link>
        </li>
      </ul>
    </div>
  );
};

export default HomeComponent;
