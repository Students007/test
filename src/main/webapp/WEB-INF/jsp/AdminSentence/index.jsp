<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="https://apps.bdimg.com/libs/jquery/2.1.4/jquery.min.js">
	
</script>
<title>句子列表</title>
</head>
<body>
	<table width="90%" border="1" align="center">
		<tr>
			<td>ID</td>
			<td>绘本</td>
			<td>Number</td>
			<td>句子</td>
			<td>状态</td>
			<td>时间</td>
		</tr>

		<c:forEach var="sentence" items="${sentences}" varStatus="status">
			<tr>
				<td><c:out value="${sentence.id}" /></td>
				<td><c:out value="${sentence.book}" /></td>
				<td><c:out value="${sentence.number}" /></td>
				<td><a href="detail?token=${token}&id=${sentence.id}" target="_blank">${sentence.name}</a></td>
				<td><c:out value="${sentence.state}" /></td>
				<td><fmt:formatDate value="${sentence.date}" type="both" /></td>
			</tr>
		</c:forEach>

	</table>

	<table width="90%" border="0" align="center">
		<tr>
			<td width="90%"><input type="submit" id="addSentence" name="addSentence" value="新增绘本句子"> 
				</td>
		</tr>
	</table>
	
</body>

<script type="text/javascript">

$("#addSentence").click(function() {
	window.open("../adminSentence/addView?token=" + sessionStorage.getItem("token") + "&bookId=${bookId}"  );
});	
	
</script>

</html>