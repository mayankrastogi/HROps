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
});

function updateDB(){
	
	$.ajax({
		   type: "POST",
		   url: "admin_config.do",
		   data: $('#db_config').serialize()+"&taskid=1",
		   success:function(html){
			   if(html){
				   alert("Properties Changed");
			   }
			   else
				   alert("Error Occured. Check your input");
			   
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert(errorThrown);
			   
		   }
	});
	
	
}

function updateimap(){
	$.ajax({
		   type: "POST",
		   url: "admin_config.do",
		   data: $('#imap_config').serialize()+"&taskid=2",
		   success:function(html){
			   if(html){
				   alert("Properties Changed");
			   }
			   else
				   alert("Error Occured.Check Your Input");
			   
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert(errorThrown);
			   
		   }
	});
	
	
}

function updatesmtp(){
	
	$.ajax({
		   type: "POST",
		   url: "admin_config.do",
		   data: $('#smtp_config').serialize()+"&taskid=3",
		   success:function(html){
			   if(html){
				   alert("Properties Changed");
			   }
			   else
				   alert("Error Occured. Check Your Input");
			   
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert(errorThrown);
			   
		   }
	});
	
}
function updatemsg()
{
	$.ajax({
		   type: "POST",
		   url: "admin_config.do",
		   data: $('#message_config').serialize()+"&taskid=4",
		   success:function(html){
			   if(html){
				   alert("Properties Changed");
			   }
			   else
				   alert("Error Occured. Check Your Input");
			   
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert(errorThrown);
			   
		   }
	});

}