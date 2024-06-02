package application.controller;

import application.config.DatabaseConnection;
import application.repository.PaymentDao;
import application.repository.impl.PaymentDaoImpl;
import application.repository.impl.UserDaoImpl;
import application.service.PaymentService;
import application.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/payments/*")
public class PaymentController extends HttpServlet {
    private PaymentService paymentService;
    @Override
    public void init() throws ServletException {
        PaymentDaoImpl paymentDao = new PaymentDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.paymentService = new PaymentService(paymentDao);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Work payments do method");
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
