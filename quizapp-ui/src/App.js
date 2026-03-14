import { BrowserRouter as Router, Route, Routes, Navigate } from "react-router-dom";
import Login from "./components/Login";
import Signup from "./components/Signup";
import Dashboard from "./components/Dashboard";
import { useState } from "react";


function App () {
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    return (
        <Router>
            <Routes>
                <Route path="/" element={<Navigate to="/login" />} />
                <Route path="/login" element={ < Login onLogin={setIsAuthenticated} /> } />
                <Route path="/signup" element={<Signup />} />
                <Route  path="/dashboard" element={ isAuthenticated ? <Dashboard /> : <Navigate to="/login" /> } />
            </Routes>
        </Router>
    )
}

export default App;