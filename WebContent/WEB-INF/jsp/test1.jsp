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
  .smallCell { width:5%;}
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
		$('#companySearchBtn').on("click",function(){
			var text = $('#companyName').val();
			getCompanyJob(text);});
		$( "#AgentBtn" ).click(function( event ) {
			  event.preventDefault();
			  filterAgentJob();
		});
		/* $('#AgentBtn').on("click",function(){
			this.preventDefault();filterAgentJob();}); */
		
		<%--$('#companyList').selectmenu().selectmenu('menuWidget').addClass('overflow');--%>
		});
	
</script>
<script type="text/javascript" src="resources/js/jobTable.js"></script>
</head>
<body>
<div id="head"><jsp:include page="menu.jsp"></jsp:include></div>
<form action="${pageContext.request.contextPath}/service/filterByDate" method="post" >
	<input type="text" name="dateAfter" id="dateAfter"/> <input type="text" name="dateBefore" id="dateBefore"/>
	<input type="submit" value="filter" />
</form>
<div>
<input type="text" id="companyName"/>
	<input type="button" value="filter" id="companySearchBtn"/>
	</div>
<form action="${pageContext.request.contextPath}/service/filterAgent" method="post">
	<input type="checkbox" name="true_False" id="true_False" class="checkBoxes" value="true">Filter agent</input>
	<input type="checkbox" name="PCCW" id="PCCW" class="checkBoxes" value="true">Filter PCCW</input>
	<input id="AgentBtn" type="submit"value="Submit"/>
</form>
<form method="post" action="${pageContext.request.contextPath}/service/filterBySkill">
	<span name="skills" class="select">
	<c:forEach var="skill" items="${skillList}">
		<input type="checkbox" name="skill" class="checkBoxes" value="${skill}">${skill}</input>
	</c:forEach>
	<input type="submit"value="Submit"/>
	</span>
</form>
		<table id="jobTable" class="display" cellspacing="0">
			<thead>
				<tr><th class="smallCell">save</th> 
					<th class="smallCell">hate</th>
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
					<td><c:if test="${job.hated==true}">
					<span class="ui-icon ui-icon-star" onclick="deleteHateJob('${job.jobId}',$(this));"/>
					</c:if>
					<c:if test="${job.hated==false}">
					<span class="ui-icon ui-icon-plus" onclick="hateJob('${job.jobId}',$(this));"/>
					</c:if></td>
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
</body>
</html>