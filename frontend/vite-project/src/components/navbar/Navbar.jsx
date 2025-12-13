import "./Navbar.css";
import { useAuth } from "../../context/AuthContext";
import { useState } from "react";
import { Loginbox } from "../loginbox/Loginbox";
import { useNavigate} from "react-router-dom";

export default function Navbar() {
    const { user, logout } = useAuth();
    const [showLogin, setShowLogin] = useState(false);
const navigate = useNavigate();
    return (
        <nav className="navbar">

            <h1 onClick={() => navigate(user ? "/notes" : "/")}>
                NotesHandler
            </h1>

            <div>
                {user ? (
                    <>
                        <span>Hallo, {user.username}</span>
                        <button onClick={logout}>Logout</button>
                    </>
                ) : (
                    <button onClick={(navi) => setShowLogin(true)}>Login</button>
                )}
            </div>

            {showLogin && <Loginbox onClose={() => setShowLogin(false)} />}
        </nav>
    );
}
