$(document).ready(function(){
	
	var appId = null;
	
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-155});
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
    
    $('.content').hide();
    
    $('.nav li').click(function(){
    	appId = $(this).data('appid');
    	$('input[name="appId"]').val(appId);
    	$('.content').show();
    });
    
    $('#filter').change(function(){
    	var oppId = $('#filter :selected').val();
    	$.ajax({
    		  type: "POST",
    		  url: "Manager.do",
    		  data: { task: "1",oppId: oppId }
    		}).done(function( msg ) {
    		  $('.nav').html(msg);
    		  $('.nav li').click(function(){
    		    	appId = $(this).data('appid');
    		    	$('input[name="appId"]').val(appId);
    		    	$('.content').show();
    		    });
    		});
    });
    
    $('#scheduleForm input:submit').click(function(e){
    	e.preventDefault();
    	var data = $('#scheduleForm').serialize();
    	data += '&task=2';
    	$.ajax({
  		  type: "POST",
  		  url: "Manager.do",
  		  data: data
  		}).done(function( msg ) {
  			if(msg)
				alert("Interview was scheduled successfully.");
			else
				alert("Error: An error occured while scheduling the interview.");
			$('.content').hide();
			$('#filter').trigger('change');
  		});
    });
});