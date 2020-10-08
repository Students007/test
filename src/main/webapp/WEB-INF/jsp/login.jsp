<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
</script>
<title>登录</title>
</head>
<body>
<div align="center"><br />
  <br />
  <br />
  <br />
  <br />
    <a href="https://devapi.xindaniu.cn/resources/apk/fuyebao.apk" target="_blank">副业君APK下载</a><br />
  <br />
  <br />
</div>
<table width="400" border="1" align="center">
		<tr>
			<td>手机号码：</td>
			<td><input type="text" id="phone" name="phone"></td>
		</tr>
		<tr>
			<td>验证码：</td>
			<td><input type="text" id="verCode" name="verCode">
		        <input type="submit" id="verCodeBtn" name="verCodeBtn"
				value="获取验证码">
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" id="loginBtn"  name="loginBtn" value="登录"></td>
		</tr>
</table>
</body>

<script type="text/javascript">

	$("#verCodeBtn").click(function() {
		
		var phone = $("#phone").val();
	
		$.ajax({
			type : "POST",
			url : "adminLogin/verCode",
			data : {
				"phone" : phone
			},
			dataType : "json",
			success : function(message) {
				if(message.status == 1000){
					alert("发送成功");
				}else{
					alert("发送失败");
				}
			
			},
			error : function(message) {
				alert("发送失败");
			}
		});

	});
	
	$("#loginBtn").click(function() {
		
		var phone = $("#phone").val();
		var verCode = $("#verCode").val();
	
		$.ajax({
			type : "POST",
			url : "adminLogin/loginVerCode",
			data : {
				"phone" : phone,
				"verCode" : verCode
			},
			dataType : "json",
			success : function(message) {
			
				sessionStorage.setItem('token',message.data);
				location.href = "adminIndex/index?token="+sessionStorage.getItem("token");
			},
			error : function(message) {
				alert("登录失败");
			}
		});

	});	
</script>

</html>