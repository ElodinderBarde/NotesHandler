import { createContext, useContext, useState } from "react";
import { login as loginService, logout as logoutService } from "../utils/authService";

const AuthContext = createContext();

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(localStorage.getItem("authToken"));

    const login = async (usernameOrEmail, password) => {
        const data = await loginService(usernameOrEmail, password);
        setUser({
            username: data.username,
            email: data.email,
            role: data.role,
        });
        setToken(data.token);
        localStorage.setItem("authToken", data.token);
    };

    const logout = () => {
        logoutService();
        setUser(null);
        setToken(null);
    };

    return (
        <AuthContext.Provider value={{ user, token, login, logout }}>
            {children}
        </AuthContext.Provider>
    );
}

export const useAuth = () => useContext(AuthContext);
