package PSsys.Controller;

import PSsys.Service.SubscriptionService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet("/subscriptions/*")
public class SubscriptionController extends HttpServlet {
    private SubscriptionService subscriptionService;
}
