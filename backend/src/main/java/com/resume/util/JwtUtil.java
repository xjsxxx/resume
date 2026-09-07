package com.resume.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类：签发 / 解析 Token
 */
@Component
public class JwtUtil {

    @Value("${resume.jwt.secret}")
    private String secret;

    @Value("${resume.jwt.expire-ms}")
    private long expireMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    /** 签发 token，subject 存用户名 */
    public String createToken(Long userId, String username, String nickname) {
        Date now = new Date();
        Date expire = new Date(now.getTime() + expireMs);
        return Jwts.builder()
                .setSubject(username)
                .claim("uid", userId)
                .claim("nickname", nickname == null ? "" : nickname)
                .setIssuedAt(now)
                .setExpiration(expire)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /** 校验并解析，非法/过期抛异常 */
    public Claims parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }
}
