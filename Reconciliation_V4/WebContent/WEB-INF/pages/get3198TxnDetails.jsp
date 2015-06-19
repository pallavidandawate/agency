<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>3198 Transaction Details</title>

    <!-- Bootstrap -->
    <link href="atm/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
<h2><b><font color="blue">Transaction details of BGL 3198 linked to Agency Account ${bglAccountNumber}</font></b></h2>
<B>
   <table class="table table-striped table-bordered table-hover table-fixed">
    <tr>
        <th>AGENCY A/C NO</th>
        <td>${bglAccountNumber}</td>
    </tr>
    <tr>
        <th>Transaction Date</th>
        <td>${txnDate}</td>
    <tr>
    	<th>Total Debit</th>
    	<td>${TotalDebit}</td>
    </tr>
    </table>
    <table class="table table-striped table-bordered table-hover table-fixed">
    	<tr>
    		<th>BGL 3198 A/c No.</th><th>ATM ID</th>
    	</tr>
    	<c:forEach items="${atmList}" var="atm">
    		<tr>
    			<td> ${atm.bgl_3198.bglAccountNumber} </td><td>${atm.atmId}</td>		
	   		</tr>
    	
    	</c:forEach>
    </table>
    <h4><font color="red"> BGL 3198 Transaction Details </font></h4>
<table class="table table-striped table-bordered table-hover table-fixed">
<tr>
	<th>Txn Date</th>
	<th>BGL A/c No.</th>
	<th>Amount</th>
	<th>Journal Number</th>
</tr>



    	<c:forEach items="${bgl3198Txns}" var="txn">
    	  <c:choose>
    			<c:when test="${fn:length(txn.value) gt 0}">
    				<c:forEach items="${txn.value}" var="record">
						<tr>
							<td align="center"> <fmt:formatDate pattern="dd/MM/yyyy" value="${record.txnDate}"/></td>
							<td align="center"> <c:out value="${txn.key }"/> </td>
							<td align="right">  <c:out value="${record.txnKey.txnAmount}"/> </td>
							<td align="center">  <c:out value="${record.txnKey.journalNumber}"/> </td>
						</tr>
					</c:forEach>
			</c:when>
    		<c:otherwise>
       			<tr>
       					<td align="center"> <c:out value="${txnDate}"/> </td>
						<td align="center"> <c:out value="${txn.key }"/> </td>
						<td align="center">  <c:out value="Nil"/> </td>
						<td align="center">  <c:out value="Nil"/> </td>
       			
       			</tr>
   			 </c:otherwise>
			</c:choose>
   		</c:forEach>
</table>
<h4> <font color="red"> Admin Transaction Details </font></h4> 
<table class="table table-striped table-bordered table-hover table-fixed">
<tr>
	<th>Txn Date</th>
	<th>ATM ID</th>
	<th>Amount</th>
	<th>Journal Number</th>
</tr>

<c:forEach items="${AtmTxns}" var="atmTxn">

	 <c:choose>
    	<c:when test="${fn:length(atmTxn.value) gt 0}">
			<c:forEach items="${atmTxn.value}" var="record">
				<tr>
				<td align="center"> <fmt:formatDate pattern="dd/MM/yyyy" value="${record.adminKey.date}"/></td>
				<td align="center"> <c:out value="${atmTxn.adminKey.atm }"/> </td>
				<td align="right">  <c:out value="${record.amount}"/> </td>
				<td align="center">  <c:out value="${record.adminKey.time}"/> </td>
			</tr>
		</c:forEach>
		</c:when>
		<c:otherwise>
			<tr><td align="center"> <c:out value="${txnDate}"/> </td>
				<td align="center"> <c:out value="${atmTxn.adminKey.atm}"/> </td>
				<td align="center">  <c:out value="Nil"/> </td>
				<td align="center">  <c:out value="Nil"/> </td>
			</tr>
		</c:otherwise>
		</c:choose>
</c:forEach>
</table>
</B>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="atm/js/jquery/jquery-1.11.3.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="atm/js/bootstrap/bootstrap.min.js"></script>
   </div>
</body>
</html>