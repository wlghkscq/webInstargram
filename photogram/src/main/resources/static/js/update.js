// (1) 회원정보 수정
function update(userId, event) {
	event.preventDefault(); // 폼태크 액션 막기 
	let data = $("#profileUpdate").serialize(); // jsp파일에 태그 id값이(#)  profileUpdate 인 태그안에 모든 key=value값을 가져온다. 
	
	console.log(data);
	
	$.ajax({
		type:"put",
		url:`/api/user/${userId}`,
		data: data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json"
		
	}).done(res=>{ // HttpStatue 상태코드 200번대 
		console.log("성공", res);
		location.href=`/user/${userId}`; // 성공시 회원정보 페이지로 이동 

	}).fail(error=>{ // HttpStatue 상태코드 200번대가 아닐때
		if(error.data == null){
			alert(error.responseJSON.message);
		}else{
			alert(JSON.stringify(error.responseJSON.data)); 
		}
		
	});
	
} 

