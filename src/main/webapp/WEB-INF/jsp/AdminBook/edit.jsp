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
		  <td width="8%"><div align="right">ID</div></td>
			<td width="59%"><input name="id" type="text" id="id" value="${book.id}"
				disabled="disabled" size="40"></td>
			<td width="7%" rowspan="14"><div align="right">封面PNG<br />
	      300*390<br />非白背景</div></td>
			<td width="26%" rowspan="14"><input type="file" id="coverFile" name="coverFile" value="${book.cover}">
              <br />
          <img src="${book.cover}" alt="暂无" /></td>
		</tr>
		<tr>
			<td><div align="right">名称</div></td>
			<td><input name="name" type="text" id="name" size="40"
				value="${book.name}"></td>
	    </tr>
		<tr>
			<td><div align="right">编辑时间</div></td>
			<td><fmt:formatDate value="${book.date}" type="both" /></td>
	    </tr>
		<tr>
			<td><div align="right">状态</div></td>
			<td>
			<c:choose>
			<c:when test="${book.state == true}">
			    <input name="stateVo" id="stateVo" type="radio" value="true" checked="checked"/> True
				<input name="stateVo" id="stateVo" type="radio" value="false" /> False			</c:when>
			<c:otherwise>
			    <input name="stateVo" id="stateVo" type="radio" value="true" /> True
				<input name="stateVo" id="stateVo" type="radio" value="false" checked="checked"/> False			</c:otherwise>
			</c:choose>			</td>
	    </tr>
		<tr>
		  <td><div align="right">售价</div></td>
		  <td><input name="totalFee" type="text" id="totalFee" size="40"
				value="${book.totalFee}"> 元</td>
	    </tr>
		<tr>
		  <td><div align="right">句子数量</div></td>
		  <td>${book.sentenceCount}</td>
      </tr>
		<tr>
		  <td>&nbsp;</td>
		  <td><a href="../adminSentence/indexView?token=${token}&bookId=${book.id}" target="_blank"><strong>句子管理</strong></a></td>
	    </tr>
		<tr>
		  <td><div align="right">所属分类</div></td>
		  <td><c:forEach var="classify" items="${book.classifys}"> ${classify.name} , </c:forEach>          </td>
	    </tr>
		<tr>
		  <td><div align="right">所有分类</div></td>
		  <td><c:forEach var="classify" items="${book.classifyAll}">
              <label>
              <input id="classifyIds" name="classifyIds" type="checkbox" value="${classify.id}" />
                ${classify.name}</label>
          </c:forEach></td>
	  </tr>
		<tr>
			<td>&nbsp;</td>
			<td><input type="submit" id="okBtn" name="okBtn" value="提交/更新"></td>
	    </tr>
		<tr>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
	  </tr>
		<tr>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
	  </tr>
		<tr>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
	  </tr>
		<tr>
		  <td>&nbsp;</td>
		  <td>&nbsp;</td>
	  </tr>
	</table>
	<p>&nbsp;</p>
</body>

<script type="text/javascript">
	$("#okBtn").click(
			function() {

				var formData = new FormData();
				formData.append("id", $("#id").val());
				formData.append("name", $("#name").val());
				formData.append("stateVo", $("input[name='stateVo']:checked").val());
				formData.append("totalFee", $("#totalFee").val());
				formData.append("sentenceStrs", $("#sentenceStrs").val());
				var chk_value =[];
				$('input[name="classifyIds"]:checked').each(function(){
					chk_value.push($(this).val());
				});
				formData.append("classifyIds",chk_value);
				
				if ($('#coverFile')[0].files[0] != null) {
					formData.append("coverFile", $('#coverFile')[0].files[0]);
				}
				
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
							location.href = "../adminBook/indexView?token="
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
	
	
	$("#adoptBtn").click(
			function() {
				
				var bookUserId = $("#bookUserId").val();
				if( bookUserId == "" || bookUserId == null){
					alert("bookUserId为空");
					return;
				}
				
				var formData = new FormData();
				formData.append("id", '${book.id}');
				formData.append("bookUserId", bookUserId);

				$.ajax({
					url : "adopt",
					dataType : "json",
					type : "POST",
					async : false,
					data : formData,
					processData : false,
					contentType : false,
					headers : {
						"token" : sessionStorage.getItem("token")
					},
					success : function(message) {
						
						if(message.status == 1000){
							
							window.open("detail?token="+sessionStorage.getItem("token") + "&id=${book.id}");

							
						} else if(message.status == 1080){
							alert("暂无数据");
						} else{
							alert("此结果情况未判断");
						}
					
					},
					error : function(message) {
						alert("提交失败");
					}
				});

			});	
</script>

</html>