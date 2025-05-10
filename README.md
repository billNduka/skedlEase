# SkedlEase

SkedlEase is a healthcare appointment scheduling platform designed for students at the University of Lagos. It connects students with doctors at the Unilag Medical Centre, making it easier to book and manage appointments efficiently.

## Features

- **User Registration and Login**
- **Patient Dashboard**
- **Appointment Scheduling**
- **Doctor Listings**
- **Secure Authentication using CSRF Tokens**
- **JavaFX Frontend with Maven**
- **Backend built with Django (see separate repo)**

## Tech Stack

- **Frontend:** Java, JavaFX
- **HTTP Client:** OkHttp
- **Build Tool:** Maven
- **Backend:** Django (separate repository)

## Getting Started

### Prerequisites

- Java 17+
- Maven
- Git

### Running the Application

1. Clone the repository:
   ```bash
   git clone https://github.com/billNduka/skedlEase.git
   cd skedlEase

2. Build the project:

mvn clean install


3. Run the app:

mvn javafx:run



Project Structure

src/
└── main/
    ├── java/
    │   └── com/skedlease/
    │       ├── App.java
    │       ├── LoginController.java
    │       ├── RegisterController.java
    │       └── PrimaryController.java
    └── resources/
        └── com/skedlease/
            ├── login.fxml
            ├── register.fxml
            └── primary.fxml

License

This project is for educational purposes and currently has no official license.
