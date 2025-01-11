package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.nikita.dto.CompanyDto;
import ru.nikita.service.CompanyService;
import ru.nikita.utils.PathHelper;

import java.io.IOException;
import java.util.List;

@WebServlet("/companies")
public class CompanyServlet extends HttpServlet {

    private final CompanyService companyService = CompanyService.getInstance();
    private static final String JSP = "company.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CompanyDto> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        req.getRequestDispatcher(PathHelper.create(JSP)).forward(req, resp);
    }
}
