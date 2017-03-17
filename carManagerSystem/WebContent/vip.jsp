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
	//所有验证通过调用添加方法
	function checkValue() {
			if ($("#carNo").val().length != 0) {
				$("#carNospan").html("");
				$("#carNospan").css("color", 'green');
				return true;
			} else {
				$("#carNospan").html("请输入车牌号");
				$("#carNospan").css("color", '#B52726');
				return false;
			}
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

				<li><i class="icon-list"></i> <a href="userLogin.action">返回主页</a>

					<i class="icon-angle-right"></i></li>

				<li><a href="#">VIP车辆入住登记界面</a></li>

			</ul>
		</div>
		<form action="queryVipInfoByCarNo.action" method="post"
			onsubmit="return checkValue();">
			<div style="border: solid 1px; border-color: #DDDDDD">
				<table class="tableSet">
					<tr>
						<td style="text-align: right;">车牌号：</td>
						<td><input type="text" name="vip.carNo" id="carNo" />
							<span style="color: red;">*</span> <label id="carNospan"></label></td>
						<td>
							<div class="text-center span3">
								<input type="submit" value="查询" class="btn btn-info"
									style="width: 100px" />
							</div>
						</td>
						<td>
							<div class="text-center span3">
								<input type="button" value="添加VIP" class="btn btn-info"
									style="width: 100px" onclick="location.href='queryVipInfo.action'"/>
							</div>
						</td>
					</tr>
				</table>
			</div>
		</form>
		<br>
		<h4>查询记录信息</h4>
		<table class="table table-bordered" width="100%" cellspacing="0"
			cellpadding="0" style="margin-top: 8;">
			<tr>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					用户名</th>
				<th
					style="text-align: center; vertical-align: middle; width: 300px;">
					车牌号</th>
				<th
					style="text-align: center; vertical-align: middle; width: 100px;">
					租或售</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					开始时间</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					截至时间</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					注册时间</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					停车场</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					停车位</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					操作</th>
			</tr>
			<s:iterator var="vip" value="vips"  status="status">
				<tr>
					<td style="text-align: center; vertical-align: middle;">
						<s:property value="#vip.userName" />
					</td>
					<td style="text-align: center; vertical-align: middle;">
					<s:property value="#vip.carNo"/>
					</td>
					<td style="text-align: center; vertical-align: middle;">
					<s:if test="#vip.type==0">租</s:if>
					<s:else>
						售
					</s:else>
					</td>
					<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:property value="#vip.startTime" />
					</td>
					<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:property value="#vip.endTime" />
					</td>
					<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:property value="#vip.createTime" />
					</td>
					<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:property value="#vip.parkNo" />
					</td>
					<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:property value="#vip.location" />
					</td>
					<td style="text-align: center; vertical-align: middle;">
						<button class="btn btn-danger"
							onclick="location.href='<%=request.getContextPath()%>/deleteVipInfoById.action?id=<s:property value="#vip.id"/>'">删除</button>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>

</body>
</html>