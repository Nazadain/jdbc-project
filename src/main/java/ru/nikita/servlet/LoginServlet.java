package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.nikita.dto.UserDto;
import ru.nikita.service.UserService;
import ru.nikita.utils.PathHelper;

import java.io.IOException;

import static ru.nikita.utils.UrlPath.LOGIN;

@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {
    private static final String JSP = "login.jsp";
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        req.getRequestDispatcher(PathHelper.create(JSP)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userService.login(req.getParameter("username"), req.getParameter("password"))
                .ifPresentOrElse(userDto -> onLoginSuccess(userDto, req, resp),
                        ()-> onLoginFail(req, resp));
    }

    @SneakyThrows
    private void onLoginSuccess(UserDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect("/companies");
    }

    @SneakyThrows
    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        resp.sendRedirect(LOGIN + "?error&username=" + req.getParameter("username"));
    }

}
