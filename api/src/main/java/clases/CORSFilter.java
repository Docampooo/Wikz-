package clases;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CORSFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {

        // En lugar de "*", usa el origen de la petición o tu localhost:3000
        String origin = requestContext.getHeaderString("Origin");
        if (origin == null)
            origin = "http://localhost:3000";

        responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);

        responseContext.getHeaders().add("Access-Control-Allow-Headers",
                "origin, content-type, accept, authorization, x-requested-with");

        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");

        responseContext.getHeaders().add("Access-Control-Allow-Methods",
                "GET, POST, PUT, DELETE, OPTIONS, HEAD");

        // Cachear la respuesta preflight para mejorar rendimiento
        responseContext.getHeaders().add("Access-Control-Max-Age", "1209600");
    }
}