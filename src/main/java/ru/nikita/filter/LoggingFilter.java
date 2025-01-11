package ru.nikita.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LocalDateTime now = LocalDateTime.now();
        servletRequest.getParameterMap().forEach((k, v) -> {
                    System.out.print("\n[" + formatter.format(now) + "] ");
                    System.out.print(k + ": " + Arrays.toString(v));
                }
        );
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
