$(document).ready(function(){
	
	var appId = null;
	
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-155});
        $('#rt_content').css({height: $(window).height()-162, width: $('.content').width()});
        
        var fwidth = $('.content').width();
        var fheight = $('#rt_content').height();
        $('#resume-frame').attr('width',fwidth).attr('height',fheight);
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
    
    $('#score').hide();
    $('#rt_content').hide();
    $('#select-button').hide();
    $('#reject-button').hide();
    
    $('.nav li>a').click(function(){
    	appId = $(this).parent().data('appid');
    	$.ajax({
    		  type: "POST",
    		  url: "Manager.do",
    		  data: { task: "4",appId: appId }
    		}).done(function( msg ) {
    		  if(msg != 0) {
    			$('#score').html("Interview Score : " +msg);
    			$('#score').show();
            	$('#select-button').show();
            	$('#select-button').data('appid',appId);
            	$('#reject-button').show();
            	$('#reject-button').data('appid',appId);
    		  }
    		});
        $('#rt_content').show();
    	$('#resume-frame').attr('src','../getResume.do?appId='+appId);
    });
    
    $('#filter').change(function(){
    	var oppId = $('#filter :selected').val();
    	
    	$.ajax({
    		  type: "POST",
    		  url: "Manager.do",
    		  data: { task: "3",oppId: oppId }
    		}).done(function( msg ) {
    		  $('.nav').html(msg);
    		  $('.nav li>a').click(function(){
    		    	appId = $(this).parent().data('appid');
    		    	$.ajax({
    		    		  type: "POST",
    		    		  url: "Manager.do",
    		    		  data: { task: "4",appId: appId }
    		    		}).done(function( msg ) {
    		    		  if(msg != 0) {
    		    			$('#score').html("Interview Score : " +msg);
    		    			$('#score').show();
    		            	$('#select-button').show();
    		            	$('#select-button').data('appid',appId);
    		            	$('#reject-button').show();
    		            	$('#reject-button').data('appid',appId);
    		    		  }
    		    		});
    		    	$('#rt_content').show();
    		    	$('#resume-frame').attr('src','../getResume.do?appId='+appId);
    		    });
    		});
    });
    $('#select-button').click(function(){
    	appId = $(this).data('appid');
    	if(confirm("Are you sure you wish to select this application?"))
    	{
    		$.ajax({
    			type: "POST",
    			url: "Manager.do",
    			data: { task: "5", appId: appId }
    		}).done(function(msg){
    			if(msg)
    				alert("Application was selected successfully.");
    			else
    				alert("Error: An error occured while selecting the application.");
    			$('#score').hide();
			    $('#rt_content').hide();
			    $('#select-button').hide();
			    $('#reject-button').hide();
    			$('#filter').trigger('change');
    		});
    	}
   	});
    
    $('#reject-button').click(function(){
    	appId = $(this).data('appid');
    	var reason = prompt("Are you sure you wish to reject this application?\n\nReason for rejection:");
    	if(reason != null)
    	{
    		if(reason == "")
    			alert("Please enter a reason for rejection.");
    		else
    		{
    			$.ajax({
    				type: "POST",
    				url: "Manager.do",
    				data: { task: "6", appId: appId, reason: reason }
    			}).done(function(msg){
    				if(msg)
    					alert("Application was rejected successfully.");
    				else
    					alert("Error: An error occured while rejecting the application.");
    				$('#score').hide();
    			    $('#rt_content').hide();
    			    $('#select-button').hide();
    			    $('#reject-button').hide();
    				$('#filter').trigger('change');
    			});
    		}
    	}
   	});
});