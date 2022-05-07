package com.fitee.security.jwt;

import net.jodah.expiringmap.ExpiringMap;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtCache {

    private final ExpiringMap<String, Date> blacklistCache;

    /**
     * Blacklist cache configurator
     */
    public JwtCache() {
        blacklistCache = ExpiringMap.builder().variableExpiration().build();
    }

    /**
     * Adds a given token to the blacklist given an expiration date so that it can
     * be automatically removed once it expires.
     */
    public void addTokenIdToBlacklist(String tokenId, Date expirationDate) {
        if (!blacklistCache.containsKey(tokenId)) {
            long remainingTime = System.currentTimeMillis() - expirationDate.getTime();
            blacklistCache.put(tokenId, expirationDate, remainingTime, TimeUnit.MILLISECONDS);
        }
    }

    /**
     * Checks whether a given token is blacklisted or not.
     */
    public boolean isTokenBlacklisted(String tokenId) {
        return blacklistCache.containsKey(tokenId);
    }
}
