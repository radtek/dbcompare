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
 <body  >   
<div id="cc" class="easyui-layout" style=" width:600px;height:400px;">      
    <div data-options="region:'north',title:'North Title',split:true" style="height:100px;"></div>   
    <div data-options="region:'south',title:'South Title',split:true" style="height:100px;"></div>   
    <div data-options="region:'east',iconCls:'icon-reload',title:'East',split:true" style="width:100px;"></div>   
    <div data-options="region:'west',title:'参数归属',split:true" style="width:150px;">
    <ul id="tt" class="easyui-tree">   
   <li>   
                        <span>目录配置</span>   
                    </li>   
                    <li>   
                        <span>清分配置</span>   
                    </li> 
                     <li>   
                        <span>南海行动</span>   
                    </li>   
      
</ul>  
    </div>   
    <div data-options="region:'center',title:'参数配置'" style="padding:5px;background:#eee;">
    <table class="easyui-datagrid">   
    <thead>   
        <tr>   
            <th data-options="field:'code'">参数名</th>   
            <th data-options="field:'name'">参数值</th>   
            <th data-options="field:'price'">修改时间格</th>   
        </tr>   
    </thead>   
    <tbody>   
        <tr>   
            <td>acc对账目录</td><td>xx</td><td>2018-10-12 12：10：10</td>   
        </tr>   
        <tr>   
            <td>共享目录</td><td>xx</td><td>2018-10-12 12：10：10</td>   
        </tr>   
    </tbody>   
</table>  
    </div>   
</div>  
</body>  
</html>