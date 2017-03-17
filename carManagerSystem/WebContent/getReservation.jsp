<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>欢迎使用停车场管理系统</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src="bootstrap/js/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript">

	function getReservationInfo() {
		var userName = $("#userName").val().trim();
		var carNo = $("#carNo").val().trim();
		if (carNo.length == 0 || userName.length== 0) {
			alert("请输入用户名及车牌号");
			return false;
		}
		$.ajax({
			url : "getReservationInfo.action",
			type : "post",
			dataType : "json",
			data : "name=" + userName + "&carNo=" + carNo,
			success : function(arg) {
				console.log(arg.isOk);
				if ("noreservationinfo" == arg.isOk ) {
					alert("该用户没有预约");
				} else if ("success" == arg.isOk) {
					alert("预约号使用成功！！！");
				}
				else if ("chaoshi" == arg.isOk) {
					alert("预约超时，请重新预约！！！");
					
				}
			}
		});
		
	}
</script>

</head>
<body>
	<%
	String LOGIN_USER = (String)session.getAttribute("userName");
	if(LOGIN_USER==null || LOGIN_USER.length()==0){
		response.sendRedirect("login.jsp");
	}
	%>
	<div class="container">
		<div>
			<ul class="breadcrumb">

				<li><i class="icon-list"></i> <a href="index.jsp">返回主页</a> <i
					class="icon-angle-right"></i></li>

				<li><a href="#">车辆入住登记界面</a></li>

			</ul>
		</div>
		<h4>添加记录信息</h4>
		<div style="border: solid 1px; border-color: #DDDDDD">
			<table class="tableSet">
				<tr>
					<td style="text-align: right;">用户名：</td>
					<td>
						<input type="text" id="userName" name="userName">
					</td>
					<td style="text-align: right;">车牌号：</td>
					<td>
						<input type="text" id="carNo" name="carNo">
					</td>
				
					<td><a id="find" onclick="getReservationInfo()" class="btn btn-success"
						data-toggle="modal"> <i class="icon-white icon-search"></i> 使用
					</a></td>
				</tr>
			</table>
		</div>
	</div>
</body>
</html>