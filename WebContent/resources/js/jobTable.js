function filterByDate() {
			var data = {'dateAfter':$('#dateBefore').val(),'dateBefore':$('#dateAfter').val()};
			$.post(window.location.host+"/service/filterByDate",data);
		}
function saveJob(jj,ref) {
			$.ajax({type:"POST",
				url:"/service/saveJob",
				data:{objId:jj},
				dataType: 'json',
				success: function(res){ 
				//console.log(res);
				ref.removeClass('ui-icon-plus').addClass('ui-icon-star');
				ref.prop('onclick',null).off('click').click(function(){deleteSavedJob(jj,ref);});
				},
				error:function( jqXHR,  textStatus,  errorThrown ){
		            alert('error '+errorThrown);
		           }
			});
		}
	function deleteSavedJob(jj,ref) {
		$.ajax({type:"POST",
			url:"/service/deleteSaveJob",
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
			url:"/service/hateJob",
			data:{objId:jj},
			dataType: 'json',
			success: function(res){ console.log(res);
			console.log($(this).attr("jid"));
			ref.removeClass('ui-icon-plus').addClass('ui-icon-star');
			ref.prop('onclick',null).off('click').click(function(){deleteHateJob(jj,ref);});
			},
			error:function( jqXHR,  textStatus,  errorThrown ){
	            alert('error '+errorThrown);
	           }
		});
		ref.removeClass('ui-icon-plus').addClass('ui-icon-star');
		ref.prop('onclick',null).off('click').click(function(){deleteHateJob(jj,ref);});
	}
