package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.nikita.dto.CompanyDto;
import ru.nikita.service.CompanyService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/companies")
public class CompanyServlet extends HttpServlet {
    private CompanyService companyService = CompanyService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CompanyDto> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        req.getRequestDispatcher("/WEB-INF/company.jsp").forward(req, resp);
    }
}
