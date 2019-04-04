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
    <title>银行conf列表</title>
	
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
  <div   class="easyui-layout" style="width:98%;;height:100%;;">   
    <div data-options="region:'north',title:'功能区',split:true" style="height:100px;padding-top: 5px;"> 
         <div style="padding-left: 20px;padding-bottom: 2px;">
	    <form id="fmbankconfsearch" >
                
				<label>用户空间:</label>
             	 <input  name="bankName"   type= "text" class= "easyui-box" > </input>
                
               <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBankConf.fsearch()">条件查询</a> 
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#fmbankconfsearch').form('reset')">清除</a>  
        </form>
	   </div>
        <div   style="padding-left: 20px;">
	        <tag:permissiontag>  </tag:permissiontag>
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBankConf.addOpen()">添加银行配置</a> 
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBankConf.updateOpen()">修改银行配置</a> 
	        
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBankConf.oneGen()">一键生成模板</a> 
	          
	       
	    	</div>
    </div>
    	 
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    <table id="mybankconfdg"  class="easyui-datagrid" style="height: 98%;"
            url="myBankConf/findAll.do"
             pagination="true"  pageSize="100" pageList="[100,200,300,400,500]"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,singleSelect:true,remoteSort:false," >
        <thead>
            <tr>
            	<th field="" width="50"  data-options="checkbox:true">表名</th>
                <th field="bankName" width="50"  sortable="true">银行名称</th>
                <th field="stype" width="50" sortable="true" formatter="myBankConf.fstype">类型</th>
                <th field="remark" width="50"  sortable="true">备注</th>
                 <th field="createTime" width="50" formatter="myBankConf.fcreateTime" sortable="true">创建时间</th>
                <th field="id" width="50" formatter="myBankConf.fid"  >详情</th>
            </tr>
        </thead>
    </table>
    
    
    </div>   
</div> 

	<!-- 添加 -->
     <div id="bankconfAddWinfileDg" class="easyui-dialog"  style="padding:10px 20px; width:500px;height:400px;"    
            closed="true" buttons="#bankconfAddWin-buttons" modal="true" >
        <div class="ftitle"></div>
        <form id="bankconfAddfile" method="post" enctype="multipart/form-data" novalidate>
        <input    type="hidden" id="bankconfAddWinfileName" class="easyui-validatebox" required="true"  />
         <input  type="hidden" name="bankName" id="bankconfAdd-bankName" class="easyui-validatebox" required="true"  />
          
            <div class="fitem" >
                <label>银行名称:</label>
                 <input id="bankconfAdd-bankId" class="easyui-combobox" name="bankId"   data-options="valueField:'id',textField:'text',editable:true" style="width: 200px;"/>  
    			<br/><br/>
            </div> 
            <div class="fitem">
                <label>环境列表:</label>
                <input type="file" name="uploadFile"    required="true" value=""  onchange="$('#bankconfAddWinfileName').val(this.value)" />
            		<span style="color: red;"><a href="/pic/conftpl/excel/project-list.xlsx">模板下载</a></span>
            		<br/><br/>
            </div> 
             <div class="fitem">
                <label>是否验证:</label>
                <input type="radio" name="stype"      value="0"  checked="true" />测试线
                  <input type="radio" name="stype"    value="1"   />正式线
                 <br/><br/>   
            </div> 
               
             <div class="fitem">
                <label>备注:</label>
                <input type="text"   class="easyui-textbox" name="remark"    required="true" value=""   />
                <br/><br/>
            </div> 
              
            
        </form>
        
        
    </div>
    <div id="bankconfAddWin-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="myBankConf.save()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bankconfAddWinfileDg').dialog('close')" style="width:90px">取消</a>
    </div>
    
    <!-- 修改-->
     <div id="bankconfUpdateWinfileDg" class="easyui-dialog"  style="padding:10px 20px; width:500px;height:400px;"    
            closed="true" buttons="#bankconfUpdatWin-buttons" modal="true" >
        <div class="ftitle"></div>
        <form id="bankconfUpdatfile" method="post" enctype="multipart/form-data" novalidate>
        <input    type="hidden" id="bankconfUpdateWinFileName" class="easyui-validatebox" required="true"  />
         <input  type="hidden" name="id"   class="easyui-validatebox" required="true"  />
           <input  type="hidden" name="bankId"   class="easyui-validatebox" required="true"  />
            <div class="fitem" >
                <label>银行名称:</label>
                 <input   class="easyui-combobox" name="bankName"   data-options="valueField:'id',textField:'text',editable:false,readonly:true" style="width: 200px;"/>  
    			<br/><br/>
            </div> 
            <div class="fitem">
                <label>环境列表:</label>
                <input type="file" name="uploadFile"     required="true" value=""  onchange="$('#bankconfUpdateWinFileName').val(this.value)" />
            		<span style="color: red;"><a href="/pic/conftpl/excel/project-list.xlsx" id="bankconfUpdatfile-a">银行配置模板</a></span>
            		<br/><br/>
            		<span style="color: red;"><a href="/pic/conftpl/excel/project-list.xlsx">模板下载</a></span>
            		<br/><br/>
            </div> 
             <div class="fitem">
                <label>是否验证:</label>
                <input type="radio" name="stype"      value="0"  checked="true" />测试线
                  <input type="radio" name="stype"    value="1"   />正式线
                 <br/><br/>   
            </div> 
               
             <div class="fitem">
                <label>备注:</label>
                <input type="text"   class="easyui-textbox" name="remark"       />
                <br/><br/>
            </div> 
              
            
        </form>
        
        
    </div>
    <div id="bankconfUpdatWin-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="myBankConf.update()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bankconfUpdateWinfileDg').dialog('close')" style="width:90px">取消</a>
    </div>
    
   <!-- 模板详情 -->
  <div id="myBankConf_tables"> </div>
 
    <script type="text/javascript">
    
    var myBankConf = {};
    //格式时间
    myBankConf.fcreateTime=function(value,row,index){
   		return new Date(value).toLocaleString();
   };
     
   //状态
  myBankConf.fstype=function(value,row,index){
   	 
   	if(value==0){
   		return '<font color="blue">测试线</font>';
   	}
   	return '<font color="red">正式线</font>'; 
  		 
  };
 //详情
   myBankConf.fid=function(value,row,index){
	 
   	var html  = "<a href='javascript:void(0);' onclick='myBankConf.viewInfo(\"realName\")'>配置模板</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	 	html = html.replace("realName", row.id);
	 	var excelPath = row.excelPath;
	 	var confPath = row.confPath;
	 	/* if(typeof excelPath!='undefined'){
	 		html += "<a href='/pic/"+excelPath+"' >配置修改</a>&nbsp;&nbsp;";
	 	} */
	 	if(typeof confPath!='undefined'){
	 		html += "<a href='/pic/"+confPath+"' >配置下载</a>&nbsp;&nbsp;";
	 	}
		return html;
  };
  
