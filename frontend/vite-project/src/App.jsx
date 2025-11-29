import { BrowserRouter, Routes, Route } from "react-router-dom";
import Navbar from "./components/navbar/Navbar";
import Home from "./pages/home/Home";
//import Dashboard from "./pages/dashboard/Dashboard";
import ProtectedRoute from "./components/protectedRoute/ProtectedRoute.jsx";
import { AuthProvider } from "./context/AuthContext";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Navbar />

                <Routes>
                    <Route path="/" element={<Home />} />

                    <Route
                        path="/dashboard"
                        element={
                            <ProtectedRoute>
                            </ProtectedRoute>
                        }
                    />
                </Routes>
            </BrowserRouter>
        </AuthProvider>
    );
}

export default App;
