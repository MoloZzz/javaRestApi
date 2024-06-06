package application.controller.auth;

import application.config.DatabaseConnection;
import application.dto.user.CreateUserDto;
import application.repository.impl.UserDaoImpl;
import application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;
    @Override
    public void init() throws ServletException {
        UserDaoImpl userDao = new UserDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.userService = new UserService(userDao);
        this.objectMapper = new ObjectMapper();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        if (username == null || password == null || email == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing username, email or password ");
            return;
        }

        userService.create(new CreateUserDto(username, password, email, role != null ? role : "user"));
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write("User registered successfully");
    }
}
