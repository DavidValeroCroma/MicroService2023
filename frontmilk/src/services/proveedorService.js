import axios from 'axios';

const API_URL = 'http://localhost:8080/proveedor';

// Obtener todos los proveedores
export const obtenerProveedores = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error al obtener los proveedores:', error);
    throw error;
  }
};

// Crear un nuevo proveedor
export const crearProveedor = async (proveedor) => {
  try {
    const response = await axios.post(API_URL + '/fileUpload', proveedor);
    return response.data;
  } catch (error) {
    console.error('Error al crear el proveedor:', error);
    throw error;
  }
};
