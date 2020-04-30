package com.sdpp.backend.rest.util;


import com.google.common.cache.CacheBuilder;
import com.google.common.cache.Cache;

import java.util.concurrent.TimeUnit;

public class CustomCacheBuilder {
    private CustomCacheBuilder(){}

    public static <K,V> Cache<K, V> newCache(int pool, int cacheTtl) {


        return CacheBuilder.newBuilder()
                .maximumSize(pool)
                .expireAfterAccess(cacheTtl, TimeUnit.SECONDS)
                .build();
    }
}
