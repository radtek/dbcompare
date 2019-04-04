<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>用户信息</title>
	
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
  
   	  <form id="fmlogin" method="post"  novalidate>
              <table>
              	<tr>
              	   <td><label>用户名:</label></td>
              	   <td> <input  name="userName"   type= "text" class= "easyui-box" required="true" value="${user.userName }"> </input></td>
              	
              	</tr>
              	
              	<tr>
              	   <td><label>用户密码:</label></td>
              	   <td> <input  name="pwd"   type= "text" class= "easyui-box" required="true"  value="${user.pwd }"> </input></td>
              	
              	</tr>
              	
              	<tr>
              	  
 
              	</tr>
              </table>
				 
             
                
        </form>
    
     
</div> 

    
    
     
    </script>
    <script type="text/javascript">
 
   
    
    </script>
  
</body>
</html>