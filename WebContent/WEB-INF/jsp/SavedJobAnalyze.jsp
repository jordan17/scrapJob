<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="per.qoq.scrap.jobsdb.enu.AgentEnum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.11/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<style>
  .overflow { height: 200px; }
  .company-list {height:300px;}
  #cpyTable {width : 50%;}
  .checkBoxes {display:inline-block;}
  .select {background-color:#AAAAAA;}
  </style>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/u/dt/dt-1.10.12/datatables.min.js"></script>
<script type="text/javascript" >
	var jobTable;
	var dateTable_option = {paging:true,autoWidth:true,pageLength:100,lengthChange:true};
	$(document).ready(function(){
		jobTable=$('#jobTable').dataTable(dateTable_option);
		$('#cpyTable').dataTable({paging:true});
		$('#dateBefore').datepicker();
		$('#dateAfter').datepicker();
		
		<%--$('#companyList').selectmenu().selectmenu('menuWidget').addClass('overflow');--%>
		});
</script>
</head>
<body>
<div id="head"><jsp:include page="menu.jsp"></jsp:include></div>
		<table id="jobTable" class="display" cellspacing="0">
			<thead>
				<tr><th>Title</th>
					<th>Company</th>
					<th>Saved Date</th>
					<th>Last Hit</th>
					<th>Hit Count</th>
					<th>Latest URL</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="job" items="${jobList}">
				<tr>
					<td>${job.jobTtile}</td>
					<td>${job.company}</td>
					<td><fmt:formatDate value="${job.datePosted}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${job.lastHit}" pattern="yyyy-MM-dd" /></td>
					<td>${job.hitCount}</td>
					<td><a href="${job.url}">Click</a></td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
</body>
</html>