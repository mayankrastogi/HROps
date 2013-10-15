$(document).ready(function(){
	
	var appId = null;
	
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
    
    $('#date').change(function(){
    	var date = $('#date :selected').val();
    	if(date == "")
    		return;
    	$.ajax({
    		  type: "POST",
    		  url: "Interviewer.do",
    		  data: { task: "1",date: date }
    		}).done(function( msg ) {
    		  $('#schedule').html(msg);
    	});
    });
});