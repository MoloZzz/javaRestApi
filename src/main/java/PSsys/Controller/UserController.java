package PSsys.Controller;

import PSsys.Configuration.DatabaseConnection;
import PSsys.DAO.impl.UserDaoImpl;
import PSsys.Model.User;
import PSsys.Service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
@WebServlet("/users/*")
public class UserController extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        UserDaoImpl userDao = new UserDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.userService = new UserService(userDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("We are in do method");
        resp.getWriter().write("Work users do method");
//        String userIdParam = req.getParameter("id");
//        if (userIdParam == null || userIdParam.isEmpty()) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write("Missing or invalid user ID");
//            return;
//        }
//
//        try {
//            int userId = Integer.parseInt(userIdParam);
//            User user = userService.findById(userId);
//            if (user != null) {
//                resp.setContentType("application/json");
//                PrintWriter out = resp.getWriter();
//                out.print("{\"id\": \"" + user.getId() + "\", \"username\": \"" + user.getUsername() + "\", \"email\": \"" + user.getEmail() + "\"}");
//                out.flush();
//            } else {
//                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
//                resp.getWriter().write("User not found");
//            }
//        } catch (NumberFormatException e) {
//            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//            resp.getWriter().write("Invalid user ID format");
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Обробка POST запитів
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Обробка PUT запитів
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Обробка DELETE запитів
    }
}