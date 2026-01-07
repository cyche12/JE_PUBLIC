package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.FoodItemDAO;
import database.FoodItemDTO;

public class ConsumerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ConsumerServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("email") != null) {
            request.setAttribute("userEmail", session.getAttribute("email"));
            
            FoodItemDAO foodItemDAO = new FoodItemDAO();
            List<FoodItemDTO> availableItems = foodItemDAO.getAllFoodItems();
            List<FoodItemDTO> purchasedItems = foodItemDAO.getPurchasedFoodItems();
            
            request.setAttribute("availableItems", availableItems);
            request.setAttribute("purchasedItems", purchasedItems);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("ConsumerDashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("Login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
