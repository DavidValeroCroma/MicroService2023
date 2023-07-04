import axios from 'axios';

const API_URL = 'http://localhost:8080/pago';

// Obtener la lista de pagos
export const obtenerPagos = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error al obtener los pagos:', error);
    throw error;
  }
};
