<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>הרשמה למחולל התירוצים</title>
    <style>
        body { font-family: Arial, sans-serif; background: #121212; color: #e0e0e0; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; direction: rtl; }
        .register-box { background: #1e1e1e; padding: 30px 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); width: 300px; text-align: right; border: 1px solid #2c2c2c; }
        h1 { font-size: 20px; margin-top: 0; color: #ffffff; text-align: center; }
        label { display: block; margin-top: 12px; margin-bottom: 4px; font-size: 14px; color: #b0b0b0; }
        input[type=text], input[type=password], input[type=date] { width: 100%; padding: 10px; box-sizing: border-box; background: #2c2c2c; border: 1px solid #444; color: #fff; border-radius: 4px; }
        input[type=text]:focus, input[type=password]:focus, input[type=date]:focus { border-color: #00bcd4; outline: none; }
        button { margin-top: 18px; width: 100%; padding: 10px; background: #00bcd4; color: #121212; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; font-weight: bold; }
        button:hover { background: #008ba3; }
        .error { color: #ff5252; font-size: 13px; margin-top: 10px; text-align: center; }
        .login-link { margin-top: 15px; text-align: center; font-size: 13px; }
        .login-link a { color: #00bcd4; text-decoration: none; font-weight: bold; }
        .login-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="register-box">
        <h1>הרשמה</h1>
        <form method="post" action="${pageContext.request.contextPath}/register">
            <label for="username">שם משתמש</label>
            <input type="text" id="username" name="username" required autofocus/>

            <label for="password">סיסמה</label>
            <input type="password" id="password" name="password" required/>

            <label for="birthdate">תאריך לידה</label>
            <input type="date" id="birthdate" name="birthdate" required/>

            <button type="submit">הירשם</button>

            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
        </form>
        <div class="login-link">
            כבר יש לך חשבון? <a href="${pageContext.request.contextPath}/login">התחבר כאן</a>
        </div>
    </div>
</body>
</html>