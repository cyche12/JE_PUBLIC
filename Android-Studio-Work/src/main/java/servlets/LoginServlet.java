package servlets;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import database.UserDAO;
import database.UserDTO;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    public LoginServlet() {
        super();
        userDao = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isAuthenticated = userDao.authenticate(email, password);
        if (isAuthenticated) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email);

            String userType = userDao.getUserType(email);
            session.setAttribute("userType", userType);

            UserDTO user = userDao.getUserByEmail(email);
            if (user != null) {
                session.setAttribute("userId", user.getUserId());

                switch (userType) {
                    case "RETAIL":
                        response.sendRedirect("RetailServlet");
                        break;
                    case "CONSUMER":
                        response.sendRedirect("ConsumerServlet");
                        break;
                    case "CHARITY":
                        response.sendRedirect("CharityServlet");
                        break;
                    default:
                        request.setAttribute("error", "Invalid user type");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
                        dispatcher.forward(request, response);
                        break;
                }
            } else {
                request.setAttribute("error", "User not found");
                RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
                dispatcher.forward(request, response);
            }
        } else {
            request.setAttribute("error", "Invalid email or password");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Login.jsp");
            dispatcher.forward(request, response);
        }
    }
}
