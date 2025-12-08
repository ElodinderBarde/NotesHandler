import { Navigate } from "react-router-dom";
import { useAuth } from "../../context/AuthContext";
import * as auth from "../../utils/authService.js";

export default function ProtectedRoute({ children }) {
    const { user } = useAuth();
    const token = localStorage.getItem("token");


    if (auth === null) {
        return <div>Loading...</div>;
    }
    return auth.isAuthenticated ? children : <Navigate to="/login" />;


    if (!user && !token) {
        return <Navigate to="/login" replace />;
    }

    return children;
}
