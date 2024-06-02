package application.repository.impl;

import application.model.Payment;
import application.repository.PaymentDao;
import application.repository.UserDao;

import java.sql.Connection;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    private Connection connection;

    public PaymentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Payment findById(int id) {
        return null;
    }

    @Override
    public List<Payment> findAll() {
        return null;
    }

    @Override
    public void save(Payment payment) {

    }

    @Override
    public void update(Payment payment) {

    }

    @Override
    public void delete(int id) {

    }
}
