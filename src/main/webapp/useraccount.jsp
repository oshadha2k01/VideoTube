<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Account - VideoHub</title>
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style type="text/css">
        :root {
            --bg-color: #05060f;
            --text-primary: #ffffff;
            --text-secondary: #94a3b8;
            --hover-color: #1e293b;
            --accent-color: #38bdf8;
            --accent-red: #ff4e4e;
            --border-color: #1e293b;
            --card-bg: #0a0c1a;
        }

        * {
            box-sizing: border-box;
            font-family: 'Roboto', sans-serif;
            margin: 0;
            padding: 0;
        }

        body {
            background-color: var(--bg-color);
            color: var(--text-primary);
            min-height: 100vh;
            padding: 40px;
        }

        .container {
            max-width: 900px;
            margin: 0 auto;
        }

        .header {
            display: flex;
            align-items: center;
            gap: 24px;
            margin-bottom: 40px;
        }

        .avatar {
            width: 80px;
            height: 80px;
            background-color: var(--accent-color);
            color: #05060f;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 36px;
            font-weight: bold;
            box-shadow: 0 0 20px rgba(56, 189, 248, 0.3);
        }

        .user-info h1 {
            font-size: 32px;
            margin-bottom: 4px;
        }

        .user-info p {
            color: var(--text-secondary);
            font-size: 14px;
        }

        .profile-card {
            background-color: var(--card-bg);
            border-radius: 12px;
            overflow: hidden;
            border: 1px solid var(--border-color);
            margin-bottom: 30px;
        }

        .card-header {
            padding: 20px 24px;
            border-bottom: 1px solid var(--border-color);
            font-size: 18px;
            font-weight: 500;
        }

        .card-body {
            padding: 24px;
        }

        .detail-row {
            display: flex;
            padding: 12px 0;
            border-bottom: 1px solid #2a2a2a;
        }

        .detail-row:last-child {
            border-bottom: none;
        }

        .detail-label {
            width: 200px;
            color: var(--text-secondary);
            font-size: 14px;
        }

        .detail-value {
            flex: 1;
            font-size: 14px;
        }

        .actions {
            display: flex;
            gap: 16px;
            margin-top: 20px;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 20px;
            font-size: 14px;
            font-weight: 500;
            cursor: pointer;
            text-decoration: none;
            display: inline-block;
            transition: 0.2s;
            border: none;
        }

        .btn-primary {
            background-color: var(--accent-color);
            color: #05060f;
            font-weight: 600;
        }

        .btn-primary:hover {
            background-color: #0ea5e9;
            transform: scale(1.05);
        }

        .btn-danger {
            background-color: transparent;
            color: var(--accent-red);
            border: 1px solid var(--accent-red);
        }

        .btn-danger:hover {
            background-color: rgba(255, 78, 78, 0.1);
        }

        .back-link {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            color: var(--text-secondary);
            text-decoration: none;
            margin-bottom: 24px;
            font-size: 14px;
        }

        .back-link:hover {
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <a href="home.jsp" class="back-link">
            <i class="fas fa-arrow-left"></i> Back to Home
        </a>

        <c:forEach var="us" items="${usDetails}">
            <c:set var="id" value="${us.uID}"/>
            <c:set var="fname" value="${us.uFirstName}"/>
            <c:set var="lname" value="${us.uLastName}"/>
            <c:set var="username" value="${us.uName}"/>
            <c:set var="email" value="${us.uEmail}"/>
            <c:set var="phone" value="${us.uPhoneNo}"/>

            <div class="header">
                <div class="avatar">${username.substring(0,1).toUpperCase()}</div>
                <div class="user-info">
                    <h1>${us.uFirstName} ${us.uLastName}</h1>
                    <p>@${us.uName} • ID: ${us.uID}</p>
                </div>
            </div>

            <div class="profile-card">
                <div class="card-header">Personal Information</div>
                <div class="card-body">
                    <div class="detail-row">
                        <div class="detail-label">First Name</div>
                        <div class="detail-value">${us.uFirstName}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Last Name</div>
                        <div class="detail-value">${us.uLastName}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">User Name</div>
                        <div class="detail-value">${us.uName}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Email</div>
                        <div class="detail-value">${us.uEmail}</div>
                    </div>
                    <div class="detail-row">
                        <div class="detail-label">Phone Number</div>
                        <div class="detail-value">${us.uPhoneNo}</div>
                    </div>
                </div>
            </div>

            <div class="actions">
                <c:url value="updateuser.jsp" var="usupdate">
                    <c:param name="id" value="${id}" />
                    <c:param name="fname" value="${fname}" />
                    <c:param name="lname" value="${lname}" />
                    <c:param name="uname" value="${username}" />
                    <c:param name="email" value="${email}" />
                    <c:param name="phone" value="${phone}" />
                </c:url>
                <a href="${usupdate}" class="btn btn-primary">Edit Profile</a>

                <c:url value="deleteuser.jsp" var="usdelete">
                    <c:param name="id" value="${id}" />
                    <c:param name="fname" value="${fname}" />
                    <c:param name="lname" value="${lname}" />
                    <c:param name="uname" value="${username}" />
                    <c:param name="email" value="${email}" />
                    <c:param name="phone" value="${phone}" />
                </c:url>
                <a href="${usdelete}" class="btn btn-danger">Delete Account</a>
            </div>
        </c:forEach>
    </div>
</body>
</html>
