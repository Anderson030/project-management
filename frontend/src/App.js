import { useEffect, useState } from "react";
import "./App.css";

const API = "http://localhost:8080/api";

function App() {
  const [token, setToken] = useState("");
  const [projects, setProjects] = useState([]);
  const [name, setName] = useState("");
  const [msg, setMsg] = useState("");

  const login = async () => {
    try {
      const res = await fetch(`${API}/auth/login`, { method: "POST" });
      const data = await res.json();
      setToken(data.accessToken);
      setMsg("Logged in");
    } catch {
      setMsg("Login failed");
    }
  };

  const loadProjects = async () => {
    if (!token) return;

    const res = await fetch(`${API}/projects`, {
      headers: { Authorization: `Bearer ${token}` }
    });

    if (res.ok) {
      const data = await res.json();
      setProjects(data);
    }
  };

  const createProject = async () => {
    const res = await fetch(`${API}/projects`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`
      },
      body: JSON.stringify({ name })
    });

    if (res.ok) {
      setName("");
      setMsg("Project created");
      loadProjects();
    } else {
      setMsg("Forbidden: not owner");
    }
  };

  useEffect(() => {
    loadProjects();
  }, [token]);

  return (
    <div className="container">
      <h1>📁 Project Management</h1>

      {!token && <button onClick={login}>Login</button>}

      {token && (
        <>
          <input
            placeholder="New project name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
          <button onClick={createProject}>Create Project</button>

          <p>{msg}</p>

          <h3>Projects</h3>
          {projects.length === 0 && <p>No projects available</p>}

          <ul>
            {projects.map((p) => (
              <li key={p.id}>{p.name} ({p.status})</li>
            ))}
          </ul>
        </>
      )}
    </div>
  );
}

export default App;
