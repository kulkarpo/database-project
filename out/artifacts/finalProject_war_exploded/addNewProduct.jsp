<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="parts" scope="request" class="de.tum.in.dbpra.finalProject.model.bean.TimeslotBean"/>

<html>

<head>
    <title>Product addition</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        .logout {
            position: fixed;
            left: 0;
            top: 0;
            width: 100%;
            z-index: 1000;
            height:80px;
            display: flex;
            flex-direction: row-reverse;
            justify-content: space-between;
            align-items:center;
        }

        .left {
            color: #ffcc33;
            display: inline-block;
            font-size: 16px;
            font-weight: 300;
            margin: 20px 20px;
            text-transform: uppercase;
        }
    </style>
    <link href="https://fonts.googleapis.com/css?family=Raleway" type="text/css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="style.css">
</head>
<body>

<div class="logout">
    <div class="left"><a href="logout">Logout</a></div>
    <div class="left"><a href="product">Back to the Product Page</a></div>
</div>

<% if (request.getAttribute("error") != null) { %>
<h1>Products not found!</h1>
<%= request.getAttribute("error") %>

<% } else { %>

<h3 align="center" style="padding-top:30px">Add a New Product</h3>

<div style="width:40%; margin:auto">
    <div>
        <form action="/addproduct" method="post" id = "addproduct">
        <table align="center" width="100%">
            <colgroup>
                <col style="width:30%">
                <col style="width:70%">
            </colgroup>
            <tr>
                <td class="headers">Name</td>
                <td><input style="margin-top:5px" name="productname" placeholder="Product Name" required></td>
            </tr>
            <tr>
                <td class="headers">Unit-Price</td>
                <td><input style="margin-top:5px" type="number" min=0.01 step="0.01" name="unitprice" placeholder="Unit Price" required></td>
            </tr>
            <tr>
                <td class="headers">Vendor</td>
                <td><input style="margin-top:5px" name="vendor" placeholder="Vendor if any"></td>
            </tr>
            <tr>
                <td class="headers">Product Type</td>
                <td>
                <select id="type" name="type" required>
                    <option value="merchandise">Merchandise</option>
                    <option value="food">Food</option>
                    <option value="drink">Drink</option>
                    <option value="tobacco">Tobacco</option>
                    <option value="camping-supplies">Camping-supplies</option>
                </select>
                </td>
            </tr>
            <tr>
                <td class="headers">Quantity</td>
                <td><input style="margin-top:5px" type="number" min=1 name="quantity" placeholder="Quantity" required></td>
            </tr>
            <tr>
                <td class="headers">Extra Description</td>
                <td><input style="margin-top:5px" name="extradescription" placeholder="Extra Description"></td>
            </tr>
        </table>
            <input type="submit" value="save" name="save" align="center">
        </form>
    </div>
    <br>
</div>

<div class="left"><a href="providerHomePage.jsp">Back to the Home Page</a></div>

<%}%>

</body>
</html>
