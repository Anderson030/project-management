import { useState } from "react";
import { createTask, completeTask } from "../api";

export default function TaskList({ token, project, reload }) {
  const [title, setTitle] = useState("");

  const addTask = async () => {
    if (!title.trim()) return;
    await createTask(token, project.id, title);
    setTitle("");
    reload();
  };

  return (
    <div style={{ marginLeft: 20 }}>
      <ul>
        {project.tasks?.map(task => (
          <li key={task.id}>
            {task.title} {task.completed && "✅"}
            {!task.completed && (
              <button onClick={() => completeTask(token, task.id).then(reload)}>
                Complete
              </button>
            )}
          </li>
        ))}
      </ul>

      <input
        placeholder="New task"
        value={title}
        onChange={e => setTitle(e.target.value)}
      />
      <button onClick={addTask}>Add Task</button>
    </div>
  );
}
