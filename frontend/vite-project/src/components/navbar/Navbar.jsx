import "./Navbar.css";
import { useAuth } from "../../context/AuthContext";
import { useState } from "react";
import { Loginbox } from "../loginbox/Loginbox";

export default function Navbar() {
    const { user, logout } = useAuth();
    const [showLogin, setShowLogin] = useState(false);

    return (
        <nav className="navbar">
            <h1>NotesHandler</h1>

            <div>
                {user ? (
                    <>
                        <span>Hallo, {user.username}</span>
                        <button onClick={logout}>Logout</button>
                    </>
                ) : (
                    <button onClick={() => setShowLogin(true)}>Login</button>
                )}
            </div>

            {showLogin && <Loginbox onClose={() => setShowLogin(false)} />}
        </nav>
    );
}
