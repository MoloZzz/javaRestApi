package application.service;

import application.model.Subscription;
import application.repository.SubscriptionDao;

import java.sql.SQLException;
import java.util.List;

public class SubscriptionService {

    private SubscriptionDao subscriptionDao;

    public SubscriptionService(SubscriptionDao subscriptionDao) {
        this.subscriptionDao = subscriptionDao;
    }

    public List<Subscription> findAll() throws SQLException {
        return subscriptionDao.findAll();
    }

    public Subscription findById(int id) throws SQLException {
        return subscriptionDao.findById(id);
    }

    public List<Subscription> findByUserId(int userId) throws SQLException {
        return subscriptionDao.findByUserId(userId);
    }

    public List<Subscription> findByPublicationId(int publicationId) throws SQLException {
        return subscriptionDao.findByPublicationId(publicationId);
    }


    public void create(Subscription subscription) throws SQLException {
        subscriptionDao.save(subscription);
    }

    public void update(Subscription subscription) throws SQLException {
        subscriptionDao.update(subscription);
    }

    public void delete(int id) throws SQLException {
        subscriptionDao.delete(id);
    }
}
