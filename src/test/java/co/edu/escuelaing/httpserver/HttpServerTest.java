package co.edu.escuelaing.httpserver;

import org.junit.jupiter.api.*;
import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

public class HttpServerTest {

    @BeforeEach
    public void setup() {
        HttpServer.staticFiles("/webroot");
    }

    @Test
    public void testStaticFilesSetsCorrectPath() {
        HttpServer.staticFiles("/testdir");
        String response = HttpServer.notFound();
        assertTrue(response.contains("404"), "Debe devolver 404 cuando no existe testdir/404.html");
    }

    @Test
    public void testRegisterGetService() {
        HttpServer.get("/hello", (req, res) -> "Hola Mundo");
        assertNotNull(getPrivateService("getServices", "/hello"));
    }

    @Test
    public void testRegisterPostService() {
        HttpServer.post("/echo", (req, res) -> req.getBody());
        assertNotNull(getPrivateService("postServices", "/echo"));
    }

    @Test
    public void testNotFoundReturnsDefaultMessage() {
        String response = HttpServer.notFound();
        assertTrue(response.startsWith("HTTP/1.1 404 Not Found"));
        assertTrue(response.contains("404 - Not Found"));
    }

    @Test
    public void testNotFoundReturnsCustomHtmlIfExists() throws Exception {
        File file = new File("target/classes/webroot/404.html");
        file.getParentFile().mkdirs();
        Files.writeString(file.toPath(), "<html><body>Error 404</body></html>");

        String response = HttpServer.notFound();
        assertTrue(response.contains("<html>"));
        assertTrue(response.contains("Content-Type: text/html"));

        file.delete();
    }

    @Test
    public void testMultipleGetServices() {
        HttpServer.get("/pi", (req, res) -> String.valueOf(Math.PI));
        HttpServer.get("/hi", (req, res) -> "Hola");
        assertNotNull(getPrivateService("getServices", "/pi"));
        assertNotNull(getPrivateService("getServices", "/hi"));
    }

    @Test
    public void testMultiplePostServices() {
        HttpServer.post("/sum", (req, res) -> "10");
        HttpServer.post("/data", (req, res) -> "ok");
        assertNotNull(getPrivateService("postServices", "/sum"));
        assertNotNull(getPrivateService("postServices", "/data"));
    }

    @Test
    public void testGetServiceExecution() {
        HttpServer.get("/hello", (req, res) -> "Hola Test");
        Service s = getPrivateService("getServices", "/hello");
        assertEquals("Hola Test", s.executeService(new HttpRequest(
                java.net.URI.create("/hello"), "GET", null), new HttpResponse()));
    }

    @Test
    public void testPostServiceExecution() {
        HttpServer.post("/echo", (req, res) -> req.getBody());
        Service s = getPrivateService("postServices", "/echo");
        assertEquals("mensaje", s.executeService(new HttpRequest(
                java.net.URI.create("/echo"), "POST", "mensaje"), new HttpResponse()));
    }

    @SuppressWarnings("unchecked")
    private Service getPrivateService(String fieldName, String path) {
        try {
            var field = HttpServer.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            var map = (java.util.Map<String, Service>) field.get(null);
            return map.get(path);
        } catch (Exception e) {
            fail("Error al acceder al campo privado: " + e.getMessage());
            return null;
        }
    }
}