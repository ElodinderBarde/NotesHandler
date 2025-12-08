import axios from "axios";

const API_BASE_URL = "http://localhost:8080/api";

// AXIOS INSTANCE
const api = axios.create({
    baseURL: API_BASE_URL,
    timeout: 5000,
    headers: { "Content-Type": "application/json" }
});

// REQUEST INTERCEPTOR
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("authToken");
        console.log("Request sent", config.url);

        if (token) {
            config.headers["Authorization"] = `Bearer ${token}`;
            console.log("Token found and set in header");
        } else {
            console.log("No token found");
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// RESPONSE INTERCEPTOR optional
api.interceptors.response.use(
    (res) => res,
    (err) => {
        if (err.response && err.response.status === 401) {
            localStorage.removeItem("authToken");
        }
        return Promise.reject(err);
    }
);

export default api;
