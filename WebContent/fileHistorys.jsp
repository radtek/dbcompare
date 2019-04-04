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
    <title>基础表维护</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<!-- <script type="text/javascript" src="jquery-easyui-1.4.1/datagrid-filter.js"></script> -->
	  <script type="text/javascript" src="jquery-easyui-1.4.1/TableShow.js"></script>
</head>
<body>
  <div  class="easyui-layout" style="width:98%;height:100%;">   
  
    	<div data-options="region:'north',title:'功能区',split:true" style="height:120px;padding-top: 10px;">   
	    
	    	
	    <div style="padding-left: 20px;padding-bottom: 5px;">
	    <form id="fmFileSearch" >
               <label>脚本类型:</label>
                 <select class="easyui-combobox" name="fileType" style="width:50px;">   
				    <option value="">--</option>   
				   <option value="DDL">DDL</option>   
				   <option value="DML">DML</option>   
				</select>
				<label>文件名:</label>
             	 <input  name="realName"   type= "text" class= "easyui-box" > </input>
             	 <select class="easyui-combobox" name="lunece" style="width:100px;">   
				   <option value="" >--文件名检索--</option>   
				   <option value="lunece" selected="selected">--全文检索--</option>   
				</select>
               <label>起始时间:</label>
             	 <input  name="sbeginTime"   type= "text" class= "easyui-datebox" > </input> 
               <label>--结束时间:</label>
              	<input  name="sendTime"   type= "text" class= "easyui-datebox" > </input> 
              	时间类型:<select class="easyui-combobox" name="timeType" style="width:100px;">   
				   <option value="create" >创建时间</option>   
				   <option value="upload" selected="selected">上传时间</option>   
				</select>
               <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="fileHistorys.fsearch()">条件查询</a> 
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#fmFileSearch').form('reset')">清除</a>  
        </form>
	   </div>
	    	
	    <div id="toolbar1" style="padding-left: 20px;">
	        <tag:permissiontag>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="fileHistorys.add('DDL')">新增DDL</a> 
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="fileHistorys.add('DML')">新增DML</a> 
	          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="fileHistorys.updateWin()">脚本时间变更</a> 
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="fileHistorys.compress()">按id号下载脚本</a>
	          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="fileHistorys.createIndex()">重建lunece</a> 
	         </tag:permissiontag>
	          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="fileHistorys.down()">按时间跨度下载脚本</a>
	    	</div>
   	 </div>
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    		  <table id="dgfile"  class="easyui-datagrid" style="height: 98%;"
            url="file/findAll.do"
             pagination="true"   pageSize="100" pageList="[100,200,300,400,500]"
            rownumbers="true" fitColumns="true" singleSelect="false"
            data-options="fit:false,border:false,singleSelect:true,remoteSort:false," >
        <thead>
            <tr>
            	<th field="tt" width="50"  data-options="checkbox:true">id</th>
            	<th field="id" width="10" sortable="true" >id</th>
                <th field="fileType" width="50" sortable="true">类型</th>
                <th field="realName" width="100" sortable="true" sortable="true">文件名</th>
                <th field="createTime" width="50" formatter="fileHistorys.fcreateTime" sortable="true">创建时间</th>
                <th field="updateTime" width="50" formatter="fileHistorys.fcreateTime" sortable="true">上传时间</th>
                <th field="serviceUrl" width="50" formatter="fileHistorys.fserviceUrl">下载</th>
                
            </tr>
        </thead>
    </table>
    
    
    </div>   
