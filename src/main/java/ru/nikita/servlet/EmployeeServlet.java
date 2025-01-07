package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.nikita.service.EmployeeService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeService employeeService = EmployeeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        Long companyId = Long.parseLong(req.getParameter("companyId"));

        try (PrintWriter out = resp.getWriter()) {
            out.write("<h2>Сотрудники: </h2>");
            out.write("<ul>");
            employeeService.findAllByCompanyId(companyId).forEach(employee -> {
                out.write("<li>%s %d</li>"
                        .formatted(employee.name(), employee.age()));
            });
            out.write("</ul>");
        }
    }
}
