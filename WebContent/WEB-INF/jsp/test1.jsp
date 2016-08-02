<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="BIG5"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

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
  </style>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/u/dt/dt-1.10.12/datatables.min.js"></script>
<script type="text/javascript" >
	var jobTable;
	
	$(document).ready(function(){
		jobTable=$('#jobTable').dataTable({paging:true,autoWidth:true,pageLength:100});
		$('#cpyTable').dataTable({paging:true});
		$('#dateBefore').datepicker();
		$('#dateAfter').datepicker();
		
		<%--$('#companyList').selectmenu().selectmenu('menuWidget').addClass('overflow');--%>
		});
	function filterByDate() {
			var data = {'dateAfter':$('#dateBefore').val(),'dateBefore':$('#dateAfter').val()};
			$.post("${pageContext.request.contextPath}/service/filterByDate",data);
		}
	function saveJob(jj,ref) {
			$.ajax({type:"POST",
				url:"${pageContext.request.contextPath}/service/saveJob",
				data:{objId:jj},
				dataType: 'json',
				success: function(res){ console.log(res);
				ref.removeClass('ui-icon-plus').addClass('ui-icon-star');
				ref.prop('onclick',null).off('click').click(function(){deleteSavedJob(jj,ref);});
				},
				error:function( jqXHR,  textStatus,  errorThrown ){
		            alert('error '+errorThrown);
		           }
			});
			ref.removeClass('ui-icon-plus').addClass('ui-icon-star');
			ref.prop('onclick',null).off('click').click(function(){deleteSavedJob(jj,ref);});
		}
	function deleteSavedJob(jj,ref) {
		$.ajax({type:"POST",
			url:"${pageContext.request.contextPath}/service/deleteSaveJob",
			data:{objId:jj},
			dataType: 'json',
			success: function(res){ console.log(res);
				ref.removeClass('ui-icon-star').addClass('ui-icon-plus');
				ref.prop('onclick',null).off('click').click(function(){saveJob(jj,ref)});
			},
			error:function( jqXHR,  textStatus,  errorThrown ){
	            alert('error '+errorThrown);
	           }
	        }
		);
		}
	function hateJob(jj,ref) {
		$.ajax({type:"POST",
			url:"${pageContext.request.contextPath}/service/hateJob",
			data:{objId:jj},
			dataType: 'json',
			success: function(res){ console.log(res);
			ref.removeClass('ui-icon-plus').addClass('ui-icon-star');
			ref.prop('onclick',null).off('click').click(function(){deleteSavedJob(jj,ref);});
			},
			error:function( jqXHR,  textStatus,  errorThrown ){
	            alert('error '+errorThrown);
	           }
		});
		ref.removeClass('ui-icon-plus').addClass('ui-icon-star');
		ref.prop('onclick',null).off('click').click(function(){deleteSavedJob(jj,ref);});
	}
function deleteHateJob(jj,ref) {
	$.ajax({type:"POST",
		url:"${pageContext.request.contextPath}/service/deleteHateJob",
		data:{objId:jj},
		dataType: 'json',
		success: function(res){ console.log(res);
			ref.removeClass('ui-icon-star').addClass('ui-icon-plus');
			ref.prop('onclick',null).off('click').click(function(){hateJob(jj,ref)});
		},
		error:function( jqXHR,  textStatus,  errorThrown ){
            alert('error '+errorThrown);
           }
        }
	);
}
	function removeCompany(companyName) {
			console.log("remove Company:"+companyName);
			jobTable.api().destroy();
			$('#jobTable tr').each(function(tableRef) {
				if($(this).children('td.company').text()==companyName) {
						console.log("removed");
						$(this).remove();
					}
				});
			 jobTable.dataTable({paging:true});
	}
</script>
</head>
<body>
<form action="${pageContext.request.contextPath}/service/filterByDate" method="post" >
	<input type="text" name="dateAfter" id="dateAfter"/> <input type="text" name="dateBefore" id="dateBefore"/>
	<input type="submit" value="filter" />
</form>
		<table id="jobTable" class="display" cellspacing="0">
			<thead>
				<tr><th style="width:5%">save</th> 
					<th style="width:5%">hate</th>
					<th>Title</th>
					<th>Company</th>
					<th style="width:3%"></th>
					<th>Date</th>
					<th style="width:5%">URL</th>
					</tr>
			</thead>
			<tbody>
			<c:set var="count" value="1" scope="page"/>
			<c:forEach var="job" items="${jobList}">
				<tr>
					<td><c:if test="${job.saved==true}">
					<span class="ui-icon ui-icon-star" onclick="deleteSavedJob('${job.objId}',$(this));"/>
					</c:if>
					<c:if test="${job.saved==false}">
					<span class="ui-icon ui-icon-plus" onclick="saveJob('${job.objId}',$(this));"/>
					</c:if></td>
					<td><c:if test="${job.hated==true}">
					<span class="ui-icon ui-icon-star" onclick="deleteHateJob('${job.objId}',$(this));"/>
					</c:if>
					<c:if test="${job.hated==false}">
					<span class="ui-icon ui-icon-plus" onclick="hateJob('${job.objId}',$(this));"/>
					</c:if></td>
					<td><div onclick="$('#content-${count}').dialog({width:'80%',closeOnEscape:true}).show()">
					<span style="${job.saved==true?'color:red;':''}${job.hated==true?'color:blue;':''}">${job.jobTtile}</span>
					</div></td>
					<td class="company" style="${job.manyJobs==true?'color:red;':''}">${job.company}</td>
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