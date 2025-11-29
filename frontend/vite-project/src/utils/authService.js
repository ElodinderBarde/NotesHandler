import apiClient from "./api.js";

export const login = async (usernameOrEmail, password) => {
    try {
        console.log("Login attempt for user:", usernameOrEmail);

        const response = await apiClient.post('/auth/login', {
            usernameOrEmail,
            password,
        });

        console.log('login successful', response.data);

        const token = response.data?.token;
        if (!token) {
            throw new Error('Token not found in the response');
        }
        localStorage.setItem('authToken', token);
        console.log('Token stored in localStorage');

        return response.data;

    } catch (error) {
        console.error('Login attempt error:', error);
        throw new Error(error.response?.data?.message || 'Login failed');
    }
};

export const logout = () => {
    console.log('logout, removing token from localStorage');
    localStorage.removeItem('authToken');
};

export const isAuthenticated = () => {
    const token = localStorage.getItem('authToken');
    return !!token;
};
export const registerUser = async (username, email, password) => {
    try {
        const response = await apiClient.post("/auth/register", {
            username,
            email,
            password,
        });

        return response.data;
    } catch (error) {
        console.error("Register error:", error);
        throw new Error(error.response?.data?.error || "Registration failed");
    }
};


export const checkUsernameAvailable = async (username) => {
    if (!username || username.length < 3) return null;

    try {
        const response = await apiClient.get(`/auth/check-username/${username}`);
        return response.data; // { available: true/false, message: "..." }
    } catch (err) {
        console.error("Username check failed:", err);
        return null;
    }
};

export const checkEmailAvailable = async (email) => {
    if (!email || email.length < 5) return null;

    try {
        const response = await apiClient.get(`/auth/check-email/${email}`);
        return response.data; // { available: true/false, message: "..." }
    } catch (err) {
        console.error("Email check failed:", err);
        return null;
    }
};
