<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注册管理员信息</title>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script src="bootstrap/js/jquery-1.9.0.js"></script>
<link rel="stylesheet" href="bootstrap/css/bootstrap-responsive.min.css">
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
<script src="bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript">  
	function cheakName() {
		$("#userNamespan").html("");
		if ($("#userName").val().length>0) {
			$("#userNamespan").html("");
			var regEx = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$/;
			if(regEx.test($("#userName").val()) == false){
				console.log("21======");
				$("#userNamespan").html("用户必须由4-8位的数字和字母组成");
    			$("#userNamespan").css("color", '#B52726');
    			return false;
			}
			$("#userNamespan").css("color", 'green');
			$.ajax({
		    	url:"queryUserByNameAjax.action",
		    	type:"post",
		    	dataType:"text",
		    	data:"userName="+$("#userName").val(),
		    	success:function(result){
		    		if ("true"==result) {
		    			$("#userNamespan").html("用户名已被注册");
		    			$("#userNamespan").css("color", '#B52726');
		    			return false;
		    		}
		    	}
		    });
			 return true;
		} else {
			$("#userNamespan").html("用户名不能为空");
			$("#userNamespan").css("color", '#B52726');
			return false;
		}
	}
	
	function cheakPwd() {
		$("#passWordspan").html("");
		if ($("#passWord").val().length>0) {
			$("#passWordspan").html("");
			var regEx = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{4,8}$/;
			if(regEx.test($("#passWord").val()) == false){
				$("#passWordspan").html("密码必须由4-8位的数字和字母组成");
    			$("#passWordspan").css("color", '#B52726');
    			$("#passWord").val("");
    			return false;
			}
		}
	}
	function cheakPhone() {
		var reg = /^(13|15|18)\d{9}$/;
		if (reg.test($("#phone").val())) {
			$("#phonespan").html("");
			$("#phonespan").css("color", 'green');
			return true;
		} else {
			$("#phonespan").html("13|15|18的11位电话号码");
			$("#phonespan").css("color", '#B52726');
			return false;
		}
	}
	

	
	
	
	
    //所有验证通过调用添加方法
	function cheakAll() {
		$("#realNamespan").html("");
		$("#passWordspan").html("");
		$("#repassWordspan").html("");
		if (!(cheakName()&&cheakPhone())) {
			return false;
		}
		if ($("#realName").val().trim().length == 0) {
			$("#realNamespan").html("真实姓名不能为空");
			$("#realNamespan").css("color", '#B52726');
			return false;
		} 
		if ($("#passWord").val().trim().length == 0) {
			$("#passWordspan").html("密码不能为空");
			$("#passWordspan").css("color", '#B52726');
			return false;
		} 
		if ($("#repassWord").val().trim().length == 0 || $("#repassWord").val()!=$("#passWord").val()) {
			$("#repassWordspan").html("输入的两次密码不一致");
			$("#repassWordspan").css("color", '#B52726');
			return false;
		}
		if ($("#adminCommandSpan").html().trim().length > 0) {
			return false;
		}
		return true;
	} 
	/* 
	function cheakName() {
		$("#userNamespan").html("");
		if ($("#userName").val().length>0) {
			$("#userNamespan").html("");
			$("#userNamespan").css("color", 'green');
			$.ajax({
		    	url:"queryUserByNameAjax.action",
		    	type:"post",
		    	dataType:"text",
		    	data:"userName="+$("#userName").val(),
		    	success:function(result){
		    		if ("true"==result) {
		    			$("#userNamespan").html("用户名已被注册");
		    			$("#userNamespan").css("color", '#B52726');
		    			return false;
		    		}
		    	}
		    });
			 return true;
		} else {
			$("#userNamespan").html("用户名不能为空");
			$("#userNamespan").css("color", '#B52726');
			return false;
		}
	}
	 */
	function adminCommandFunction() {
		if ($("#adminCommand").val().length>0) {
			$("#adminCommandSpan").html("");
			$("#adminCommandSpan").css("color", 'green');
			$.ajax({
		    	url:"queryAdminCommand.action",
		    	type:"post",
		    	dataType:"text",
		    	data:"adminCommand="+$("#adminCommand").val(),
		    	success:function(result){
		    		if ("false"==result) {
		    			$("#adminCommandSpan").html("管理员命令错误");
		    			$("#adminCommandSpan").css("color", '#B52726');
		    			return false;
		    		}
		    	}
		    });
		} else {
			$("#adminCommandSpan").html("管理员命令不能为空");
			$("#adminCommandSpan").css("color", '#B52726');
			return false;
		}
	}
	
</script>

</head>
<body>
	<div class="container">
		<div>
			<ul class="breadcrumb">

				<li><i class="icon-list"></i> <a href="index.jsp">返回主页</a>

					<i class="icon-angle-right">></i></li>

				<li><a href="#">管理员注册/修改界面</a></li>

			</ul>
		</div>
		<h4 style="text-align:center;">注册/修改</h4>
		
		<form action="register.action" method="post" onsubmit="return cheakAll()">
			<div style="border: solid 1px; border-color: #DDDDDD">
			<table class="tableSet">
				<tr>
					<td style="display: none;">
						<input type="text" name="user.id" id="id" value="${user.id}"/>
					</td>
					<td style="text-align:right;">用户名：</td>
					<td><input type="text" name="user.userName" id="userName"
						onblur="cheakName()" value="${user.userName}"/> <span style="color: red;">*</span>
						 <span id="userNamespan"></span>
					</td>
					<td style="text-align:right;">真实姓名：</td>
					<td><input type="text" name="user.realName" id="realName" value="${user.realName}"/> <span style="color: red;">*</span> <span
						id="realNamespan"></span></td>
				</tr>

				<tr>
					<td style="text-align:right;">密码：</td>
					<td><input type="password" id="passWord" name="user.passWord" onblur="cheakPwd()" /> <span style="color: red;">*</span> <span
						id="passWordspan"></span></td>
					<td style="text-align:right;">核对密码：</td>
					<td><input type="password" name="repassWord" id="repassWord"/> <span style="color: red;">*</span> <span
						id="repassWordspan"></span></td>
				</tr>
				<tr>
					<td style="text-align:right;">电话：</td>
					<td><input type="text" name="user.phone" id="phone" value="${user.phone}"/> 
					<span style="color: red;">*</span> <span
						id="phonespan"></span></td>
					<td style="text-align:right;display:none;">是否管理员：</td>
					<td style="display: none;"><input type="text" name="user.type" id="type" value="1"/> 
				</tr>
				<tr>
					<td style="text-align:right;">管理员指令：</td>
					<td><input type="text" name="adminCommand" id="adminCommand" value="" onblur="adminCommandFunction()"/> 
					<span style="color: red;">*</span> <span
						id="adminCommandSpan"></span></td>
				</tr>
			</table>
			</div>
			<div class="text-center span6">
				<input type="submit" value="提交" style="width: 105px" class="btn btn-info"/>
			</div>
		</form>
		
	</div>
	<div class="container" style="margin-top: 70px">
		
	</div>

</body>
</html>