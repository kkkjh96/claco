// 비밀번호 일치여부 확인
    function isSame1() {
        
        if(document.getElementById('company_pwd').value==document.getElementById('company_check').value) {
           document.getElementById('same1').innerHTML='비밀번호가 일치합니다.';
           document.getElementById('same1').style.color='blue';
        }
        else if(document.getElementById('company_pwd').value=='') {
			document.getElementById('same1').innerHTML='';
		}
        
        else {
           document.getElementById('same1').innerHTML='비밀번호가 일치하지 않습니다.';
           document.getElementById('same1').style.color='red';
        }
}
	function isSame2() {
        
        if(document.getElementById('member_pwd').value==document.getElementById('member_check').value) {
           document.getElementById('same2').innerHTML='비밀번호가 일치합니다.';
           document.getElementById('same2').style.color='blue';
        }
        else if(document.getElementById('member_pwd').value=='') {
			document.getElementById('same2').innerHTML='';
		}
        else {
           document.getElementById('same2').innerHTML='비밀번호가 일치하지 않습니다.';
           document.getElementById('same2').style.color='red';
        }


}

/* 아이디 중복 체크 : ajax 비동기처리 */
function idCheck() {
    
    var id = $("#member_id").val();
    
    if(id.search(/\s/) != -1) { 
        alert("아이디에는 공백이 들어갈 수 없습니다.");        
    } else {             
        if(id.trim().length != 0) {
            $.ajax({
                async : true, 
                type : 'GET', 
                data: {id:id},
                url: "/user/idCheck",
                dataType: "json",
                contentType: "application/json; charset=UTF-8",
                success: function(check) {    
                    if(check) {
                        alert("아이디가 이미 존재합니다.");    
                        $("#member_submit").attr("disabled", "disabled");
                    } else {
                        alert("사용가능한 아이디입니다.");
                        $("#member_submit").removeAttr("disabled");
                    }            
                },
                error: function(error) {
                    alert("에러가 발생했습니다.");
                }        
            });
        } else {
            alert("아이디를 입력해주세요.");
        }        
    }
}

function idCheck2() {
    
    var id = $("#company_id").val();
    
    if(id.search(/\s/) != -1) { 
        alert("아이디에는 공백이 들어갈 수 없습니다.");        
    } else {             
        if(id.trim().length != 0) {
            $.ajax({
                async : true, 
                type : 'GET', 
                data: {id:id},
                url: "/user/idCheck",
                dataType: "json",
                contentType: "application/json; charset=UTF-8",
                success: function(check) {    
                    if(check) {
                        alert("아이디가 이미 존재합니다.");    
                        $("#company_submit").attr("disabled", "disabled");
                    } else {
                        alert("사용가능한 아이디입니다.");
                        $("#company_submit").removeAttr("disabled");
                    }            
                },
                error: function(error) {
                    alert("에러가 발생했습니다.");
                }        
            });
        } else {
            alert("아이디를 입력해주세요.");
        }        
    }
}

	    