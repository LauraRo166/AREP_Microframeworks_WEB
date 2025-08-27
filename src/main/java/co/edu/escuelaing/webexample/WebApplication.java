package co.edu.escuelaing.webexample;

import static co.edu.escuelaing.httpserver.HttpServer.*;

/**
 * Entry point for the web application using the custom HTTP server.
 * This class demonstrates how to configure static file serving,
 * register routes for GET and POST requests, and start the server.
 *
 * @author laura.rsanchez
 */
public class WebApplication {

    /**
     * Main entry point. Configures static resources, registers routes,
     * and starts the HTTP server on port 35000.
     *
     * @param args command-line arguments.
     * @throws Exception if an error occurs while starting the server.
     */
    public static void main(String[] args) throws Exception {
        staticFiles("/webroot");
        get("/hello", (req, resp) -> "Hola " + req.getValue("name"));
        get("/pi", (req, resp) -> String.valueOf(Math.PI));
        post("/echo", (req, res) -> req.getBody());

        startServer(args);
    }
}
