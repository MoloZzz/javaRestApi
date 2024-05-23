package PSsys.Controller;

import PSsys.Service.PaymentService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/payments/*")
public class PaymentController extends HttpServlet {
    private PaymentService paymentService;
}
