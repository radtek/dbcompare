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
    <title>银行conftpl模板比较</title>
	
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
  <div  id="myBankConftplComparescontenttpllayout"   class="easyui-layout" style="width:98%;;height:100%;">   
     
     
            	 
    <div data-options="region:'north'" style="padding:5px;background:#eee;height:100px;padding-top: 15px;"  >
        <form id="myBankConftplComparesWinFileNameUpdatfile" method="post" enctype="multipart/form-data" novalidate>
         
          
            <div class="fitem">
                <label>请上传配置:</label>
                <input type="file" name="uploadFile"     required="true" value=""  onchange="$('myBankConftplComparesWinFileName').val(this.value)" /> 		
            	
            	 
                <label>配置类型:</label>
                 <input id="myBankConftplComparesType" class="easyui-combobox" required="true" name="myBankConftplComparesType"   data-options="valueField:'id',textField:'text',editable:true" style="width: 200px;"/>  
    		 
            <br/> <br/> 
            <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="myBankConftplCompares.save()" style="width:200px" >正向比较(以模板文件为准)</a>
            &nbsp;  &nbsp; &nbsp; &nbsp;&nbsp;  &nbsp; &nbsp; &nbsp;<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="myBankConftplCompares.save('isRerverse')" style="width:200px" >反向比较(以上传文件为准)</a>
            </div> 
              
             
        </form>
    </div>   
    
      <div data-options="region:'center',title:'列表区(请上传配置文件获得差异内容)'" style="padding:5px;background:#eee;">
      <textarea id="myBankConftplComparescontenttpl" class="easyui-textbox" data-options="multiline:true" style="width:100%; height:100%;">请上传配置文件获取获得差异内容</textarea>
    </div>  
</div> 

 
 
    <script type="text/javascript">
    
    var myBankConftplCompares = {};
    //保存
    myBankConftplCompares.save=function(isRerverse){
    	$('#myBankConftplComparescontenttpl').textbox('setValue',"") ;
    	  $('#myBankConftplComparescontenttpllayout').layout('panel', 'center').panel({ title: '列表区(请上传配置文件获得差异内容)' });
     	 
           $('#myBankConftplComparesWinFileNameUpdatfile').form('submit',{
               url: 'myBankConf/comparesContentTpl.do?isRerverse='+isRerverse,
               onSubmit: function(){
                   return $(this).form('validate');
               },
               success: function(data){
                   var dd = eval('('+data+')');
                    //console.log(dd);
                   if (!dd.flag){
                       $.messager.alert('error',dd.result); 
                   } else {
                	   if (dd.isRerverse){
                		   $.messager.alert('info','反向比较内容仅做参考'); 
                		   $('#myBankConftplComparescontenttpllayout').layout('panel', 'center').panel({ title: '列表区(请上传配置文件获得差异内容)--反向比较内容仅做参考' });
                	   }
                	  
                	   $('#myBankConftplComparescontenttpl').textbox('setValue',dd.result) ;  
                   }
               }
           });
       };
       
    
       
       $(function(){
      	 //模板类型
      	 $.post("myBankConf/comparesContentTplType.do",function(data){
        		 if(data.flag){
        			 var rowarr = data.result;
        			 
        			 var arrdata = [];
        			 for(var i=0,len=rowarr.length;i<len;i++){
        				 var row = rowarr[i];  
        				arrdata.push({"id":row,"text":row});
        			 }
        			 
        			$('#myBankConftplComparesType').combobox('loadData', arrdata);
        		

        		 }
        	 },"json");
      	
       
      	 
       });
    </script>
  
</body>
</html>