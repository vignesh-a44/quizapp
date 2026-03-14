import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import UsePost from '../Hooks/UsePost';
import '../Auth.css';

export default function Login({ onLogin }) {
    const [loginId, setLoginId] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    const postCall = UsePost("http://localhost:8080/user/login");

    const login = async () => {
        const credentials = { email: loginId, password: password };
        try {
            const statusResponse = await postCall(credentials);
            if (verifyLoginResponse(statusResponse)) {
                onLogin(true);
                navigate('/dashboard');
            }
        } catch (err) {
            alert("Invalid credentials");
        }
    };

    const verifyLoginResponse = function(response) {
        try {
            if (response.status !== 'SUCCESS') {
                return false;
            } else {
                
            }
            return true
        } catch (e) {
            console.error('Error while validating user login request: ',e);
            return false;
        }
    }

    return (
        <div className="auth-container">
            <div className="auth-box">
                <h2>Sign In</h2>

                <input type="email" placeholder="Email" onChange={(e) => setLoginId(e.target.value)}/>

                <input type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)}/>

                <button onClick={login}>Login</button>

                <p className="auth-footer">
                    Don’t have an account? <Link to="/signup">Create one</Link>
                </p>
            </div>
        </div>
    );
}
