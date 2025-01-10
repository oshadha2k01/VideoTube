<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create your Google Account - YouTube</title>
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
            min-height: 100vh;
            margin: 0;
            color: var(--text-primary);
            padding: 20px;
        }

        .reg-container {
            width: 750px;
            display: flex;
            border: 1px solid var(--border-color);
            background-color: var(--card-bg);
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.3);
        }

        .form-side {
            flex: 1.5;
            padding: 48px 40px 36px;
        }

        .info-side {
            flex: 1;
            padding: 48px 40px;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            background-color: #020617;
        }

        .logo {
            color: var(--accent-color);
            font-size: 32px;
            margin-bottom: 12px;
            text-align: left;
            text-shadow: 0 0 10px rgba(56, 189, 248, 0.3);
        }

        h1 {
            font-size: 24px;
            font-weight: 400;
            margin: 0 0 8px;
            text-align: left;
        }

        .subtitle {
            font-size: 16px;
            margin-bottom: 32px;
            text-align: left;
        }

        .input-row {
            display: flex;
            gap: 16px;
            margin-bottom: 24px;
        }

        .input-group {
            flex: 1;
            position: relative;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px 15px;
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

        .input-hint {
            font-size: 12px;
            color: var(--text-secondary);
            margin-top: 4px;
            display: block;
        }

        .actions {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 40px;
        }

        .sign-in-link {
            color: var(--accent-color);
            font-weight: 500;
            font-size: 14px;
            text-decoration: none;
        }

        .btn-submit {
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

        .btn-submit:hover {
            background-color: var(--accent-hover);
            transform: scale(1.02);
        }

        .error {
            color: #d93025;
            font-size: 12px;
            margin-top: 4px;
        }

        .illustration {
            width: 100%;
            max-width: 200px;
            margin-bottom: 24px;
        }

        @media (max-width: 768px) {
            .reg-container {
                flex-direction: column;
                width: 100%;
                border: none;
            }
            .info-side {
                display: none;
            }
        }
    </style>
</head>
<body>
    <div class="reg-container">
        <div class="form-side">
            <div class="logo">
                <i class="fab fa-youtube"></i>
            </div>
            <h1>Create your VideoHub Account</h1>
            <p class="subtitle">Join the creator community</p>

            <form action="insert" method="post" id="regForm">
                <div class="input-row">
                    <div class="input-group">
                        <input type="text" name="fname" placeholder="First name" required>
                    </div>
                    <div class="input-group">
                        <input type="text" name="lname" placeholder="Last name" required>
                    </div>
                </div>

                <div class="input-group" style="margin-bottom: 24px;">
                    <input type="text" name="uname" placeholder="Username" required>
                    <span class="input-hint">You can use letters, numbers & periods</span>
                </div>

                <div class="input-group" style="margin-bottom: 24px;">
                    <input type="text" name="email" placeholder="Your email address" required>
                    <div id="emailError" class="error"></div>
                </div>

                <div class="input-row" style="margin-bottom: 12px;">
                    <div class="input-group">
                        <input type="password" name="passw" placeholder="Password" required>
                    </div>
                    <div class="input-group">
                        <input type="password" name="confirm" placeholder="Confirm" required>
                    </div>
                </div>
                <span class="input-hint" style="margin-bottom: 24px;">Use 8 or more characters with a mix of letters, numbers & symbols</span>

                <div class="input-group" style="margin-top: 24px;">
                    <input type="text" name="phone" placeholder="Phone number (10 digits)" required>
                    <div id="phoneError" class="error"></div>
                </div>

                <div class="actions">
                    <a href="login.jsp" class="sign-in-link">Sign in instead</a>
                    <button type="submit" class="btn-submit">Register</button>
                </div>
            </form>
        </div>
        <div class="info-side">
            <i class="fas fa-shield-alt" style="font-size: 64px; color: var(--google-blue); margin-bottom: 20px;"></i>
            <p style="text-align: center; font-size: 16px;">One account. All of VideoHub working for you.</p>
        </div>
    </div>

    <script>
        document.getElementById('regForm').addEventListener('submit', function(e) {
            const email = this.email.value;
            const phone = this.phone.value;
            const pass = this.passw.value;
            const confirm = this.confirm.value;
            
            let hasError = false;

            // Reset errors
            document.getElementById('emailError').textContent = '';
            document.getElementById('phoneError').textContent = '';

            if (!/\S+@\S+\.\S+/.test(email)) {
                document.getElementById('emailError').textContent = 'Enter a valid email address';
                hasError = true;
            }

            if (!/^\d{10}$/.test(phone)) {
                document.getElementById('phoneError').textContent = 'Phone number must be 10 digits';
                hasError = true;
            }

            if (pass !== confirm) {
                alert('Passwords do not match');
                hasError = true;
            }

            if (hasError) e.preventDefault();
        });
    </script>
</body>
</html>
