<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/mytag" prefix="tag" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>数据库加解密</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	 <style type="text/css">
	 
	 </style>
</head>
<body>
  <div  id="codedblayout"   class="easyui-layout" style="width:98%;;height:100%;">   
     
     
            	 
    <div data-options="region:'north'" style="padding:5px;background:#eee;height:300px;padding-top: 15px;"  >
        <textarea id="codedbinput" class="easyui-textbox" data-options="multiline:true,prompt:'请输入(多个以,分割)'" style="width:100%; height:200px;"></textarea>
             
            <br/> <br/> 
            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codedb.code('en')" style="width:200px" >加密</a>
            &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp;  &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codedb.code('de')" style="width:200px" >解密</a>
         
    </div>   
    
      <div data-options="region:'center',title:'结果以key::val展示'" style="padding:5px;background:#eee;">
      <textarea id="codedbtpl" class="easyui-textbox" data-options="multiline:true" style="width:100%; height:200px;"></textarea>
    </div>  
</div> 

 
 
    <script type="text/javascript">
    
    var codedb = {};
    //加解密
    codedb.code=function(code){
    	var inputstr =  $('#codedbinput').textbox('getValue') ;
    	$.post("code/codedb.do",{"code":code,"inputstr":inputstr},function(json){
    		var result = json.result;
    		 $('#codedbtpl').textbox('setValue',result) ;  
    		 
    	},"json");
    		
    	 
    };
       
     
    </script>
  
</body>
</html>