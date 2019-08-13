package com.sdpp.backend.rest.util;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import java.util.concurrent.TimeUnit;

public class CustomCacheBuilder {
    private CustomCacheBuilder(){}

    public static Cache<Object,Object> newCache(String name, int pool, int cacheTtl) {
        CacheManager cacheManager = CacheManagerBuilder
                .newCacheManagerBuilder().build();
        cacheManager.init();

        return  cacheManager
                .createCache(name, CacheConfigurationBuilder
                        .newCacheConfigurationBuilder(
                                Object.class, Object.class,
                                ResourcePoolsBuilder.heap(pool))
                        .withExpiry(Expirations.timeToIdleExpiration(new Duration(cacheTtl, TimeUnit.MINUTES))));
    }
}
