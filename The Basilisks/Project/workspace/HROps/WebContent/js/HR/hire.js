$(document).ready(function(){
	
	var appId = null;
	
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-193});
        $('#rt_content').css({height: $(window).height()-162, width: $('.content').width()});
        
        var fwidth = $('.content').width()-4;
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
    $('#reject-button').hide();
    
    $('.nav li>input:checkbox').click(function(){
    	if($(this).is(':checked'))
    		$(this).parent().addClass('selected');
    	else
    		$(this).parent().removeClass('selected');
    });
    
    $('.nav li>a').click(function(){
    	appId = $(this).parent().data('appid');
    	$.ajax({
    		  type: "POST",
    		  url: "HR.do",
    		  data: { task: "9",appId: appId }
    		}).done(function( msg ) {
    		  $('#score').html("Interview Score : " +msg);
    		});
    	$('#score').show();
        $('#rt_content').show();
        $('#reject-button').show();
        $('#reject-button').data('appid',appId);
    	$('#resume-frame').attr('src','../getResume.do?appId='+appId);
    });
    
    $('#filter').change(function(){
    	var oppId = $('#filter :selected').val();
    	$.ajax({
  		  type: "POST",
  		  url: "HR.do",
  		  data: { task: "8",oppId: oppId }
  		}).done(function( msg1 ) {
  		  $('#vacancy').html("No. of vacancies : " +msg1);
  		  $('#applications input:hidden').attr('value',oppId);
  		});
    	
    	$.ajax({
    		  type: "POST",
    		  url: "HR.do",
    		  data: { task: "7",oppId: oppId }
    		}).done(function( msg ) {
    		  $('.nav').html(msg);
    		  $('.nav li>a').click(function(){
    		    	appId = $(this).parent().data('appid');
    		    	$.ajax({
    		    		  type: "POST",
    		    		  url: "HR.do",
    		    		  data: { task: "9",appId: appId }
    		    		}).done(function( msg ) {
    		    		  $('#score').html("Interview Score : " +msg);
    		    		});
    		    	$('#score').show();
    		        $('#rt_content').show();
    		        $('#reject-button').show();
    		        $('#reject-button').data('appid',appId);
    		    	$('#resume-frame').attr('src','../getResume.do?appId='+appId);
    		    });
    		  $('.nav li>input:checkbox').click(function(){
    		    	if($(this).is(':checked'))
    		    		$(this).parent().addClass('selected');
    		    	else
    		    		$(this).parent().removeClass('selected');
    		    });
    		});
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
    				url: "HR.do",
    				data: { task: "11", appId: appId, reason: reason }
    			}).done(function(msg){
    				if(msg)
    					alert("Application was rejected successfully.");
    				else
    					alert("Error: An error occured while rejecting the application.");
    				$('#score').hide();
    			    $('#rt_content').hide();
    			    $('#reject-button').hide();
    				$('#filter').trigger('change');
    			});
    		}
    	}
   	});
    
    $('#hire-button>a').click(function(){
    	var vacancy = $('#vacancy').html();
    	var numSelected = $('.nav li>input:checked').size();
    	
    	if(numSelected == 0)
    		alert("Please select at least one application.")
    	else if(numSelected > vacancy)
    		alert("Only " +vacancy +" vacancies are there. Please select fewer applications.");
    	else {
    		var data = $('#applications').serialize();
    		data += '&task=10';
    		$.ajax({
    	  		  type: "POST",
    	  		  url: "HR.do",
    	  		  data: data
    	  		}).done(function( msg ) {
    	  			if(msg)
    					alert("Applications were selected successfully.");
    				else
    					alert("Error: An error occured while selecting the applications.");
    				$('#score').hide();
    			    $('#rt_content').hide();
    			    $('#reject-button').hide();
    				$('#filter').trigger('change');
    	  		});
    	}
    });
});