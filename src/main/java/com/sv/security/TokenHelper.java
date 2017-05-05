package com.sv.security;

import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Component
public class TokenHelper {

    //@Value("${app.name}")
    private String APP_NAME="BB";

    //@Value("${jwt.secret}")
    private String SECRET="ThisIsmy$ecretKe";

    //@Value("${jwt.expires_in}")
    private int EXPIRES_IN=5000;

    private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public String generateToken(String username) {
        String jws = Jwts.builder()
                .setIssuer( APP_NAME )
                .setSubject(username)
                .setIssuedAt(generateCurrentDate())
                .setExpiration(generateExpirationDate())
                .signWith( SIGNATURE_ALGORITHM, SECRET )
                .compact();
        return CryptUtil.encrypt(jws);
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        String decrtyptedToken = CryptUtil.decrypt(token);
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.SECRET)
                    .parseClaimsJws(decrtyptedToken)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private long getCurrentTimeMillis() {
        return new DateTime().getMillis();
    }

    private Date generateCurrentDate() {
        return new Date(getCurrentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(getCurrentTimeMillis() + this.EXPIRES_IN * 1000);
    }
    
    
    
    
    
    public static void main(String[] args) {
		
    	TokenHelper helper = new TokenHelper();
    	String token = helper.generateToken("sree");
    	System.out.println("token :" + token);
    	
    	String enc = CryptUtil.encrypt(token);
    	System.out.println(enc);
    	System.out.println("enc done");
    	
    	String dec = CryptUtil.decrypt(enc);
    	System.out.println(dec);
    	
    	String username = helper.getUsernameFromToken(dec);
    	System.out.println("user :" + username);
    	
	}
    
}
