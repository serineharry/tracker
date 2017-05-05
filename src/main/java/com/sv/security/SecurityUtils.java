package com.sv.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sv.framework.RestErrorResponse;

/**
 * Utility class for Spring Security.
 */
@CrossOrigin
public final class SecurityUtils {


    private static final ObjectMapper mapper = new ObjectMapper();
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";

    private SecurityUtils() {
    }


    /**
     * Get the login of the current user.
     */
    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String userName = null;

        if(authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }

        return userName;
    }


    
    public static void sendError(HttpServletRequest request, HttpServletResponse response, Exception exception, int status, String message) throws IOException {
        
    	String origin = request.getHeader("Origin");
        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);
        PrintWriter writer = response.getWriter();
        
        writer.write(mapper.writeValueAsString(new RestErrorResponse(""+status, message)));
        writer.flush();
        writer.close();
    }


    public static void sendResponse(HttpServletRequest request, HttpServletResponse response, int status, Object object) throws IOException {
        
       
    	String origin = request.getHeader("Origin");
    	
        response.setContentType("application/json;charset=UTF-8");        
        response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, origin);
        
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(object));
        response.setStatus(status);
        writer.flush();
        writer.close();
    }

}