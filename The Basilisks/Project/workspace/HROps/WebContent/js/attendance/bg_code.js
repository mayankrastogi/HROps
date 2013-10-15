function updateClock() {
    var now = new Date(), // current date
        months = ['January', 'February', 'March','April','May','June','July','August','September','October','November','December']; // you get the idea
        time = now.getHours() + ':' + now.getMinutes()+":"+now.getSeconds(); // again, you get the idea

        // a cleaner way than string concatenation
        /*date = [now.getDate(), 
                months[now.getMonth()],
                now.getFullYear()].join(' ');
*/
    date=now.getDate()+"-"+months[now.getMonth()]+"-"+now.getFullYear();
    // set the content of the element with the ID time to the formatted string
    //document.getElementById('time').innerHTML = [date, time].join(' / ');
    document.getElementById("dt_show").innerHTML="Date :"+ date;
   document.getElementById("time_show").innerHTML="Time : "+ time;
    // call this function again in 1000ms
    setTimeout(updateClock, 1000);
}
updateClock(); // initial call

function punch()
{
	
	var now = new Date(); // current date
    //months = ['January', 'February', 'March','April','May','June','July','August','September','October','November','December']; // you get the idea
    //time = now.getHours() + ':' + now.getMinutes()+":"+now.getSeconds(); // again, you get the idea
	//alert(now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds());
	$.ajax({
        url: "queryProcess.jsp",
        type: "POST",
        data: {timestamp:now.getFullYear()+"-"+(now.getMonth()+1)+"-"+now.getDate()+" "+now.getHours()+":"+now.getMinutes()+":"+now.getSeconds(),punch:1},     
        success: function (html) {  
			
			if((html.search("100"))!=-1){
			   document.getElementById("punch_head").innerHTML="Punch In";
			   document.getElementById("button_area").innerHTML="<input type=button name=in id=in value= 'Punch In' onclick=punch()>";
			   document.getElementById("msg").innerHTML="";
			}
			else if((html.search("200"))!=-1)
			   {
				   document.getElementById("punch_head").innerHTML="Punch Out";
				   document.getElementById("button_area").innerHTML="<input type=button name=out id=out value= 'Punch Out' onclick=punch()>";
				   document.getElementById("msg").innerHTML="";
			   }
			else{
			  //document.getElementById("res").innerHTML=html;
			  document.getElementById("msg").innerHTML="There was an error. Please report it to admin";
			}
        	
			} ,
        error: function(jqXHR, textStatus, errorThrown){
			alert(errorThrown);
	}      
    });
}
