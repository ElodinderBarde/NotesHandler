import { createContext, useState, useEffect, useContext } from "react";
import { loginRequest, logoutRequest } from "../utils/authService";
import axios from "axios";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const token = localStorage.getItem("authToken");
        if (token) {
            axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
            setUser({ token });
        }
    }, []);


    async function login(username, password) {
        const data = await loginRequest(username, password);
        setUser({ username: data.username, token: data.token });
    }

    function logout() {
        logoutRequest();
        setUser(null);
    }

    return (
        <AuthContext.Provider value={{ user, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    return useContext(AuthContext);
}
