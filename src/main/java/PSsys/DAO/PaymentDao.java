package PSsys.DAO;

import PSsys.Model.Payment;

import java.util.List;

public interface PaymentDao {
    Payment findById(int id);
    List<Payment> findAll();
    void save(Payment payment);
    void update(Payment payment);
    void delete(int id);
}
