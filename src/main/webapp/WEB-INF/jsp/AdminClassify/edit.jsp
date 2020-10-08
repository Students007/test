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
<title>绘本-新增-编辑-详情</title>
</head>
<body>
	<div align="right">
		<a
			href="javascript:window.opener=null;window.open('','_self');window.close();">关闭</a>
	</div>
	<table width="90%" border="1" align="center">
		<tr>
			<td><div align="right">ID</div></td>
			<td><input name="id" type="text" id="id" value="${classify.id}"
				disabled="disabled" size="40"></td>
		</tr>
		<tr>
			<td><div align="right">名称</div></td>
			<td><input name="name" type="text" id="name" size="40"
				value="${classify.name}"></td>
		</tr>
		<tr>
			<td><div align="right">编辑时间</div></td>
			<td><fmt:formatDate value="${classify.date}" type="both" /></td>
		</tr>
		<tr>
			<td><div align="right">状态</div></td>
			<td>
      <c:choose>
        <c:when test="${classify.state == true}">
          <input name="stateVo" id="stateVo" type="radio" value="true" checked="checked"/>
          True
          <input name="stateVo" id="stateVo" type="radio" value="false" />
          False </c:when>
        <c:otherwise>
          <input name="stateVo" id="stateVo" type="radio" value="true" />
          True
          <input name="stateVo" id="stateVo" type="radio" value="false" checked="checked"/>
          False </c:otherwise>
      </c:choose>							
				</td>
		</tr>
		<tr>
			<td><div align="right">绘本数量</div></td>
			<td>XXX</td>
		</tr>
		<tr>
			<td><div align="right">绘本列表</div></td>
			<td><c:forEach var="book" items="${books}">
			{book.name}={book.state}
		</c:forEach></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" id="addBtn" name="addBtn" value="提交/更新"></td>
		</tr>
	</table>
	<p>&nbsp;</p>
</body>

<script type="text/javascript">
	$("#addBtn").click(
			function() {

				var formData = new FormData();
				formData.append("id", $("#id").val());
				formData.append("name", $("#name").val());
				formData.append("stateVo", $("input[name='stateVo']:checked")
						.val());
				$.ajax({
					url : "edit",
					dataType : "json",
					type : "POST",
					async : false,
					data : formData,
					processData : false, // 使数据不做处理
					contentType : false, // 不要设置Content-Type请求头
					headers : {
						"token" : sessionStorage.getItem("token")
					},
					success : function(message) {
						if (message.status == 1000) {
							location.href = "../adminClassify/indexView?token="
									+ sessionStorage.getItem("token");
						} else {
							alert("编辑失败");
						}

					},
					error : function(message) {
						alert("提交失败");
					}
				});
			});
</script>

</html>