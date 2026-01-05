import { useState } from "react";
import { createProject } from "../api";
import TaskList from "./TaskList";

export default function ProjectList({ token, projects, reload }) {
  const [name, setName] = useState("");
  const [error, setError] = useState("");

  const handleCreate = async () => {
    if (!name.trim()) return;

    try {
      await createProject(token, name);
      setName("");
      setError("");
      reload(); // 🔑 ESTO ES LO QUE ACTUALIZA LA VISTA
    } catch (e) {
      setError(e.message);
    }
  };

  return (
    <>
      {/* FORMULARIO DE CREACIÓN */}
      <div className="card">
        <input
          placeholder="New project name"
          value={name}
          onChange={e => setName(e.target.value)}
        />
        <button onClick={handleCreate}>Create Project</button>
        {error && <p style={{ color: "red" }}>{error}</p>}
      </div>

      {/* LISTADO */}
      {projects.length === 0 && <p>No projects available</p>}

      <ul>
        {projects.map(project => (
          <li key={project.id}>
            <strong>{project.name}</strong> ({project.status})
            <TaskList
              token={token}
              project={project}
              reload={reload}
            />
          </li>
        ))}
      </ul>
    </>
  );
}
