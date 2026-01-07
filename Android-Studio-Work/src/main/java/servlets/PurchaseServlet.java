package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.FoodItemDAO;

public class PurchaseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private FoodItemDAO foodItemDAO;

    public PurchaseServlet() {
        super();
        foodItemDAO = new FoodItemDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        boolean isPurchased = foodItemDAO.purchaseFoodItem(foodId);
        
        if (isPurchased) {
            response.sendRedirect("ConsumerServlet");
        } else {
            response.getWriter().print("Failed to purchase item.");
        }
    }
}
