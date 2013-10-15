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
    
    $('.content').hide();
    
    $('.nav li').click(function(){
    	appId = $(this).data('appid');
    	$.ajax({
  		  type: "POST",
  		  url: "HR.do",
  		  data: { task: "6",appId: appId }
  		}).done(function( msg ) {
  		  $('.reason').html(msg);
  		});
    	$('.content').show();
    	$('#resume-frame').attr('src','../getResume.do?appId='+appId);
    });
    
    $('#filter').change(function(){
    	var oppId = $('#filter :selected').val();
    	$.ajax({
    		  type: "POST",
    		  url: "HR.do",
    		  data: { task: "5",oppId: oppId }
    		}).done(function( msg ) {
    		  $('.nav').html(msg);
    		  $('.nav li').click(function(){
    		    	appId = $(this).data('appid');
    		    	$.ajax({
    		    		  type: "POST",
    		    		  url: "HR.do",
    		    		  data: { task: "6",appId: appId }
    		    		}).done(function( msg ) {
    		    		  $('.reason').html(msg);
    		    		});
    		    	$('.content').show();
    		    	$('#resume-frame').attr('src','../getResume.do?appId='+appId);
    		    });
    		});
    });
});