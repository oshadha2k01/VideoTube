<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>User Account Delete</title>
    <style>
        body {
        background: url("image1/jj.jpg");
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            text-align: center;
        }
        .container {
            background-color: #fff;
            border: 1px solid #ccc;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            width: 300px;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            margin: 5px 0;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        input[type="submit"] {
            background-color: #ff0000;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #cc0000;
        }
    </style>
</head>
<body>

 <%
       String uID = request.getParameter("id");
       String uFirstName = request.getParameter("fname");
       String uLastName = request.getParameter("lname");
       String uName = request.getParameter("uname");
       String uEmail = request.getParameter("email");
       String uPhoneNo = request.getParameter("phone");
       
    %>
    
     <div class="container">
    <h1>Delete Account</h1>
    <form action="delete" method="post">
        <label for="id">UserID</label>
        <input type="text" name="id" value="<%= uID %>" readonly>

        <label for="fname">FirstName</label>
        <input type="text" name="fname" value="<%= uFirstName %>" readonly>

        <label for="lname">LastName</label>
        <input type="text" name="lname" value="<%= uLastName %>" readonly>

        <label for="uname">UserName</label>
        <input type="text" name="uname" value="<%= uName %>" readonly>

        <label for="email">Email</label>
        <input type="text" name="email" value="<%= uEmail %>" readonly>

        <label for="phone">PhoneNo</label>
        <input type="text" name="phone" value="<%= uPhoneNo %>" readonly>

        <input type="submit" name="submit" value="Delete Account">
    </form>
</div>

</body>
</html>