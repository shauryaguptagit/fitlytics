# FitLytics: AI-Powered Wellness Platform

FitLytics is a full-stack, secure, multi-user web application designed to track personal fitness. It allows users to log their workouts and receive instant, AI-driven estimates for their calorie burn, all managed through a secure, microservice-based architecture.

---

## 🚀 Features

* **Secure User Accounts:** Full user registration and login system built with **Spring Security** and **JWT (JSON Web Tokens)**.
* **Personalized Dashboard:** Users can only view and manage their own workout logs.
* **AI Calorie Estimation:** A **Python/Flask** microservice hosts a `scikit-learn` regression model to provide real-time calorie burn estimates based on the workout description and duration.
* **Workout Logger:** A clean, responsive frontend built with **JavaScript** and **Tailwind CSS** allows users to log new workouts and view their complete history.

## 🏛️ Architecture (Microservice)

This project uses a microservice architecture to separate the core application from the machine learning model.

1.  **Core Backend (Java/Spring Boot):** A secure REST API that handles all user authentication, authorization, and storage of workout logs in a relational SQL database.
2.  **AI Service (Python/Flask):** A separate, lightweight service. When a user logs a workout, the Java backend calls this API, which uses a trained `scikit-learn` model to calculate the calories burned and returns the estimate.
3.  **Frontend (JavaScript):** A modern, single-page application that provides the user interface for logging in and interacting with the workout dashboard.



## 💻 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot, Spring Security (JWT), Spring Data JPA |
| **AI Service** | Python, Flask, scikit-learn, Pandas |
| **Database** | H2 (for development), PostgreSQL (for production) |
| **Frontend** | Vanilla JavaScript (ES6+), HTML5, Tailwind CSS |
| **Libraries** | `jjwt` (for Java JWT) |

## ⚙️ How to Run Locally

1.  **Clone the repo.**
2.  **Run the Python AI Service (Port 5001):**
    ```bash
    cd calorie_service
    pip install -r requirements.txt
    python app.py
    ```
3.  **Run the Java Backend (Port 8080):**
    ```bash
    cd fitlytics
    ./mvnw spring-boot:run
    ```
4.  **Open the Frontend:**
    * Open the `fitlytics_frontend/index.html` file in your browser.
