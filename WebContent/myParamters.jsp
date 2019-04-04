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
    <title>参数记录</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	 
</head>
<body>
  <div id="aabb" class="easyui-layout" style="width:98%;;height:100%;;">   
    
    	<div data-options="region:'north',title:'功能区',split:true" style="height:80px;padding-top: 10px;">   
	      <div   style="padding-left: 20px;">
	        <tag:permissiontag>
	          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myParamter.updateOpen()">编辑</a>
	    	 </tag:permissiontag>
	    	</div>
   	 </div>
    	 
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    <table id="myParamterDowndg"  class="easyui-datagrid" style="height: 98%;"
            url="myParamter/findAll.do"
             pagination="true" pageSize="200"  pageList="[10,50,100,150,200]"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,singleSelect:true,remoteSort:false," >
        <thead>
            <tr>
            	<th field="id" width="50"  data-options="checkbox:true"></th>
                <th field="pname" width="50"  sortable="true">参数名</th>
                <th field="pval" width="50" sortable="true">参数值</th>
                <th field="remark" width="100" >备注说明</th>
               
            </tr>
        </thead>
    </table>
    
    
    </div>   
</div> 


 
     <div id="dlgmyParamter" class="easyui-dialog"  style="padding:30px 20px; width:300px;height:300px;"    
            closed="true" buttons="#dlg-myParamterbuttons" modal="true" >
        <div class="ftitle"></div>
        <form id="fmmyParamter" method="post"  novalidate>
             <div class="fitem">
                <label>参数名:</label>
               <input  name="pname"   type= "text" class= "easyui-textbox" required ="required" readonly="readonly"> </input><br/><br/>  <br/> 
            </div> 
              <div class="fitem">
                <label>参数值:</label>
               <input  name="pval"  type= "text" class= "easyui-textbox" required ="required"> </input>  <br/> <br/> <br/>
            </div> 
             
            	
            <div class="fitem">
                <label>备注说明:</label>
                <input type="text" name="remark"    class="easyui-textbox"  required="true"     /><br/> <br/><br/>
            </div> 
            
        </form>
    </div>
    <div id="dlg-myParamterbuttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="myParamter.update()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgmyParamter').dialog('close')" style="width:90px">取消</a>
    </div>
    
    

    </script>
    <script type="text/javascript">
    var myParamter = {};
    //打开
    myParamter.updateOpen=function(){
    	 var rows = $('#myParamterDowndg').datagrid('getSelected');
    	    if (rows){ 
    	 	   $('#dlgmyParamter').dialog('open').dialog('setTitle','更新');
    	        $('#fmmyParamter').form('reset');
    	  		$('#fmmyParamter').form("load",rows);
    	  	 
    	    }else{
    	   	 $.messager.alert('警告','请选择需要更新的记录'); 
    	    }//
    };
   
    //保存
    myParamter.update=function(){
           $('#fmmyParamter').form('submit',{
               url: 'myParamter/update.do',
               onSubmit: function(){
                   return $(this).form('validate');
               },
               success: function(data){
                   var dd = eval('('+data+')');
                  
                   if (!dd.flag){
                      
                       $.messager.alert('error',dd.result); 
                   } else {
                   	
                       $('#dlgmyParamter').dialog('close');        // close the dialog
                       $.messager.show({
                           title: 'OK',
                           msg: dd.result,
                           width:'300px',
                           timeout:5000,
                           height:'250px'
                       });
                       $('#myParamterDowndg').datagrid('reload');    // reload the user data
                   }
               }
           });
       };
    </script>
  
</body>
</html>