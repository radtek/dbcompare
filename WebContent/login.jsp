<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>登录界面</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
  <!--   <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css"> -->
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	 
</head>
<body   id="logincc" class="easyui-layout"  >
   
    <div data-options="region:'west',title:'扫我登录',split:true" style="width:50%;display: table-cell;vertical-align: middle; text-align: center;">
         
           <img alt="扫我" src="images/erweima.jpg" width="600px" height="600px"  />
    </div>   
    <div data-options="region:'center',title:'登录'" style="padding:5px;background:#eee;">
         <br/> <br/> <br/> <br/> <br/>
         <form id="fmlogin" method="post"  novalidate >
           
        <div style="margin-bottom:10px">  
            <input class="easyui-textbox" id="userName"  name="userName" style="width:100%;height:30px;padding:12px" data-options="prompt:'登录名',iconCls:'icon-man',iconWidth:38" value="view" required="true">  
        </div>  
        <div style="margin-bottom:20px">  
            <input class="easyui-textbox" id="pwd"  name="pwd" type="password" style="width:100%;height:30px;padding:12px" data-options="prompt:'登录密码',iconCls:'icon-lock',iconWidth:38" value="view123" required="true">  
        </div>  
         
        <div>  
            <a href="javascript:;" onclick="flogin()" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" style="padding:5px 0px;width:100%;">  
                <span style="font-size:14px;">登录</span>  
            </a>  
        </div>  
           
           
  
    
    </form>
    
    </div>   
    
  
     
    </script>
    <script type="text/javascript">

    function flogin(){
    	 
        var userName =$('#userName').textbox('getValue');
        var pwd = $('#pwd').textbox('getValue');
      
        $('#fmlogin').form('submit',{
            url: 'user/login.do?userName='+userName+'&pwd='+pwd,
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(data){
                var dd = eval('('+data+')');
                 //console.log(dd);
                if (!dd.flag){
                    $.messager.alert("error",dd.result);
                } else {
                    window.location='main.jsp';
                }
            }
        });
    }
    
     
    </script>
  
</body>
</html>