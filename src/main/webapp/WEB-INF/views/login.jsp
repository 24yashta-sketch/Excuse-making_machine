<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>מחולל התירוצים - התחברות</title>
    <style>
        body { font-family: Arial, sans-serif; background: #121212; color: #e0e0e0; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; direction: rtl; }
        .login-box { background: #1e1e1e; padding: 30px 40px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.5); width: 300px; text-align: right; border: 1px solid #2c2c2c; }
        h1 { font-size: 20px; margin-top: 0; color: #ffffff; text-align: center; }
        label { display: block; margin-top: 12px; margin-bottom: 4px; font-size: 14px; color: #b0b0b0; }
        input[type=text], input[type=password] { width: 100%; padding: 10px; box-sizing: border-box; background: #2c2c2c; border: 1px solid #444; color: #fff; border-radius: 4px; }
        input[type=text]:focus, input[type=password]:focus { border-color: #00bcd4; outline: none; }
        button { margin-top: 18px; width: 100%; padding: 10px; background: #00bcd4; color: #121212; border: none; border-radius: 4px; cursor: pointer; font-size: 16px; font-weight: bold; }
        button:hover { background: #008ba3; }
        .error { color: #ff5252; font-size: 13px; margin-top: 10px; text-align: center; }
        .register-link { margin-top: 15px; text-align: center; font-size: 13px; }
        .register-link a { color: #00bcd4; text-decoration: none; font-weight: bold; }
        .register-link a:hover { text-decoration: underline; }
    </style>
</head>
<body>
    <div class="login-box">
        <h1>התחברות</h1>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <label for="username">שם משתמש</label>
            <input type="text" id="username" name="username" required autofocus/>

            <label for="password">סיסמה</label>
            <input type="password" id="password" name="password" required/>

            <button type="submit">הכנס למערכת</button>

            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
        </form>
        <div class="register-link">
            אין לך חשבון עדיין? <a href="${pageContext.request.contextPath}/register">הירשם כאן</a>
        </div>
    </div>
</body>
</html>