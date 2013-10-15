$(document).ready(function(){
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-138});
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

function submitForm5(){
	$.ajax({
		   type: "POST",
		   url: "leave.do",
		   data: $('#leaveForm').serialize()+"&taskid=1",
		   success:function(html){
			   alert(html);
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert("5");
		   }
	});
}

function submitForm6(){
	$.ajax({
		   type: "POST",
		   url: "leave.do",
		   data: $('#grant').serialize()+"&taskid=2",
		   success:function(html){
			   alert(html);
		   },
		   error:function(jqXHR, textStatus, errorThrown){
			   alert("6");
		   }
	});
}