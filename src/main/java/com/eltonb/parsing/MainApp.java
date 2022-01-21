package com.eltonb.parsing;

import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Base64;
import java.util.Map;

public class MainApp {

    private final String agencyCode;
    private final String token;

    private KeyStore keyStore;

    public static void main(String... args) {
        MainApp app = new MainApp();
        app.go();

    }

    public MainApp() {
        System.out.println(KeyStore.INSTANCE.values().length + " key(s) in the store");
        this.agencyCode = "";
        this.token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiIsImtpZCI6ImFhZjlJelpGcGtrMzczZ3hWajVFTWc4MS1LYktXeHpMWWM5MEY3MWJhWm8ifQ.eyJleHAiOjE2NDI3NjQ5NjYuMTk3OTExLCJuYmYiOjE2NDI3NjQ4NDYuMTk3OTcsImlhdCI6MTY0Mjc2NDg0Ni4xOTc5NzQsInN1YiI6ImF8MjU1MTIwIiwiYXVkIjoibXVsdGljaGVjay1zdGFnZS5jcmFuZS5hZXJvIiwiaXNzIjoicWEtY29ja3BpdC1hdGstZGUuYWVydGlja2V0Lm9yZyIsImFnZW5jeU51bWJlciI6IjI1NTEyMCIsImNvbXBhbnlDb2RlIjoiQVRLIiwiam9iSWQiOm51bGwsInJvbGUiOiJBR0VOQ1kiLCJhZ2VudEVtYWlsIjoic3lzdGVtc0BhaXJ0dWVyay5kZSIsImFnZW50TmFtZSI6IiJ9.nRhjEMChXPEyVKLSSnIIyhY2GyRch2_K1-BbZoQU6T7QWASwgHRs4gQwdnbD2TdJs6MoOBrl48_lbTJRSpOq4Q";
    }

    private void go() {
        runValidation();
    }

    private void runValidation() {
        SigningKeyResolver signingKeyResolver = new MySigningKeyResolver();
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKeyResolver(signingKeyResolver)
                .parseClaimsJws(token);

        String userName = claimsJws.getBody().get("agencyNumber", String.class);
        System.out.println("User: " + userName);


    }


}

