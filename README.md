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
    
<img width="974" height="191" alt="image" src="https://github.com/user-attachments/assets/d7f6a2ec-c2d1-45a6-a946-75b0a86e563d" />

---

## ▶️ How to Run

1. Start the server by running:

   ```bash
   mvn exec:java -Dexec.mainClass="co.edu.escuelaing.webexample.WebApplication"
   ```

2. The server will listen on port 35000, you can open your browser and try the index file with:
   ```
   http://localhost:35000/index.html
   ```
   <img width="1917" height="976" alt="image" src="https://github.com/user-attachments/assets/3e253d53-9f0c-4600-a759-37a572268251" />

---

## 🏗️ Architecture

<img width="671" height="261" alt="Architecture" src="[https://github.com/user-attachments/assets/9f82ce77-c5d2-40b7-b565-52ce494bb432](https://github.com/user-attachments/assets/151330ca-c331-4120-9676-37751a65faff)" />

This architecture represents a simple web microframework built in Java.

1. **Client:** The user interacts with the system by sending an HTTP request (for example, through a browser or tool like curl) to port 35000.

2. **REST Application (WebApplication):** This layer defines the REST endpoints (e.g., /hello, /pi, /echo). It acts as the business logic, processing client requests and returning responses.

3. **Microframework Web:** This is the custom HTTP server implementation that powers the REST application. It is composed of:

   - HttpServer: Handles incoming client connections and routes requests to the correct service.

   - HttpRequest: Parses and encapsulates the HTTP request, including method, URI, parameters, and body.

   - HttpResponse: Builds and returns the HTTP response with status codes, headers, and body content.

The client communicates with the REST application through HTTP, while the REST application relies on the microframework components to process requests and generate appropriate responses.

---

## ✅ Evaluation (Tests)

### Unit tests
Unit tests were created using JUnit to validate the server’s functionality:

To run the tests:

   ```bash
   mvn test
   ```

Should look something like this:

<img width="1178" height="346" alt="image" src="https://github.com/user-attachments/assets/992f16f1-5b02-48a8-b94d-262b82b8d05f" />

### Testing of the developed microframework

- Get Pi with:
   ```
   http://localhost:35000/pi
   ```
  
<img width="474" height="208" alt="image" src="https://github.com/user-attachments/assets/43989038-ada3-45d6-9aed-d1ecb017995d" />


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
   <img width="1920" height="977" alt="image" src="https://github.com/user-attachments/assets/ef911f2a-067f-44ca-a647-8edc3102a5b9" />

- When you enter your name in the first option, the server will respond with a greeting with a get method. Like this:

   <img width="1919" height="970" alt="image" src="https://github.com/user-attachments/assets/f9cb82de-6c3c-4e89-8500-68cbefd23ce6" />

- And with the second option the server will return what was written with a post method. Like this:

   <img width="1915" height="981" alt="image" src="https://github.com/user-attachments/assets/30cdd14c-446a-4f20-a2f5-0fa0954ec165" />

---

## 👩‍💻 Author

Laura Daniela Rodríguez
