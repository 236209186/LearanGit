<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<link href="css/font-awesome.min.css" rel="stylesheet" type="text/css" />
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<link href="css/style.css" rel="stylesheet" type="text/css" />
<link href="css/style-responsive.css" rel="stylesheet" type="text/css" />
<link href="css/default.css" rel="stylesheet" type="text/css"
	id="style_color" />
<link href="css/uniform.default.css" rel="stylesheet" type="text/css" />
<script src="bootstrap/js/jquery-1.9.0.js"></script>

<script type="text/javascript">
	
	function querUserInfo(name) {
		var userName = "";
		if(!name){
			userName = $("#name").val().trim();
		} else {
			userName=name;
		}
		myAjax(userName);
	}
	function onloadfunction(){
		myAjax("");
	}
	
	function myAjax(userName){
		$.ajax({
			url : "querUserInfo.action",
			type : "post",
			data : "userName="+userName,
			dataType : "json",
			success : function(result) {
			console.log(result);
			var ids = $("[id=di]");
			console.log(ids);
			if(ids.length>0){
				for(var i=0;i<ids.length;i++){
				ids[i].remove();
			}
			}
			for(var i=0; i<result.length; i++)  
			{
				var htmlValue="";
				var data = eval(result[i]);
				var id = data.id;
				htmlValue ="<tr id='di'><td style='text-align: center; vertical-align: middle;'>"+data.userName+"</td>"
				          /* +"<td style='text-align: center; vertical-align: middle;'>"+data.realName+"</td>" */
				          +"<td style='text-align: center; vertical-align: middle;'>"+data.parkName+"</td>"
				          +"<td style='text-align: center; vertical-align: middle;'>"+data.carNo+"</td>"
				          +"<td style='text-align: center; vertical-align: middle;'>"+data.hour+"</td>"
				          +"<td style='text-align: center; vertical-align: middle;'>"+data.type+"</td>"
				          +"<td style='text-align: center; vertical-align: middle;'>"+data.charge+"</td>"
				          +"<td style='text-align: center; vertical-align: middle;'>"+data.createTime+"</td>"
				          +"<td style='text-align: center; vertical-align: middle;'>"
				          +"<button class='btn btn-danger' onclick='deleteUserChargeInfo("+'"{id}"'+")'>删除</button></td></tr>";
				          htmlValue = htmlValue.replace(/{id}/g,id);
				          $("#tableData").append(htmlValue);
			}  
			
				
			}
		});
	};
	//onload=onloadfunction;
	
	function deleteUserChargeInfo(id){
		$.ajax({
			url : "deleteUserChargeInfo.action",
			type : "post",
			data : "id="+id,
			dataType : "json",
			success : function(result) {
				querUserInfo(result.userName);
			}
		});
	}
	
	function queryAllCarInfo(){
    	if('<s:property value="#session.type" />'!=1){
    		alert("你无权执行该操作");
    	} else { 
    		window.location.href="queryAllCarInfo.action";
    	 } 
	}
	
	function getAllParkName(){
    	if('<s:property value="#session.type" />'!=1){
    		alert("你无权执行该操作");
    	} else {
    		window.location.href="getAllParkName.action";
    	}
	}
	
	function getListParkInfo(){
    	if('<s:property value="#session.type" />'!=1){
    		alert("你无权执行该操作");
    	} else { 
    		window.location.href="getListParkInfo.action";
    	 } 
	}
	
	function queryAllChargeInfo(){
    	 if('<s:property value="#session.type" />'!=1){
    		alert("你无权执行该操作");
    	} else { 
    		window.location.href="queryAllChargeInfo.action";
    	 } 
	}
	
	function queryAllVipInfo(){
	   	 if('<s:property value="#session.type" />'!=1){
	   		alert("你无权执行该操作");
	   	} else { 
	   		window.location.href="queryAllVipInfo.action";
	   	 } 
	}
	
	function getReservation(){
	   	 if('<s:property value="#session.type" />'!=1){
	   		alert("你无权执行该操作");
	   	} else { 
	   		window.location.href="<%=request.getContextPath()%>/getReservation.jsp";
	   	 } 
	}

	function queryBespeakInfo(){
		 if('<s:property value="#session.count" />'>=1){
		   	alert("你爽约的次数超过规定次数，已被拉入黑名单");
		 }else{
			 window.location.href="getBespeakInfo.action";
		 }
		  

	}
