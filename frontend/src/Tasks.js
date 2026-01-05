import { useState } from "react";
import { createTask, completeTask } from "./api";

export default function Tasks({ token, project }) {
  const [title, setTitle] = useState("");

  const addTask = async () => {
    await createTask(token, project.id, title);
    window.location.reload();
  };

  const finish = async (taskId) => {
    await completeTask(token, taskId);
    window.location.reload();
  };

  return (
    <>
      <h3>Tasks for {project.name}</h3>

      <input
        value={title}
        onChange={e => setTitle(e.target.value)}
        placeholder="Task title"
      />
      <button onClick={addTask}>Add</button>

      <ul>
        {project.tasks.map(t => (
          <li key={t.id}>
            {t.title} {t.completed ? "✔" : ""}
            {!t.completed && (
              <button onClick={() => finish(t.id)}>Complete</button>
            )}
          </li>
        ))}
      </ul>
    </>
  );
}
