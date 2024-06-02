package application.repository;


import application.model.Payment;

import java.util.List;

public interface PaymentDao {
    Payment findById(int id);
    List<Payment> findAll();
    void save(Payment payment);
    void update(Payment payment);
    List<Payment> findBySubscriptionId(int subscriptionId);
    void delete(int id);
}
