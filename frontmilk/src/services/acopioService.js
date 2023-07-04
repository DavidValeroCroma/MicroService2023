import axios from 'axios';

const API_URL = 'http://localhost:8080/acopio';

// Obtener todos los acopios
export const obtenerAcopios = async () => {
  try {
    const response = await axios.get(API_URL);
    return response.data;
  } catch (error) {
    console.error('Error al obtener los acopios:', error);
    throw error;
  }
};

// Subir un archivo CSV con los acopios
export const subirArchivoAcopios = async (file) => {
  try {
    const formData = new FormData();
    formData.append('file', file);
    await axios.post(API_URL, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    console.log('Archivo de acopios subido exitosamente');
    // Realizar alguna acción adicional después de subir el archivo
  } catch (error) {
    console.error('Error al subir el archivo de acopios:', error);
    throw error;
  }
};
