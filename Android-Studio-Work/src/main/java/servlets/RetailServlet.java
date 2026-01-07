package servlets;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.SurplusFoodDAO;
import database.FoodItemDTO;

public class RetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RetailServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && "RETAIL".equals(session.getAttribute("userType"))) {
            SurplusFoodDAO surplusFoodDAO = new SurplusFoodDAO();
            List<FoodItemDTO> surplusItems = surplusFoodDAO.identifySurplusFood();
            request.setAttribute("surplusItems", surplusItems);
            request.setAttribute("userEmail", session.getAttribute("email"));
            RequestDispatcher dispatcher = request.getRequestDispatcher("RetailDashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("Login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
