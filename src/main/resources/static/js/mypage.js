(window.onload = function() {  // 화면이 뜬 뒤 처리

    	  var obj1 = document.getElementById('contents'); 
    	  var obj2 = document.getElementById('sidebar'); 
    	 
    	  var obj1_height = obj1.offsetHeight;
    	  var obj2_height = obj2.offsetHeight;

    	  if(obj1_height > obj2_height) { obj2.style.height = obj1_height + 'px'; }
    	  else { obj1.style.height = obj2_height + 'px'; }
    	})();
    	
$(document).ready(function(){
	
            $('.tabs li').click(function(){
                var tab_id = $(this).attr('data-tab');

                $('.tabs li').removeClass('current');
                $('.tab-content').removeClass('current');

                $(this).addClass('current');
                $("#"+tab_id).addClass('current');
            })

})