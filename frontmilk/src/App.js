import React from 'react';
import { BrowserRouter, Route, Routes } from "react-router-dom";
import './App.css';
import CrearProveedor from './components/CrearProveedor';
import ListarProveedores from './components/ListarProveedores';
import SubirReporte from './components/SubirReporte';
import ListarReportes from './components/ListarReportes';
import ListarPagos from './components/ListarPagos';
import SubirAcopios from './components/SubirAcopios';
import ListarAcopios from './components/ListarAcopios';
import HomeComponent from './components/HomeComponent';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/crearproveedores" element={<CrearProveedor />} />
          <Route path="/listarproveedores" element={<ListarProveedores />} />
          <Route path="/listarpagos" element={<ListarPagos />} />
          <Route path="/subiracopio" element={<SubirAcopios />} />
          <Route path="/listaracopio" element={<ListarAcopios />} />
          <Route path="/subirreportes" element={<SubirReporte />} />          
          <Route path="/listarreportes" element={<ListarReportes />} />
          <Route path="/" element={<HomeComponent />} />          
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

