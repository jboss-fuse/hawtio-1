package io.hawt.web.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Helper class to perform redirects and forwards which can also be made aware of the Hawtio context path configured for Spring Boot
 */
public class Redirector {

    public static final String HAWTIO_REDIRECT_SCHEME = "hawtio.redirect.scheme";

    private String scheme;
    private String applicationContextPath = "";

    public Redirector() {
        scheme = System.getProperty(HAWTIO_REDIRECT_SCHEME);
    }

    public void doRedirect(HttpServletRequest request, HttpServletResponse response, String path) throws IOException {
        String redirectUrl = "";

        if (scheme == null) {
            scheme = request.getServletContext().getInitParameter("scheme");
        }
        if (scheme != null) {
            redirectUrl = scheme + "://" + request.getServerName() + ":" + request.getServerPort();
        }

        redirectUrl += request.getContextPath() + applicationContextPath + path;

        response.sendRedirect(redirectUrl);
    }

    public void doForward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException {
        request.getRequestDispatcher(applicationContextPath + path).forward(request, response);
    }

    public void setApplicationContextPath(String applicationContextPath) {
        this.applicationContextPath = applicationContextPath;
    }
}
