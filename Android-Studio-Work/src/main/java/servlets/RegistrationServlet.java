package servlets;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import database.UserDAO;
import database.UserDTO;
import enums.UserType;

public class RegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDao;

    public RegistrationServlet() {
        super();
        userDao = new UserDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("userEmail");
        String password = request.getParameter("userPassword");
        String userTypeString = request.getParameter("userType");
        String joinDateString = request.getParameter("joinDate");

        if (joinDateString == null || joinDateString.isEmpty()) {
            request.setAttribute("error", "Join date is required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
            dispatcher.forward(request, response);
            return;
        }

        Date joinDate;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            java.util.Date utilDate = sdf.parse(joinDateString);
            joinDate = new Date(utilDate.getTime());
        } catch (ParseException e) {
            request.setAttribute("error", "Invalid date format. Please use YYYY-MM-DD.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
            dispatcher.forward(request, response);
            return;
        }

        UserType userType;
        try {
            userType = UserType.valueOf(userTypeString.toUpperCase());
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", "Invalid user type. Please select a valid user type.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
            dispatcher.forward(request, response);
            return;
        }

        UserDTO user = new UserDTO();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserPassword(password);
        user.setUserEmail(email);
        user.setJoinDate(joinDate);
        user.setUserType(userType);

        if (userDao.isUserExists(email)) {
            request.setAttribute("error", "User already exists.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
            dispatcher.forward(request, response);
            return;
        }

        boolean isUserAdded = userDao.addUser(user);
        if (isUserAdded) {
            response.sendRedirect("LoginServlet");
        } else {
            request.setAttribute("error", "Failed to register user");
            RequestDispatcher dispatcher = request.getRequestDispatcher("Registration.jsp");
            dispatcher.forward(request, response);
        }
    }
}
