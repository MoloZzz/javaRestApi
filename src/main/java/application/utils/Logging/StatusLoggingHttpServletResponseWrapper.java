package application.utils.Logging;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

public class StatusLoggingHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private int httpStatus = SC_OK;

    public StatusLoggingHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc) throws IOException {
        httpStatus = sc;
        super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        httpStatus = sc;
        super.sendError(sc, msg);
    }

    @Override
    public void setStatus(int sc) {
        httpStatus = sc;
        super.setStatus(sc);
    }

    @Override
    public void setStatus(int sc, String sm) {
        httpStatus = sc;
        super.setStatus(sc, sm);
    }

    public int getStatus() {
        return httpStatus;
    }
}