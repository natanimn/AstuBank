package et.edu.astu.core.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
    @Value("${jwt.secret.key}")
    private String KEY;

    @Value("${jwt.expiration.seconds}")
    private Integer EXP_SECONDS;

    public String generateEmployeeJwt(Object id){
        return Jwts.builder()
                .subject(id.toString())
                .issuedAt(new Date())
                .signWith(getKey())
                .expiration(new Date(new Date().getTime() + EXP_SECONDS * 1000L ))
                .compact();
    }

    public String generateUserJwt(Long accountNumber, Long userId){
        return Jwts.builder()
                .subject(String.valueOf(accountNumber))
                .claim("userId", String.valueOf(userId))
                .issuedAt(new Date())
                .signWith(getKey())
                .compact();
    }


    public boolean validate(String token){
        try{
            Jwts.parser()
                    .verifyWith(getKey())
                    .build()
                    .parseSignedClaims(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private Claims getClaims(String token){
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getSubject(String token){
        return getClaims(token).getSubject();
    }

    public String getUserId(String token){
        return getClaims(token).get("userId", String.class);
    }

    private SecretKey getKey(){
        return Keys.hmacShaKeyFor(KEY.getBytes(StandardCharsets.UTF_8));
    }
}
