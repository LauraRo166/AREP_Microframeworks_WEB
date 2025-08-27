package co.edu.escuelaing.httpserver;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents an HTTP request received by the server.
 * This class encapsulates the basic components of an HTTP request, including:
 *     The URI of the request.
 *     The HTTP method.
 *     The request body, if present.
 *     The query parameters parsed from the URI.
 * @author laura.rsanchez
 */
public class HttpRequest {

    /** Full request URI, including path and query string. */
    private final URI requestUri;

    /** HTTP method  */
    private final String method;

    /** Body of the request */
    private final String body;

    /** Map of query parameters extracted from the URI. */
    private final Map<String, String> queryParams = new HashMap<>();

    /**
     * Constructs an HttpRequest with the specified URI, method, and body.
     * During construction, query parameters are automatically parsed
     * from the URI and stored in queryParams
     * 
     * @param reqUri the request URI (must not be {@code null})
     * @param method the HTTP method (e.g., GET, POST)
     * @param body   the request body, or {@code null} if not applicable
     */
    public HttpRequest(URI reqUri, String method, String body) {
        this.requestUri = reqUri;
        this.method = method;
        this.body = body;
        queryParams();  // parse query parameters once
    }

    /**
     * Parses the query parameters from the request URI and stores them in the queryParams map.
     */
    private void queryParams() {
        String query = requestUri.getQuery();
        if (query != null) {
            String[] params = query.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    queryParams.put(keyValue[0], keyValue[1]);
                }
            }
        }
    }

    /**
     * Retrieves the value of a query parameter by its name.
     *
     * @param paramName the name of the parameter to retrieve
     * @return the value of the parameter, or {@code null} if not present
     */
    public String getValue(String paramName) {
        return queryParams.get(paramName);
    }

    /**
     * Returns the HTTP method of the request.
     *
     * @return the HTTP method (e.g., GET, POST)
     */
    public String getMethod() {
        return method;
    }

    /**
     * Returns the body of the request.
     *
     * @return the request body, or null if none
     */
    public String getBody() {
        return body;
    }

    /**
     * Returns the full URI of the request.
     *
     * @return the request URI
     */
    public URI getRequestUri() {
        return requestUri;
    }
}