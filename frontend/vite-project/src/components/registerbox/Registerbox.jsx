import "../loginbox/Modal.css";
import { useState } from "react";
import { useAuth } from "../../context/AuthContext";
import { registerUser } from "../../utils/authService";

export function Registerbox({ onClose }) {
    const { login } = useAuth(); // optional für Auto-Login nach Registrierung

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError("");
        setLoading(true);

        try {
            const result = await registerUser(username, email, password);

            // Auto-Login nach erfolgreicher Registrierung
            await login(result.username, password);

            onClose();
        } catch (err) {
            setError(err.message || "Registrierung fehlgeschlagen");
        }

        setLoading(false);
    };

    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>

                <button className="modal-close" onClick={onClose}>✕</button>

                <h2>Registrieren</h2>

                {error && <p className="error">{error}</p>}

                <form onSubmit={handleSubmit}>
                    <div className="input-group">
                        <label>Username</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <label>Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>

                    <div className="input-group">
                        <label>Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>

                    <button type="submit" disabled={loading}>
                        {loading ? "Wird erstellt..." : "Registrieren"}
                    </button>
                </form>

            </div>
        </div>
    );
}
