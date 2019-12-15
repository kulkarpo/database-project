<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="parts" scope="request" class="de.tum.in.dbpra.finalProject.model.bean.ApplicationBean"/>

<html>
<head>
    <title>Schedules</title>
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
    <div class="left"><a href="organizerHomePage.jsp">Back to the Home Page</a></div>

</div>

<h3 align="center">Festival Applications</h3>
<br>

<% if (request.getAttribute("error") != null) { %>
<h1>Application not found!</h1>
<%= request.getAttribute("error") %>

<% } else {%>

<div style="text-align:center">${message}</div>
<br>

<table style="width:57%" align="center">
    <thead>
    <tr>
        <th>Name</th>
        <th>Wished date</th>
        <th>Type</th>
        <th>Result</th>
        <th style="color:#575757;" colspan="4">Decision</th>
    </tr>
    </thead>
    <tbody align="center" style="text-transform: capitalize">
    <c:forEach var="Fapplication" items="${Fapplications}">
        <tr>
            <td><c:out value="${Fapplication.name}"/></td>
            <td><c:out value="${Fapplication.wisheddate}"/></td>
            <td><c:out value="${Fapplication.type}"/></td>
            <td><c:out value="${Fapplication.result}"/></td>
            <td>
                <form action="/application" method="post" id = "acceptButton" style="margin-top:20px;">
                    <input type="hidden" name="applicationID_accept" value="${Fapplication.applicationid}" />
                    <input type="submit" value="Accept" name="accept">
                </form>
            </td>
            <td>
                <form action="/application" method="post" id = "rejectButton" style="margin-top:20px;">
                    <input type="hidden" name="applicationID_reject" value="${Fapplication.applicationid}" />
                    <input type="submit" value="Reject" name="reject">
                </form>
            </td>
            <td>
            <form action="/application" method="post" id = "postponeButton" style="margin-top:20px;">
                <input type="hidden" name="applicationID_postpone" value="${Fapplication.applicationid}" />
                <input type="submit" value="Postpone" name="postpone">
            </form>
            </td>
            <td>
                <form action="/application" method="post" id = "detailsButton" style="margin-top:20px;">
                    <input type="hidden" name="applicationID_details" value="${Fapplication.applicationid}" />
                    <input type="submit" value="See Details" name="details">
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
