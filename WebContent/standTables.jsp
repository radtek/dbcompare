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
    <title>基础表管理</title>
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
</head>
<body> 
  <div id="aa" class="easyui-layout" style="width:98%;height:100%;">   
  
    	<div data-options="region:'north',title:'功能区',split:true" style="height:100px;"> 
    	   <div style="padding-left: 20px;padding-bottom: 5px;">
		    <form id="fmstandTablessearch" >
	                
					<label>表名:</label>
	             	 <input  name="tableName"   type= "text" class= "easyui-box" > </input>
	                
	               <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="standTables.fsearch()">条件查询</a> 
	                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#fmstandTablessearch').form('reset')">清除</a>  
	        </form>
	   	</div>  
	   	
	      <div id="toolbar1">
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="standTables.viewTable()">表结构</a>
	         
     		 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="standTables.createDDLTable()">创建表</a>
      
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="standTables.clearData()">清空缓存数据</a>
	          <tag:permissiontag>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="standTables.viewData()">表数据</a>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="standTables.exportData()">sqluldr全量数据</a>
	         
	         </tag:permissiontag>
	    	</div>
   	 </div>
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    		  <table id="tablesdg"  class="easyui-datagrid" style="height: 98%;"
            url="dbs/findAll.do"
             pagination="true" pageSize="200"  pageList="[10,50,100,150,200]"
            rownumbers="true" fitColumns="true"  
            data-options="fit:false,border:false,singleSelect:false,remoteSort:false," >
        <thead>
            <tr>
            	<th field="id" width="50"  data-options="checkbox:true">表名</th>
                <th field="name" width="50">表名</th>
                <th field="code" width="50" sortable="true">表名code</th>
                <th field="dataType" width="50">数据类型</th>
                
            </tr>
        </thead>
    </table>
    
    
    </div>   
</div> 

    <div id="dd_tables"> </div>
    
    
     
    </script>
    <script type="text/javascript">
    
    var standTables = {};
    //表结构
     standTables.viewTable=function(){
            var row = $('#tablesdg').datagrid('getSelected');
            if (row){
            	$('#dd_tables').dialog({
            	    title: row.code,
            	    width: 900,
            	    height: 700,
            	    closed: false,
            	    cache: false,
            	    href:  'standTable.jsp?table='+row.code,
            	    modal: true
            	});
            }else{
            	$.messager.alert('警告','请选择具体某一行...'); 
            	 
            }
        };
       
         
        //表数据
         standTables.viewData=function(){
            var row = $('#tablesdg').datagrid('getSelected');
            if (row){
            	$('#dd_tables').dialog({
            	    title: row.code,
            	    width: 850,
            	    height: 700,
            	    closed: false,
            	    cache: false,
            	    href:  'standData.jsp?table='+row.code,
            	    modal: true
            	});
            }else{
            	$.messager.alert('警告','请选择具体某一行...'); 
            }
        };

        //清空缓存数据
          standTables.clearData=function(){
        	$.post("dbs/clear.do",function(data){
        		if(data.flag){
        			$.messager.alert('警告',data.result); 
        			 
        		}
        	},"json");
        };
       
        
        //刷新
        standTables.fsearch=function(){
     		 $('#tablesdg').datagrid('loading');
            $('#fmstandTablessearch').form('submit',{
                url: "dbs/findAll.do",
                onSubmit: function(){
                	var flag = $(this).form('validate');
                    return flag;
                },
                success: function(data){
                    var dd = eval('('+data+')');
                     //console.log(dd);
                     	 $('#tablesdg').datagrid('loaded');
                      $('#tablesdg').datagrid('loadData',dd.rows);    // reload the user data
                 }

            });
        };
        
      //创建表
     standTables.compareTables ="";
     standTables.createDDLTable=function(){
       	$.messager.confirm('确认','是否创建表',function(r){    
       	if (r){    
       	     var row = $('#tablesdg').datagrid('getSelected');
       	  if (row){
	       		 var rows = $('#tablesdg').datagrid('getSelections');
	       		 if(rows.length>20){
	       			 $.messager.alert('提示',"一次创建表不能超过20个!!!");
	       			 return;
	       		 }
	       		
	       		 $('#tablesdg').datagrid('loading');
	       		
	       		for(var i=0,len=rows.length;i<len;i++){
	       			standTables.compareTables +=rows[i].code+"|0,";
	       		}
	       		
	       		$.post("dbs/generateTempDDLScript.do",{
		   			 "url":"${burl}",
		   			 "name":"${busername}",
		   			 "pwd":"${bpassword}",
		   			 "schame":"zhifu",
		   			 "cstartTime":"2017-01-01",
		   			 "tables":standTables.compareTables
	   		 },function(data){
	   			 $('#tablesdg').datagrid('loaded');
	   			 //表结构差异
				 standTables.compareTables = "";
				 $.messager.alert('提示',data.result);
					
	   		 },"json");
       	  }else{
       		$.messager.alert('警告','请选择具体某一行...'); 
       	  }
    	        	 
       	    }    
       	});
       };
       
       
     //导出sqluldr全量数据
      standTables.exportData=function(){
      	$.messager.confirm('确认','导出sqluldr全量数据',function(t){    
      	    if (t){
      	    		window.location="basic/sqluldr2.do";
      	    	}
      		}
      	);
      };
    </script>
  
</body>
</html>