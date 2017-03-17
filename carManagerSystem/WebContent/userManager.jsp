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
</head>
<body>
	<div class="container">
		<div>
			<ul class="breadcrumb">

				<li><i class="icon-list"></i> <a href="index.html">停车场管理系统</a>

					<i class="icon-angle-right"></i></li>

				<li><a href="#">用户管理界面</a></li>

			</ul>
		</div>
		
		<!-- <div class="container" style="margin-top: 70px"> -->
		<div class="text-center span6">
			<a style="width: 80px" type="submit" id="add" href="modify.jsp"
				class="btn btn-info">注册</a>
		</div>
		<div class="span1 left">
			<a style="width: 80px" id="return" class="btn btn-warning"
				href="index.jsp">返回主页</a>
		</div>
		<!-- </div> -->
		<br>
		<h3>用户信息列表</h3>
		<table class="table table-bordered" width="100%" cellspacing="0"
			cellpadding="0" style="margin-top: 8;">
			<tr>
				<th
					style="text-align: center; vertical-align: middle; width: 15%;">
					用户昵称</th>
				<th
					style="text-align: center; vertical-align: middle; width: 15%;">
					用户姓名</th>
				<th
					style="text-align: center; vertical-align: middle; width: 15%;">
					用户电话</th>
				<th
					style="text-align: center; vertical-align: middle; width: 15%;">
					用户类型</th>
				<th
					style="text-align: center; vertical-align: middle; width: 40%;">
					操作</th>
			</tr>
			<s:iterator var="user" value="userList" status="status">
			<tr>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#user.userName" />
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#user.realName" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:property value="#user.phone" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:if test="<s:property value='#user.type' />=='1'">
						管理员
					</s:if>
					<s:else>
						普通用户
					</s:else>
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<button class="btn btn-danger"
						onclick="location.href='<%=request.getContextPath()%>/deleteUser.action?id=<s:property value="#user.id"/>'">删除</button>
					<button class="btn btn-info" type="button" data-backdrop="static"
						data-toggle="modal" data-target="#dlg"
						onclick="location.href='<%=request.getContextPath()%>/modifyUser.action?id=<s:property value="#user.id"/>'">修改</button>
				</td>
			</tr>
			</s:iterator>
		</table>
	</div>


</body>
</html>