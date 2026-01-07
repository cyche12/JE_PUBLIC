package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.SurplusFoodDAO;
import database.FoodItemDAO;

public class ClaimSurplusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SurplusFoodDAO surplusFoodDAO;
    private FoodItemDAO foodItemDAO;

    public ClaimSurplusServlet() {
        super();
        surplusFoodDAO = new SurplusFoodDAO();
        foodItemDAO = new FoodItemDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        
        boolean isClaimed = surplusFoodDAO.claimSurplusFood(foodId, quantity);
        
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        if (isClaimed) {
            int currentQuantity = foodItemDAO.getFoodItemQuantity(foodId);
            int newQuantity = currentQuantity - quantity;
            if (newQuantity < 0) newQuantity = 0;
            boolean isUpdated = foodItemDAO.updateFoodItemQuantity(foodId, newQuantity);
            if (isUpdated) {
                out.print("Item claimed successfully. Remaining quantity: " + newQuantity);
            } else {
                out.print("Item claimed but failed to update quantity.");
            }
        } else {
            out.print("Failed to claim item.");
        }
    }
}
