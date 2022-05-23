const main = {
	init : function() {
		const _this = this;
		
		// 댓글 저장 실행
		$('#btn-review-save').on('click', function() {
			_this.reviewSave();
		});
		
		// 댓글 수정 관련
		document.querySelectorAll('btn-review-update').forEach(function(item) {
			item.addEventListener('click', function() { // 버튼 클릭 시 
				const form = this.closest('form');  // btn의 가장 가까운 조상 form 반환
				_this.reviewUpdate(form);  // form update 실행
			});
		});
	},
	
	// 수강후기 저장 메서드
	reviewSave : function() {
		const data = {
			classid: $('#classid').val(),
			contents: $('#contents').val(),
			status: $('#status').val(),
			score: $('#score').val()
		}
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		
		// 공백, 빈 문자열 체크
		if ((!data.contents || data.contents.trim() === "") && (!data.score || data.score.trim() === ""))  {
			alert("공백 또는 입력하지 않은 부분이 있습니다.");
			return false;
		} else {
			$.ajax({ // ajax 설정
				type: 'POST',
				url: '/claco/post/' + data.classid + '/review',
				dataType: 'JSON',
				contentType: 'application/json; charset=utf-8',
				data: JSON.stringify(data),
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
					}
				}).done(function() { // 수행 성공 시
					alert("수강후기가 등록되었습니다.");
					window.location.reload(); // 화면 갱신
				}).fail(function (error) { // 수행 실패 시
					alert(JSON.stringify(error));
				});
		}
	},
	
	// 수강후기 삭제 메서드
		reviewDelete : function(classid, reviewid) {
		const con_check = confirm("삭제하시겠습니까?");
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		if (con_check === true) {
			$.ajax({
				type: 'DELETE',
				url: '/claco/post/' + classid + '/review/' + reviewid,
				dataType: 'JSON',
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
					}
			}).done(function() {
				alert('댓글이 삭제되었습니다.');
				window.location.reload();
			}).fail(function(error) {
				alert(JSON.srtingify(error));
			});
		}
	}/*,
	
	
	// 수강후기  수정 메서드
	commentUpdate : function(form) {
		const data = {
			cmtindex: form.querySelector("#cmtindex").value,
			boardid = form.querySelector("#boardidModify").value,
			comment: form.querySelector("#comment-content").value
		}
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		
		if (!data.comment || data.comment.trim() === "") {
			alert("공백 또는 입력하지 않은 부분이 있습니다.");
			return false;
		}
		const con_check = confirm("수정하시겠습니까?");
		
		if (con_check === true) {
			$.ajax({
				type: 'PUT',
				url: '/freeboard/post/' + data.boardid + '/comments/' + data.cmtindex,
				dataType: 'JSON',
				contentType: 'application/json; charset=utf-8',
				data: JSON.stringify(data),
				beforeSend: function(xhr) {
					xhr.setRequestHeader(header, token);
					}
			}).done(function() {
				window.location.reload();
			}).fail(function(error) {
				alert(JSON.srtingify(error));
			});
		}
	}
	*/
};

main.init();