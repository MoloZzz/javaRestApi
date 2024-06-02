package application.repository.impl;

import application.model.Subscription;
import application.repository.SubscriptionDao;
import application.repository.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubscriptionDaoImpl implements SubscriptionDao {

    private Connection connection;

    public SubscriptionDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Subscription findById(int id) {
        String sql = "SELECT id, user_id, publication_id, start_date, end_date FROM subscriptions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Subscription subscription = new Subscription();
                subscription.setId(rs.getInt("id"));
                subscription.setUserId(rs.getInt("user_id"));
                subscription.setPublicationId(rs.getInt("publication_id"));
                subscription.setStartDate(rs.getDate("start_date").toLocalDate());
                subscription.setEndDate(rs.getDate("end_date").toLocalDate());
                return subscription;
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обробка помилки
        }
        return null;
    }

    @Override
    public List<Subscription> findByUserId(int userId) throws SQLException {
        String sql = "SELECT * FROM subscriptions WHERE user_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Subscription> subscriptions = new ArrayList<>();
                while (rs.next()) {
                    Subscription subscription = new Subscription();
                    subscription.setId(rs.getInt("id"));
                    subscription.setUserId(rs.getInt("user_id"));
                    subscription.setPublicationId(rs.getInt("publication_id"));
                    subscription.setStartDate(rs.getDate("start_date").toLocalDate());
                    subscription.setEndDate(rs.getDate("end_date").toLocalDate());
                    subscriptions.add(subscription);
                }
                return subscriptions;
            }
        }
    }

    @Override
    public List<Subscription> findByPublicationId(int publicationId) throws SQLException {
        String sql = "SELECT * FROM subscriptions WHERE publication_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, publicationId);
            try (ResultSet rs = pstmt.executeQuery()) {
                List<Subscription> subscriptions = new ArrayList<>();
                while (rs.next()) {
                    Subscription subscription = new Subscription();
                    subscription.setId(rs.getInt("id"));
                    subscription.setUserId(rs.getInt("user_id"));
                    subscription.setPublicationId(rs.getInt("publication_id"));
                    subscription.setStartDate(rs.getDate("start_date").toLocalDate());
                    subscription.setEndDate(rs.getDate("end_date").toLocalDate());
                    subscriptions.add(subscription);
                }
                return subscriptions;
            }
        }
    }

    @Override
    public List<Subscription> findAll() {
        List<Subscription> subscriptions = new ArrayList<>();
        String sql = "SELECT id, user_id, publication_id, start_date, end_date FROM subscriptions";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Subscription subscription = new Subscription();
                subscription.setId(rs.getInt("id"));
                subscription.setUserId(rs.getInt("user_id"));
                subscription.setPublicationId(rs.getInt("publication_id"));
                subscription.setStartDate(rs.getDate("start_date").toLocalDate());
                subscription.setEndDate(rs.getDate("end_date").toLocalDate());

                subscriptions.add(subscription);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Обробка помилки
        }

        return subscriptions;
    }
    @Override
    public void save(Subscription subscription){
        String sql = "INSERT INTO subscriptions (user_id, publication_id, start_date, end_date) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getUserId());
            pstmt.setInt(2, subscription.getPublicationId());
            pstmt.setDate(3, java.sql.Date.valueOf(subscription.getStartDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(subscription.getEndDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Subscription subscription) {
        String sql = "UPDATE subscriptions SET user_id = ?, publication_id = ?, start_date = ?, end_date = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, subscription.getUserId());
            pstmt.setInt(2, subscription.getPublicationId());
            pstmt.setDate(3, java.sql.Date.valueOf(subscription.getStartDate()));
            pstmt.setDate(4, java.sql.Date.valueOf(subscription.getEndDate()));
            pstmt.setInt(5, subscription.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Обробка помилки
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM subscriptions WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Обробка помилки
        }
    }
}
