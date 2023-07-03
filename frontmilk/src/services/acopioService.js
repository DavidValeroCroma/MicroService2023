import axios from 'axios';

const API_URL = 'http://localhost:8080/acopio';

const subirArchivo = (file) => {
  const formData = new FormData();
  formData.append('file', file);

  return axios.post(API_URL, formData);
};

const obtenerAcopios = () => {
  return axios.get(API_URL);
};

const obtenerAcopiosPorProveedor = (idProveedor) => {
  return axios.get(`${API_URL}/${idProveedor}`);
};

export default {
  subirArchivo,
  obtenerAcopios,
  obtenerAcopiosPorProveedor
};
