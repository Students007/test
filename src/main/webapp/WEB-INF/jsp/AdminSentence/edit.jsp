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
<title>绘本句子-新增-编辑-详情</title>
</head>
<body>
	<div align="right">
		<a
			href="javascript:window.opener=null;window.open('','_self');window.close();">关闭</a>
	</div>
	<table width="90%" border="1" align="center">
		<tr>
		  <td width="8%"><div align="right">ID</div></td>
			<td width="29%"><input name="id" type="text" id="id" value="${sentence.id}"
				disabled="disabled" size="40"></td>
		    <td width="15%" rowspan="12"><div align="right">封面PNG<br />
		      300*300<br />
	      非白背景</div></td>
		    <td width="15%" rowspan="12"><p>
		      <input type="file" id="imgFile" name="imgFile" value="${sentence.img}">
		      <br />
		      <img src="${sentence.img}" alt="暂无" /> </td>
		</tr>
		<tr>
          <td><div align="right">绘本</div></td>
		  <td>${sentence.book}</td>
      </tr>
		<tr>
		  <td><div align="right">序号</div></td>
	      <td width="29%"><input name="number" type="text" id="number" size="40"
				value="${sentence.number}"></td>
      </tr>
		<tr>
			<td><div align="right">句子</div></td>
			<td><input name="name" type="text" id="name" size="40"
				value="${sentence.name}"></td>
        </tr>
		<tr>
		  <td><div align="right">录音</div></td>
		  <td><a href="${sentence.voice}" target="_blank">播放</a> &nbsp; <input type="file" id="voiceFile" name="voiceFile"></td>
        </tr>
		<tr>
          <td><div align="right">编辑时间</div></td>
		  <td><fmt:formatDate value="${sentence.date}" type="both" /></td>
      </tr>
		<tr>
          <td><div align="right">状态</div></td>
		  <td><c:choose>
              <c:when test="${sentence.state == true}">
                <input name="stateVo" id="stateVo" type="radio" value="true" checked="checked"/>
                True
                <input name="stateVo" id="stateVo" type="radio" value="false" />
                False </c:when>
              <c:otherwise>
                <input name="stateVo" id="stateVo" type="radio" value="true" />
                True
                <input name="stateVo" id="stateVo" type="radio" value="false" checked="checked"/>
                False </c:otherwise>
            </c:choose>          </td>
      </tr>
		<tr>
		  <td>&nbsp;</td>
		  <td><input type="submit" id="saveBtn" name="saveBtn" value="提交/更新"></td>
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
	$("#saveBtn").click(
			function() {

				var formData = new FormData();
				formData.append("id", $("#id").val());
				formData.append("bookId", '${sentence.bookId}');
				formData.append("number", $("#number").val());
				formData.append("name", $("#name").val());
				
				if ($('#voiceFile')[0].files[0] != null) {
					formData.append("voiceFile", $('#voiceFile')[0].files[0]);
				}
				
				if ($('#imgFile')[0].files[0] != null) {
					formData.append("imgFile", $('#imgFile')[0].files[0]);
				}
				
				formData.append("stateVo", $("input[name='stateVo']:checked").val());
				
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
							location.href = "../adminSentence/indexView?token="
									+ sessionStorage.getItem("token") + "&bookId=${sentence.bookId}";
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