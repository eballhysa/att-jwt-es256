package com.eltonb.parsing;

import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;

import java.net.URL;
import java.security.Key;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum KeyStore {

    INSTANCE;

    private Map<String, Key> keyMap;

    KeyStore() {
        try {
            keyMap = new HashMap<String, Key>();
            loadKeys();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadKeys() throws Exception {
        loadKeyQA("https://qa-cockpit-aer-de.aerticket.org/common/keys/jwks.json");
    }

    private void loadKeyQA(String url) throws Exception {
        JWKSet publicKeys = JWKSet.load(new URL(url));
        List<JWK> keys = publicKeys.getKeys();
        ECKey ecKey = keys.get(0).toECKey();
        System.out.println(ecKey.getClass().getName());
        String kid = ecKey.getKeyID();
        System.out.println("found kid " + kid);
        PublicKey publicKey = ecKey.toECPublicKey();
        keyMap.put(kid, publicKey);
        System.out.println("loaded " + keys.size() + " keys from remote URL: " + url);
    }

    public Key locate(String kid) {
        if (keyMap.containsKey(kid))
            return keyMap.get(kid);
        throw new IllegalArgumentException("No such key in keystore");
    }
}
