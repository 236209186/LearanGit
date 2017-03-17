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
	function cheakName() {
		if ($("#userName").val().length >0) {
			$("#userNamespan").html("");
			$("#userNamespan").css("color", 'green');
			$.ajax({
		    	url:"queryUserByNameAjax.action",
		    	type:"post",
		    	dataType:"text",
		    	data:"userName="+$("#userName").val(),
		    	success:function(result){
		    		if ("false"==result) {
		    			$("#userNamespan").html("请输入正确用户名");
		    			$("#userNamespan").css("color", '#B52726');
		    			return false;
		    		}
		    	}
		    });
			return true;
		} else {
			$("#userNamespan").html("请输入用户名称");
			$("#userNamespan").css("color", '#B52726');
			return false;
		}
	}
	
	function checkParkName() {
		if ($("#parkName").val().length != 0) {
			$("#parkNamespan").html("");
			$("#parkNamespan").css("color", 'green');
			return true;
		} else {
			$("#parkNamespan").html("请输入车库名");
			$("#parkNamespan").css("color", '#B52726');
			return false;
		}
	}

	function inTime() {
		if ($("#inTime").val().length != 0) {
			$("#inTimespan").html("");
			$("#inTimespan").css("color", 'green');
			return true;
		} else {
			$("#inTimespan").html("请输入进入时间");
			$("#inTimespan").css("color", '#B52726');
			return false;
		}
	}

	//所有验证通过调用添加方法
	function checkValue() {
		/*if (checkParkName()) {
			if ($("#carNo").val().length != 0) {
				$("#carNospan").html("");
				$("#carNospan").css("color", 'green');
				return true;
			} else {
				$("#carNospan").html("请输入车牌号");
				$("#carNospan").css("color", '#B52726');
				return false;
			}
			if ($("#locationNo").val().length != 0) {
				$("#locationNospan").html("");
				$("#locationNospan").css("color", 'green');
				return true;
			} else {
				$("#locationNospan").html("请输入车位号");
				$("#locationNospan").css("color", '#B52726');
				return false;
			}
		} else{
			return false;
		}*/
		if ($("#carNo").val().length != 0) {
			$("#carNospan").html("");
			$("#carNospan").css("color", 'green');
			return true;
		} else {
			$("#carNospan").html("请输入车牌号");
			$("#carNospan").css("color", '#B52726');
			return false;
		}
		if ($("#locationNo").val().length != 0) {
			$("#locationNospan").html("");
			$("#locationNospan").css("color", 'green');
			return true;
		} else {
			$("#locationNospan").html("请输入车位号");
			$("#locationNospan").css("color", '#B52726');
			return false;
		}
	}

	function modifyInfo(id){
		  $.ajax({
		    	url:"modifyCarParkInfo.action",
		    	type:"post",
		    	dataType:"json",
		    	data:"id="+id,
		    	success:function(result){
		    	$("#userName").val(result.userName);
		    	$("#userName").attr("disabled","true");
		    	$("#parkName").val(result.parkName);
		    	$("#carNo").val(result.carNo);
		    	$("#inTime").val(result.inTime);
		    	$("#type").val(result.type);
		    	$("#outTime").val(result.outTime);
		    	$("#locationNo").val(result.locationNo);
		    	}
		    });
		}

	function selectNumByParkName(parkName){
		$.ajax({
	    	url:"selectCarParkInfoByName.action",
	    	type:"post",
	    	dataType:"json",
	    	data:"name="+parkName,
	    	success:function(result){
	    		var listResult = result.locationList;
	    		console.log(listResult);
	    		$("#locationNo").empty();
	    		for (var i = 0 ;i < listResult.length;i++) {
	    			$("#locationNo").append("<option value="+listResult[i]+">"+listResult[i]+"</option>");
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

				<li><i class="icon-list"></i> <a href="index.jsp">返回主页</a>

					<i class="icon-angle-right"></i></li>

				<li><a href="#">车辆入住登记界面</a></li>

			</ul>
		</div>
		<h4>添加记录信息</h4>
		<form action="addCarParkInfo.action" method="post"
			onsubmit="return checkValue();">
			<div style="border: solid 1px; border-color: #DDDDDD">
				<table class="tableSet">
					<tr>
						<td style="text-align: right;">车牌号：</td>
						<td><input type="text" name="carParkInfo.carNo" id="carNo" />
							<span style="color: red;">*</span> <label id="carNospan"></label>
						</td>
						<td style="text-align: right;">车库名：</td>
						<td>
							<select name="carParkInfo.parkName" id="parkName">
							<s:iterator var="parkNo" value="parkList">
								<option value="${parkNo}" onclick="selectNumByParkName('${parkNo}')">${parkNo}</option>
							</s:iterator>
							</select>
							<span style="color: red;">*</span> 
							<label id="parkNamespan"></label>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">车位号：</td>
						<td>
							<select name="carParkInfo.locationNo" id="locationNo">
							<%-- <s:iterator var="location" value="locationList">
								<option value="${location}">${location}</option>
							</s:iterator> --%>
							</select>
							<span style="color: red;">*</span> 
							<label id="locationNospan"></label></td>
						<td style="text-align: right;">消费类型：</td>
						<td><select name="carParkInfo.type" id="type">
								<option value="0">租</option>
								<option value="1">售</option>
						</select> <span style="color: red;">*</span></td>
					</tr>
					<tr>
						<td style="text-align: right;">用户名：</td>
						<td><input type="text" name="carParkInfo.userName" id="userName" >
						<span style="color: red;">注册用户才填写</span><label id="userNamespan"></label>
						</td>
					</tr>

					<tr>
						<td></td>
						<td></td>
						<td>
							<div class="text-center span3">
								<input type="submit" value="添加" class="btn btn-info"
									style="width: 100px" />
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
					车库名</th>
				<th
					style="text-align: center; vertical-align: middle; width: 100px;">
					车牌名</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					车位号</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					进入时间</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					消费类型</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					操作</th>
			</tr>
			<s:iterator var="carPark" value="listData" status="status">
				<tr>
					<td style="text-align: center; vertical-align: middle;"><s:property
							value="#carPark.userName" /></td>
					<td style="text-align: center; vertical-align: middle;"><s:property
							value="#carPark.parkName" /></td>
					<td style="text-align: center; vertical-align: middle;"><s:property
							value="#carPark.carNo" /></td>
				 	<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:property value="#carPark.locationNo" />
					</td>
					<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:property value="#carPark.inTime" />
					</td>
					<td
						style="text-align: center; vertical-align: middle; width: 200px;">
						<s:if test="#carPark.type==0">租</s:if>
						<s:else>
							售
						</s:else>
					</td>
					<td style="text-align: center; vertical-align: middle;">
						<button class="btn btn-danger" onclick="location.href='<%=request.getContextPath()%>/deleteCarInfo.action?id=<s:property value="#carPark.id"/>'">删除</button>
						<%-- <button class="btn btn-info" type="button" data-backdrop="static"
							data-toggle="modal" data-target="#dlg"
							onclick="modifyInfo(${carPark.id})">修改</button> --%>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
</body>
</html>