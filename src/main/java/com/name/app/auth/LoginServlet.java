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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("username") != null) {
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null) {
            request.setAttribute("error", "נא למלא את כל השדות");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            return;
        }

        String sql = "SELECT birthdate, age_group FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.sql.Date dbDate = rs.getDate("birthdate");
                    LocalDate birthDate = dbDate.toLocalDate();
                    int age = Period.between(birthDate, LocalDate.now()).getYears();
                    String ageGroup = rs.getString("age_group");
                    String roleDesc = (age >= 18) ? "מבוגר (18+)" : "צעיר (מתחת ל-18)";

                    HttpSession session = request.getSession(true);
                    session.setAttribute("username", username);
                    session.setAttribute("age", age);
                    session.setAttribute("ageGroup", ageGroup);
                    session.setAttribute("role", roleDesc);

                    response.sendRedirect(request.getContextPath() + "/home");
                } else {
                    request.setAttribute("error", "שם משתמש או סיסמה שגויים");
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "שגיאה בחיבור למסד הנתונים");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}