const API = "http://localhost:8080/api";

async function safeJson(res) {
  const text = await res.text();
  return text ? JSON.parse(text) : null;
}

export async function login() {
  const res = await fetch(`${API}/auth/login`, { method: "POST" });
  if (!res.ok) throw new Error("Login failed");
  return safeJson(res);
}

export async function getProjects(token) {
  const res = await fetch(`${API}/projects`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });

  if (!res.ok) return [];
  return safeJson(res) || [];
}

export async function createProject(token, name) {
  const res = await fetch(`${API}/projects`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify({ name })
  });

  if (!res.ok) {
    if (res.status === 403) {
      throw new Error("Forbidden: not owner");
    }
    throw new Error("Create project failed");
  }

  return safeJson(res);
}

export async function createTask(token, projectId, title) {
  const res = await fetch(`${API}/projects/${projectId}/tasks`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`
    },
    body: JSON.stringify({ title })
  });

  if (!res.ok) throw new Error("Create task failed");
  return safeJson(res);
}

export async function completeTask(token, taskId) {
  const res = await fetch(`${API}/tasks/${taskId}/complete`, {
    method: "PATCH",
    headers: {
      Authorization: `Bearer ${token}`
    }
  });

  if (!res.ok) throw new Error("Complete task failed");
}
