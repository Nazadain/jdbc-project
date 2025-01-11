package ru.nikita.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {
    private static final String UNIQUE_USER = "userId";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null || Arrays.stream(cookies)
                .filter(cookie -> UNIQUE_USER.equals(cookie.getName()))
                .findFirst()
                .isEmpty()) {
        Cookie cookie = new Cookie(UNIQUE_USER, "id1");
        cookie.setPath("/");
        cookie.setMaxAge(3600 * 24 * 7);
        resp.addCookie(cookie);
        }
    }
}
