package application.controller;

import application.config.DatabaseConnection;
import application.dto.subscription.CreateSubscriptionDto;
import application.dto.subscription.InfoSubscriptionDto;
import application.dto.subscription.UpdateSubscriptionDto;
import application.model.Subscription;
import application.repository.impl.SubscriptionDaoImpl;
import application.service.SubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/subscriptions/*")
public class SubscriptionController extends HttpServlet {
    private SubscriptionService subscriptionService;
    private ObjectMapper objectMapper;
    @Override
    public void init() throws ServletException {
        SubscriptionDaoImpl subscriptionDao = new SubscriptionDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.subscriptionService = new SubscriptionService(subscriptionDao);
        this.objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        String userIdParam = req.getParameter("userId");
        String publicationIdParam = req.getParameter("publicationId");

        try {
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                Subscription subscription = subscriptionService.findById(id);
                if (subscription != null) {
                    resp.setContentType("application/json");
                    resp.getWriter().write(objectMapper.writeValueAsString(subscription));
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Subscription not found");
                }
            } else if (userIdParam != null) {
                int userId = Integer.parseInt(userIdParam);
                List<Subscription> subscriptions = subscriptionService.findByUserId(userId);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(subscriptions));
            } else if (publicationIdParam != null) {
                int publicationId = Integer.parseInt(publicationIdParam);
                List<Subscription> subscriptions = subscriptionService.findByPublicationId(publicationId);
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(subscriptions));
            } else {
                List<Subscription> subscriptions = subscriptionService.findAll();
                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(subscriptions));
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid ID format");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error retrieving subscriptions: " + e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            InfoSubscriptionDto infoSubscriptionDto = objectMapper.readValue(req.getInputStream(), InfoSubscriptionDto.class);
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusMonths(1);
            CreateSubscriptionDto newSubscription = new CreateSubscriptionDto(
                    infoSubscriptionDto.getUserId(),
                    infoSubscriptionDto.getPublicationId(),
                    startDate,
                    endDate
            );
            subscriptionService.create(newSubscription);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Subscription created successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating subscription: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UpdateSubscriptionDto updateDto = objectMapper.readValue(req.getInputStream(), UpdateSubscriptionDto.class);

            if (updateDto.getId() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("ID is required for updating");
                return;
            }

            Subscription subscription = subscriptionService.findById(updateDto.getId());
            if (subscription == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Subscription not found");
                return;
            }
            if (updateDto.getUserId() != null) {
                subscription.setUserId(updateDto.getUserId());
            }
            if (updateDto.getPublicationId() != null) {
                subscription.setPublicationId(updateDto.getPublicationId());
            }
            if (updateDto.getStartDate() != null) {
                subscription.setStartDate(updateDto.getStartDate());
            }
            if (updateDto.getEndDate() != null) {
                subscription.setEndDate(updateDto.getEndDate());
            }

            subscriptionService.update(subscription);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Subscription updated successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating subscription: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                subscriptionService.delete(id);
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid subscription ID format");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Subscription ID is required");
        }
    }
}
