package application.controller;

import application.config.DatabaseConnection;
import application.dto.publication.CreatePublicationDto;
import application.dto.publication.UpdatePublicationDto;
import application.model.Publication;
import application.repository.impl.PublicationDaoImpl;
import application.service.PublicationService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/publications/*")
public class PublicationController extends HttpServlet {
    private PublicationService publicationService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        PublicationDaoImpl publicationDao = new PublicationDaoImpl(DatabaseConnection.getInstance().getConnection());
        this.publicationService = new PublicationService(publicationDao);
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String idParam = req.getParameter("id");

        if (idParam == null) {
            List<Publication> publications = publicationService.findAll();
            PrintWriter out = resp.getWriter();
            objectMapper.writeValue(out, publications);
        } else {
            try {
                int id = Integer.parseInt(idParam);
                Publication publication = publicationService.findById(id);
                if (publication != null) {
                    PrintWriter out = resp.getWriter();
                    objectMapper.writeValue(out, publication);
                } else {
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.getWriter().write("Publication not found");
                }
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid publication ID");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CreatePublicationDto createDto = objectMapper.readValue(req.getInputStream(), CreatePublicationDto.class);
            Publication newPublication = new Publication();
            newPublication.setTitle(createDto.getTitle());
            newPublication.setDescription(createDto.getDescription());
            newPublication.setPrice(createDto.getPrice());
            publicationService.create(newPublication);
            resp.setStatus(HttpServletResponse.SC_CREATED);
            resp.getWriter().write("Publication created successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error creating publication: " + e.getMessage());
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            UpdatePublicationDto updateDto = objectMapper.readValue(req.getInputStream(), UpdatePublicationDto.class);

            if (updateDto.getId() == null) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("ID is required for updating");
                return;
            }

            Publication publication = publicationService.findById(updateDto.getId());
            if (publication == null) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getWriter().write("Publication not found");
                return;
            }

            if (updateDto.getTitle() != null) {
                publication.setTitle(updateDto.getTitle());
            }
            if (updateDto.getDescription() != null) {
                publication.setDescription(updateDto.getDescription());
            }
            if (updateDto.getPrice() != null) {
                publication.setPrice(updateDto.getPrice());
            }

            publicationService.update(publication);
            resp.setStatus(HttpServletResponse.SC_OK);
            resp.getWriter().write("Publication updated successfully");
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("Error updating publication: " + e.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String paramId = req.getParameter("id");
        try {
            int id = Integer.parseInt(paramId);
            publicationService.delete(id);
            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Invalid publication ID");
        }
    }
}
