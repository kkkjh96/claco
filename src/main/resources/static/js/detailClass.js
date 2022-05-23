$(document).ready(function(){
	
            $('.tabs li').click(function(){
                var tab_id = $(this).attr('data-tab');

                $('.tabs li').removeClass('current');
                $('.tab-content').removeClass('current');

                $(this).addClass('current');
                $("#"+tab_id).addClass('current');
            })

})

function resize(obj) {
    obj.style.height = '1px';
    obj.style.height = (12 + obj.scrollHeight) + 'px';
}

function click_registration()
{
	var bb = $("input[class='star']:checked").val();
	$("#score").val(bb);
}
