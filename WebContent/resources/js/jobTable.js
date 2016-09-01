function filterByDate() {
			var data = {'dateAfter':$('#dateBefore').val(),'dateBefore':$('#dateAfter').val()};
			$.post("${pageContext.request.contextPath}/service/filterByDate",data);
		}
	function saveJob(jj,ref) {
			$.ajax({type:"POST",
				url:"service/saveJob",
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
			url:"service/deleteSaveJob",
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
			url:"service/hateJob",
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
		url:"service/deleteHateJob",
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
		url:"service/getCompany/"+jobName,
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
		url:"service/filterAgentJSON",
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
				
				trHTML="<tr><td class='saved' jid='"+item.jobId+"'>"+item.saved+"</td><td class='hated'  jid='"+item.jobId+"'>"+item.hated+"</td><td>"+item.jobTtile+"</td><td>"+item.company+"</td><td></td><td>"+item.datePosted+"</td><td>"+item.url+"</td></tr>";
				inner+=trHTML;});
			inner+="</tbody>";
			//console.log(inner);
			$("#jobTable").html(inner);
			setSaveJob();
			jobTable=$('#jobTable').dataTable(dateTable_option);
			
		}
	function setSaveJob() {
		$(".saved").each(function(){
			jobid = $(this).attr("jid");
			d = document.createElement('span');
			$(d).addClass("ui-icon");
			if($(this).text()=="true"){
					$(d).addClass("ui-icon-star");
					$(d).html("true");
					$(d).on("click",function(){
						deleteSavedJob(jobid,$(this));
						});
				}
			else {
				$(d).addClass("ui-icon-plus");
				$(d).html("false");
				$(d).on("click",function(){
					saveJob(jobid,$(this));
					});
				}
			$(this).empty().append(d);
		});
	}
	function setHateJob() {
		$(".hated").each(function(){
			jobid = $(this).attr("jid");
			d = document.createElement('span');
			$(d).addClass("ui-icon");
			if($(this).text()=="true"){
					$(d).addClass("ui-icon-star");
					$(d).html("true");
					$(d).on("click",function(){
						deleteHateJob(jobid,$(this));
						});
				}
			else {
				$(d).addClass("ui-icon-plus");
				$(d).html("false");
				$(d).on("click",function(){
					hateJob(jobid,$(this));
					});
				}
			$(this).empty().append(d);
		});
	}