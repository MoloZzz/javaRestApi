package PSsys.Controller;

import PSsys.Service.PublicationService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/publications/*")
public class PublicationController extends HttpServlet {
    private PublicationService publicationService;
}
