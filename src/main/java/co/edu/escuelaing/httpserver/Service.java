package co.edu.escuelaing.httpserver;

/**
 * Represents a service or handler that can process an HTTP request
 * and generate a response.
 *
 * @author laura.rsanchez
 */
public interface Service {

    /**
     * Executes the logic of the service for the given request and response.
     *
     * @param req the HttpRequest containing request data.
     * @param res the HttpResponse where headers and status can be configured.
     * @return the body of the response as a String.
     */
    String executeService(HttpRequest req, HttpResponse res);
}