</div> 

     
     <div id="dlgfile" class="easyui-dialog"  style="padding:10px 20px; width:500px;height:400px;"    
            closed="true" buttons="#dlg-buttons" modal="true" >
        <div class="ftitle"></div>
        <form id="fmfile" method="post" enctype="multipart/form-data" novalidate>
        <input type="hidden" name="realName" id="realName" class="easyui-validatebox" required="true"  />
          <div class="fitem">
                <label>脚本类型:</label>
                <input name="fileType" id="fileType" class="easyui-textbox" required="true" readonly="readonly" value="DDL" />
            </div>
           
            
            <div class="fitem">
                <label>脚本名称:</label>
                <input type="file" name="uploadFile" id="uploadFile"   required="true" value=""  onchange="$('#realName').val(this.value)" />
            </div> 
             <div class="fitem">
                <label>是否验证:</label>
                <input type="radio" name="isreg"      value="0"  checked="true" />是
                  <input type="radio" name="isreg"    value="1"   />否
                   	<span style="color: red;">&nbsp;&nbsp;(针对存储过程只能通过直接验证模式,选择否)</span>
            </div> 
              <div class="fitem">
                <label>是否入库:</label>
                <input type="radio" name="checkOn"      value="0"  checked="true" />入库
                  <input type="radio" name="checkOn"    value="1"   />不入库
            </div> 
              <div class="fitem">
                <label>是否追加入标准文件:</label>
                <input type="radio" name="appendBasic"      value="0"  checked="true" />是
                  <input type="radio" name="appendBasic"    value="1"   />否
            </div> 
              <div class="fitem">
                <label>创建时间:</label>
               <input  name="time" id="time"  type= "text" class= "easyui-datebox" required ="required"> </input>   
            </div> 
            
            <div class="fitem" style="color: red;">
                   <br/><br/>【是否验证】:是指某些脚本比较复杂需要跳过验证正则表达式环节
                      <br/>【是否入库】:是指改脚本文件是否插入数据库,执行脚本
                   <br/>【是否追加入标准文件】:是指改脚本文件是否追加到标准脚本库文件中
                    <br/>【创建时间】:是指改脚本文件文件名时间(当批次dml时间必须落在ddl区间内)
                     <br/>【ps备注】:脚本文件必须是UTF-8无bom类型
            </div> 
        </form>
        
        
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="fileHistorys.save()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgfile').dialog('close')" style="width:90px">取消</a>
    </div>
    
    
    
    
     <div id="dlgfiledown" class="easyui-dialog"  style="padding:30px 20px; width:500px;height:500px;"    
            closed="true" buttons="#dlg-filedownbuttons" modal="true" >
        <div class="ftitle"></div>
        <form id="fmfiledown" method="post"  novalidate>
        
         	<div class="fitem">
                <label>用户空间:</label>
                <input type="text" name="schame" id="schamee"    class="easyui-textbox"  required="true" data-options="events:{blur:fileHistorys.findBySechmaAndLastTime}"   /><br/> <br/><br/>
            </div> 
             <div class="fitem">
                <label>开始时间:</label>
               <input  name="strDate"   type= "text" class= "easyui-datebox" required ="required"> </input><br/><br/>  <br/> 
            </div> 
              <div class="fitem">
                <label>截止时间:</label>
               <input  name="endDate"  type= "text" class= "easyui-datebox" required ="required"> </input>  <br/> <br/> <br/>
            </div> 
             <div class="fitem">
                <label>时间类型:</label>
               <select class="easyui-combobox" name="timeType" style="width:100px;">   
				   <option value="create" >创建时间</option>   
				   <option value="upload" selected="selected">上传时间</option>   
				</select> <br/> <br/> <br/>
            </div> 
            	
           
             <div class="fitem">
                <label>备  &nbsp; &nbsp;  注:</label>
                <input type="text" name="remark"    class="easyui-textbox"  required="true"     /><br/> <br/><br/>
            </div> 
             
             <div class="fitem" id="mydownDesc" >
                 
            </div> 
        </form>
    </div>
    <div id="dlg-filedownbuttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="fileHistorys.createScript()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgfiledown').dialog('close')" style="width:90px">取消</a>
    </div>
     
     <div id="detailDg" class="easyui-dialog"  style=" width:85%;height:85%;"    
            closed="true" buttons="#dlg-detailDg" modal="true" >
           <div id="fdetail"></div>
    </div>
    <div id="dlg-detailDg">
       
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailDg').dialog('close')" style="width:90px">取消</a>
    </div>
    
    
    <!-- 脚本时间变更 -->
     <div id="dlgfileupdate" class="easyui-dialog"  style="padding:30px 20px; width:400px;height:400px;"    
            closed="true" buttons="#dlg-fileupdatebuttons" modal="true" >
        <div class="ftitle"></div>
        <form id="fmfileupdate" method="post"  novalidate>
        <input  name="id"   type="hidden"   class="easyui-textbox"> </input><br/><br/>  <br/> 
         	 <div class="fitem">
                <label>脚本名:</label>
               <input  name="realName"   type= "text"  class="easyui-textbox" > </input><br/><br/>  <br/> 
            </div> 
             <div class="fitem">
                <label>创建时间:</label>
               <input  name="screateTime"   type= "text" class= "easyui-datebox"  > </input><br/><br/>  <br/> 
            </div> 
              <div class="fitem">
                <label>上传时间:</label>
               <input  name="supdateTime"  type= "text" class= "easyui-datebox" > </input>  <br/> <br/> <br/>
            </div> 
             
            	
            
        </form>
    </div>
    <div id="dlg-fileupdatebuttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="fileHistorys.update()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgfileupdate').dialog('close')" style="width:90px">取消</a>
    </div>
    
    <script type="text/javascript">
    
    var fileHistorys={};
     //下载
     fileHistorys.fserviceUrl=function(value,row,index){
	 	var html = "<a href='file/download.do?id="+row.id+"' target='_blank'>下载</a>&nbsp;&nbsp;&nbsp;&nbsp;";
	 	html += "&nbsp;&nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='fileHistorys.viewDetail("+row.id+",\"realName\")'>查看</a>&nbsp;&nbsp;"
	 	html = html.replace("realName", row.realName);
	 	if(row.physicsFlag==2){
	 		html += " <font color='red'>作废</font>";
	 	}
		return html;
	 };
     
     //格式时间
     fileHistorys.fcreateTime=function(value,row,index){
    		return new Date(value).toLocaleString();
    };
         
 	 //添加弹窗
    fileHistorys.add=function(type){
       	  $('#dlgfile').dialog('open').dialog('setTitle','新增'+type);
             $('#fmfile').form('reset');
             $('#fileType').textbox('setValue',type);
              
      };
      
      //脚本时间变更弹窗
      fileHistorys.updateWin=function(type){
              var rows = $('#dgfile').datagrid('getSelections');
              if (rows.length){ 
              	 var size = rows.length;
           
              	 if(size==1){
              		 $('#dlgfileupdate').dialog('open').dialog('setTitle','脚本时间变更');
                    $('#fmfileupdate').form('reset');
                    
                	$('#fmfileupdate').form("load",rows[0]);
            		 
                  
              	 }else{
              		 $.messager.alert('警告','一次只能变更一个脚本'); 
              	 }
              	 
              }else{
             	 $.messager.alert('警告','请选择变更的脚本'); 
              	 
              }//
        };
        
     //保存
     fileHistorys.save=function(){
            $('#fmfile').form('submit',{
                url: 'file/add.do',
                onSubmit: function(){
                    return $(this).form('validate');
                },
                success: function(data){
                    var dd = eval('('+data+')');
                     //console.log(dd);
                    if (!dd.flag){
                       
                        $.messager.alert('error',dd.result); 
                    } else {
                    	
                        $('#dlgfile').dialog('close');        // close the dialog
                        $.messager.show({
                            title: 'OK',
                            msg: "上传成功",
                            width:'300px',
                            timeout:5000,
                            height:'250px'
                        });
                        $('#dgfile').datagrid('reload');    // reload the user data
                    }
                }
            });
        };

        //按id号下载脚本
        fileHistorys.compress=function(){
        	 
        	$.messager.confirm('确认','在您确认下载前,请前往【<font color="red">增量下载记录</font>】菜单查看是否已有下载记录,<font color="red">以免重复下载</font>？',function(t){    
        	    if (t){    
        	    	 var rows = $('#dgfile').datagrid('getSelections');
                     if (rows.length){ 
                     	 var size = rows.length;
                     	 var ids = "";
                     	 for(var i=0;i<size;i++){
                     		ids += rows[i].id;
                     		 if(i<size-1){ 
                     			ids += ",";
                     		 }
                     		
                     	 }
                     	  
                     	$.messager.prompt('提示信息', '请输入schame:', function(r){
                     		if (r){
                     			 
                     			window.location="file/makeFile.do?ids="+ids+"&schame="+r;
                     		}
                     	});
                     	 
                     	 
                     }else{
                    	 $.messager.alert('警告','请选择需要下载压缩的脚本'); 
                     	 
                     }//
        	    }    
        	});
        	 
        	
        };
        
        
        //脚本时间变更
        fileHistorys.update=function(){
        	var param = $("#fmfileupdate").serialize();
        	 $.post("file/changeTime.do",param,function(data){
        		 $('#dlgfileupdate').dialog('close');        // close the dialog
                 $.messager.show({
                     title: 'OK',
                     msg: "修改成功",
                     width:'300px',
                     timeout:5000,
                     height:'250px'
                 });
                 $('#dgfile').datagrid('reload');    // reload the user data
        	 },"json");
               
           };

        
        //跨时间下载弹窗
         fileHistorys.down=function(){
        	$.messager.confirm('确认','在您确认下载前,请前往【<font color="red">增量下载记录</font>】菜单查看是否已有下载记录时间区间,<font color="red">以免重复下载</font>？',function(r){    
        	    if (r){    
        	    	 $('#dlgfiledown').dialog('open').dialog('setTitle','按时间跨度下载脚本');
                     $('#fmfiledown').form('reset');   
        	    }    
        	});
        	
             
        };
       
      //跨时间下载
       fileHistorys.createScript=function(){
      
            $('#fmfiledown').form('submit',{
                url: "file/makeFile.do",
                onSubmit: function(){
                	var flag = $(this).form('validate');
                	if(flag){
                		$('#dlgfiledown').dialog('close'); 
                	}
                    return flag;
                },
                success: function(data){
                    var dd = eval('('+data+')');
                     //console.log(dd);
                    if (!dd.flag){
                        $.messager.show({
                            title: 'Error',
                            msg: dd.result,
                            width:'300px',
                            height:'250px'
                        });
                    } else {
                        $('#dlgfiledown').dialog('close');        // close the dialog
                        $('#dgfile').datagrid('reload');    // reload the user data
                    }
                }
            });
           
       };
      
       //刷新
       fileHistorys.fsearch=function(){
        	 $('#dgfile').datagrid('loading');
            $('#fmFileSearch').form('submit',{
                url: "file/findAll.do",
                onSubmit: function(){
                	var flag = $(this).form('validate');
                    return flag;
                },
                success: function(data){
                    var dd = eval('('+data+')');
                    // console.log(dd);
                     $('#dgfile').datagrid('loaded');
                      $('#dgfile').datagrid('loadData',dd.rows);    // reload the user data
                 }

            });
        };
        
      
        
        
        //查看脚本详细
         fileHistorys.viewDetail=function(id,realName){
        	$.post("file/findById.do",{"id":id},function(dd){
        		 if(dd.flag){
        			 //console.log(dd);
        			 $('#detailDg').dialog('open').dialog('setTitle','脚本详细:'+realName);
        			 var content = dd.content;
        			 content =  Table.show1(content);
        			 
        			 $("#fdetail").html(content);
        		 }
        				 
     		},"json");
        };
       //重建lunece
       fileHistorys.createIndex=function(){ 
    	   $.post("file/luneceIndex.do",function(data){
    		   $.messager.alert('信息',data.result); 
     			 
       	},"json");
       };
       
       //获得比较库获取时最后一次最新的时间的记录
       fileHistorys.findBySechmaAndLastTime=function(){
    	   var val =$('#schamee').textbox('getValue') ;
    	   $("#mydownDesc").html("");
    	   if($.trim(val)){
    		   $.post("myDown/findBySechmaAndLastTime.do?sechma="+val,function(data){
    			  
        		  if(data.rows){
        			  $("#mydownDesc").html("<font color='red'>最近改 "+val+" 下载记录,尽作参考:</font> 【"+data.rows.way+" 下载时间范围 ： "+data.rows.range+"】");
        		  } 
           	},"json");
    	   }
    	  
       };
    </script>
  
</body>
</html>