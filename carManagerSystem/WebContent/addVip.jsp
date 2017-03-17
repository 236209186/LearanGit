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
		    	url:"queryVipByNameAjax.action",
		    	type:"post",
		    	dataType:"text",
		    	data:"userName="+$("#userName").val(),
		    	success:function(result){
		    		if ("true"==result) {
		    			$("#userNamespan").html("用户名已经占用");
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
	function carNo() {
		if ($("#carNo").val().length != 0) {
			$("#carNospan").html("");
			$("#carNospan").css("color", 'green');
			return true;
		} else {
			$("#carNospan").html("请输入车牌");
			$("#carNospan").css("color", '#B52726');
			return false;
		}
	}

	function startTime() {
		if ($("#startTime").val().length != 0) {
			$("#startTimespan").html("");
			$("#startTimespan").css("color", 'green');
			return true;
		} else {
			$("#startTimespan").html("请输入开始时间");
			$("#startTimespan").css("color", '#B52726');
			return false;
		}
	}
	
	function endTime() {
		if ($("#endTime").val().length != 0) {
			$("#endTimespan").html("");
			$("#endTimespan").css("color", 'green');
			return true;
		} else {
			$("#endTimespan").html("请输入截至时间");
			$("#endTimespan").css("color", '#B52726');
			return false;
		}
	}

	//所有验证通过调用添加方法
	function checkValue() {
		if (cheakName() && carNo()&& endTime()&& startTime()) {
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
	    		$("#location").empty();
	    		for (var i = 0 ;i < listResult.length;i++) {
	    			$("#location").append("<option value="+listResult[i]+">"+listResult[i]+"</option>");
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

				<li><i class="icon-list"></i> <a href="userLogin.action">返回主页</a>

					<i class="icon-angle-right"></i></li>

				<li><a href="#">添加VIP界面</a></li>

			</ul>
		</div>
		<h4>添加记录信息</h4>
		<form action="addVipInfo.action" method="post"
			onsubmit="return checkValue();">
			<div style="border: solid 1px; border-color: #DDDDDD">
				<table class="tableSet">
					<tr>
						<td style="text-align: right;">用户名：</td>
						<td><input type="text" name="vip.userName" id="userName" />
							<span style="color: red;">*</span> <label id="userNamespan"></label></td>
						<td style="text-align: right;">车牌号：</td>
						<td><input type="text" name="vip.carNo" id="carNo" /> <span
							style="color: red;">*</span> <label id="carNospan"></label></td>

					</tr>
					<tr>
						<td style="text-align: right;">停车场</td>
						<td><select name="vip.parkNo" id="parkNo">
							<s:iterator var="parkNo" value="parkList">
								<option value="${parkNo}" onclick="selectNumByParkName('${parkNo}')">${parkNo}</option>
							</s:iterator>
						</select> <span style="color: red;">*</span></td>
						<td style="text-align: right;">车位</td>
						<td><select name="vip.location" id="location">
							<%-- <s:iterator var="location" value="locationList">
								<option value="${location}">${location}</option>
							</s:iterator> --%>
						</select> <span style="color: red;">*</span></td>
					</tr>
					<tr>
						<td style="text-align: right;">开始时间：</td>
						<td>
							<div class="form-group">
								<input type="text" name="vip.startTime" class="form-control"
									id="startTime" placeholder="格式:2016/01/01 00:00"> <span
									style="color: red;">*</span> <label id="startTimespan"></label>
							</div>
						</td>
						<td style="text-align: right;">截至时间：</td>
						<td>
							<div class="form-group">
								<input type="text" name="vip.endTime" class="form-control"
									id="endTime" placeholder="格式:2016/01/01 00:00"> <span
									style="color: red;">*</span> <label id="endTimespan"></label>
							</div>
						</td>
					</tr>

					<tr>
						<td style="text-align: right;">消费类型：</td>
						<td><select name="vip.type" id="type">
								<option value="0">租</option>
								<option value="1">售</option>
						</select> <span style="color: red;">*</span></td>
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
	</div>
</body>
</html>