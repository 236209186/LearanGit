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
		var reg = /^[a-zA-Z\u4e00-\u9fa5]{6,}$/;
		if (reg.test($("#userName").val())) {
			 $("#userNamespan").html("");
			$("#userNamespan").css("color", 'green');
			 return true;
		} else {
			$("#userNamespan").html("请输入大于2个字符的英文或中文名称");
			$("#userNamespan").css("color", '#B52726');
			return false;
		}
	}
	function checkRealName() {
			var reg = /^[a-zA-Z\u4e00-\u9fa5]{2,}$/;
			if (reg.test($("#userName").val())) {
				 $("#realNamespan").html("");
				$("#realNamespan").css("color", 'green');
				 return true;
			} else {
				$("#realNamespan").html("请输入大于2个字符的英文或中文名称");
				$("#realNamespan").css("color", '#B52726');
				return false;
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
		if (cheakName()&&cheakPhone()) {
			alert("== 45 line ==");
		}
	} 
	
	
</script>

</head>
<body>
	<div class="container">
		<div>
			<ul class="breadcrumb">

				<li><i class="icon-list"></i> <a href="index.html">停车场管理系统</a>

					<i class="icon-angle-right">></i></li>

				<li><a href="#">车库管理</a></li>

			</ul>
		</div>
		<form action="" method="post">
			<table class="tableSet">
				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;用户名：</td>
					<td><input type="text" name="user.userName" id="userName"
						onblur="cheakName()" /> <span style="color: red;">*</span> <label
						id="userNamepan"></label>
					</td>
					<td>真实姓名：</td>
					<td><input type="text" name="user.realName" id="realName"
						onblur="checkRealName()" /> <span style="color: red;">*</span> <label
						id="realNamespan"></label></td>
				</tr>

				<tr>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;密码：</td>
					<td><input type="password" id="passWord" name="user.passWord" /> <span
						style="color: red;">*</span> <label id="passWordpan"></label></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;电话：</td>
					<td><input type="text" name="user.phone" id="phone"
						onblur="cheakPhone()" /> <span style="color: red;">*</span> <label
						id="phonespan"></label></td>
				</tr>
				<tr>
					<td>是否管理员：</td>
					<td><select name="user.type" id="type" required>
							<option value="1">是</option>
							<option value="0">否</option>
					</select> <span style="color: red;">*</span></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="container" style="margin-top: 70px">
		<div class="text-center span6">
			<a style="width: 80px" type="submit" id="add" onclick="cheakAll()"
				class="btn btn-info">添加</a>
		</div>
	</div>


</body>
</html>