$(document).ready(function(){
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-139});
        $('#rt_content').css({height: $(window).height()-119, width: $('.content').width()});
    });
	
	$('.attendance').css({left: $('#attendance').position().left});
    $('.leave').css({left: $('#leave').position().left});

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
    
    $('#pdf').click(function(e){
    	window.location.href="report/getPDF";
    	return false;
	});
    
    $('#xls').click(function(e){
    	window.location.href="report/getXLS";
    	return false;
	});
});