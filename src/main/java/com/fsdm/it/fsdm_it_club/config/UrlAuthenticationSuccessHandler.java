package com.fsdm.it.fsdm_it_club.config;

import com.fsdm.it.fsdm_it_club.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import java.io.IOException;


public class UrlAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //Logic
        var authaurities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        if(authaurities.contains(User.Role.MEMBER.name())) {
            // redirect to memeber page
            this.getRedirectStrategy().sendRedirect(request,response,request.getContextPath() + "/");
        }else{
            // redirect to dashboard  page depending on the role // index
            //if user should see admin/index

            this.getRedirectStrategy().sendRedirect(request,response,request.getContextPath() + "/admin");
        }
        //
        super.onAuthenticationSuccess(request, response, authentication);
    }

}
