<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="parts" scope="request" class="de.tum.in.dbpra.finalProject.model.bean.TimeslotBean"/>

<html>

<head>
    <title>Products</title>
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
    <ul>
        <li><a href="providerHomePage.jsp">BACK TO HOME PAGE</a></li>
        <li><a href="addNewProduct.jsp">ADD PRODUCT</a></li>
        <li><a href="logout">LOGOUT</a></li>
    </ul>
</div>




<% if (request.getAttribute("error") != null) { %>
<h1>Products not found!</h1>
<%= request.getAttribute("error") %>

<% } else { %>

<h3 align="center" style="padding-top:30px">Products</h3>
<div style="text-align:center">${message}</div>

<table style="width:40%" align="center">
    <thead>
    <tr>
        <th><a href="/product?orderby=name" style="text-align: center; margin:0 auto; display:inline-block">Product Name</a></th>
        <th><a href="/product?orderby=price" style="text-align: center; margin:0 auto; display:inline-block">Unit Price</a></th>
        <th>Registered Quantity</th>
        <th>Remaining Quantity</th>
        <th style="color:#575757;">Add Quantity</th>
    </tr>
    </thead>
    <tbody align="center">
    <c:forEach var="RProducts" items="${RProducts}">
        <tr>
            <td><c:out value="${RProducts.productName}"/></td>
            <td><c:out value="${RProducts.unitPrice}"/></td>
            <td><c:out value="${RProducts.registeredQuantity}"/></td>
            <td><c:out value="${RProducts.remainingQuantity}"/></td>

            <td>
                <form action="/product" method="post" id = "insertButton">
                    <input style="margin-top:5px" type="number" min=1 name="quantity" placeholder="Quantity" required>
                    <input style="margin-top:5px" type="hidden" name="productid" value="${RProducts.productId}" />
                    <input style="margin-top:5px" type="submit" value="Add" name="add">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<br>

<%}%>

</body>
</html>
