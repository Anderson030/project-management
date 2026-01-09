import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Interceptor to add Token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Interceptor for Errors (401/403)
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response && error.response.status === 401) {
      // Clear invalid token and only redirect to login if we are not already there
      localStorage.removeItem('token');
      if (window.location.pathname !== '/login') {
        window.location.href = '/login';
      }
    }
    // 403 should NOT log out, it just means permission denied for that specific resource
    return Promise.reject(error);
    return Promise.reject(error);
  }
);

export const login = async (username, password) => {
  const response = await api.post('/auth/login', { username, password });
  if (response.data.accessToken) {
    localStorage.setItem('token', response.data.accessToken);
  }
  return response.data;
};

export const getProjects = () => api.get('/projects');
export const createProject = (project) => api.post('/projects', project);
export const activateProject = (projectId) => api.patch(`/projects/${projectId}/activate`);

export const getTasks = (projectId) => api.get(`/projects/${projectId}/tasks`);
export const createTask = (task) => api.post(`/projects/${task.projectId}/tasks`, task);
export const completeTask = (taskId) => api.patch(`/tasks/${taskId}/complete`);
// Note: Backend might not have delete, checking... logic usually implies soft delete or just complete
// But we will add the function call for completeness if we add it later
export const deleteTask = (taskId) => api.delete(`/tasks/${taskId}`);

export default api;
