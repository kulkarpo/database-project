<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="parts" scope="request" class="de.tum.in.dbpra.finalProject.model.bean.TimeslotBean"/>


<html>
<head>
    <title>Updated Individual Timetable</title>
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
    <div class="left"><a href="timeslot">Back to Schedules</a></div>

</div>

<% if (request.getAttribute("error") != null) { %>
<h1>Schedule not found!</h1>
<%= request.getAttribute("error") %>

<% } else {%>
<div style="text-align: center; padding-top: 20px">

${message}
</div>
<br>

<h3 align="center">Individual Schedule</h3>

<table style="width:40%" align="center">
    <thead>
    <tr>
        <th>Date</th>
        <th>Begin</th>
        <th>End</th>
        <th>Stage</th>
        <th>Band</th>
    </tr>
    </thead>
    <tbody align="center">
    <c:forEach var="Itimeslot" items="${Itimeslots}">
        <tr>
            <td><c:out value="${Itimeslot.dateBegin}"/></td>
            <td><c:out value="${Itimeslot.begintime}"/></td>
            <td><c:out value="${Itimeslot.endtime}"/></td>
            <td><c:out value="${Itimeslot.stageid}"/></td>
            <td><c:out value="${Itimeslot.band}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%}%>


    <div class="left"><a href="visitorHomePage.jsp">Back to the Home Page</a></div>


</body>
</html>
