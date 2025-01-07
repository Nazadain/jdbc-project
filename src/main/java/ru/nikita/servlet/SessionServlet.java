package ru.nikita.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.nikita.dto.UserDto;

import java.io.IOException;

@WebServlet("/session")
public class SessionServlet extends HttpServlet {

    private static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute(USER);

        if(user == null) {
            user = UserDto.builder()
                    .id(1L)
                    .username("nazadain")
                    .password("123")
                    .build();
        }
        session.setAttribute(USER, user);
    }
}
