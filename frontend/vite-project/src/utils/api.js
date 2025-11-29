import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const apiClient = axios.create({
    baseURL: API_BASE_URL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

apiClient.interceptors.request.use(
    (config) => {
        console.log('Request sent', config.url);

        const token = localStorage.getItem('authToken');

        if (token) {
            config.headers['Authorization'] = `Bearer ${token}`;
            console.log("Token found and set in header");
        } else {
            console.log("No token found");
        }

        return config;
    },
    (error) => {
        console.log('Request error', error);

        if (error.response && error.response.status === 401) {
            console.log('Unauthorized! Redirecting to login...');
            localStorage.removeItem('authToken');
        }
        return Promise.reject(error);
    }
);

export default apiClient;
