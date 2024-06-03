package application.service;

import application.model.Publication;
import application.repository.impl.PublicationDaoImpl;

import java.util.List;

public class PublicationService {
    private PublicationDaoImpl publicationDao;

    public PublicationService(PublicationDaoImpl publicationDao) {
        this.publicationDao = publicationDao;
    }

    public List<Publication> findAll() {
        return publicationDao.findAll();
    }
    public List<Publication> findByUserId(int userId) {
        return publicationDao.findByUserId(userId);
    }

    public Publication findById(int id) {
        return publicationDao.findById(id);
    }

    public void create(Publication publication) {
        publicationDao.save(publication);
    }

    public void update(Publication publication) {
        publicationDao.update(publication);
    }

    public void delete(int id) {
        publicationDao.delete(id);
    }
}
