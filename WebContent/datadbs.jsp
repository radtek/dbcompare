<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>内容比较</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/demo/demo.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
</head>
 <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
    
<body>
 
    
  <div   class="easyui-layout" style="width:98%;;height:100%;;"> 
    <div data-options="region:'west',title:'比较区',split:true" style="width:300px;">
    	 <div class="ftitle"><!-- 比较库连接 --></div>
        <form id="ckcontentfm" method="post" novalidate>
           <fieldset style="border:11px solid purple;padding:10px;margin-bottom:10px;display:block;">
           <legend> 线上库</legend>
            <div class="fitem" style="padding-top: 20px;">
                <label>连接地址:</label>
                <input name="url"   class="easyui-textbox"   required="true" value="123.207.118.153:1521:ntest">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>&nbsp;&nbsp;用户名:</label>
                <input name="name"  class="easyui-textbox"    required="true"  value="bsbbank" data-options="prompt:'用户名',iconWidth:38">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>&nbsp;&nbsp;&nbsp;&nbsp;密码:</label>
                <input name="pwd"  class="easyui-textbox"  type="password"   required="true" value="bsbbank" data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>用户空间:</label>
                <input name="schame"  class="easyui-textbox"    required="true"  value="bsbbank" data-options="prompt:'实列名',iconWidth:38">
            </div>
             
             <div class="fitem" style="padding-top: 20px;">
                <label>快速选择:</label>
                <select id="quickConentSel"  class="" name="quickConentSel"  >
                  
                 </select>
               
            </div>
            
            </fieldset>
            
            <fieldset style="border:11px solid #B5B8C8;padding:10px;margin-bottom:10px;display:block;">
           <legend> 本地库</legend>
            <div class="fitem" style="padding-top: 20px;">
                <label>连接地址:</label>
                <input name="url1"   class="easyui-textbox"   required="true" value="123.207.118.153:1521:ntest">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>&nbsp;&nbsp;用户名:</label>
                <input name="name1"  class="easyui-textbox"    required="true"  value="bsbbank" data-options="prompt:'用户名',iconWidth:38">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>&nbsp;&nbsp;&nbsp;&nbsp;密码:</label>
                <input name="pwd1"    class="easyui-textbox"  type="password"   required="true" value="bsbbank" data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>用户空间:</label>
                <input name="schame1"   class="easyui-textbox"    required="true"  value="bsbbank" data-options="prompt:'实列名',iconWidth:38">
            </div>
            </fieldset>
          
        </form>
        
    </div>   
      
    <div data-options="region:'north',title:'功能区',split:true" style="height:60px;">   
      <div  >
        
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="contentCompares.createTable()">创建参数脚本</a>
      
    </div>
    </div>
    
    <div data-options="region:'center',title:'列表区'" style=" background:#eee;">
       <div class="easyui-layout"  style="width:100%;height:100%;">   
	     
    	<div data-options="region:'west',split:true" style="width:100px;">
    		<p><a href="javascript:void(0);" onclick="contentCompares.check('sysParameter')">系统参数表</a></p>
    		<p><a href="javascript:void(0);" onclick="contentCompares.check('sysorgParameter')">受理机构参数表</a></p>
    		<p><a href="javascript:void(0);" onclick=" contentCompares.viewData('CLE_ACC_PLAN_CONF')">清分日结表</a></p>
    		<p><a href="javascript:void(0);" onclick="contentCompares.viewData('CLE_CLEANING_PROCESS_CONF')">清分类配置表</a></p> 
    		<p><a href="javascript:void(0);" onclick="contentCompares.viewData('CLE_BILL_MERGE_CONF')">合并方案表</a></p> 
    	</div>   
  	
    	<div data-options="region:'center'" >
    	   
    		<table id="contentdg"  class="easyui-datagrid" style="height: 98%;"    
             	pagination="fales" rownumbers="true" fitColumns="true" singleSelect="true"
	           		 data-options="fit:false,border:false,singleSelect:true,remoteSort:false" >
		        <thead>
		            <tr> 
		                <th field="parameterName" width="50" sortable="true">参数名称</th>
		                <th field="parameterValue" width="50" sortable="true">参数值</th>
		                 <th field="remark" width="50"  sortable="true">备注</th>
		                 <th field="type" width="50"  sortable="true" formatter="contentCompares.ftype">差异</th>
		                  
		            </tr>
		        </thead>
   			 </table>
    	</div>
    	   
	</div>

    </div>   
