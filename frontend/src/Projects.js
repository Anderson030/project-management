import React, { useEffect, useState } from 'react';
import { getProjects, createProject, activateProject } from './api';
import { useNavigate } from 'react-router-dom';

const Projects = () => {
  const [projects, setProjects] = useState([]);
  const [showModal, setShowModal] = useState(false);
  const [newProjectName, setNewProjectName] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    loadProjects();
  }, []);

  const loadProjects = async () => {
    try {
      const response = await getProjects();
      setProjects(response.data);
    } catch (error) {
      console.error('Error loading projects:', error);
    }
  };

  const handleCreateProject = async (e) => {
    e.preventDefault();
    if (!newProjectName.trim()) return;
    try {
      await createProject({ name: newProjectName }); // Basic creation
      setShowModal(false);
      setNewProjectName('');
      loadProjects();
    } catch (error) {
      console.error('Error creating project:', error);
    }
  };

  const handleActivate = async (e, projectId) => {
    e.stopPropagation(); // Prevent card click
    try {
      await activateProject(projectId);
      loadProjects();
    } catch (error) {
      alert('Error activating project: ' + (error.response?.data?.message || error.message));
    }
  };

  return (
    <div className="container fade-in">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '2rem' }}>
        <div>
          <h1 className="title">Proyectos</h1>
          <p className="subtitle">Gestiona tus proyectos y equipos</p>
        </div>
        <button className="btn btn-primary" onClick={() => setShowModal(true)}>
          + Nuevo Proyecto
        </button>
      </div>

      <div style={{ display: 'grid', gridTemplateColumns: 'repeat(auto-fill, minmax(300px, 1fr))', gap: '2rem' }}>
        {projects.map((project) => (
          <div
            key={project.id}
            className="card"
            style={{ cursor: 'pointer', position: 'relative' }}
            onClick={() => navigate(`/projects/${project.id}`)}
          >
            <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'flex-start', marginBottom: '1rem' }}>
              <h3 style={{ fontSize: '1.25rem', fontWeight: '600', color: 'var(--text-primary)' }}>
                {project.name}
              </h3>
              <span style={{
                padding: '0.25rem 0.75rem',
                borderRadius: '999px',
                fontSize: '0.75rem',
                fontWeight: 'bold',
                backgroundColor: project.status === 'ACTIVE' ? 'rgba(34, 197, 94, 0.2)' : 'rgba(245, 158, 11, 0.2)',
                color: project.status === 'ACTIVE' ? 'var(--success)' : 'var(--warning)',
                border: `1px solid ${project.status === 'ACTIVE' ? 'var(--success)' : 'var(--warning)'}`
              }}>
                {project.status}
              </span>
            </div>

            <div style={{ marginTop: 'auto', display: 'flex', justifyContent: 'flex-end' }}>
              {project.status === 'DRAFT' && (
                <button
                  className="btn btn-secondary"
                  style={{ fontSize: '0.875rem', padding: '0.5rem 1rem' }}
                  onClick={(e) => handleActivate(e, project.id)}
                >
                  Activar Proyecto
                </button>
              )}
            </div>
          </div>
        ))}

        {projects.length === 0 && (
          <div style={{ gridColumn: '1 / -1', textAlign: 'center', padding: '4rem', color: 'var(--text-secondary)' }}>
            <p>No hay proyectos aún. ¡Crea el primero!</p>
          </div>
        )}
      </div>

      {/* Modal */}
      {showModal && (
        <div className="modal-overlay">
          <div className="modal-content fade-in">
            <h2 className="title" style={{ fontSize: '1.5rem', marginBottom: '1.5rem' }}>Nuevo Proyecto</h2>
            <form onSubmit={handleCreateProject}>
              <div className="input-group">
                <label className="label">Nombre del Proyecto</label>
                <input
                  type="text"
                  className="input"
                  value={newProjectName}
                  onChange={(e) => setNewProjectName(e.target.value)}
                  placeholder="Ej. Rediseño Web"
                  autoFocus
                />
              </div>
              <div style={{ display: 'flex', justifyContent: 'flex-end', gap: '1rem', marginTop: '2rem' }}>
                <button type="button" className="btn btn-secondary" onClick={() => setShowModal(false)}>
                  Cancelar
                </button>
                <button type="submit" className="btn btn-primary">
                  Crear Proyecto
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default Projects;
