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
    <title>银行conftpl模板</title>
	
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
  <div   class="easyui-layout" style="width:98%;;height:100%;">   
    <tag:permissiontag>  <div data-options="region:'north',title:'功能区',split:true" style="height:80px;padding-top: 10px;"> 
          
        <div   style="padding-left: 20px;">
	        
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBankConfTpl.save()">编辑保存</a> 
	        
	        
	    	</div>
    </div>
    </tag:permissiontag>
     <div data-options="region:'west',title:'功能区',split:true" style="height:100px;padding-top: 10px;width: 300px;"> 
    		 
       <ul    id="tpl_tree">
		</ul>
    
     </div>
            	 
    <div data-options="region:'center',title:'列表区(单击左边文件)'" style="padding:5px;background:#eee;">
      <textarea id="contenttpl" class="easyui-textbox" data-options="multiline:true" style="width:100%; height:100%;">单击左边文件获取内容</textarea>
    </div>   
</div> 


 <!-- 修改excel模板-->
     <div id="bankconfTplUpdateWinfileDg" class="easyui-dialog"  style="padding:10px 20px; width:500px;height:400px;"    
            closed="true" buttons="#bankconfTplUpdatWin-buttons" modal="true" >
        <div class="ftitle"></div>
        <form id="bankconfTplUpdatfile" method="post" enctype="multipart/form-data" novalidate>
        <input    type="hidden" id="bankconfUpdateTplWinFileName" class="easyui-validatebox" required="true"  />
          
          
            <div class="fitem">
                <label>环境列表:</label>
                <input type="file" name="uploadFile"     required="true" value=""  onchange="$('bankconfUpdateTplWinFileName').val(this.value)" />
            		<span style="color: red;"><a href="/pic/conftpl/excel/project-list.xlsx" id="bankconfplUpdatfile-a">银行模板</a></span>
            		<br/><br/>
            </div> 
              
            
        </form>
        
        
    </div>
    <div id="bankconfTplUpdatWin-buttons">
         <tag:permissiontag> 
         <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="javascript:alert('暂未实现...,请手动修改模板上传')" style="width:90px" >保存</a>
         </tag:permissiontag>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#bankconfTplUpdateWinfileDg').dialog('close')" style="width:90px">取消</a>
    </div>
 
    <script type="text/javascript">
    
    var myBankConfTpl = {};
    //选择的模板
    myBankConfTpl.selectfileName = null;
 
    $(function(){
    	
    	//加载树
    	$('#tpl_tree').tree({    
    		url:'myBankConf/gentree.do',
    		onClick: function(node){
    			if($(this).tree('isLeaf', node.target)){// 在用户点击的时候提示
    				
    				var father = $(this).tree("getParent",node.target);
    				myBankConfTpl.reloadTplContent(father.text+"/"+node.text);
    				 
    			}  
    		}

		}); 
	     
    });
   
     myBankConfTpl.reloadTplContent = function(filename){
    	 $('#contenttpl').textbox('setValue',"") ;
    	 myBankConfTpl.selectfileName = null;
    	 if(filename.indexOf(".xlsx")>0){
    			myBankConfTpl.selectfileName = filename;
    			 $('#bankconfTplUpdateWinfileDg').dialog('open').dialog('setTitle','修改excel配置');
    			 $('#bankconfTplUpdatfile').form('reset');
    				//修改银行模板标签
    	         	$("#bankconfplUpdatfile-a").attr("href","/pic/conftpl/"+filename);
    	 }else{
    		 $.post("myBankConf/findContentTpl.do",{"fileName":filename},function(data){
			 		myBankConfTpl.selectfileName = filename;
		  			$('#contenttpl').textbox('setValue',data) ;  
			 } ); 
    	 }
    		
    	  
		   
    };
    
    //编辑保存
    myBankConfTpl.save  = function(){
    	if(!myBankConfTpl.selectfileName){
    		 $.messager.alert('ok',"请选择！！"); 
    		return;
    	}
     
    	$.messager.confirm('确认',myBankConfTpl.selectfileName+'确认保存？',function(t){    
    	    if (t){
    	    	var content  = $('#contenttpl').textbox('getValue');
      	    	 
   	    	 	$.post("myBankConf/updateContentTpl.do",{"fileName":myBankConfTpl.selectfileName,"content":content},function(data){
   	    		   $.messager.alert('ok',data.result); 
   		 		} ,"json");
    	    	
    	    }
    	 }
    	);
    	
    };
    </script>
  
</body>
</html>