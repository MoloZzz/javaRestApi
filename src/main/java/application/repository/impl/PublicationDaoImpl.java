package application.repository.impl;

import application.dto.user.CreateUserDto;
import application.model.Publication;
import application.model.User;
import application.repository.PublicationDao;
import application.repository.UserDao;

import java.sql.Connection;
import java.util.List;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import application.model.Publication;

public class PublicationDaoImpl implements PublicationDao{
    private Connection connection;

    public PublicationDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Publication> findByUserId(int userId) {
        List<Publication> publications = new ArrayList<>();
        String sql = "SELECT p.* FROM publications p JOIN subscriptions s ON s.publication_id = p.id WHERE s.user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Publication publication = new Publication();
                publication.setId(rs.getInt("id"));
                publication.setTitle(rs.getString("title"));
                publication.setDescription(rs.getString("description"));
                publication.setPrice(rs.getBigDecimal("price"));
                publications.add(publication);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publications;
    }
    public List<Publication> findAll() {
        List<Publication> publications = new ArrayList<>();
        String sql = "SELECT * FROM publications";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Publication publication = new Publication();
                publication.setId(resultSet.getInt("id"));
                publication.setTitle(resultSet.getString("title"));
                publication.setDescription(resultSet.getString("description"));
                publication.setPrice(resultSet.getBigDecimal("price"));
                publications.add(publication);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving publications", e);
        }
        return publications;
    }

    public Publication findById(int id) {
        String sql = "SELECT * FROM publications WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Publication publication = new Publication();
                    publication.setId(resultSet.getInt("id"));
                    publication.setTitle(resultSet.getString("title"));
                    publication.setDescription(resultSet.getString("description"));
                    publication.setPrice(resultSet.getBigDecimal("price"));
                    return publication;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving publication", e);
        }
        return null;
    }

    public void save(Publication publication) {
        String sql = "INSERT INTO publications (title, description, price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, publication.getTitle());
            statement.setString(2, publication.getDescription());
            statement.setBigDecimal(3, publication.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving publication", e);
        }
    }

    public void update(Publication publication) {
        String sql = "UPDATE publications SET title = ?, description = ?, price = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, publication.getTitle());
            statement.setString(2, publication.getDescription());
            statement.setBigDecimal(3, publication.getPrice());
            statement.setInt(4, publication.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating publication", e);
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM publications WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting publication", e);
        }
    }
}