//刷新
  myBankConf.fsearch=function(){
		 $('#mybankconfdg').datagrid('loading');
      $('#fmbankconfsearch').form('submit',{
          url: "myBankConf/findAll.do",
          onSubmit: function(){
          	var flag = $(this).form('validate');
              return flag;
          },
          success: function(data){
              var dd = eval('('+data+')');
               //console.log(dd);
               	 $('#mybankconfdg').datagrid('loaded');
                $('#mybankconfdg').datagrid('loadData',dd.rows);    // reload the user data
           }

      });
  };
  
  
  myBankConf.viewInfo=function(id){
	  $('#myBankConf_tables').dialog({
  	    title: '配置表',
  	    width:"80%",
  	    height: "90%",
  	    closed: false,
  	    cache: false,
  	    href:  'myBankConfinfo.jsp?id='+id,
  	    modal: true
  	});
  };
  
//添加弹窗
  myBankConf.addOpen=function(){
     	  $('#bankconfAddWinfileDg').dialog('open').dialog('setTitle','新增银行配置');
           $('#bankconfAddfile').form('reset');
           
            
    };
    
  //修改弹窗
    myBankConf.updateOpen=function(){
    	var rows = $('#mybankconfdg').datagrid('getSelected');
        if (rows){ 
        	 $('#bankconfUpdateWinfileDg').dialog('open').dialog('setTitle','修改银行配置');
             $('#bankconfUpdatfile').form('reset');
         	$('#bankconfUpdatfile').form("load",rows );
         	//修改银行模板标签
         	$("#bankconfUpdatfile-a").attr("href","/pic/"+rows.excelPath);
        }else{
        	 $.messager.alert('警告','请选择... '); 
        }
        
       	  
              
      };
 
    //保存
    myBankConf.save=function(){
           $('#bankconfAddfile').form('submit',{
               url: 'myBankConf/add.do',
               onSubmit: function(){
                   return $(this).form('validate');
               },
               success: function(data){
                   var dd = eval('('+data+')');
                    //console.log(dd);
                   if (!dd.flag){
                       $.messager.alert('error',dd.result); 
                   } else {
                   	
                       $('#bankconfAddWinfileDg').dialog('close');        // close the dialog
                       $.messager.alert('ok',dd.result); 
                       $('#mybankconfdg').datagrid('reload');    // reload the user data
                   }
               }
           });
       };
       
     //保存
      myBankConf.update=function(){
             $('#bankconfUpdatfile').form('submit',{
                 url: 'myBankConf/update.do',
                 onSubmit: function(){
                     return $(this).form('validate');
                 },
                 success: function(data){
                     var dd = eval('('+data+')');
                      //console.log(dd);
                     if (!dd.flag){
                         $.messager.alert('error',dd.result); 
                     } else {
                     	
                         $('#bankconfUpdateWinfileDg').dialog('close');        // close the dialog
                         $.messager.alert('ok',dd.result); 
                         $('#mybankconfdg').datagrid('reload');    // reload the user data
                     }
                 }
             });
         };
         
    //一键生成模板
    myBankConf.oneGen=function(){
    	var rows = $('#mybankconfdg').datagrid('getSelected');
        if (rows){ 
        	 $.post("myBankConf/oneGen.do",{id:rows.id},function(data){
           	     $.messager.alert('ok',data.result); 
                 $('#mybankconfdg').datagrid('reload');    // reload the user data
           	    },"json");
        }else{
        	 $.messager.alert('警告','请选择... '); 
        }
   	   
    };
   
  $(function(){
 	 //银行列表
 	 $.post("myBasicBank/findAll.do",function(data){
   		 if(data.total>0){
   			 var rowarr = data.rows;
   			 var arrdata = [];
   			 for(var i=0,len=rowarr.length;i<len;i++){
   				 var row = rowarr[i]; 
   				arrdata.push({"id":row.id,"text":row.realName});
   			 }
   			 //console.log(arrdata);
   			$('#bankconfAdd-bankId').combobox('loadData', arrdata);
   		

   		 }
   	 },"json");
 	
 	 
 	//选择银行事件
 	 $('#bankconfAdd-bankId').combobox({onSelect:function(record){
 	  
 		$('#bankconfAdd-bankName').val(record.text);
 	 }});
 	 
 	 
  });
 </script>
    
</body>
</html>