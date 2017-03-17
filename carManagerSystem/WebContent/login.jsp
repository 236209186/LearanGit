<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<title>停车场管理系统登录界面</title>
<meta name="author" content="Muhammad Usman">

<!-- The styles -->
<link id="bs-css" href="css/bootstrap-cerulean.css" rel="stylesheet">
<!-- jQuery -->
<script src="js/jquery-1.7.2.min.js"></script>
<style type="text/css">
body {
	padding-bottom: 40px;
}

.sidebar-nav {
	padding: 9px 0;
}
</style>
<link href="css/bootstrap-responsive.css" rel="stylesheet">
<link href="css/charisma-app.css" rel="stylesheet">
<link href="css/jquery-ui-1.8.21.custom.css" rel="stylesheet">
<link href='css/uniform.default.css' rel='stylesheet'>
<link href='css/colorbox.css' rel='stylesheet'>
<script type="text/javascript">
      function checkForm(){
    		 var username=$("#username").val();
    		 var password=$("#password").val();
    		 $("#username_error").html("");
    		 $("#password_error").html("");
    		 if(username==""){
    			 $("#username_error").html("昵称不能为空！");
    			 return false;
    		 }
    		 if(password==""){
    			 $("#password_error").html("密码不能为空！");
    			 return false;
    		 }
    		 if ($("#ecodeSpan").html()=="验证码错误") {
    			 return false;
    		 }
    		 return true;
    	}
      
      function checkEcode() {
  		$("#ecodeSpan").html("");
  		if ($("#check").val().length>0) {
  			$.ajax({
  		    	url:"checkEcode.action",
  		    	type:"post",
  		    	dataType:"text",
  		    	data:"ecode="+$("#check").val(),
  		    	success:function(result){
  		    		if ("false"==result) {
  		    			$("#ecodeSpan").html("验证码错误");
  		    			$("#ecodeSpan").css("color", '#B52726');
  		    			return false;
  		    		} else {
  		    			$("#ecodeSpan").html("验证码正确");
  		    			$("#ecodeSpan").css("color", 'blue');
  		    		}
  		    	}
  		    });
  			 return true;
  		} else {
  			$("#ecodeSpan").html("请填写验证码");
  			$("#ecodeSpan").css("color", '#B52726');
  			return false;
  		}
  	}
      
   </script>

</head>

<body>
	<div class="container-fluid">
		<div class="row-fluid">

			<div class="row-fluid">
				<div class="span12 center login-header">
					<h2>欢迎使用停车场管理系统</h2>
				</div>
				<!--/span-->
			</div>
			<!--/row-->

			<div class="row-fluid">
				<div class="well span5 center login-box">
					<div class="alert alert-info">请在输入框中填写用户信息</div>
					<form class="form-horizontal" method="post" action="userLogin.action" onsubmit="return checkForm();">
						<fieldset>
							<div class="input-prepend" title="用户名" data-rel="tooltip">
								<span class="add-on"> <i class="icon-user"></i></span><input
									autofocus class="input-large span10" name="user.userName"
									id="username" type="text" value="" />
							</div>
							<div class="clearfix" id="username_error"></div>

							<div class="input-prepend" title="密码" data-rel="tooltip">
								<span class="add-on"><i class="icon-lock"></i></span><input
									class="input-large span10" name="user.passWord" id="password"
									type="password" value="" />
							</div>
							<br/>
							<%--<div class="input-prepend" title="验证码:" data-rel="tooltip">
								<span>验证码:</span>
								<input name="repeat" type="text" id="check"
							onFocus="this.style.backgroundColor='#FBFFD9'"
							onBlur="checkEcode();" style="width: 100px;"/>
						    <img id="img" src="getChcekEcodes.action">
						    <a href="javascript:;" onclick="document.getElementById('img').src='getChcekEcodes.action?time='+new Date().getTime()">
						        <span style="font-size:16px; color: green">看不清,换一张</span>
						    </a>
						    	<span id='ecodeSpan' style="font-size:16px;color:red;"></span>
							</div>
							--%><div class="clearfix" id="password_error"></div>

							<div class="input-prepend">
								<s:if test="errorInfo!=null">
									${errorInfo}
								</s:if>
							</div>
							<div class="clearfix"></div>

							&nbsp; &nbsp;&nbsp;
							<p class="right span1"></p>
							<p class="right span4">
								<input type="submit" value="登录" style="width: 130px" class="btn btn-primary"/> 
							</p>
							<p class="right span1"></p>
							<p class="right span4" style="margin-left: 10px;">
								<input type="reset" value="重置" style="width: 130px" class="btn btn-warning"/> 
							</p>
						</fieldset>
					</form>
					<span><a href="modify.jsp">游客注册</a>&nbsp;&nbsp;&nbsp;</span><span><a href="employeeRegister.jsp">员工注册</a></span>&nbsp;&nbsp;&nbsp;</span><span><a href="adminRegister.jsp">管理员注册</a></span>
				</div>
				<!--/span-->
				
			</div>
			<!--/row-->
		</div>
		<!--/fluid-row-->

	</div>
</body>
</html>
