//각 시 버튼을 눌렀을때 다른 시의 체크박스를 없애고 체크박스를 해제
function btnClick(button)
{
    var seoul = document.getElementById('btn_Seoul'); //btn_Seoul이라는 id를 찾아서 변수 seoul에 저장
    var busan = document.getElementById('btn_Busan'); //btn_Busan이라는 id를 찾아서 변수 busan에 저장
    
    var buttonValue = button.value; //어느지역의 버츤이 선택되었는지 value를 저장 (ex>서울시, 부산시)

	if(button.name == 'btnBusan') //누른 버튼의 name이 busan이면
	{
	    seoul.style.display = 'none'; //부산 버튼 클릭시에 부산의 체크박스 표시
    	busan.style.display = 'block'; //부산 버튼 클릭시 서울의 체크박스 없애기
    		
    	//서울 지역의 체크된 체크박스들을 가져와서 해제시켜줌
    	$('input:checkbox[name=checkbox_seoul]:checked').each(function(){
			$("input:checkbox[class='checkbox_seoul']").prop("checked", false);
		});
	}

	if(button.name == 'btnSeoul') //누른 버튼의 name이 seoul이면
	{
		seoul.style.display = 'block'; //서울 버튼 클릭시에 서울의 체크박스 표시
    	busan.style.display = 'none'; //서울 버튼 클릭시 부산의 체크박스 없애기
    	
    	//부산 지역의 체크된 체크박스들을 가져와서 해제시켜줌
    	$('input:checkbox[name=checkbox_busan]:checked').each(function(){
			$("input:checkbox[class='checkbox_busan']").prop("checked", false);
		});
	}
	
	//숨겨둔 input에 (ex)부산시,서울시) 값을 저장
	$("#btn").val(buttonValue);
}

/*전체 버튼 클릭시 지역 체크박스 전부 선택*/
function selectAll(selectAll)  {
	
	var area_checkboxes; //어느 지역의 체크박스인지 저장할 변수
	
	if(selectAll.name == 'checkbox_busan_selectAll') //선택된 전체선택 체크박스가 부산이면
	{
		area_checkboxes = document.getElementsByName('checkbox_busan'); //name을 저장
	}
	if(selectAll.name == 'checkbox_seoul_selectAll') //선택된 전체선텍 체크박스가 서울이면
	{
		area_checkboxes = document.getElementsByName('checkbox_seoul'); //name을 저장
	}
	
	//저장된 name을 반복하며 모든 체크박스를 선택
	area_checkboxes.forEach((area_checkboxes) => 
	{
	    area_checkboxes.checked = selectAll.checked
    })
}

//전체버튼 클릭되어있는 상황에 다른 구 체크박스를 해제하면 전체버튼 해제
function checkSelectAll(checkbox)  {
	var selectall;
	
	//서울인경우
	if(checkbox.name == 'checkbox_seoul')
	{
		selectall = document.querySelector('input[name="checkbox_seoul_selectAll"]');
	}
	//부산인경우
	if(checkbox.name == 'checkbox_busan')
	{
		selectall = document.querySelector('input[name="checkbox_busan_selectAll"]');
	}
	
	//(ex)강남구,강서구)체크박스 해제시 전체 체크박스 해제
	if(checkbox.checked == false)  
	{
		// checked 상태를 false로
	    selectall.checked = false;
	}
	
}

/*체크박스에 체크된 값들을 배열에 저장*/
function fnGetdata(){
    var chkArray = new Array(); // 배열 선언 (서울의 지역을 저장할 배열)
    $('input:checkbox[name=checkbox_seoul]:checked').each(function() { // 체크된 체크박스의 value 값을 가지고 온다.
    	if(this.value != "전체") //value가 전체가 아닌 경우 (전체라는 이름의 체크박스를 빼고 배열에 넣어야하기 때문에)
    	{
			chkArray.push($("#btn").val() + " " + this.value);
		}
    		    	
    });
    
    $('input:checkbox[name=checkbox_busan]:checked').each(function() { // 체크된 체크박스의 value 값을 가지고 온다.
    	if(this.value != "전체") //value가 전체가 아닌 경우 (전체라는 이름의 체크박스를 빼고 배열에 넣어야하기 때문에)
    	{
			chkArray.push($("#btn").val() + " " + this.value);
		}	
    });
    
    $('input[name=arr_location]').val(chkArray); //input의 값에 chkArray_S값(배열)을 저장
}