import { BrowserRouter, Route, Routes } from "react-router-dom";
import './App.css';
import ProveedoresComponent from "./components/ProveedoresComponent.jsx";
import PagosComponent from "./components/PagosComponent.jsx";
import AcopioComponent from "./components/AcopioComponent.jsx";
import ReportesComponent from "./components/ReportesComponent.jsx";
import HomeComponent from "./components/HomeComponent.jsx";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/proveedores" element={<ProveedoresComponent />} />
          <Route path="/pagos" element={<PagosComponent />} />
          <Route path="/acopio" element={<AcopioComponent />} />
          <Route path="/reportes" element={<ReportesComponent />} />
          <Route path="/" element={<HomeComponent />} />          
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

