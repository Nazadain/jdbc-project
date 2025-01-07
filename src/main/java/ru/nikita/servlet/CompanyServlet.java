package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.nikita.service.CompanyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/companies")
public class CompanyServlet extends HttpServlet {
    private CompanyService companyService = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        try (PrintWriter out = resp.getWriter()) {
            out.write("<h2>Компании: </h2>");
            out.write("<ul>");
            companyService.findAll().forEach(company -> {
                out.write("<li><a href='/employee?companyId=%d'>%s</a></li>"
                        .formatted(company.id(), company.name()));
            });
            out.write("</ul>");
        }
    }
}
