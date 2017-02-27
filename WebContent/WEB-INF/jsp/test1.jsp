<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page import="per.qoq.scrap.jobsdb.enu.AgentEnum" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Homepage</title>

<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
  .overflow { height: 200px; }
  .company-list {height:300px;}
  #cpyTable {width : 50%;}
  .checkBoxes {display:inline-block;}
  .select {background-color:#AAAAAA;}
  .smallCell { width:5%;}
  .pointer {background-color:#AA00AA;}
  </style>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/bs-3.3.7/dt-1.10.13/datatables.min.css"/>
<script type="text/javascript" src="https://cdn.datatables.net/v/bs-3.3.7/dt-1.10.13/datatables.min.js"></script>

<script type="text/javascript" >
	var jobTable;
	var cmpTable;
	var jobMap = {};
	var dateTable_option = {paging:true,autoWidth:true,pageLength:25,lengthChange:true};
	$(document).ready(function(){
		jobTable=$('#jobTable').dataTable(dateTable_option);
		cmpTable=$('#cpyTable').dataTable({paging:true});
		$('#dateBefore').datepicker();
		$('#dateAfter').datepicker();
		$('#companySearchBtn').on("click",function(){
			var text = $('#companyName').val();
			getCompanyJob(text);});
		$( "#AgentBtn" ).click(function( event ) {
			  event.preventDefault();
			  filterAgentJob();
		});
		$( "#filterByDateBtn" ).click(function( event ) {
			  event.preventDefault();
			  filterByDate();
		});
		$( "#hateBtn" ).click(function( event ) {
			  event.preventDefault();
			  filterHateJob();
		});
		/* $('#AgentBtn').on("click",function(){
			this.preventDefault();filterAgentJob();}); */
		
		<%--$('#companyList').selectmenu().selectmenu('menuWidget').addClass('overflow');--%>
		});
	
</script>
<script type="text/javascript" src="resources/js/jobTable.js"></script>
</head>
<body>
<div class="container">
<div id="head"><jsp:include page="menu.jsp"></jsp:include></div>
<div class="form-group">
	<form action="${pageContext.request.contextPath}/service/filterByDateJSON" method="post" >
		<label>Date range:</label>
		<input type="text" name="dateAfter" id="dateAfter" class="form-control"/> <input type="text" name="dateBefore" id="dateBefore" class="form-control"/>
		<input id="filterByDateBtn" type="submit" value="filter" />
	</form>
</div>
<div>
	<label>Specific Company:</label><br/>
	<input type="text" id="companyName" /><input type="button" value="filter" id="companySearchBtn" class="input-sm"/>
	</div>
<div class="form-group" style="margin-top:20px">
	<form action="${pageContext.request.contextPath}/service/filterAgent" method="post">
		<input type="checkbox" name="true_False" id="true_False" class="checkBoxes form_control" value="true">Filter agent</input>
		<input type="checkbox" name="PCCW" id="PCCW" class="checkBoxes form_control" value="true">Filter Agent</input>
		<input id="AgentBtn" type="submit" value="Submit"/>
	</form>
</div>
<%-- <form action="" method="post">
	<input type="checkbox" name="filterHate" id="filterHate" class="checkBoxes" value="true">Filter Hated</input>
	<input id="hateBtn" type="submit"value="Submit"/>
</form> --%>
<%-- <form method="post" action="${pageContext.request.contextPath}/service/filterBySkill">
	<span name="skills" class="select">
	<c:forEach var="skill" items="${skillList}">
		<input type="checkbox" name="skill" class="checkBoxes" value="${skill}">${skill}</input>
	</c:forEach>
	<input type="submit"value="Submit"/>
	</span>
</form> --%>
		<table id="jobTable" class="display" cellspacing="0">
			<thead>
				<tr><th class="smallCell">save</th> 
					<!-- <th class="smallCell">hate</th> -->
					<th>Title</th>
					<th>Company</th>
					<th class="smallCell"></th>
					<th>Date</th>
					<th class="smallCell">URL</th>
					</tr>
			</thead>
			<tbody>
			<c:set var="count" value="1" scope="page"/>
			<c:forEach var="job" items="${jobList}">
				<tr>
					<td ><c:if test="${job.saved==true}">
					<span class="ui-icon ui-icon-star" onclick="deleteSavedJob('${job.jobId}',$(this));"/>
					</c:if>
					<c:if test="${job.saved==false}">
					<span class="ui-icon ui-icon-plus" onclick="saveJob('${job.jobId}',$(this));"/>
					</c:if></td>
					<%-- <td><c:if test="${job.hated==true}">
					<span class="ui-icon ui-icon-star" onclick="deleteHateJob('${job.jobId}',$(this));"/>
					</c:if>
					<c:if test="${job.hated==false}">
					<span class="ui-icon ui-icon-plus" onclick="hateJob('${job.jobId}',$(this));"/>
					</c:if></td> --%>
					<td><div onclick="$('#content-${count}').dialog({width:'80%',closeOnEscape:true}).show()">
					<span style="${job.saved==true?'color:red;':''}${job.hated==true?'color:blue;':''}">${job.jobTtile}</span>
					</div></td>
					<td class="company" style="${job.manyJobs==true?'color:red;':''}" >
					<!--  onclick="getCompanyJob('${job.company}')" -->
					${job.company}</td>
					<td><span class="ui-icon ui-icon-closethick" style="display:inline-block" onclick='removeCompany($(this).parent().prev("td.company").text())'></span></td>
					<td><fmt:formatDate value="${job.datePosted}" pattern="yyyy-MM-dd" /></td>
					<td><a href="${job.url}">click</a></td>
				</tr>
				<c:set var="count" value="${count + 1}" scope="page"/>
			</c:forEach>
			</tbody>
		</table>
		<%-- <form:form action="${pageContext.request.contextPath}/service/filterCompany" method="post"  modelAttribute="filterCompany">
			<form:select multiple="multiple" path="companys" class="company-list" label="Filter">
				<c:forEach var="company" items="${companyList}">
					<form:option  value="${company}">${company}</form:option>
				</c:forEach>
			</form:select>
			<input type="submit" name="submit1" value="submit"/>
		</form:form>
		--%>
		<%-- <%-- <form:form action="${pageContext.request.contextPath}/service/excludeCompany" method="post"  modelAttribute="filterCompany">
			<form:select multiple="multiple" path="companys" class="company-list" label="Exclude">
				<c:forEach var="company" items="${companyList}">
					<form:option  value="${company}">${company}</form:option>
				</c:forEach>
			</form:select>
			<input type="submit" name="submit2" value="submit"/>
		</form:form>
		 --%>
		<c:set var="count" value="1" scope="page"/>
		<c:forEach var="job" items="${jobList}">
			<div style="display:none" id="content-${count}"><span>${job.experience}</span><span>${job.jobDesc}</span></div>
			<c:set var="count" value="${count + 1}" scope="page"/>
		</c:forEach> 
		<div></div>
		<table id="cpyTable" class="display" cellspacing="0">
			<thead>
				<tr>
					<th>Company</th>
					<th>Count</th>
				</tr>
			</thead>
			<tbody>
			<c:forEach var="cmp" items="${companyCount}">
				<tr>
					<td>${cmp.key}</td>
					<td>${cmp.value}</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<div id="jobContentDialog" style="display:none"></div>
</div>
</body>
</html>