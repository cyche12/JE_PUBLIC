package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.SurplusFoodDAO;
import database.FoodItemDAO;
import database.FoodItemDTO;

public class ClaimDonationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SurplusFoodDAO surplusFoodDAO;
    private FoodItemDAO foodItemDAO;

    public ClaimDonationServlet() {
        super();
        surplusFoodDAO = new SurplusFoodDAO();
        foodItemDAO = new FoodItemDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int foodId = Integer.parseInt(request.getParameter("foodId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        StringBuilder xmlResponse = new StringBuilder();
        response.setContentType("application/xml");
        PrintWriter out = response.getWriter();

        int currentQuantity = foodItemDAO.getFoodItemQuantity(foodId);
        if (quantity > currentQuantity) {
            xmlResponse.append("<response>");
            xmlResponse.append("<success>false</success>");
            xmlResponse.append("<message>Not enough stock available.</message>");
            xmlResponse.append("</response>");
        } else {
            boolean isUpdated = surplusFoodDAO.claimSurplusFood(foodId, quantity);
            if (isUpdated) {
                surplusFoodDAO.updateClaimedFoodQuantity(foodId, quantity);
                int newQuantity = currentQuantity - quantity;
                foodItemDAO.updateFoodItemQuantity(foodId, newQuantity);

                FoodItemDTO foodItem = foodItemDAO.getFoodItemById(foodId); // 실제 food item 정보 가져오기

                xmlResponse.append("<response>");
                xmlResponse.append("<success>true</success>");
                xmlResponse.append("<foodId>").append(foodId).append("</foodId>");
                xmlResponse.append("<foodName>").append(foodItem.getFoodName()).append("</foodName>"); // 실제 food name 가져오기
                xmlResponse.append("<claimedQuantity>").append(quantity).append("</claimedQuantity>");
                xmlResponse.append("<foodCost>").append(foodItem.getFoodCost()).append("</foodCost>"); // 실제 cost 가져오기
                xmlResponse.append("<foodExpiry>").append(foodItem.getFoodExpiry()).append("</foodExpiry>"); // 실제 expiry date 가져오기
                xmlResponse.append("<newQuantity>").append(newQuantity).append("</newQuantity>");
                xmlResponse.append("</response>");
            } else {
                xmlResponse.append("<response>");
                xmlResponse.append("<success>false</success>");
                xmlResponse.append("<message>Failed to claim item.</message>");
                xmlResponse.append("</response>");
            }
        }
        
        out.print(xmlResponse.toString());
        out.flush();
    }
}
