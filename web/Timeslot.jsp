<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="parts" scope="request" class="de.tum.in.dbpra.finalProject.model.bean.TimeslotBean"/>

<html>

<head>
    <title>Schedules</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        table .btn {
            vertical-align: center;
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

        .right {
            align-self: flex-start;
            color: #ffcc33;
            display: inline-block;
            font-size: 16px;
            font-weight: 300;
            margin: 20px 20px;
            text-transform: uppercase;
        }

        .logout li {
            text-align: right;
            color: #ffcc33;
            display: inline-block;
            font-size: 16px;
            font-weight: 300;
            margin: 20px 20px;
            text-transform: uppercase;
        }

        .homepage li {
            text-align: left;
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
    <div class="left"><a href="visitorHomePage.jsp">Back to the Home Page</a></div>

</div>

<% if (request.getAttribute("error") != null) { %>
<h1>Schedule not found!</h1>
<%= request.getAttribute("error") %>

<% } else { %>

<h3 align="center">Festival Schedule</h3>

<table style="width:40%" align="center">
    <thead>
    <tr>
        <th>Date</th>
        <th><a href="/timeslot?orderby=begintime">Begin</a></th>
        <th><a href="/timeslot?orderby=endtime">End</a></th>
        <th><a href="/timeslot?orderby=stageid">Stage</a></th>
        <th><a href="/timeslot?orderby=bandid">Band</a></th>
    </tr>
    </thead>
    <tbody align="center">
    <c:forEach var="Ftimeslot" items="${Ftimeslots}">
        <tr>
            <td><c:out value="${Ftimeslot.dateBegin}"/></td>
            <td><c:out value="${Ftimeslot.begintime}"/></td>
            <td><c:out value="${Ftimeslot.endtime}"/></td>
            <td><c:out value="${Ftimeslot.stageid}"/></td>
            <td><c:out value="${Ftimeslot.band}"/></td>
            <td>
                <form action="/timeslot" method="post" id = "insertButton" style="margin-top:20%;">
                    <input type="hidden" name="slotID" value="${Ftimeslot.timeslotid}" />
                    <input type="submit" value="Add" name="add" >
                </form>
            </td>

        </tr>
    </c:forEach>
    </tbody>
</table>
<br>



<h3 align="center">Individual Schedule</h3>

<table style="width:40%" align="center">
    <thead>
    <tr>
        <th>Date</th>
        <th><a href="/timeslot?iorderby=begintime">Begin</a></th>
        <th><a href="/timeslot?iorderby=endtime">End</a></th>
        <th><a href="/timeslot?iorderby=stageid">Stage</a></th>
        <th><a href="/timeslot?iorderby=bandid">Band</a></th>
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
            <td>
                <form action="/timeslot" method="post" id = "deleteButton" style="margin-top:20%;">
                    <input type="hidden" name="slotIDdel" value="${Itimeslot.timeslotid}" />
                    <input type="submit" value="Delete" name="delete">
                </form>
            </td>
        </tr>

    </c:forEach>
    </tbody>
</table>


<%}%>



</body>
</html>
