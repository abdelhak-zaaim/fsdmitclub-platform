package com.fsdm.it.fsdm_it_club.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;

public class UrlAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        var loginType = request.getParameter("loginType");
       if(loginType.contains("member")) {
           response.sendRedirect(request.getContextPath() + "/login?error-memeber=Bad Credentials");
       }else {
           response.sendRedirect(request.getContextPath() + "/login?error=Bad Credentials");
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
