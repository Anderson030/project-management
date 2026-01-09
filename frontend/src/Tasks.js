import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getTasks, createTask, completeTask, deleteTask } from './api';

const Tasks = () => {
  const { projectId } = useParams();
  const navigate = useNavigate();
  const [tasks, setTasks] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newTaskTitle, setNewTaskTitle] = useState('');

  useEffect(() => {
    loadTasks();
  }, [projectId]);

  const loadTasks = async () => {
    try {
      const response = await getTasks(projectId);
      setTasks(response.data);
    } catch (error) {
      console.error('Error loading tasks:', error);
    }
  };

  const handleCreateTask = async (e) => {
    e.preventDefault();
    if (!newTaskTitle.trim()) return;
    try {
      await createTask({ projectId, title: newTaskTitle });
      setShowModal(false);
      setNewTaskTitle('');
      loadTasks();
    } catch (error) {
      console.error('Error creating task:', error);
    }
  };

  const handleComplete = async (taskId) => {
    try {
      await completeTask(taskId);
      loadTasks();
    } catch (error) {
      console.error('Error completing task:', error);
    }
  };

  const handleDelete = async (taskId) => {
    if (!window.confirm('¿Estás seguro de eliminar esta tarea?')) return;
    try {
      await deleteTask(taskId);
      loadTasks();
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    navigate('/login');
  };

  return (
    <div className="container fade-in">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <button
          onClick={() => navigate('/')}
          style={{
            background: 'none',
            border: 'none',
            color: 'var(--text-secondary)',
            cursor: 'pointer',
            display: 'flex',
            alignItems: 'center',
            gap: '0.5rem',
            fontSize: '1rem'
          }}
        >
          ← Volver al Dashboard
        </button>
        <button className="btn btn-secondary" onClick={handleLogout} style={{ fontSize: '0.875rem' }}>
          Cerrar Sesión
        </button>
      </div>

      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <div>
          <h1 className="title">Tareas del Proyecto</h1>
          <p className="subtitle">Gestiona el progreso de tu equipo</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowModal(true)}>
          + Nueva Tarea
        </button>
      </div>

      <div className="card">
        {tasks.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '3rem', color: 'var(--text-secondary)' }}>
            <p>No hay tareas en este proyecto. ¡Agrega una!</p>
          </div>
        ) : (
          <ul style={{ listStyle: 'none' }}>
            {tasks.map((task) => (
              <li
                key={task.id}
                style={{
                  display: 'flex',
                  justifyContent: 'space-between',
                  alignItems: 'center',
                  padding: '1rem',
                  borderBottom: '1px solid var(--border-color)',
                  transition: 'background-color 0.2s'
                }}
              >
                <div style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
                  <div
                    onClick={() => !task.completed && handleComplete(task.id)}
                    style={{
                      width: '24px',
                      height: '24px',
                      borderRadius: '50%',
                      border: `2px solid ${task.completed ? 'var(--success)' : 'var(--text-secondary)'}`,
                      backgroundColor: task.completed ? 'var(--success)' : 'transparent',
                      cursor: task.completed ? 'default' : 'pointer',
                      display: 'flex',
                      alignItems: 'center',
                      justifyContent: 'center',
                      color: 'white',
                      fontSize: '14px'
                    }}
                  >
                    {task.completed && '✓'}
                  </div>
                  <span style={{
                    textDecoration: task.completed ? 'line-through' : 'none',
                    color: task.completed ? 'var(--text-secondary)' : 'var(--text-primary)',
                    fontSize: '1.1rem'
                  }}>
                    {task.title}
                  </span>
                </div>
                <button
                  onClick={() => handleDelete(task.id)}
                  className="btn btn-danger"
                  style={{ padding: '0.5rem', borderRadius: '0.5rem' }}
                  title="Eliminar tarea"
                >
                  🗑️
                </button>
              </li>
            ))}
          </ul>
        )}
      </div>

      {/* Modal */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-content fade-in">
            <h2 className="title" style={{ fontSize: '1.5rem', marginBottom: '1.5rem' }}>Nueva Tarea</h2>
            <form onSubmit={handleCreateTask}>
              <div className="input-group">
                <label className="label">Título de la Tarea</label>
                <input
                  type="text"
                  className="input"
                  value={newTaskTitle}
                  onChange={(e) => setNewTaskTitle(e.target.value)}
                  placeholder="Ej. Diseñar Wireframes"
                  autoFocus
                />
              </div>
              <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '2rem' }}>
                <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>
                  Cancelar
                </button>
                <button type="submit" className="btn btn-primary">
                  Agregar Tarea
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Tasks;
