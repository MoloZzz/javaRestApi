import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class TestServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(TestServlet.class.getName());

    public TestServlet() {
        logger.info("TestServlet is being instantiated");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("We are in test method");
        resp.getWriter().write("Work tests do method");
    }
}
