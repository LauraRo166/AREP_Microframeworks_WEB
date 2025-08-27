# Microframework web

This project is a lightweight Java microframework for building web applications.
It allows developers to:

Register simple GET and POST routes.

Serve static resources (HTML, CSS, JS, images).

Return dynamic responses using custom services.

The framework is implemented from scratch without external libraries, 
showcasing how an HTTP server and basic routing can be built in pure Java.

---

## 📦 Installation

1. Clone this repository:

   ```bash
   git clone https://github.com/LauraRo166/AREP_Microframeworks_WEB.git
   cd AREP_Microframeworks_WEB
   ```
   
2. Make sure you have Java 17+ and Maven installed:

   ```bash
   java -version
   mvn -version
   ```
3. Build the project:

   ```bash
   mvn clean package
   ```
4. Should look something like this:
    
{imagen build}

---

## ▶️ How to Run

1. Start the server by running:

   ```bash
   mvn exec:java -Dexec.mainClass="co.edu.escuelaing.httpserver.HttpServer"
   ```

2. The server will listen on port 35000, you can open your browser and try the index file with:
   ```
   http://localhost:35000/index.html
   ```
   <img width="1920" height="979" alt="image" src="https://github.com/user-attachments/assets/945f3de4-16e9-4f43-8b86-cf8d7db51f3e" />
---

## 🏗️ Architecture

<img width="671" height="261" alt="Architecture" src="https://github.com/user-attachments/assets/9f82ce77-c5d2-40b7-b565-52ce494bb432" />

---

## ✅ Evaluation (Tests)

### Unit tests
Unit tests were created using JUnit to validate the server’s functionality:

To run the tests:

   ```bash
   mvn test
   ```

Should look something like this:

{imagen tests }

### Testing of the developed microframework

- Get Pi with:
   ```
   http://localhost:35000/pi
   ```
  
{Imagen de /pi}

- You can try other resources stored in the resources folder, for example:
   ```
   perrito.jpg
   ```
  or
   ```
   gatito.jpg
   ```

  With:
   ```
   http://localhost:35000/perrito.jpg
   ```
   <img width="1920" height="981" alt="image" src="https://github.com/user-attachments/assets/da7748f4-36cb-4f3d-9e2d-21cd830b1ee0" />

- When you enter your name in the first option, the server will respond with a greeting with a get method. Like this:

 <img width="1920" height="914" alt="image" src="https://github.com/user-attachments/assets/ec1a5cb0-011b-481f-b2ac-fdfa7d67963d" />

- And with the second option the server will return what was written with a post method. Like this:

   <img width="1920" height="909" alt="image" src="https://github.com/user-attachments/assets/94533ed2-74b1-4727-8180-88e595b0aca2" />

---

## 👩‍💻 Author

Laura Daniela Rodríguez
