package application.repository;

import application.dto.user.CreateUserDto;
import application.model.User;

import java.util.List;

public interface UserDao {
    User findById(int id);
    User findByUsername(String username);
    List<User> findAll();
    void save(CreateUserDto user);
    void update(User user);
    void delete(int id);
}
