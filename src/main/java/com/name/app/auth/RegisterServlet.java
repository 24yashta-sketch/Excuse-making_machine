package com.name.app.auth;

import com.name.app.db.DatabaseManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String birthdateStr = request.getParameter("birthdate");

        if (username == null || username.trim().isEmpty() || password == null || birthdateStr == null) {
            request.setAttribute("error", "נא למלא את כל השדות");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            return;
        }

        try {
            LocalDate birthDate = LocalDate.parse(birthdateStr);
            LocalDate currentDate = LocalDate.now();

            if (birthDate.isAfter(currentDate)) {
                request.setAttribute("error", "תאריך לידה לא תקין");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            int age = Period.between(birthDate, currentDate).getYears();
            String ageGroup = (age >= 18) ? "ADULT" : "MINOR";
            String roleDesc = (age >= 18) ? "מבוגר (18+)" : "צעיר (מתחת ל-18)";

            saveUserToDatabase(username.trim(), password, birthDate, ageGroup);

            HttpSession session = request.getSession(true);
            session.setAttribute("username", username.trim());
            session.setAttribute("age", age);
            session.setAttribute("ageGroup", ageGroup);
            session.setAttribute("role", roleDesc);

            response.sendRedirect(request.getContextPath() + "/home");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "שגיאה בעיבוד הנתונים");
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        }
    }

    private void saveUserToDatabase(String username, String password, LocalDate birthdate, String ageGroup) {
        String sql = "INSERT INTO users (username, password, birthdate, age_group) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setDate(3, java.sql.Date.valueOf(birthdate));
            ps.setString(4, ageGroup);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}