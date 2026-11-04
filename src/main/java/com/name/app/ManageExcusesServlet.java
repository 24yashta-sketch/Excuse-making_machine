package com.name.app;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/manage-excuses")
public class ManageExcusesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String ageGroup = (String) session.getAttribute("ageGroup");
        List<Excuse> excuses = getExcusesByAgeGroup(ageGroup);

        request.setAttribute("excuses", excuses);
        request.getRequestDispatcher("/WEB-INF/views/manage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String excuseText = request.getParameter("excuseText");
        String ageGroup = (String) session.getAttribute("ageGroup");

        if (excuseText != null && !excuseText.trim().isEmpty() && ageGroup != null) {
            insertExcuse(excuseText.trim(), ageGroup);
        }

        response.sendRedirect(request.getContextPath() + "/manage-excuses");
    }

    private List<Excuse> getExcusesByAgeGroup(String ageGroup) {
        List<String> listStr = new ArrayList<>();
        List<Excuse> list = new ArrayList<>();
        String sql = "SELECT id, text, age_group FROM excuses WHERE age_group = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ageGroup);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Excuse(rs.getInt("id"), rs.getString("text"), rs.getString("age_group")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private void insertExcuse(String text, String ageGroup) {
        String sql = "INSERT INTO excuses (text, age_group) VALUES (?, ?)";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, text);
            ps.setString(2, ageGroup);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}