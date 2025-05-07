package com.skedlease;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class SimpleCookieJar implements CookieJar {
    private final List<Cookie> cookieStore = new ArrayList<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.addAll(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> validCookies = new ArrayList<>();
        for (Cookie cookie : cookieStore) {
            if (cookie.matches(url)) {
                validCookies.add(cookie);
            }
        }
        return validCookies;
    }

    // Add this method
    public List<Cookie> getAllCookies() {
        return Collections.unmodifiableList(cookieStore);
    }
}