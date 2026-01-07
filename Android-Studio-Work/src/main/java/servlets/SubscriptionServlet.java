package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.SubscriptionDAO;
import database.SubscriptionDTO;
import enums.CommunicationMethod;

public class SubscriptionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SubscriptionDAO subscriptionDAO;

    public SubscriptionServlet() {
        super();
        subscriptionDAO = new SubscriptionDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Subscription.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        String location = request.getParameter("location");
        String communicationMethodString = request.getParameter("communicationMethod");
        String foodPreferences = request.getParameter("foodPreferences");

        CommunicationMethod communicationMethod;
        try {
            communicationMethod = CommunicationMethod.valueOf(communicationMethodString.toUpperCase());
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid communication method. Please select a valid communication method.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Subscription.jsp");
            dispatcher.forward(request, response);
            return;
        }

        SubscriptionDTO subscription = new SubscriptionDTO();
        subscription.setUserId(userId);
        subscription.setLocation(location);
        subscription.setCommunicationMethod(communicationMethod);
        subscription.setFoodPreferences(foodPreferences);

        boolean isSubscribed = subscriptionDAO.addSubscription(subscription);
        if (isSubscribed) {
            request.setAttribute("message", "Subscription successful!");
        } else {
            request.setAttribute("error", "Failed to subscribe.");
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("Subscription.jsp");
        dispatcher.forward(request, response);
    }
}
