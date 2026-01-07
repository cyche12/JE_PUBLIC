package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.SurplusFoodDAO;
import database.SurplusFoodDTO;

public class ListSurplusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private SurplusFoodDAO surplusFoodDAO;

    public ListSurplusServlet() {
        super();
        surplusFoodDAO = new SurplusFoodDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && "RETAIL".equals(session.getAttribute("userType"))) {
            int foodId = Integer.parseInt(request.getParameter("foodId"));
            String listingType = request.getParameter("listingType");
            int retailerId = (Integer) session.getAttribute("userId");

            response.setContentType("text/plain");
            PrintWriter out = response.getWriter();

            // 이미 추가된 항목인지 확인
            if (surplusFoodDAO.isAlreadyListed(foodId, listingType)) {
                out.print("Item already listed as " + listingType.toLowerCase() + ".");
                out.flush();
                return;
            }

            SurplusFoodDTO surplusFood = new SurplusFoodDTO();
            surplusFood.setFoodId(foodId);
            surplusFood.setRetailerId(retailerId);
            surplusFood.setListingType(listingType);
            surplusFood.setListingDate(new Date(System.currentTimeMillis()));
            surplusFood.setDonated(listingType.equals("DONATION"));
            surplusFood.setClaimed(false);

            boolean isListed = surplusFoodDAO.listSurplusFood(surplusFood);
            if (isListed) {
                out.print(listingType.toUpperCase() + " added successfully.");
            } else {
                out.print("Failed to add " + listingType.toLowerCase() + ".");
            }
            out.flush();
        } else {
            response.sendRedirect("Login.jsp");
        }
    }
}