</div> 

   
   
    <div id="contentDg" class="easyui-dialog"  style=" width:900px;height:600px;"    
            closed="true" buttons="#dlg-contentDg" modal="true" >
            <textarea id="fffcontent"     style=" width:850px;height:500px;"  onclick="$(this).select()"></textarea>
           <!-- <div id="fffcontent" onclick="$(this).select()"></div> -->
    </div>
    <div id="dlg-contentDg">
       
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#contentDg').dialog('close')" style="width:90px">取消</a>
    </div>
    
    <div id="content_tables"> </div>
    
    <script type="text/javascript">
    var contentCompares = {};
    
    contentCompares.dbtypes=null;
    
    $(function(){
   	 
    	$.post("dbType/findAll.do",{},function(json){
    		 
    		var html = "";
    		var arrary = json.rows;
    		contentCompares.dbtypes = arrary;
    		for(var i=0,len=arrary.length;i<len;i++){
    			var db = arrary[i];
    			html += " <option type='radio'  value='"+db.type+"' >" +db.name+"</option>";
    			
    		}
    		$("#quickConentSel").html(html);
    	},"json");
    	
       //快速选择框
      	$('#quickConentSel').change(function(){
      		var val  = this.value;
      		contentCompares.quickSel(val);
      	}); 
    	
    });
    
    //是否内容比较了
    contentCompares.chekcOn = false;

    //差异
     contentCompares.ftype = function(value,row,index){
    	if(value=='1'){//存在相同
    		return "<span style='color:purple'>线上库,本地库数据值相同</span>";
    	}else if(value=='2'){//存在不相同
    		return "<span style='color:red'>线上库,本地库数据值不一致</span>";
    	}else if(value=='0' && row.typeDatabase=='0'){//不存在
    		return "<span style='color:blue '>线上库有,本地库没有</span>";
    	}else if(value=='0' && row.typeDatabase=='1' ){//存在不相同
    		return "<span style='color:yellow'>线上库没有,本地库有</span>";
    	}
    	
    	return "";
		 
	 };
	 
 
	
     //选择
     contentCompares.quickSel=function(value){
    	 if(contentCompares.dbtypes){
     		for(var i=0,len=contentCompares.dbtypes.length;i<len;i++){
     			var db = contentCompares.dbtypes[i];
     			if(value==db.type){
     				$('#ckcontentfm').form('load',{
    					url:db.url,
    					name:db.userName,
    					pwd:db.pwd,
    					
    					schame:''
    					 
    				});
     			}
     		}
     	}
    };
    
    
    
    //比对表
     contentCompares.check=function(type){
    	 contentCompares.chekcOn = false;
	   	$('#ckcontentfm').form('submit',{
	           url: "content/"+type+".do",
	           onSubmit: function(){
	        	   var flag = $(this).form('validate');
	        	   if(flag){
	        		   //进度条
	        		   	 $('#contentdg').datagrid('loading');
	        		   	$("#ckcontentfm :input").attr("readonly", "readonly");
	        		     
	        	   }
	               return flag;
	           },
	           success: function(data){
	            
		             var data = eval('('+data+')');
		             var tables = data.rows;
		             $('#contentdg').datagrid('loaded');
		             $("#ckcontentfm :input").removeAttr("readonly");
		         	if(data.flag){
		         		$('#contentdg').datagrid('loadData',tables);
		         		contentCompares.chekcOn = true;
		         	}else{
		         		 $.messager.alert('警告',data.result); 
		         	}
		            
	             
	           }
	       },'json');
   };
   
   
   //创建补丁脚本
    contentCompares.createTable=function(){
	   
	    if(contentCompares.chekcOn){
	    	$('#ckcontentfm').form('submit',{
		           url: "content/generateSysParameterScript.do",
		           onSubmit: function(){
		        	   var flag = $(this).form('validate');
		        	    
		               return flag;
		           },
		           success: function(data){
			             var data = eval('('+data+')');
			         	if(data.flag){
			         		 $('#contentDg').dialog('open').dialog('setTitle','脚本创建');
			         		 var content = data.result;
			         		 $("#fffcontent").html(content);
			         		contentCompares.chekcOn = false;
				           
			         	}else{
			         		 $.messager.alert('警告',data.result); 
			         	}
			            
		             
		           }
		       },'json');
	    }else{
	    	 $.messager.alert('警告','请先内容比对在创建!!'); 
	    }
    	
   	 
   		};
       
   		
   	 //表数据
      contentCompares.viewData=function(table){
    	  var url = $("input[name='url']","#ckcontentfm").val();	 
          var name =$("input[name='name']","#ckcontentfm").val();
          var pwd = $("input[name='pwd']","#ckcontentfm").val();
          var schame = $("input[name='schame']","#ckcontentfm").val();
    	   $('#content_tables').dialog({
         	    title:table,
         	    width: 800,
         	    height: 600,
         	    closed: false,
         	    cache: false,
         	    href:  'contentData.jsp?table='+table+'&url='+url+'&name='+name+'&pwd='+pwd+'&schame='+schame,
         	    modal: true
         	});
      };
    </script>
   
</body>
</html>