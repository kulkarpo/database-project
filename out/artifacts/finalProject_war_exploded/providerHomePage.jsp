<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Home Page</title>
    <link href="https://fonts.googleapis.com/css?family=Raleway" type="text/css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="style.css">

</head>
<body>

<%
    if(session.getAttribute("username") == null) {
        response.sendRedirect("/");
    }else if(!session.getAttribute("type").equals("provider")){
        response.sendRedirect("/");
    }

%>

<div class="header">
    <h1>Welcome!</h1>
</div>

<div class="navigation">
    <ul>
        <li><a href="product">Products</a></li>
        <li><a href="logout">Logout</a></li>
    </ul>
</div>



<div class="credentials">
    <p>Team 1</p>
</div>


</body>
</html>