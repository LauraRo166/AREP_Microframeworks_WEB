package co.edu.escuelaing.httpserver;

import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple HTTP server implementation that listens on port 35000.
 * Return 404 responses for resources that are not found.
 *
 * @author laura.rsanchez
 *
 */
public class HttpServer {

    /**
     * Root directory where static resources (HTML, CSS, JS, images, etc.) are
     * located.
     */
    private static final Map<String, Service> getServices = new HashMap<>();
    private static final Map<String, Service> postServices = new HashMap<>();
    private static String staticFilesDir;

    /**
     * Entry point of the HTTP server. Starts listening on port 35000 and
     * handles incoming requests in an infinite loop.
     *
     * @param args command-line arguments (not used).
     * handling requests.
     * @throws URISyntaxException if a malformed URI is found in the request.
     */
    public static void startServer(String[] args) throws URISyntaxException {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            return;
        }

        while (true) {
            try (Socket clientSocket = serverSocket.accept()) {
                System.out.println("New connection...");
                handleClientRequest(clientSocket);
            } catch (IOException e) {
                System.err.println("Accept failed.");
            }
        }
    }

    /**
     * Handles a client request by reading the HTTP request, extracting the
     * method and URI, and delegating the response to
     *
     * @param clientSocket the connected client socket.
     * @throws URISyntaxException if the request URI is malformed.
     * @throws IOException if an I/O error occurs while reading the request or
     * writing the response.
     */
    private static void handleClientRequest(Socket clientSocket) throws URISyntaxException, IOException {
        OutputStream out = clientSocket.getOutputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine = in.readLine();
        String[] request = inputLine.split(" ");
        String method = request[0];
        URI requestUri = new URI(request[1]);
        System.out.println("Method: " + method + " / Path: " + requestUri.getPath());

        int contentLength = 0;
        String line;
        while (!(line = in.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        StringBuilder body = new StringBuilder();
        if ("POST".equalsIgnoreCase(method) && contentLength > 0) {
            char[] buf = new char[contentLength];
            body.append(buf);
        }

        if ("GET".equalsIgnoreCase(method)) {
            handleGet(out, requestUri);
        } else if ("POST".equalsIgnoreCase(method)) {
            handlePost(out, requestUri, body.toString());
        } else {
            out.write(notFound().getBytes());
        }
    }

    /**
     * Serves a static file (HTML, CSS, JS, etc.)
     * directory.
     *
     * @param requestUri requested resource URI.
     * @param out writer for sending headers and text responses.
     * @throws IOException if an error occurs while reading the file or writing
     * the response.
     */
    private static void serveStaticFile(URI requestUri, OutputStream out) throws IOException {
        String path = requestUri.getPath();
        if (path.equals("/")) {
            path = "/index.html";
        }
        File file = new File(staticFilesDir + path);
        if (file.exists() && !file.isDirectory()) {
            String contentType = getContentType(path);
            byte[] fileData = Files.readAllBytes(file.toPath());

            String header = "HTTP/1.1 200 OK\r\n"
                    + "Content-Type: " + contentType + "\r\n"
                    + "Content-Length: " + fileData.length + "\r\n"
                    + "\r\n";

            out.write(header.getBytes());
            out.write(fileData, 0, fileData.length);
            out.flush();
        } else {
            out.write(notFound().getBytes());
            out.flush();
        }
    }

    /**
     * Determines the MIME type of a file based on its extension.
     *
     * @param path file path or URI string.
     * @return MIME type string.
     */
    private static String getContentType(String path) {
        if (path.endsWith(".html")) {
            return "text/html";
        }
        if (path.endsWith(".css")) {
            return "text/css";
        }
        if (path.endsWith(".js")) {
            return "application/javascript";
        }
        if (path.endsWith(".png")) {
            return "image/png";
        }
        if (path.endsWith(".jpg") || path.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (path.endsWith(".gif")) {
            return "image/gif";
        }
        return "application/octet-stream";
    }

    /**
     * Generates a 404 Not Found HTTP response. If a custom 404.html file exists
     * in WEB_ROOT, it will be returned as the response body.
     *
     * @return HTTP 404 response string.
     */
    public static String notFound() {
        File file = new File(staticFilesDir + "/404.html");
        if (file.exists()) {
            try {
                String body = Files.readString(file.toPath());
                return "HTTP/1.1 404 Not Found\r\n"
                        + "Content-Type: text/html\r\n"
                        + "Content-Length: " + body.getBytes().length + "\r\n"
                        + "\r\n"
                        + body;
            } catch (IOException e) {
                return "HTTP/1.1 404 Not Found\r\n"
                        + "Content-Type: text/plain\r\n"
                        + "\r\n"
                        + "404 - Not Found";
            }
        } else {
            return "HTTP/1.1 404 Not Found\r\n"
                    + "Content-Type: text/plain\r\n"
                    + "\r\n"
                    + "404 - Not Found";
        }
    }

    /**
     * Registers a new GET route and associates it with a {@link Service}.
     *
     * @param route the URL path to handle.
     * @param s     the service implementation that will process the request.
     */
    public static void get(String route, Service s) {
        getServices.put(route, s);
    }

    /**
     * Registers a new POST route and associates it with a {@link Service}.
     *
     * @param route the URL path to handle.
     * @param s     the service implementation that will process the request.
     */
    public static void post(String route, Service s) {
        postServices.put(route, s);
    }

    /**
     * Configures the directory where static files will be served from.
     *
     * @param dir the relative path to the static resources directory.
     */
    public static void staticFiles(String dir) {
        staticFilesDir = "target/classes" + dir;
    }

    /**
     * Handles a GET request by either:
     *
     * @param out        the output stream to send the response.
     * @param requestUri the requested URI.
     * @throws IOException if an I/O error occurs while building or sending the response.
     */
    private static void handleGet(OutputStream out, URI requestUri) throws IOException {
        String path = requestUri.getPath();

        if (getServices.containsKey(path)) {
            HttpRequest req = new HttpRequest(requestUri, "GET", null);
            HttpResponse res = new HttpResponse();
            String body = getServices.get(path).executeService(req, res);
            res.setBody(body);
            out.write(res.buildResponse().getBytes());
            out.flush();
        } else {
            serveStaticFile(requestUri, out);
        }
    }

    /**
     * Handles a POST request by either:
     *
     * @param out        the output stream to send the response.
     * @param requestUri the requested URI.
     * @param reqBody    the body of the POST request.
     * @throws IOException if an I/O error occurs while building or sending the response.
     */
    private static void handlePost(OutputStream out, URI requestUri, String reqBody) throws IOException {
        String path = requestUri.getPath();

        if (postServices.containsKey(path)) {
            HttpRequest req = new HttpRequest(requestUri, "POST", reqBody);
            HttpResponse res = new HttpResponse();
            String body = postServices.get(path).executeService(req, res);
            res.setBody(body);
            out.write(res.buildResponse().getBytes());
            out.flush();
        } else {
            out.write(notFound().getBytes());
        }
    }
}