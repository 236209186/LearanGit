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
/*
 * 
   修改车库的相关信息
 */
function modifyInfo(id){
		  $.ajax({
		    	url:"modifyParkInfo.action",
		    	type:"post",
		    	dataType:"json",
		    	data:"id="+id,
		    	success:function(result){
		    	$("#parkName").val(result.parkName);
		    	$("#parkLocation").val(result.parkLocation);
		    	$("#parkNumber").val(result.parkNumber);
		    	$("#feeScale").val(result.feeScale);
		    	$("#openDate").val(result.openDate);
		    	$("#closeDate").val(result.closeDate);
		    	}
		    	
		    });
 }
 
	function parkName() {
		if ($("#parkName").val().length != 0) {
			$("#parkNamespan").html("");
			$("#parkNamespan").css("color", 'green');
			return true;
		} else {
			$("#parkNamespan").html("长度必须大于2个字符");
			$("#parkNamespan").css("color", '#B52726');
			return false;
		}
	}

	//所有验证通过调用添加方法
	function cheakAll() {
	 $("#parkLocationspan").html("");
	 $("#parkNamespan").html("");
	 $("#feeScalespan").html("");
		if (!(parkName())) {
			return false;
		}
		if ($("#parkLocation").val().trim().length == 0) {
			$("#parkLocationspan").html("地址不能为空");
			$("#parkLocationspan").css("color", '#B52726');
			return false;
		} 
		if ($("#feeScale").val().trim().length == 0) {
			$("#feeScalespan").html("租金不能为空");
			$("#feeScalespan").css("color", '#B52726');
			return false;
		} 
		return true;
	} 

</script>

</head>
<body>
	<div class="container">
		<div>
			<ul class="breadcrumb">

				<li><i class="icon-list"></i> <a href="index.jsp">返回主页</a>

					<i class="icon-angle-right"></i></li>

				<li><a href="#">车库信息界面</a></li>

			</ul>
		</div>
		<h4>添加车库信息</h4>
		<form action="addParkInfo.action" method="post" onsubmit="return cheakAll();">
		<div style="border: solid 1px; border-color: #DDDDDD">
			<table class="tableSet">
				<tr>
					<td style="text-align: right;">车库名：</td>
					<td><input type="text" name="parkInfoSet.parkName" id="parkName" value=""/> <span style="color: red;">*</span>
						<span id="parkNamespan"></span></td>
					<td style="text-align: right;">地址：</td>
					<td><input type="text" name="parkInfoSet.parkLocation"
						id="parkLocation"/> <span
						style="color: red;">*</span> <span id="parkLocationspan"></span>
					</td>

				</tr>
				<tr>
					<td style="text-align: right;">车位数目：</td>
					<td><input type="text" id="parkNumber"
						name="parkInfoSet.parkNumber" /> <span style="color: red;">*</span>
						<span id="parkNumberspan"></span></td>
					<td style="text-align: right;">租金：</td>
					<td><input type="text" id="feeScale"
						name="parkInfoSet.feeScale" /> <span style="color: red;">*</span>
						<span id="feeScalespan"></span></td>
				</tr>
				
				<tr>
					<td style="text-align: right;">开放时间：</td>
					<td>
					<div class="form-group">
						<input type="text" name="parkInfoSet.openDate" class="form-control"  id="openDate" placeholder="格式:06:30">
					</div>
					</td>
					<td style="text-align: right;">关闭时间：</td>
					<td>
						<div class="form-group">
						<input type="text" name="parkInfoSet.closeDate" class="form-control" id="closeDate" placeholder="格式:22:30">
					</div>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">消费类型：</td>
					<td><select name="parkInfoSet.rentOrSale" id="rentOrSale"
						required>
							<option value="0">租</option>
							<option value="1">售</option>
					</select> <span style="color: red;">*</span></td>
					<td>
						<div class="text-center span3">
						<input type="submit" value="添加" style="width: 100px" class="btn btn-info">
						</div>
					</td>
				</tr>
			</table>
		</div>
		</form>
		<br>
		<h4>信息列表</h4>
		<table class="table table-bordered" width="100%" cellspacing="0"
			cellpadding="0" style="margin-top: 8;">
			<tr>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					车库名称</th>
				<th
					style="text-align: center; vertical-align: middle; width: 300px;">
					车库停车位数</th>
				<th
					style="text-align: center; vertical-align: middle; width: 100px;">
					租金</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					消费类型</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					地址</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					开放时间</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					关闭时间</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					操作</th>
			</tr>
			<s:iterator var="park" value="listPark" status="status">
			<tr>
				<td style="text-align: center; vertical-align: middle;display: none;" id="parkId">
					<s:property value="#park.id" />
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#park.parkName" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:property value="#park.parkNumber" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:property value="#park.feeScale" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:if test="#park.rentOrSale == 0">
						租
					</s:if>
					<s:else>
						售
					</s:else>
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#park.parkLocation" />
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#park.openDate" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:property value="#park.closeDate" />
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<%-- <button class="btn btn-danger"
						onclick="location.href='<%=request.getContextPath()%>/deleteInfo.action?id=<s:property value="#send.id"/>'">删除</button> --%>
					
					<button class="btn btn-danger" onclick="location.href='<%=request.getContextPath()%>/deleteParkInfo.action?id=<s:property value="#park.id"/>'">删除</button>
					<button class="btn btn-info" type="button" data-backdrop="static"
						data-toggle="modal" data-target="#dlg"
						onclick="modifyInfo(${park.id})">修改</button>
				</td>
			</tr>
			</s:iterator>
		</table>
	</div>
	<%--<div class="container" style="margin-top: 70px">
		<div class="text-center span6">
			<a style="width: 80px" type="submit" id="add" onclick="cheakAll()"
				class="btn btn-info">添加</a>
		</div>
	</div>
--%></body>
</html>