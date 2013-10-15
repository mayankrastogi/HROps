$(document).ready(function(){
	
	var appId = null;
	var date = null;
	
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
    
    $('#filter-time').hide();
    $('#score').hide();
    $('#update-score-button').hide();
    $('#rt_content').hide();
    
    $('#date').change(function(){
    	date = $('#filter-date :selected').val();
    	
    	if(date == ''){
    		$('#time').html('<option value="" selected="selected"></option>');
    		$('#time').trigger('change');
    		$('#filter-time').hide();
    		$('#score').hide();
    	    $('#update-score-button').hide();
    	    $('#rt_content').hide();
    		$('.nav').html('');
    		return;
    	}
    	$.ajax({
    		  type: "POST",
    		  url: "Interviewer.do",
    		  data: { task: "2",date: date }
    		}).done(function( msg ) {
    			$('#time').html(msg);
    			
    			if(msg == '')
    				return;
    			
    			$('#filter-time').show();
    			
    	});
    });
    
    $('#time').change(function(){
		var time = $('#time :selected').val();
		
		if(time == ''){
    		$('.nav').html('');
    		$('#score').hide();
    	    $('#update-score-button').hide();
    	    $('#rt_content').hide();
    		return;
    	}
		
		$.ajax({
			type: "POST",
			url: "Interviewer.do",
			data: { task: "3",date: date, time: time }
		}).done(function( msg ) {
			$('.nav').html(msg);
			$('.nav li>a').click(function(){
		    	appId = $(this).parent().data('appid');
		    	$.ajax({
		    		  type: "POST",
		    		  url: "Interviewer.do",
		    		  data: { task: "4",appId: appId }
		    		}).done(function( msg ) {
		    		  if(msg != 0)
		    			$('input[name=score]').val(msg);
		    		  else
		    			  $('input[name=score]').val('');
		    		});
		    	$('#score').show();
		        $('#rt_content').show();
		        $('#update-score-button').show();
		        $('#update-score-button').data('appid',appId);
		    	$('#resume-frame').attr('src','../getResume.do?appId='+appId);
		    });
		});
    });
    
    $('#update-score-button').click(function(){
    	appId = $(this).data('appid');
    	var score = $('input[name=score]').val();
    	if(score == "" || isNaN(score))
    		alert("Please enter a valid score.");
    	else
    	{
    		$.ajax({
    			type: "POST",
    			url: "Interviewer.do",
    			data: { task: "5", appId: appId, score: score }
    		}).done(function(msg){
    			if(msg)
    				alert("The result was updated successfully.");
    			else
    				alert("Error: An error occured while updating the result.");
    		});
    	}
   	});
});