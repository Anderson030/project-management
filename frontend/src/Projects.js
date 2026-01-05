import { useEffect, useState } from "react";
import { getProjects, createProject } from "./api";

export default function Projects({ token, onSelect }) {
  const [projects, setProjects] = useState([]);
  const [name, setName] = useState("");

  useEffect(() => {
    getProjects(token).then(setProjects);
  }, [token]);

  const create = async () => {
    await createProject(token, name);
    const updated = await getProjects(token);
    setProjects(updated);
    setName("");
  };

  return (
    <>
      <h2>Projects</h2>

      <input
        value={name}
        onChange={e => setName(e.target.value)}
        placeholder="Project name"
      />
      <button onClick={create}>Create</button>

      <ul>
        {projects.map(p => (
          <li key={p.id} onClick={() => onSelect(p)}>
            {p.name} ({p.status})
          </li>
        ))}
      </ul>
    </>
  );
}
