<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>מחולל התירוצים - דף הבית</title>
    <style>
        body { font-family: Arial, sans-serif; background: #121212; color: #e0e0e0; margin: 0; direction: rtl; text-align: right; }
        .topbar { background: #1f1f1f; color: #fff; padding: 14px 24px; display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #2c2c2c; }
        .topbar a { color: #00bcd4; text-decoration: none; font-size: 14px; }
        .topbar a:hover { text-decoration: underline; }
        .content { padding: 30px; display: flex; flex-direction: column; align-items: center; }
        h1 { color: #ffffff; }
        .excuse-box { background: #1976d2; padding: 40px; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); width: 500px; text-align: center; margin-top: 20px; transition: background-color 0.4s ease; color: #ffffff; }
        .excuse-text { font-size: 24px; margin-bottom: 30px; font-weight: bold; min-height: 70px; display: flex; align-items: center; justify-content: center; }
        button { padding: 14px 28px; background: #ffffff; color: #121212; border: none; border-radius: 6px; cursor: pointer; font-size: 18px; font-weight: bold; box-shadow: 0 2px 6px rgba(0,0,0,0.3); transition: transform 0.1s; }
        button:hover { background: #e0e0e0; transform: scale(1.02); }
        .nav-links { margin-top: 25px; text-align: center; }
        .nav-links a { text-decoration: none; color: #00bcd4; font-size: 16px; font-weight: bold; }
        .nav-links a:hover { text-decoration: underline; }
    </style>
    <script>
        function generateExcuse() {
            const excuses = [
                <%
                    java.util.List<String> excuseList = (java.util.List<String>) request.getAttribute("excusesList");
                    if (excuseList != null && !excuseList.isEmpty()) {
                        for (int i = 0; i < excuseList.size(); i++) {
                %>
                    "<%= excuseList.get(i).replace("\"", "\\\"") %>"<%= (i < excuseList.size() - 1) ? "," : "" %>
                <%
                        }
                    } else {
                %>
                    "אין עדיין תירוצים זמינים בקטגוריה זו. הוסף תירוצים חדשים בדף הניהול!"
                <%
                    }
                %>
            ];

            const colors = [
                '#1976d2', '#e91e63', '#9c27b0', '#673ab7',
                '#3f51b5', '#009688', '#4caf50', '#ff9800',
                '#ff5722', '#795548', '#607d8b', '#00bcd4'
            ];

            const randomExcuse = excuses[Math.floor(Math.random() * excuses.length)];
            const randomColor = colors[Math.floor(Math.random() * colors.length)];

            document.getElementById('excuseDisplay').innerText = randomExcuse;
            document.getElementById('boxContainer').style.backgroundColor = randomColor;
        }
    </script>
</head>
<body>
    <div class="topbar">
        <span>שלום, <%= session.getAttribute("username") %> (<%= session.getAttribute("role") %>)</span>
        <a href="${pageContext.request.contextPath}/logout">התנתק</a>
    </div>
    <div class="content">
        <h1>מחולל התירוצים האוטומטי</h1>

        <div id="boxContainer" class="excuse-box">
            <div id="excuseDisplay" class="excuse-text">
                לחץ על הכפתור כדי לקבל תירוץ חדש!
            </div>
            <button type="button" onclick="generateExcuse()">הגרל תירוץ</button>
        </div>

        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/manage-excuses">והוספת תירוצים למערכת</a>
        </div>
    </div>
</body>
</html>