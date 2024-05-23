package PSsys.DAO;

import PSsys.Model.Publication;

import java.util.List;

public interface PublicationDao {
    Publication findById(int id);
    List<Publication> findAll();
    void save(Publication publication);
    void update(Publication publication);
    void delete(int id);
}
