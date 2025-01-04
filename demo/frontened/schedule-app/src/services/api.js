// frontend/schedule-app/src/services/api.js
import axios from 'axios';

const API_URL = 'http://localhost:8080/api'; // Backend base URL

export const getStudents = async () => {
  try {
    const response = await axios.get(`${API_URL}/students`);
    return response.data; // Returns data from backend
  } catch (error) {
    console.error('Error fetching students:', error);
  }
};

// You can create more methods for other requests like POST, PUT, DELETE
