import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import '../Auth.css';

export default function Signup() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const signup = () => {
        console.log({ email, password });
        navigate('/login');
    };

    return (
        <div className="auth-container">
            <div className="auth-box">
                <h2>Create Account</h2>

                <input
                    type="email"
                    placeholder="Email"
                    onChange={(e) => setEmail(e.target.value)}
                />

                <input
                    type="password"
                    placeholder="Password"
                    onChange={(e) => setPassword(e.target.value)}
                />

                <button onClick={signup}>Sign Up</button>

                <p className="auth-footer">
                    Already have an account? <Link to="/login">Login</Link>
                </p>
            </div>
        </div>
    );
}