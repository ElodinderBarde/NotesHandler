import "./Home.css";
import { useState } from "react";
import { Loginbox } from "../../components/loginbox/Loginbox";
import { Registerbox } from "../../components/registerbox/Registerbox";

function Home() {
    const [showLogin, setShowLogin] = useState(false);
    const [showRegister, setShowRegister] = useState(false);

    return (
        <div className="home-page">
            <main>
                <h1>Welcome to NotesHandler</h1>
                <p>Bitte logge dich ein, um deine Notizen zu bearbeiten</p>

                <button className="login-button" onClick={() => setShowLogin(true)}>
                    Zum Login
                </button>
                <button onClick={() => setShowRegister(true)}>Registrieren</button>

                {showLogin && <Loginbox onClose={() => setShowLogin(false)} />}
                {showRegister && <Registerbox onClose={() => setShowRegister(false)} />}

            </main>
        </div>
    );
}

export default Home;
