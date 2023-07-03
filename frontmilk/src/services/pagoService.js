import axios from 'axios';

const API_URL = 'http://localhost:8080/pago';

const crearPago = (reporte, idReporte) => {
  return axios.post(`${API_URL}/upload/${idReporte}`, reporte);
};

const obtenerPagos = () => {
  return axios.get(API_URL);
};

export default {
  crearPago,
  obtenerPagos
};
