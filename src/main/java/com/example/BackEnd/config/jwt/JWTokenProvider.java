package com.example.BackEnd.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.BackEnd.config.Constants;
import com.example.BackEnd.entity.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTokenProvider {
    private static final Logger log = LoggerFactory.getLogger(JWTokenProvider.class);

    public String generateToken(AuthUserDetails userDetails) {
        Date now = new Date();
        Date expirDate = new Date(now.getTime() + Constants.JWT_EXPIRATION * 1000);

        return JWT.create()
                .withIssuer("self")
                .withIssuedAt(now)
                .withExpiresAt(expirDate)
                .withSubject(userDetails.getUser().getEmail())
                .withClaim("user", toMap(userDetails.getUser()))
                .sign(Algorithm.HMAC512(Constants.JWT_SECRET));

    }

//    private Map<String, Object> toMap(Object obj) {
//        Map<String, Object> map = new HashMap<>();
//        for (Field field : obj.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            try {
//                if (field.getType().equals(UserRole.class)) {
//                    // If the field is of type UserRole, convert it to a map
//                    UserRole userRole = (UserRole) field.get(obj);
//                    if (userRole != null) {
//                        map.put(field.getName(), toMap(userRole));
//                    } else {
//                        map.put(field.getName(), null);
//                    }
//                } else if (Arrays.stream(Constants.ATTRIBUTES_TO_TOKEN).anyMatch(field.getName()::equals)) {
//                    // For other fields, add to map
//                    map.put(field.getName(), field.get(obj));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return map;
//    }
private Map<String, Object> toMap(Object obj) {
    Map<String, Object> map = new HashMap<>();
    for (Field field : obj.getClass().getDeclaredFields()) {
        field.setAccessible(true);
        try {
            if (field.getType().equals(UserRole.class)) {
                // If the field is of type UserRole, convert it to a map
                UserRole userRole = (UserRole) field.get(obj);
                if (userRole != null) {
                    Map<String, Object> userRoleMap = new HashMap<>();
                    userRoleMap.put("userRoleId", userRole.getUserRoleId());
                    userRoleMap.put("roleName", userRole.getUserRoleName());
                    map.put(field.getName(), userRoleMap);
                } else {
                    map.put(field.getName(), null);
                }
            } else if (Arrays.stream(Constants.ATTRIBUTES_TO_TOKEN).anyMatch(field.getName()::equals)) {
                map.put(field.getName(), field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    return map;
}



    public String getUsernameFromJWT(String token) {
        return JWT.decode(token).getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            DecodedJWT token = JWT.require(Algorithm.HMAC512(Constants.JWT_SECRET)).build().verify(authToken);
            Date expireAt = token.getExpiresAt();
            if (expireAt.compareTo(new Date()) > 0) {
                return true;
            }
        } catch (JWTVerificationException ex) {
            log.error("Invalid JWT token");
        }
        return false;
    }
}
