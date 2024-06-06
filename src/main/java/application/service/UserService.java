package application.service;

import application.dto.user.CreateUserDto;
import application.model.User;
import application.repository.UserDao;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Objects;

public class UserService {
    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User findById(int id) {
        return userDao.findById(id);
    }

    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public void create(CreateUserDto user) {
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        CreateUserDto newUser = new CreateUserDto();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(hashedPassword);
        newUser.setEmail(user.getEmail());
        String userRole = user.getRole();
        newUser.setRole(userRole != null ? userRole : "user");
        userDao.save(newUser);
    }

    public void update(User user) {
        userDao.update(user);
    }

    public void delete(int id) {
        userDao.delete(id);
    }

    public User authenticateUser(String username, String password) {
        User user = userDao.findByUsername(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }
        return null;
    }

}