</script>
</head>

<body class="page-header-fixed">
	<%
	String LOGIN_USER = (String)session.getAttribute("userName");
	if(LOGIN_USER==null || LOGIN_USER.length()==0){
		response.sendRedirect("login.jsp");
	}
	%>
	<div class="header navbar navbar-inverse navbar-fixed-top container">
		<div class="navbar-inner">
			<div class="container-fluid">
				<a class="brand" href="#" id="im"> </a> <a href="javascript:;"
					class="btn-navbar collapsed" data-toggle="collapse"
					data-target=".nav-collapse"> <img src="img/sidebar-toggler.jpg"
					alt="" width="100%" />
				</a>
				<ul class="nav pull-right">
					<li class="dropdown" id="header_inbox_bar"
						style="outline: 0px; box-shadow: rgba(221, 81, 49, 0) 0px 0px 13px; outline-offset: 20px;">

						<a href='logOut.action' class="dropdown user">退出</a>

						<ul class="dropdown-menu extended inbox" id="message">

						</ul>

					</li>

					<li class="dropdown user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
					<label> </label> 
					欢迎你&nbsp;&nbsp;&nbsp;
							<span class="username" id="userName"><s:property
									value="#session.userName" /> 
							</span> 
					<i class="icon-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li><a href="login.html"><i class="icon-key"></i> 退出</a></li>
					</ul>
					</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="page-container">
		<div class="page-content">
			<div class="container-fluid">
				<div class="row-fluid">
					<div class="span12">
						<h3 class="page-title">
							<center>停车场管理系统</center>
							<span id="nowDiv"
								style="margin-left: 570px; font-size: 15px; color: #66cc33"></span>
						</h3>
					</div>
				</div>
				<div class="container">
					<div>
						<ul class="breadcrumb">

							<li><i class="icon-list"></i> <a href="userLogin.action">停车管理系统</a>
								<i class="icon-angle-right"></i></li>

							<li><a href="#">主界面</a></li>

						</ul>
					</div>
					<div style="border: solid 1px; border-color: #DDDDDD">
						<table class="tableSet">
							<tr>
								<td>用户名：</td>
								<td><input style="width: 120px" type="text" id="name" value="" name="userName"></td>
								<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<td><a data-dismiss="modal" id="find" style="width: 100px"
									onclick="querUserInfo()" class="btn btn-success"
									data-toggle="modal"> <i class="icon-white icon-search"></i>
										查询
								</a></td>
							</tr>
						</table>
					</div>
					<br>
					<div class="container">
						<a id="add" style="width: 120px" href="queryUserByName.action"
							class="btn btn-info">用户管理</a>
						<a id="uptable2" onclick="queryBespeakInfo()" style="width: 120px" class="btn btn-info">车位预约管理</a>
						<a id="Reservation"onclick="getReservation()" style="width: 100px" class="btn btn-info">获取预约信息</a>
						<a id="uptable2" onclick="queryAllCarInfo()" style="width: 100px" class="btn btn-info">临时车辆登记</a>
						<!-- <a id="uptable2" onclick="queryAllVipInfo()" style="width: 90px" class="btn btn-info">VIP车辆登记</a> -->
						<a id="addTable" onclick="getAllParkName()" style="width: 100px" class="btn btn-info">停车位查询管理</a> 
						<a id="uptable2" onclick="getListParkInfo()" style="width: 100px" class="btn btn-info">车库信息管理</a>
						<a id="addphone1" onclick="queryAllChargeInfo()"
							style="width: 100px" class="btn btn-info">收费信息管理</a>
					</div>
					<br> <br>

					<table class="table table-bordered table-hover">
						<thead>
							<tr>
								<th>用户名</th>
								<th>停车场</th>
								<th>车牌号</th>
								<th>使用时间(小时)</th>
								<th>类型</th>
								<th>费用</th>
								<th>离开日期</th>
								<th>操作</th>
							</tr>
						</thead>

						<tbody id="tableData">
							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="footer">
		<div class="footer-tools">
			<span class="go-top"> <i class="icon-angle-up"></i>
			</span>
		</div>

	</div>
</body>
</html>