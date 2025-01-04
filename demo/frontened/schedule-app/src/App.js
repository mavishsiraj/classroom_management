import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';

function App() {
  const [students, setStudents] = useState([]);

  useEffect(() => {
    // Fetch students data from backend API
    axios.get('http://localhost:8080/api/students')
      .then(response => {
        setStudents(response.data); // Store the students data in state
      })
      .catch(error => {
        console.error('Error fetching students:', error);
      });
  }, []); // Empty array means this runs only once when the component is mounted

  return (
    <div className="App">
      <header className="App-header">
        <h1>Student List</h1>
        <ul>
          {students.map(student => (
            <li key={student.id}>{student.name}</li>
          ))}
        </ul>
      </header>
    </div>
  );
}

export default App;
