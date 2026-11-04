<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name.app.Excuse" %>
<!DOCTYPE html>
<html>
<head>
    <title>ניהול והוספת תירוצים</title>
    <style>
        body { font-family: Arial, sans-serif; background: #121212; color: #e0e0e0; margin: 0; direction: rtl; text-align: right; }
        .topbar { background: #1f1f1f; color: #fff; padding: 14px 24px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #2c2c2c; }
        .topbar a { color: #00bcd4; text-decoration: none; font-size: 14px; }
        .topbar a:hover { text-decoration: underline; }
        .content { padding: 30px; max-width: 800px; margin: 0 auto; }
        .header-row { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        h1, h2 { color: #ffffff; margin: 0; }
        .card { background: #1e1e1e; padding: 25px; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.5); margin-bottom: 25px; border: 1px solid #2c2c2c; }
        label { display: block; margin-top: 10px; margin-bottom: 4px; font-size: 14px; font-weight: bold; color: #b0b0b0; }
        input[type=text] { width: 100%; padding: 10px; box-sizing: border-box; background: #2c2c2c; border: 1px solid #444; color: #fff; border-radius: 4px; }
        input[type=text]:focus { border-color: #00bcd4; outline: none; }
        button { margin-top: 15px; padding: 10px 20px; background: #00bcd4; color: #121212; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; font-weight: bold; }
        button:hover { background: #008ba3; }
        table { width: 100%; border-collapse: collapse; margin-top: 15px; background: #1e1e1e; border-radius: 8px; overflow: hidden; }
        th, td { padding: 12px 15px; border-bottom: 1px solid #2c2c2c; text-align: right; }
        th { background: #2c2c2c; color: #ffffff; }
        tr:hover { background: #252525; }
        .back-link { text-decoration: none; color: #00bcd4; font-weight: bold; font-size: 15px; }
        .back-link:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="topbar">
        <span>שלום, <%= session.getAttribute("username") %> (<%= session.getAttribute("role") %>)</span>
        <a href="${pageContext.request.contextPath}/logout">התנתק</a>
    </div>
    <div class="content">
        <div class="header-row">
            <h1>ניהול תירוצים אישי</h1>
            <a class="back-link" href="${pageContext.request.contextPath}/home">← חזרה לדף הבית</a>
        </div>

        <div class="card">
            <h2>הוסף תירוץ חדש לקטגוריה שלך</h2>
            <form method="post" action="${pageContext.request.contextPath}/manage-excuses">
                <label for="excuseText">תוכן התירוץ</label>
                <input type="text" id="excuseText" name="excuseText" required autocomplete="off"/>
                <button type="submit">הוסף למערכת</button>
            </form>
        </div>

        <div class="card">
            <h2>התירוצים בקטגוריה שלך (<%= session.getAttribute("role") %>)</h2>
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>תוכן התירוץ</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        List<Excuse> excuses = (List<Excuse>) request.getAttribute("excuses");
                        if (excuses != null && !excuses.isEmpty()) {
                            int counter = 1;
                            for (Excuse ex : excuses) {
                    %>
                    <tr>
                        <td><%= counter++ %></td>
                        <td><%= ex.getText() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="2" style="text-align: center; color: #888;">אין עדיין תירוצים בקטגוריה זו.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>