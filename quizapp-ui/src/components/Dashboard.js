import React, { useState } from "react";
import "../dashboard.css";

export default function Dashboard() {
  const [assessments] = useState([]);

  return (
    <div className="dashboard">
      {/* Header */}
      <header className="dashboard-header">
        <h2>Dashboard</h2>

        <div className="profile-section">
          <img
            src="https://via.placeholder.com/35"
            alt="profile"
            className="profile-img"
          />
          <span>Profile</span>
        </div>
      </header>

      {/* Body */}
      <main className="dashboard-body">
        <h3>Your Assessments</h3>

        {assessments.length === 0 ? (
          <p className="empty-message">
            You haven't created any assessments yet.
          </p>
        ) : (
          <div className="assessments-grid">
            {assessments.map((assessment, index) => (
              <div key={index} className="assessment-card">
                <h4>{assessment.title}</h4>
                <p>{assessment.description}</p>
              </div>
            ))}
          </div>
        )}
      </main>
    </div>
  );
}