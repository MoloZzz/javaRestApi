package application.repository;


import application.model.Subscription;

import java.sql.SQLException;
import java.util.List;

public interface SubscriptionDao {
    List<Subscription> findAll() throws SQLException;

    Subscription findById(int id) throws SQLException;

    List<Subscription> findByUserId(int userId) throws SQLException;

    List<Subscription> findByPublicationId(int publicationId) throws SQLException;

    void save(Subscription subscription) throws SQLException;

    void update(Subscription subscription) throws SQLException;

    void delete(int id) throws SQLException;
}