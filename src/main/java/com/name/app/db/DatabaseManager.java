package com.name.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = "jdbc:h2:~/excusedb;AUTO_SERVER=TRUE";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("org.h2.Driver");
            initDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initDatabase() {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(255) NOT NULL, " +
                "password VARCHAR(255) NOT NULL, " +
                "birthdate DATE NOT NULL, " +
                "age_group VARCHAR(50) NOT NULL)";

        String createExcusesTable = "CREATE TABLE IF NOT EXISTS excuses (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "text VARCHAR(1000) NOT NULL, " +
                "age_group VARCHAR(50) NOT NULL)";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createExcusesTable);

            java.sql.ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM excuses");
            if (rs.next() && rs.getInt(1) == 0) {
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('השעון המרכזי בטלפון התאפס מעצמו בלילה', 'MINOR')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('המורה שכח לתת שיעורי בית אז חשבתי שגם לא צריך ללמוד למבחן', 'MINOR')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('הכלב שלי אכל את המחברת בדיוק כשהייתי צריך לכתוב בה', 'MINOR')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('הייתי בטוח שהיום חופש בגלל איזה חג לא מוכר', 'MINOR')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('האינטרנט התנתק בדיוק כשבאתי לשלוח את העבודה', 'MINOR')");

                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('הפקקים באיילון היו ברמה היסטורית שלא נראתה מעולם', 'ADULT')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('היה לוז מטורף בעבודה ולא הספקתי לנשום', 'ADULT')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('שכחתי את הארנק בבית והייתי חייב לחזור', 'ADULT')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('המזגן במשרד התקלקל ואי אפשר היה להתרכז', 'ADULT')");
                stmt.execute("INSERT INTO excuses (text, age_group) VALUES ('הייתי צריך לטפל בצינור התפוצץ בבית בדיוק בבוקר', 'ADULT')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}