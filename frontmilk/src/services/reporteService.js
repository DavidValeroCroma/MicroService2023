import axios from 'axios';

const API_URL = 'http://localhost:8080/reporte';

const subirArchivo = (file) => {
  const formData = new FormData();
  formData.append('file', file);

  return axios.post(`${API_URL}/fileUpload`, formData);
};

const obtenerReportes = () => {
  return axios.get(`${API_URL}/fileInformation`);
};

export default {
  subirArchivo,
  obtenerReportes
};
