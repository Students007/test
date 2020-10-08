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
<title>绘本分类管理</title>
</head>
<body>
	<table width="90%" border="1" align="center">
		<tr>
			<td>ID</td>
			<td>名称</td>
			<td>绘本数量</td>
			<td>状态</td>
			<td>更新时间</td>
		</tr>

		<c:forEach var="classify" items="${classifys}">
			<tr>
				<td><c:out value="${classify.id}" /></td>
				<td><a href="detail?token=${token}&id=${classify.id}"
					target="_blank">${classify.name}</a></td>
				<td>XXX</td>
				<td><c:out value="${classify.state}" /></td>
				<td><fmt:formatDate value="${classify.date}" type="both" /></td>
			</tr>
		</c:forEach>

	</table>
	<table width="90%" border="0" align="center">
		<tr>
			<td width="90%"><input type="submit" id="addClassify" name="addClassify" value="新增分类"> 
				</td>
		</tr>
	</table>
	
	

</body>

<script type="text/javascript">

	$("#addClassify").click(function() {
		window.open("../adminClassify/addClassify?token=" + sessionStorage.getItem("token"));
	});
	
</script>
</html>