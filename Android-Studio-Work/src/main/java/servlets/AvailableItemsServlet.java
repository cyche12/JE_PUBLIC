package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.FoodItemDAO;
import database.FoodItemDTO;

public class AvailableItemsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AvailableItemsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FoodItemDAO foodItemDAO = new FoodItemDAO();
        List<FoodItemDTO> foodItems = foodItemDAO.getAllFoodItems();
        
        request.setAttribute("foodItems", foodItems);
        
        RequestDispatcher dispatcher = request.getRequestDispatcher("/AvailableItems.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
