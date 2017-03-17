<%@ page contentType="text/html; charset=utf-8" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<title>操作成功!</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">
	onload=function(){
    	var para=${para};
    	if((para=="1")){
    		alert("操作成功，请登录");
    		window.location.href="<%=request.getContextPath()%>/login.jsp";
    	}else if((para=="0")){
    		alert("操作失败");
    		window.location.href="<%=request.getContextPath()%>/queryUserByName.action";
    	}else if((para=="5")){
    		alert("删除成功");
    		window.location.href="<%=request.getContextPath()%>/queryUserByName.action";
    	}else if((para=="6")){
    		alert("删除失败");
    		window.location.href="<%=request.getContextPath()%>/queryUserByName.action";
    	}
     }
    </script>
</head>

<body>
		
</body>
</html>