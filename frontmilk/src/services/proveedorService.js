import axios from 'axios';

const API_URL = 'http://localhost:8080/proveedor';

const obtenerProveedores = () => {
  return axios.get(API_URL);
};

const obtenerProveedor = (idProveedor) => {
  return axios.get(`${API_URL}/${idProveedor}`);
};

const guardarProveedor = (proveedor) => {
  return axios.post(`${API_URL}/fileUpload`, proveedor);
};

export default {
  obtenerProveedores,
  obtenerProveedor,
  guardarProveedor
};
