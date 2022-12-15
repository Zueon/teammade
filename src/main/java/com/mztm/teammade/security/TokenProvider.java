package com.mztm.teammade.security;

import com.mztm.teammade.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private static final String SECRET_KEY = "asdfvgdrthdegw43refdsrgtbyjre4y53trfsdv";

    public String create(Member member) {
        Date expDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setSubject(member.getEmail())
                .setIssuer("JUEON^^v")
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .compact();
    }

    public String validateAndGetMemberEmail(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();

        return  claims.getSubject();
    }
}