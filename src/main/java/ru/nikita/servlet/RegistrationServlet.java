package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.nikita.dto.CreateUserDto;
import ru.nikita.entity.Role;
import ru.nikita.exception.ValidationException;
import ru.nikita.service.UserService;
import ru.nikita.utils.PathHelper;

import java.io.IOException;

import static ru.nikita.utils.UrlPath.LOGIN;
import static ru.nikita.utils.UrlPath.REGISTRATION;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private static final String JSP = "registration.jsp";
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.setAttribute("roles", Role.values());
        req.getRequestDispatcher(PathHelper.create(JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        CreateUserDto userDto = CreateUserDto.builder()
                .name(req.getParameter("username"))
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .build();
        try {
            userService.create(userDto);
            resp.sendRedirect(LOGIN);
        } catch (ValidationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
