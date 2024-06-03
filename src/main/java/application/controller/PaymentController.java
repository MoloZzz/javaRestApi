package application.controller;

import application.config.DatabaseConnection;
import application.dto.payment.CreatePaymentDto;
import application.model.Payment;
import application.repository.impl.PaymentDaoImpl;
import application.service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/payments/*")
public class PaymentController extends HttpServlet {
    private PaymentService paymentService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        PaymentDaoImpl paymentDao = new PaymentDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.paymentService = new PaymentService(paymentDao);
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        String idParam = req.getParameter("id");
        String subscriptionIdParam = req.getParameter("subscriptionId");
        String userIdParam = req.getParameter("userId");

        try {
            if (idParam != null) {
                handleGetById(resp, idParam);
            } else if (subscriptionIdParam != null) {
                handleGetBySubscriptionId(resp, subscriptionIdParam);
            } else if (userIdParam != null) {
                handleGetByUserId(resp, userIdParam);
            } else {
                handleGetAllPayments(resp);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid ID format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Error retrieving payments: " + e.getMessage() + "\"}");
        }
    }

    private void handleGetByUserId(HttpServletResponse resp, String userIdStr) throws IOException {
        int userId = Integer.parseInt(userIdStr);
        List<Payment> payments = paymentService.findByUserId(userId);
        resp.getWriter().write(objectMapper.writeValueAsString(payments));
    }

    private void handleGetById(HttpServletResponse resp, String idParam) throws IOException {
        try {
            int id = Integer.parseInt(idParam);
            Payment payment = paymentService.findById(id);
            if (payment != null) {
                resp.getWriter().write(objectMapper.writeValueAsString(payment));
            } else {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Payment not found\"}");
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid payment ID format\"}");
        }
    }

    private void handleGetBySubscriptionId(HttpServletResponse resp, String subscriptionIdParam) throws IOException {
        try {
            int subscriptionId = Integer.parseInt(subscriptionIdParam);
            List<Payment> payments = paymentService.findBySubscriptionId(subscriptionId);
            resp.getWriter().write(objectMapper.writeValueAsString(payments));
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid subscription ID format\"}");
        }
    }

    private void handleGetAllPayments(HttpServletResponse resp) throws IOException {
        List<Payment> payments = paymentService.findAll();
        resp.getWriter().write(objectMapper.writeValueAsString(payments));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreatePaymentDto payment = parsePaymentFromRequest(req);
            paymentService.create(payment);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("{\"message\": \"Payment created successfully\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Error creating payment: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreatePaymentDto paymentDto = parsePaymentFromRequest(req);
            if (paymentDto.getId() == 0) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\": \"Payment ID is required\"}");
                return;
            }
            Payment payment = paymentService.findById(paymentDto.getId());
            if (payment == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("{\"error\": \"Payment not found\"}");
                return;
            }
            if (paymentDto.getAmount() != null) {
                payment.setAmount(paymentDto.getAmount());
            }
            paymentService.update(payment);
            resp.getWriter().write("{\"message\": \"Payment updated successfully\"}");
        } catch (IOException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Error updating payment: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramId = req.getParameter("id");
        try {
            int id = Integer.parseInt(paramId);
            paymentService.delete(id);
            resp.getWriter().write("{\"message\": \"Payment deleted successfully\"}");
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\": \"Invalid payment ID format\"}");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\": \"Error deleting payment: " + e.getMessage() + "\"}");
        }
    }

    private CreatePaymentDto parsePaymentFromRequest(HttpServletRequest req) throws IOException {
        return objectMapper.readValue(req.getReader(), CreatePaymentDto.class);
    }
}
