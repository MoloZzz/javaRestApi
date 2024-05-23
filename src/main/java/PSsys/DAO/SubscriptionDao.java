package PSsys.DAO;

import PSsys.Model.Subscription;

import java.util.List;

public interface SubscriptionDao {
    Subscription findById(int id);
    List<Subscription> findAll();
    void save(Subscription subscription);
    void update(Subscription subscription);
    void delete(int id);
}