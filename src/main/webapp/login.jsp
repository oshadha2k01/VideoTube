<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign in - YouTube</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style type="text/css">
        :root {
            --accent-color: #38bdf8;
            --accent-hover: #0ea5e9;
            --text-primary: #ffffff;
            --text-secondary: #94a3b8;
            --border-color: #1e293b;
            --bg-color: #05060f;
            --card-bg: #0a0c1a;
        }

        * {
            box-sizing: border-box;
            font-family: 'Roboto', sans-serif;
        }

        body {
            background-color: var(--bg-color);
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            color: var(--text-primary);
        }

        .login-card {
            width: 450px;
            padding: 48px 40px 36px;
            border: 1px solid var(--border-color);
            background-color: var(--card-bg);
            border-radius: 12px;
            text-align: center;
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3);
        }

        .logo {
            color: var(--accent-color);
            font-size: 48px;
            margin-bottom: 12px;
            text-shadow: 0 0 15px rgba(56, 189, 248, 0.3);
        }

        h1 {
            font-size: 24px;
            font-weight: 500;
            margin: 0 0 8px;
        }

        .subtitle {
            font-size: 16px;
            margin-bottom: 40px;
        }

        .input-group {
            position: relative;
            margin-bottom: 24px;
            text-align: left;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 13px 15px;
            font-size: 16px;
            background-color: #020617;
            border: 1px solid var(--border-color);
            color: white;
            border-radius: 6px;
            outline: none;
            transition: all 0.2s;
        }

        input:focus {
            border: 1px solid var(--accent-color);
            box-shadow: 0 0 0 2px rgba(56, 189, 248, 0.2);
        }

        .forgot-link {
            color: var(--accent-color);
            font-weight: 500;
            font-size: 14px;
            text-decoration: none;
            display: block;
            text-align: left;
            margin-bottom: 40px;
            cursor: pointer;
        }

        .actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .create-account {
            color: var(--accent-color);
            font-weight: 500;
            font-size: 14px;
            text-decoration: none;
            cursor: pointer;
        }

        .btn-next {
            background-color: var(--accent-color);
            color: #05060f;
            padding: 10px 24px;
            border: none;
            border-radius: 6px;
            font-size: 14px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.2s;
        }

        .btn-next:hover {
            background-color: var(--accent-hover);
            transform: scale(1.02);
        }

        @media (max-width: 450px) {
            .login-card {
                width: 100%;
                height: 100vh;
                border: none;
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="login-card">
        <div class="logo">
            <i class="fab fa-youtube"></i>
        </div>
        <h1>Sign in</h1>
        <p class="subtitle">to continue to VideoHub</p>
        
        <form action="login" method="post">
            <div class="input-group">
                <input type="text" id="uid" name="uid" placeholder="Username" required>
            </div>

            <div class="input-group">
                <input type="password" id="pass" name="pass" placeholder="Password" required>
            </div>

            <a href="#" class="forgot-link">Forgot password?</a>

            <div class="actions">
                <a href="userinsert.jsp" class="create-account">Create account</a>
                <button type="submit" class="btn-next">Login</button>
            </div>
        </form>
    </div>
</body>
</html>
