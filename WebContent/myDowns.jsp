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
    <title>增量下载记录</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	 
</head>
<body>
  <div id="aa" class="easyui-layout" style="width:98%;;height:100%;;">   
    <div data-options="region:'north',title:'功能区',split:true" style="height:100px;padding-top: 10px;"> 
         <div style="padding-left: 20px;padding-bottom: 5px;">
	    <form id="fmMyDownsearch" >
                
				<label>用户空间:</label>
             	 <input  name="sechma"   type= "text" class= "easyui-box" > </input>
                
               <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myDowns.fsearch()">条件查询</a> 
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#fmMyDownsearch').form('reset')">清除</a>  
        </form>
	   </div>
        <div id="toolbar1" style="padding-left: 20px;">
	        <tag:permissiontag>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myDowns.update()">变更状态</a> 
	           </tag:permissiontag>
	         
	    	</div>
    </div>
    	 
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    <table id="myDowndg"  class="easyui-datagrid" style="height: 98%;"
            url="myDown/findAll.do"
             pagination="true" pageSize="500"  pageList="[100,200,300,400,500]"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,singleSelect:true,remoteSort:false," >
        <thead>
            <tr>
            	<th field="id" width="50"  data-options="checkbox:true">表名</th>
                <th field="sechma" width="50"  sortable="true">用户空间</th>
                <th field="way" width="50" sortable="true">下载方式</th>
                <th field="realRange" width="60">下载范围/创建时间</th>
                 <th field="range" width="60">选择创建/上传时间</th>
                <th field="remark" width="80" >备注</th>
                   <th field="createTime" width="50" formatter="myDowns.fcreateTime" sortable="true">脚本创建时间</th>
                 <th field="url" width="50" formatter="myDowns.furl">下载</th>
                   <th field="content" width="50" formatter="myDowns.fcontent">包含的脚本</th>
            </tr>
        </thead>
    </table>
    
    
    </div>   
</div> 

     <div id="dlgdown" class="easyui-dialog"  style="padding:10px 20px; width:500px;height:500px;"    
            closed="true" buttons="#dlg-buttons" modal="true" >
            
             <table id="detailDowndg"  class="easyui-datagrid" style="height: 98%;"
           
             pagination="false" pageSize="50"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,singleSelect:true,remoteSort:false," >
        <thead>
            <tr>
            	<th field="id" width="50"  data-options="checkbox:true">表名</th>
                <th field="name" width="50"  sortable="true">下载的脚本名称</th>
                 
            </tr>
        </thead>
    </table>
     </div>
    <div id="dlg-buttons">
        
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgdown').dialog('close')" style="width:90px">取消</a>
    </div>
     
    </script>
    <script type="text/javascript">
    var myDowns = {};
    //下载
    myDowns.furl=function(value,row,index){
    	var html ="<a href='/pic/"+value+"' target='_blank'>下载</a>";
    	if(row.physicsFlag==2){
	 		html += " <font color='red'>作废</font>";
	 	}
		return html;
	 };
    
  //查看
   myDowns.fcontent=function(value,row,index){
	    var str = "<a href='javascript:myDowns.view(\"reg\")' >查看</a>";
	    str = str.replace("reg", value);
		return str;
	 };
    
    //格式时间
    myDowns.fcreateTime=function(value,row,index){
   		return new Date(value).toLocaleString();
   };
   
   //刷新
   myDowns.fsearch=function(){
		 $('#myDowndg').datagrid('loading');
       $('#fmMyDownsearch').form('submit',{
           url: "myDown/findAll.do",
           onSubmit: function(){
           	var flag = $(this).form('validate');
               return flag;
           },
           success: function(data){
               var dd = eval('('+data+')');
                //console.log(dd);
                	 $('#myDowndg').datagrid('loaded');
                 $('#myDowndg').datagrid('loadData',dd.rows);    // reload the user data
            }

       });
   };
   
   //查看
   myDowns.view=function(value ){
   	 
   	  $('#dlgdown').dialog('open').dialog('setTitle','包含脚本文档');
   	  var arr = value.split(",");
   	  var dd = [];
   	  for(var i=0;i<arr.length;i++){
   		 //dd.push({"name":arr[i]});
   		 var name = arr[i];
   		 if($.trim(name)){
   			dd.push({"name":name});
   		 }
   		  
   	  }
   	 $('#detailDowndg').datagrid('loadData',dd); 
   };
   
   //按id号变更状态
    myDowns.update=function(){
   	 
   	$.messager.confirm('确认','是否变更该状态？',function(t){    
   	    if (t){    
   	    	 var rows = $('#myDowndg').datagrid('getSelections');
                if (rows.length){ 
                	 var size = rows.length;
                	 var ids = "";
                	 if(size==1){
                		ids += rows[0].id;
                		$.post("myDown/disable.do",{"id":ids},function(dd){
                			 
                			$('#myDowndg').datagrid('reload');    // reload the user data
                		},"json");
                	 }else{
                		 $.messager.alert('警告','一次只能变更一个脚本'); 
                	 }
                	 
                }else{
               	 $.messager.alert('警告','请选择变更的脚本'); 
                	 
                }//
   	    }    
   	});

   };
    </script>
  
</body>
</html>