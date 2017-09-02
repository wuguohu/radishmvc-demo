<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
<style>
	body {
		padding:0;
		margin:0;
	}
	
	div {
		padding:10px 0;
		text-align:center;
	}
	
	input{
		width: 200px;
		height: 24px;
	}

</style>
</head>
<body>
	<form action="<%=request.getContextPath()%>/user?method=login" method="post">
		<div>用户登陆</div>
		<div>用户：<input type="text" name="username"></div>
		
		<div>密码：<input type="password" name="password"></div>
		<div><button type="submit">提交</button></div>
	</form>
</body>
</html>