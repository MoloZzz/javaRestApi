package application.controller.auth;
import application.config.DatabaseConnection;
import application.model.User;
import application.repository.impl.UserDaoImpl;
import application.service.AuthService;
import application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    private AuthService authService;
    private UserService userService;
    private ObjectMapper objectMapper;
    @Override
    public void init() throws ServletException {
        UserDaoImpl userDao = new UserDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.userService = new UserService(userDao);
        this.objectMapper = new ObjectMapper();
        this.authService  = new AuthService();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing username or password");
            return;
        }

        User user = userService.authenticateUser(username, password);
        if (user != null) {
            try {
                String token = authService.login(username, password);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.getWriter().write(token);
            } catch (Exception e) {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                resp.getWriter().write("Error generating token");
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("Invalid credentials");
        }
    }
}
