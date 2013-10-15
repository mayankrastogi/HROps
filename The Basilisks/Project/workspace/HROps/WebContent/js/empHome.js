$(document).ready(function(){
	$(window).resize(function() {
        $('#app_list').css({height: $(window).height()-138});
        $('#rt_content').css({height: $(window).height()-119, width: $('.content').width()});
    });

    $(window).trigger('resize');
    
    $('.attendance').css({left: $('#attendance').position().left});
    $('.leave').css({left: $('#leave').position().left});
    
    $('#rt_menubar ul>li').each(function() { 
        if (typeof this.onselectstart != 'undefined') {
            this.onselectstart = function() { return false; };
        } else if (typeof this.style.MozUserSelect != 'undefined') {
            this.style.MozUserSelect = 'none';
        } else {
            this.onmousedown = function() { return false; };
        }
    }); 
});