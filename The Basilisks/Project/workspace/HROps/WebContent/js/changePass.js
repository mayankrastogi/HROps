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
    
    $('#changePassForm').keyup(function(e){
    	var newPass = $('input[name=newPass]').val();
    	var confPass = $('input[name=confirmPass]').val();
    	if(newPass != '' && confPass != '' && newPass == confPass)
    		$('#tick').html(' &#10004; ');
    	else
    		$('#tick').html('');
    });
    
    $('#changePassForm>input[type=submit]').click(function(e){
    	e.preventDefault();
    	
    	var oldPass = $('input[name=oldPass]').val();
    	var newPass = $('input[name=newPass]').val();
    	var confPass = $('input[name=confirmPass]').val();
    	if(oldPass != '' && newPass != '' && confPass != '' && newPass == confPass)
    	{
    		var data = $('#changePassForm').serialize()+'&action=changePass';
    		$.ajax({
      		  type: "POST",
      		  url: "login.do",
      		  data: data
      		}).done(function( msg ) {
      		  alert(msg);
      		  $('#changePassForm>input[type=password]').val('');
      		  $('#tick').html('');
      		});
    	}
    	else
    		alert("Passwords do not match.");
    });
});