function deleteHateJob(jj,ref) {
	$.ajax({type:"POST",
		url:"/service/deleteHateJob",
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
function getCompanyJob(jobName) {
	jobTable.api().destroy();
	$.ajax({type:"get",
		url:"/service/getCompany/"+jobName,
		dataType:'json',
		success: function(res){
				rebuildTable(res);
			},
		error:function( jqXHR,  textStatus,  errorThrown ){
            alert('error '+errorThrown);
        	}
	    }
    );
}
function filterAgentJob() {
	jobTable.api().destroy();
	$.ajax({type:"post",
		url:"/service/filterAgentJSON",
		data:{"true_False":$("#true_False").val(),"PCCW":$("#PCCW").val()},
		dataType:'json',
		success: function(res){
				rebuildTable(res);
			},
		error:function( jqXHR,  textStatus,  errorThrown ){
            alert('error '+errorThrown);
        	}
	    }
    );
}

function filterHateJob() {
	jobTable.api().destroy();
	$.ajax({type:"post",
		url:"/service/filterHateJSON",
		data:{"filterHate":$("#filterHate").val()},
		dataType:'json',
		success: function(res){
				rebuildTable(res);
			},
		error:function( jqXHR,  textStatus,  errorThrown ){
            alert('error '+errorThrown);
        	}
	    }
    );
}

function filterByDate() {
	jobTable.api().destroy();
	cmpTable.api().destroy();
	$.ajax({type:"post",
		url:"/service/filterByDateJSON",
		data:{"dateAfter":$("#dateAfter").val(),"dateBefore":$("#dateBefore").val()},
		dataType:'json',
		success: function(res){
			console.log(res.jobList);
				rebuildTable($.parseJSON(res.jobList));
				rebuildCpyTable($.parseJSON(res.companyCount));
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
			 jobTable.dataTable(dateTable_option);
	}
	function rebuildTable(jsonArr) {
			inner = '<thead><tr><th class="smallCell">save</th><th class="smallCell">hate</th><th>Title</th><th>Company</th><th class="smallCell"></th><th>Date</th><th class="smallCell">URL</th></tr></thead><tbody>';
			$.each(jsonArr,function(index,item){
				jobMap[item.jobId] = item.jobDesc;
				var companyStyle = item.manyJobs==true?"style='color:red' ":"";
				trHTML="<tr><td class='saved' jid='"+item.jobId+"'>"+item.saved+"</td><td class='hated'  jid='"+item.jobId+"'>"+item.hated+"</td><td class='col-title' onclick='jobDescDialog("+item.jobId+",this)'>"+item.jobTtile+"</td><td class='col-company' "+companyStyle +" >"+item.company+"</td><td></td><td>"+item.datePosted+"</td><td><a href='"+item.url+"'>Click</a></td></tr>";
				inner+=trHTML;});
			inner+="</tbody>";
			//console.log(inner);
			$("#jobTable").html(inner);
			setSaveJob();
			setHateJob();
			setTitleCol();
			setCpyRemove();
			jobTable=$('#jobTable').dataTable(dateTable_option);
			
		}
	function rebuildCpyTable(jsonArr) {
		inner = '<thead><tr><th>Company</th><th>Count</th></tr></thead><tbody>';
		$.each(jsonArr,function(index,item){
			//console.log(index+":"+item);
			trHTML="<tr><td>"+index+"</td><td>"+item+"</td></tr>";
			inner+=trHTML;});
		inner+="</tbody>";
		//console.log(inner);
		$("#cpyTable").html(inner);
		cmpTable=$('#cpyTable').dataTable({paging:true});
		
	}
	function setSaveJob() {
		$(".saved").each(function(){
			var jobid = $(this).attr("jid");
			var thisRef = $(this);
			d = document.createElement('span');
			$(d).addClass("ui-icon");
			if($(this).text()=="true"){
					$(this).addClass("trueSaved");
					$(d).addClass("ui-icon-star");
					$(d).html("true");
					$(d).click(deleteSaveJobWrapper($(this).attr("jid"),$(d)));
				}
			else {
				$(d).addClass("ui-icon-plus");
				$(d).html("false");
				$(d).click(setSaveJobWrapper($(this).attr("jid"),$(d)));
				}
			$(this).empty().append(d);
		});
	}
	function setHateJob() {
		$(".hated").each(function(){
			var jobid = $(this).attr("jid");
			var thisRef = $(this);
			d = document.createElement('span');
			$(d).addClass("ui-icon");
			
			if($(this).text()=="true"){
					$(this).addClass("trueHated");
					$(d).addClass("ui-icon-star");
					$(d).html("true");
					$(d).click(deleteHateJobWrapper($(this).attr("jid"),$(d)));
					}
				
			else {
				$(d).addClass("ui-icon-plus");
				$(d).html("false");
				$(d).click(setHateJobWrapper($(this).attr("jid"),$(d)));
			}
			
			$(this).empty().append(d);
		});
	}
	function setTitleCol() {
		var titleElement = $('td').hasClass("col-title");
		$(".trueSaved").each(function(){
			$(this).next().next().css("color","red");
		});
		$(".trueHated").each(function(){
			$(this).next().css("color","blue");
		});
	}
	function setCpyRemove() {
		
		$(".col-company").each(function(){
			var cmpName = $(this).text();
			d = document.createElement('span');
			$(d).addClass("ui-icon").addClass("ui-icon-closethick");
			$(d).css("display","inline-block");
			$(d).on("click",function(){removeCompany(cmpName);});
			$(this).next().append(d);
			});
	}
	function jobDescDialog(jobId,ref) {
		$(".pointer").removeClass("pointer");
		$(ref).parent().addClass('pointer');
		$('#jobContentDialog').html(jobMap[jobId]).dialog({width:'80%',closeOnEscape:true}).show();
	}
	function deleteHateJobWrapper(jobId,ref) {
		return function(){deleteHateJob(jobId,ref);};
	}
	function setHateJobWrapper(jobId,ref) {
		return function(){hateJob(jobId,ref);};
	}
	function setSaveJobWrapper(jobId,ref) {
		return function(){saveJob(jobId,ref);};
	}
	function deleteSaveJobWrapper(jobId,ref) {
		return function(){deleteSavedJob(jobId,ref);};
	}
	