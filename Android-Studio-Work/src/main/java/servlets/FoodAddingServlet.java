package servlets;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.FoodItemDAO;
import database.FoodItemDTO;

public class FoodAddingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public FoodAddingServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && "RETAIL".equals(session.getAttribute("userType"))) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("AddFoodItem.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("RetailServlet");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && "RETAIL".equals(session.getAttribute("userType"))) {
            String foodName = request.getParameter("foodName");
            int foodQuantity = Integer.parseInt(request.getParameter("foodQuantity"));
            double foodCost = Double.parseDouble(request.getParameter("foodCost"));
            String foodExpiryString = request.getParameter("foodExpiry");
            int retailerId = (int) session.getAttribute("userId");

            Date foodExpiry = null;
            try {
                foodExpiry = Date.valueOf(foodExpiryString);
            } catch (IllegalArgumentException e) {
                request.setAttribute("error", "Invalid date format.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("AddFoodItem.jsp");
                dispatcher.forward(request, response);
                return;
            }

            FoodItemDTO foodItem = new FoodItemDTO();
            foodItem.setFoodName(foodName);
            foodItem.setFoodQuantity(foodQuantity);
            foodItem.setFoodCost(foodCost);
            foodItem.setFoodExpiry(foodExpiry);
            foodItem.setRetailerId(retailerId);

            FoodItemDAO foodItemDAO = new FoodItemDAO();

            boolean isAdded = foodItemDAO.addFoodItem(foodItem);
            if (isAdded) {
                response.sendRedirect("RetailServlet");
            } else {
                request.setAttribute("error", "Failed to add food item.");
                RequestDispatcher dispatcher = request.getRequestDispatcher("AddFoodItem.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            response.sendRedirect("RetailServlet");
        }
    }
}
