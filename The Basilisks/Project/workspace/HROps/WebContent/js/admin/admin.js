$(document).ready(function(){
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-139});
        $('#rt_content').css({height: $(window).height()-119, width: $('.content').width()});
    });

    $(window).trigger('resize');
    
    $('#rt_menubar').each(function() { 
        if (typeof this.onselectstart != 'undefined') {
            this.onselectstart = function() { return false; };
        } else if (typeof this.style.MozUserSelect != 'undefined') {
            this.style.MozUserSelect = 'none';
        } else {
            this.onmousedown = function() { return false; };
        }
    });
    
    $('.attendance').css({left: $('#attendance').position().left});
    $('.leave').css({left: $('#leave').position().left});
    
    $('#depthead').hide();
    $('#label-depthead').hide();
});

function gendetails()
{
   var appid=document.getElementById("accepted").value;
   //document.getElementById("appid").value=appid;
   
   $.ajax({
	   type: "POST",
	   url: "admin.do",
	   data: { taskid: "100", appid: appid }
	 }).done(function( msg ) {
	   document.getElementById("dept_div").innerHTML=msg;
	 });
   
   $.ajax({
	   type: "POST",
	   url: "admin.do",
	   data: { taskid: "200", appid: appid },
   	   error:function(jqXHR, textStatus, errorThrown){
   		   alert("jg");
   	   }
	 }).done(function( msg ) {
	   document.getElementById("pos_div").innerHTML=msg;
	 });


}
function refresh()
{
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: { taskid: "404"},
	   	   error:function(jqXHR, textStatus, errorThrown){
	   		   alert("jg");
	   	   }
		 }).done(function( msg ) {
		   document.getElementById("application_list_value").innerHTML=msg;
		 });

}

function submitForm(){
	//alert($('#frm_update').serialize());
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: $('#frm_update').serialize()+"&taskid=1",
		   success:function(html){
			   if(html){
				   alert("Updation Succesful");
				   $('#frm_update').hide();
			   } 
			   else
				   alert("Updation Failed. Probaly there was a problem with the input");
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert("An error occured.");
			   
		   }
	});
}
function submitForm1(){
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: $('#Createdept').serialize()+"&taskid=2",
		   success:function(html){
		if(html)
		alert("New Department Created");
		else
			alert("An error occured.");
		},
		   error:function(jqXHR, textStatus, errorThrown){
			alert("An error occured.");
		   }
	});
}
function submitForm2(){
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: $('#Createqual').serialize()+"&taskid=3",
		   success:function(html){
			if(html)   
			alert("New Qualification Created");
			else
				alert("An error occured.");
			},
		   error:function(jqXHR, textStatus, errorThrown){
				alert("An error occured.");
		   }
	});
}
function submitForm3(){
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: $('#Createpos').serialize()+"&taskid=4",
		   success:function(html){
			if(html)
		      alert("New Position Created");
			else
				alert("An error occured.");
			},
		   error:function(jqXHR, textStatus, errorThrown){
				alert("An error occured.");
		   }
	});
}
function submitForm4(){
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: $('#Createjob').serialize()+"&taskid=5",
		   success:function(html){
			if(html)
				alert("New Job Opportunity Created");  
			else
				alert("An error occured.");
			},
		   error:function(jqXHR, textStatus, errorThrown){
				alert("An error occured.");
		   }
	});
}
function search_emp()
{
	var empno=document.getElementById("empId").value;
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: {taskid:"300",empId:empno},
		   success:function(html){
			   $('#result_area').html(html);
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert("Employee Doesn't Exist");
		   }
	});

}
function getdept()
{
	var deptid=$('#select_dept_id').val();
	
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: {taskid:"600",dept_id:deptid},
		   success:function(html){
				   $('#depthead').html(html).show();
				   $('#label-depthead').show();
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert("An error occured.");
		   }
	});
}
function updatedept(){
	$.ajax({
		   type: "POST",
		   url: "admin.do",
		   data: $('#frm_update_dept').serialize()+"&taskid=6",
		   success:function(html){
			   if(html)
				   alert("Department Updated.");
			   else
				   alert("An error occured.");
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert("An error occured.");
		   }
	});
}