import axios from 'axios';

const API_URL = 'http://localhost:8080/reporte';

// Subir un archivo CSV con los reportes
export const subirArchivoReportes = async (file) => {
  try {
    const formData = new FormData();
    formData.append('file', file);
    await axios.post(`${API_URL}/fileUpload`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
    console.log('Archivo de reportes subido exitosamente');
    // Realizar alguna acción adicional después de subir el archivo
  } catch (error) {
    console.error('Error al subir el archivo de reportes:', error);
    throw error;
  }
};

// Obtener la lista de reportes
export const obtenerReportes = async () => {
  try {
    const response = await axios.get(`${API_URL}/fileInformation`);
    return response.data;
  } catch (error) {
    console.error('Error al obtener los reportes:', error);
    throw error;
  }
};
