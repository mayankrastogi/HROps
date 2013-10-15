$(document).ready(function(){
	
	var appId = null;
	
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-138});
        $('#rt_content').css({height: $(window).height()-119, width: $('.content').width()});
        
        var fwidth = $('.content').width();
        var fheight = $('#rt_content').height();
        $('#help-frame').attr('width',fwidth).attr('height',fheight);
    });

    $(window).trigger('resize');
    
    $('.attendance').css({left: $('#attendance').position().left});
    $('.leave').css({left: $('#leave').position().left});
});