import apiClient from "./api";
import axios from "axios";

// --------------------------------------
// LOGIN
// --------------------------------------
export async function loginRequest(usernameOrEmail, password) {
    const response = await apiClient.post("/auth/login", {
        usernameOrEmail,
        password,
    });

    const token = response.data.token;

    if (!token) {
        throw new Error("No token received from backend");
    }

    // Persistenz
    localStorage.setItem("authToken", token);

    // Axios global setzen
    axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

    return response.data;
}

// --------------------------------------
// LOGOUT
// --------------------------------------
export function logoutRequest() {
    localStorage.removeItem("authToken");
    delete axios.defaults.headers.common["Authorization"];
}

// --------------------------------------
// CHECK AUTH
// --------------------------------------
export function isAuthenticated() {
    return !!localStorage.getItem("authToken");
}

// --------------------------------------
// REGISTER
// --------------------------------------
export async function registerUser(username, email, password) {
    const response = await apiClient.post("/auth/register", {
        username,
        email,
        password,
    });

    return response.data;
}

// --------------------------------------
// USERNAME CHECK
// --------------------------------------
export async function checkUsernameAvailable(username) {
    if (!username || username.length < 3) return null;

    try {
        const response = await apiClient.get(`/auth/check-username/${username}`);
        return response.data;
    } catch (error) {
        console.error("Username check failed:", error);
        return null;
    }
}

// --------------------------------------
// EMAIL CHECK
// --------------------------------------
export async function checkEmailAvailable(email) {
    if (!email || email.length < 5) return null;

    try {
        const response = await apiClient.get(`/auth/check-email/${email}`);
        return response.data;
    } catch (error) {
        console.error("Email check failed:", error);
        return null;
    }
}
