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
    <title>标准库下载</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	 <script type="text/javascript" src="jquery-easyui-1.4.1/TableShow.js"></script>
	 
</head>
<body>
  <div id="aa" class="easyui-layout" style="width:98%;;height:100%;;">   
  
  <div data-options="region:'north',title:'功能区',split:true" style="height:60px;">   
	      <div id="toolbar1">
	        <tag:permissiontag> <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="basicTables.add()">新增脚本</a> 
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="basicTables.edit()">编辑脚本</a> 
	          </tag:permissiontag>
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="basicTables.compress()">标准脚本下载(gb2312类型)</a>
	    	</div>
   	 </div>
   	 
    	 
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    		  <table id="basicdg"  class="easyui-datagrid" style="height: 98%;"
            url="basic/findAll.do"
             pagination="true" pageSize="200"  pageList="[10,50,100,150,200]"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,singleSelect:true,remoteSort:false," >
        <thead>
            <tr>
            	<th field="id" width="50"  data-options="checkbox:true">表名</th>
            	 <th field="realName" width="50" sortable="true" sortable="true">文件名</th>
                <th field="fileType" width="50" sortable="true">类型</th>
                <th field="createTime" width="50" formatter=" basicTables.fcreateTime" sortable="true">创建时间</th>
                <th field="orders" width="50" >脚本顺序</th>
                <th field="serviceUrl" width="50" formatter=" basicTables.fserviceUrl">下载</th>
                
            </tr>
        </thead>
    </table>
    
    
    </div>  
    
     <div id="dlgBasicfile" class="easyui-dialog"  style="padding:10px 20px; width:300px;height:300px;"    
            closed="true" buttons="#dlg-buttons" modal="true" >
        <div class="ftitle"></div>
        <form id="fmBasicfile" method="post" enctype="multipart/form-data" novalidate>
        <input type="hidden" name="realName" id="realName" class="easyui-validatebox" required="true"  />
          
           
            
            <div class="fitem">
                <label>脚本名称:</label>
                <input type="file" name="uploadFile" id="uploadFile"   required="true" value=""  onchange="$('#realName').val(this.value)" />
            </div> 
              
              
            <div class="fitem">
                <label>脚本类型:</label>
                <input type="radio" name="fileType"      value="DDL"  checked="true" />DDL
                  <input type="radio" name="fileType"    value="DML"   />DML
            </div> 
              <div class="fitem">
                <label>脚本顺序:</label>
               <input name="orders"    class="easyui-numberspinner" style="width:80px;" required="required" data-options="min:1,max:100,editable:true"/>   
          
            </div> 
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="basicTables.save()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgBasicfile').dialog('close')" style="width:90px">取消</a>
    </div>
     
</div> 

       
     <div id="detailBDg" class="easyui-dialog"  style=" width:85%;height:85%;"    
            closed="true" buttons="#dlg-detailBDg" modal="true" >
           <div id="fbdetail"></div>
    </div>
    <div id="dlg-detailBDg">
       
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailBDg').dialog('close')" style="width:90px">取消</a>
    </div>
     
    </script>
    <script type="text/javascript">
    
    var basicTables = {};
    

    //下载
     basicTables.fserviceUrl = function(value,row,index){
    	var html =  "<a href='/pic/"+row.serviceUrl+"' target='_blank'>下载</a>&nbsp;&nbsp;&nbsp;&nbsp;";  
	 	html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='basicTables.viewDetail("+row.id+",\"realName\")'>查看</a>&nbsp;&nbsp;"
	 	html = html.replace("realName", row.realName);
	 	if(row.physicsFlag==2){
	 		html += " <font color='red'>作废</font>";
	 	}
		return html;
		 
	 };
    
    //格式时间
    basicTables.fcreateTime=function(value,row,index){
   		return new Date(value).toLocaleString();
   };
    
	
   //弹窗
      basicTables.add=function(){
    	  
    	  $('#dlgBasicfile').dialog('open').dialog('setTitle','新增脚本');
          $('#fmBasicfile').form('reset');
          
    };
   
  
    //保存脚本
    basicTables.save = function(){
        $('#fmBasicfile').form('submit',{
            url: "basic/add.do",
            onSubmit: function(){
                return $(this).form('validate');
            },
            success: function(data){
                var dd = eval('('+data+')');
                 //console.log(dd);
                if (!dd.flag){
                    $.messager.show({
                        title: 'Error',
                        msg: dd.result,
                        width:'300px',
                        timeout:10000,
                        height:'250px'
                    });
                } else {
                	 
                	 
                    $('#dlgBasicfile').dialog('close');// close the dialog
                    $.messager.show({
                        title: 'OK',
                        msg: "上传成功",
                        width:'300px',
                        timeout:5000,
                        height:'250px'
                    });
                    $('#basicdg').datagrid('reload');    // reload the user data
                }
            }
        });
    };

    //编辑脚本
      basicTables.edit=function(){
    	 var rows = $('#basicdg').datagrid('getSelections');
         if (rows.length){ 
         	 var size = rows.length;
         	 var ids = "";
         	 if(size==1){
         		ids = rows[0].id;
         		$.messager.prompt('提示信息', '请输入正确的文件名:', function(r){
             		if (r){
             			$.post("basic/update.do",{"id":ids,"realName":r},function(dd){
                			 
                 			$('#basicdg').datagrid('reload');    // reload the user data
                 		},"json");
             			 
             		}
             	});
         	 }
         	 
         }else{
        	 $.messager.alert('警告','请选择变更的脚本'); 
         	 
         }//
          
    };
    
    //下载脚本
     basicTables.compress=function(){
    	$.messager.confirm('确认','标准版实时最新,【<font color="red">新银行注意修改部分脚本</font>】',function(t){    
    	    if (t){
    	    		window.location="basic/makeFile.do";
    	    	}
    		}
    	);
    };
    
    
    
    //查看脚本详细
     basicTables.viewDetail=function(id,realName){
    	$.post("basic/findById.do",{"id":id},function(dd){
    		 if(dd.flag){
    			 //console.log(dd);
    			 $('#detailBDg').dialog('open').dialog('setTitle','脚本详细:'+realName);
    			 var content = dd.content;
    			 content =  Table.show1(content);
    			 
    			 $("#fbdetail").html(content);
    		 }
    				 
 		},"json");
    };
    </script>
  
</body>
</html>