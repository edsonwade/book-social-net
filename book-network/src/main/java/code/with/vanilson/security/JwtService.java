package code.with.vanilson.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@SuppressWarnings("all")
public class JwtService {

    @Value("${spring.application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.application.security.jwt.expiration-time}")
    private long jwtExpirationInMs;

    /**
     * Extracts the username from the JWT token.
     *
     * @param token the JWT token to extract the username from
     * @return the username extracted from the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT token using the provided claims resolver function.
     *
     * @param token          the JWT token to extract the claim from
     * @param claimsResolver the function to resolve the claim from the token's claims
     * @param <T>            the type of the claim to be extracted
     * @return the extracted claim
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);

    }

    /**
     * Extracts all claims from the JWT token.
     *
     * @param token the JWT token to extract the claims from
     * @return the claims extracted from the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    /**
     * Generates a token with the user details provided by the user details service implementation.
     *
     * @param userDetails the user details to be included in the token
     * @return the generated token
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);

    }

    /**
     * Generates a token with the provided claims, user details, and expiration time in milliseconds.
     *
     * @param claims      additional claims to be included in the token
     * @param userDetails the user details to be included in the token
     * @return the generated token
     */
    private String generateToken(Map<String, Object> claims, UserDetails userDetails) {
        return buildToken(claims, userDetails, jwtExpirationInMs);
    }

    /**
     * Builds the token with the provided claims, user details, and expiration time in milliseconds.
     *
     * @param extraClaims   additional claims to be included in the token
     * @param userDetails   the user details to be included in the token
     * @param jwtExpiration the expiration time in milliseconds for the token
     * @return the generated token
     */
    private String buildToken(Map<String, Object> extraClaims,
                              UserDetails userDetails,
                              long jwtExpiration) {
        var authorities = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.
                builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("authorities", authorities)
                .signWith(getSignInKey())
                .compact();
    }

    /**
     * Validates the token by checking if the username matches and if the token is not expired.
     *
     * @param token       the JWT token to validate
     * @param userDetails the user details to match against the token
     * @return true if the token is valid, false otherwise
     */
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if the token is expired.
     *
     * @param token the JWT token to check
     * @return true if the token is expired, false otherwise
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the token.
     *
     * @param token the JWT token to extract the expiration date from
     * @return the expiration date of the token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Decodes the token and returns the signing key used to verify the token's signature.
     *
     * @return the signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
