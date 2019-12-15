<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="parts" scope="request" class="de.tum.in.dbpra.finalProject.model.bean.ApplicationBean"/>


<html>
<head>
    <title>Detailed view</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
        }
        .headers {
            font-weight: bold;
            padding-top: 10px;
            padding-bottom: 10px;
            padding-left: 10px;
        }

        .elems {
            padding-left: 10px;
            text-transform: capitalize;
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
    <div class="left"><a href="application">Back to all Applications</a></div>

</div>


<% if (request.getAttribute("error") != null) { %>
<h1>Application not found!</h1>
<%= request.getAttribute("error") %>

<% } else {%>

<br>

<h3 align="center">Detailed view</h3>

<div style="width:40%; margin:auto">
    <div>
        <table align="center" width="100%">
            <colgroup>
                <col style="width:30%">
                <col style="width:70%">
            </colgroup>
            <tr>
                <td class="headers">Name</td>
                <td class="elems"><c:out value="${Details[0].name}"/></td>
            </tr>
            <tr>
                <td class="headers">Wished date</td>
                <td class="elems"><c:out value="${Details[0].wisheddate}" /></td>
            </tr>
            <tr>
                <td class="headers">Type</td>
                <td class="elems"><c:out value="${Details[0].type}" /></td>
            </tr>
            <tr>
                <td class="headers">Contact</td>
                <td class="elems"><c:out value="${Details[0].contact}" /></td>
            </tr>
            <tr>
                <td class="headers">Address</td>
                <td class="elems"><c:out value="${Details[0].address}" /></td>
            </tr>
            <tr>
                <td class="headers">Result</td>
                <td class="elems"><c:out value="${Details[0].result}" /></td>
            </tr>
            <tr>
                <td class="headers">Letter</td>
                <td class="elems"><c:out value="${Details[0].letter}" /></td>
            </tr>
        </table>
    </div>
    <br>
    <div>
        <ul style="float:right; list-style-type:none">
            <li style="float:right">
                <form action="/application" method="post" id = "postponeButton">
                    <input type="hidden" name="applicationID_postpone" value="${Details[0].applicationid}" />
                    <input type="submit" value="Postpone" name="postpone">
                </form>
            </li>
            <li style="float:right">
                <form action="/application" method="post" id = "rejectButton">
                    <input type="hidden" name="applicationID_reject" value="${Details[0].applicationid}" />
                    <input type="submit" value="Reject" name="reject">
                </form>
            </li>
            <li style="float:right">
                <form action="/application" method="post" id = "acceptButton">
                    <input type="hidden" name="applicationID_accept" value="${Details[0].applicationid}" />
                    <input type="submit" value="Accept" name="accept">
                </form>
            </li>
        </ul>
    </div>
</div>

<%}%>

<div class="left">
    <a href="organizerHomePage.jsp">Back to the Home Page</a>

</div>


</body>
</html>
