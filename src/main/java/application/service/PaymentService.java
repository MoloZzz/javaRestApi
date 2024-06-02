package application.service;

import application.model.Payment;
import application.repository.PaymentDao;

import java.util.List;

public class PaymentService {
    private PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public Payment findById(int id) {
        return paymentDao.findById(id);
    }

    public List<Payment> findAll() {
        return paymentDao.findAll();
    }

    public void save(Payment payment) {
        paymentDao.save(payment);
    }

    public void update(Payment payment) {
        paymentDao.update(payment);
    }

    public void delete(int id) {
        paymentDao.delete(id);
    }
}
