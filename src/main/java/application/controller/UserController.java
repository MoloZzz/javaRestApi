package application.controller;

import application.config.DatabaseConnection;
import application.dto.user.CreateUserDto;
import application.model.User;
import application.repository.impl.UserDaoImpl;
import application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/users/*")
public class UserController extends HttpServlet {

    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        UserDaoImpl userDao = new UserDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.userService = new UserService(userDao);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userIdParam = req.getParameter("id");
        String usernameParam = req.getParameter("username");

        try {
            if (userIdParam != null && !userIdParam.isEmpty()) {
                handleGetUserById(userIdParam, resp);
            } else if (usernameParam != null && !usernameParam.isEmpty()) {
                handleGetUserByUsername(usernameParam, resp);
            } else {
                handleGetAllUsers(resp);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid user ID format");
        }
    }

    private void handleGetUserById(String userIdParam, HttpServletResponse resp) throws IOException {
        int userId = Integer.parseInt(userIdParam);
        User user = userService.findById(userId);
        if (user != null) {
            writeJsonResponse(resp, user);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("User not found");
        }
    }

    private void handleGetUserByUsername(String usernameParam, HttpServletResponse resp) throws IOException {
        User user = userService.findByUsername(usernameParam);
        if (user != null) {
            writeJsonResponse(resp, user);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("User not found");
        }
    }

    private void handleGetAllUsers(HttpServletResponse resp) throws IOException {
        List<User> users = userService.findAll();
        writeJsonResponse(resp, users);
    }

    private void writeJsonResponse(HttpServletResponse resp, Object data) throws IOException {
        PrintWriter out = resp.getWriter();
        objectMapper.writeValue(out, data);
        out.flush();
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreateUserDto newUser = objectMapper.readValue(req.getInputStream(), CreateUserDto.class);
            userService.create(newUser);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("User created successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating user: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User updatedUser = objectMapper.readValue(req.getInputStream(), User.class);
            userService.update(updatedUser);
            resp.getWriter().write("User updated successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating user: " + e.getMessage());
        }
    }


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userIdParam = req.getParameter("id");

        if (userIdParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Missing user ID");
            return;
        }

        try {
            int userId = Integer.parseInt(userIdParam);
            userService.delete(userId);
            resp.getWriter().write("User deleted successfully");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid user ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error deleting user: " + e.getMessage());
        }
    }

}
