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
import database.SurplusFoodDTO;

public class CharityServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CharityServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && "CHARITY".equals(session.getAttribute("userType"))) {
            SurplusFoodDAO surplusFoodDAO = new SurplusFoodDAO();
            List<SurplusFoodDTO> surplusFoods = surplusFoodDAO.getDonatedSurplusFood();
            List<SurplusFoodDTO> claimedFoods = surplusFoodDAO.getAllClaimedFood();
            
            request.setAttribute("surplusFoods", surplusFoods);
            request.setAttribute("claimedFoods", claimedFoods);
            request.setAttribute("userEmail", session.getAttribute("email"));
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("CharityDashboard.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("Login.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
