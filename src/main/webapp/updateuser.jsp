<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Update User Details</title>
    <style>
        body {
         background: url("image1/jj.jpg");
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            text-align: center;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }
        .header {
            font-size: 28px;
            font-weight: bold;
            }
            
        form {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: left;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .button-container {
            text-align: center;
        }
        input[type="submit"] {
            background-color: #007BFF;
            color: #fff;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    
    <div class="header">Update Profile</div>

    <%
    
       String uID = request.getParameter("id");
       String uFirstName = request.getParameter("fname");
       String uLastName = request.getParameter("lname");
       String uName = request.getParameter("uname");
       String uEmail = request.getParameter("email");
       String uPhoneNo = request.getParameter("phone");
       
    %>
    
    
    
    <form action="update" method="post">
        <label for="id">UserID</label>
        <input type="text" id="id" name="id" value="<%= uID %>" readonly>
        <br>
        <label for="fname">First Name</label>
        <input type="text" id="fname" name="fname" value="<%= uFirstName %>">
        <br>
        <label for="lname">Last Name</label>
        <input type="text" id="lname" name="lname" value="<%= uLastName %>">
        <br>
        <label for="uname">User Name</label>
        <input type="text" id="uname" name="uname" value="<%= uName %>">
        <br>
        <label for="pass">Password</label>
        <input type="password" id="pass" name="pass" placeholder="Leave blank to keep current password">
        <br>
        <label for="email">Email</label>
        <input type="text" id="email" name="email" value="<%= uEmail %>">
        <br>
        <label for="phone">Phone Number</label>
        <input type="text" id="phone" name="phone" value="<%= uPhoneNo %>">
        <br>
        <div class="button-container">
            <input type="submit" name="submit" value="Update Account">
        </div>
    </form>
</body>
</html>
