package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.nikita.dto.EmployeeDto;
import ru.nikita.service.EmployeeService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/employees")
public class EmployeeServlet extends HttpServlet {
    private final EmployeeService employeeService = EmployeeService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        Long companyId = Long.parseLong(req.getParameter("companyId"));
        List<EmployeeDto> employees = employeeService.findAllByCompanyId(companyId);

        req.setAttribute("employees", employees);
        req.getRequestDispatcher("/WEB-INF/employee.jsp").forward(req, resp);
    }
}
