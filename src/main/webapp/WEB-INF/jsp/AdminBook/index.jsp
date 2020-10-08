<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
	
</script>
<title>绘本</title>
</head>
<body>
	<table width="90%" border="1" align="center">
		<tr>
			<td>ID</td>
			<td>名称</td>
			<td>售价</td>
			<td>状态</td>
			<td>更新时间</td>
		</tr>

		<c:forEach var="book" items="${books}">
			<tr>
				<td><c:out value="${book.id}" /></td>
				<td><a href="detail?token=${token}&id=${book.id}" target="_blank">${book.name}</a></td>
				<td><c:out value="${book.totalFee}" /></td>
				<td><c:out value="${book.state}" /></td>
				<td><fmt:formatDate value="${book.date}" type="both" /></td>
			</tr>
		</c:forEach>

	</table>
	
	<table width="90%" border="0" align="center">
		<tr>
			<td width="90%"><input type="submit" id="addBook" name="addBook" value="新增绘本"> 
				</td>
		</tr>
	</table>
	

</body>

<script type="text/javascript">

	$("#addBook").click(function() {
		window.open("../adminBook/addView?token=" + sessionStorage.getItem("token"));
	});
	
</script>
</html>