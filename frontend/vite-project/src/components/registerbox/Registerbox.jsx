import "../loginbox/Modal.css";
import { useState, useEffect } from "react";
import { registerUser, checkUsernameAvailable, checkEmailAvailable } from "../../utils/authService.js";

export function Registerbox({ onClose }) {

    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [usernameStatus, setUsernameStatus] = useState(null);
    const [errorMessage, setErrorMessage] = useState("");
    const [emailStatus, setEmailStatus] = useState(null);


    useEffect(() => {
        const delayDebounce = setTimeout(async () => {
            if (username.length >= 3) {
                const result = await checkUsernameAvailable(username);
                setUsernameStatus(result?.available);
            } else {
                setUsernameStatus(null);
            }
        }, 400);

        return () => clearTimeout(delayDebounce);
    }, [username]);



    useEffect(() => {
        const delay = setTimeout(async () => {
            if (email.length >= 5) {
                const result = await checkEmailAvailable(email);
                setEmailStatus(result?.available);
            } else {
                setEmailStatus(null);
            }
        }, 400);

        return () => clearTimeout(delay);
    }, [email]);


    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const result = await registerUser(username, email, password);
            console.log("Registered:", result);
            onClose();
        } catch (error) {
            setErrorMessage(error.message || "Registrierung fehlgeschlagen.");
        }
    };

    const disableSubmit =
        usernameStatus === false ||
        emailStatus === false ||
        !username ||
        !email ||
        !password;


    return (
        <div className="modal-overlay" onClick={onClose}>
            <div className="modal-content" onClick={(e) => e.stopPropagation()}>
                <button className="modal-close" onClick={onClose}>✕</button>

                <h2>Registrieren</h2>

                {errorMessage && <p className="error">{errorMessage}</p>}

                <form onSubmit={handleRegister}>

                    {/* Username */}
                    <label>Username</label>
                    <br/>
                    <input
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />

                    {usernameStatus === true && (
                        <p className="success">✔ Username ist verfügbar</p>
                    )}
                    {usernameStatus === false && (
                        <p className="error">✖ Username ist bereits vergeben</p>
                    )}
                    <br />
                    <br />
                    {/* Email */}
                    <label>Email</label>
                    <br />
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    {emailStatus === true && (
                        <p className="success">✔ Email ist verfügbar</p>
                    )}
                    {emailStatus === false && (
                        <p className="error">✖ Email ist bereits registriert</p>
                    )}
                    <br />
                    <br />

                    {/* Password */}
                    <label>Password</label>
                    <br />
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />

                    <button type="submit" disabled={disableSubmit}>
                        Registrieren
                    </button>
                </form>
            </div>
        </div>
    );
}