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
	function chargeInfo() {
		var carNo = $("#carNo").val().trim();
		if (!carNo || carNo.length == 0) {
			alert("请填写车牌号");
			return false;
		}
		$.ajax({
			url : "addChargeInfo.action",
			type : "post",
			dataType : "json",
			data : "carNo=" + carNo,
			success : function(result) {
				
				//$("#userName").val(result.userName);
				$("#parkName").val(result.parkName);
				$("#locationNo").val(result.locationNo);
				$("#carNo").val(result.carNo);
				$("#type").val(result.type);
				$("#hour").val(result.hour);
				$("#charge").val(result.charge);
				if("nocar"==result.isOk){
					alert("该车不在当前车库！");
				}else{
					alert("结算成功,请刷新！");
				}
				
			}
		});
	}
</script>

</head>
<body>
	<div class="container">
		<div>
			<ul class="breadcrumb">

				<li><i class="icon-list"></i> <a href="index.jsp">返回主页</a>

					<i class="icon-angle-right">></i></li>

				<li><a href="#">收费信息管理界面</a></li>

			</ul>
		</div>
		<h4>添加收费信息</h4>
		<form action="" method="post">
			<div style="border: solid 1px; border-color: #DDDDDD">
				<table class="tableSet">
					<tr>
						<%-- <td style="text-align: right;">用户名：</td>
						<td><input type="text" name="userName" class="form-control"
							id="userName" placeholder="请输入用户名" /> <span
							style="color: red;">*</span> <label id="parkNamespan"></label></td> --%>
						<td style="text-align: right;">车牌号：</td>
						<td><input type="text" name="carNo"
							id="carNo" /></td>
						<td style="text-align: right;">车库名：</td>
						<td><input type="text" name="parkName"
							readonly="true" id="parkName" /> <span style="color: red;">*</span>
							<label id="parkNamespan"></label></td>
					</tr>

					<tr>
						<td style="text-align: right;">车位号：</td>
						<td><input type="text" id="locationNo"
							name="locationNo" readonly="true"/></td>
						<td style="text-align: right;">消费类型：</td>
						<td><select name="type" id="type"
							readonly="true">
								<option value="0">租</option>
								<option value="1">售</option>
						</select> <span style="color: red;">*</span></td>
						
					</tr>
					<tr>
						<td style="text-align: right;">使用时间：</td>
						<td><input type="text" name="hour"
							id="hour" readonly="true" /> <span style="color: red;">*</span>
							<label id="hourspan"></label></td>
						<td style="text-align: right;">费用：</td>
						<td><input type="text" id="charge"
							name="charge" readonly="true"/></td>
					</tr>
					<tr>
						<td></td>
						<td></td>
						<td>
							<div class="text-center span3">
								<a style="width: 80px" type="submit" id="add"
									onclick="chargeInfo()" class="btn btn-info">结算</a>
							</div>
						</td>
						<td></td>
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
					用户名</th>
				<th
					style="text-align: center; vertical-align: middle; width: 300px;">
					车库名</th>
				<th
					style="text-align: center; vertical-align: middle; width: 100px;">
					车牌号</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					车位号</th>
				<th
					style="text-align: center; vertical-align: middle; width: 500px;">
					消费类型</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					使用时间</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					费用</th>
				<th
					style="text-align: center; vertical-align: middle; width: 150px;">
					操作</th>
			</tr>
			<s:iterator var="chargeInfo" value="list" status="status">
			<tr>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#chargeInfo.userName" />
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#chargeInfo.parkName" />
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<s:property value="#chargeInfo.carNo" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:property value="#chargeInfo.locationNo" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:if test="#chargeInfo.type == 0">
						租
					</s:if>
					<s:else>
						售
					</s:else>
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:property value="#chargeInfo.hour" />
				</td>
				<td
					style="text-align: center; vertical-align: middle; width: 200px;">
					<s:property value="#chargeInfo.charge" />
				</td>
				<td style="text-align: center; vertical-align: middle;">
					<button class="btn btn-danger" onclick="location.href='deleteChargeInfo.action?id=<s:property value="#chargeInfo.id" />'">删除</button>
				</td>
			</tr>
			</s:iterator>
		</table>
	</div>
	<div class="container" style="margin-top: 70px">
		<div class="text-center span12">
			<a style="width: 80px" type="submit" onclick="location.href='<%=request.getContextPath()%>/queryAllChargeInfo.action'"
				class="btn btn-info">刷新</a>
		</div>
	
	</div>


</body>
</html>