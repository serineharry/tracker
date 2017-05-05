package com.sv.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    // @Value("${jwt.expires_in}")
    private int EXPIRES_IN = 5;

    // @Value("${jwt.cookie}")
    private String TOKEN_COOKIE = "bb_token";

	// @Value("${app.user_cookie}")
	private String USER_COOKIE = "bb_user";

	@Autowired
	TokenHelper tokenHelper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication ) throws IOException, ServletException {
		clearAuthenticationAttributes(request);
		TrackerUserDetails user = (TrackerUserDetails)authentication.getPrincipal();
		
		String jws = tokenHelper.generateToken( user.getUsername() );

        // Create token auth Cookie
        Cookie authCookie = new Cookie( TOKEN_COOKIE, ( jws ) );
		authCookie.setPath( "/" );
		authCookie.setHttpOnly( true );
		authCookie.setMaxAge( EXPIRES_IN );
		// Create flag Cookie
		Cookie userCookie = new Cookie( USER_COOKIE, ( user.getUsername()) );
		userCookie.setPath( "/" );
		userCookie.setMaxAge( EXPIRES_IN );
		// Add cookie to response
		response.addCookie( authCookie );
		response.addCookie( userCookie );
		// JWT is also in the response
		TokenState userTokenState = new TokenState(jws, EXPIRES_IN, user.getUser());
		SecurityUtils.sendResponse(request, response, HttpServletResponse.SC_OK, userTokenState);
	}
	
    /*@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        User user = userService.findUserByName(authentication.getName());
        SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, user);
    }*/
}
