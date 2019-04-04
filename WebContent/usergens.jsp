<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户随机生成</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	 
</head>
<body>
  <div id="aa" class="easyui-layout" style="width:98%;;height:100%;">   
   <br/> <br/><br/>
     <div id="genusersss" style="font:18px;"></div> <br/> <br/><br/>
    <button onclick="genuser()">生成用户</button>
</div> 

    
    
     
    </script>
    <script type="text/javascript">
        function genuser(){
        	$.post("myParamter/gen.do",function(data){
        		$("#genusersss").html(data.result);
        	},"json");
        }
   
    
    </script>
  
</body>
</html>