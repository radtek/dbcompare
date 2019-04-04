<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>sql文件null属性过滤</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
 
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
 
	 
</head>
<body>
  <div  id="codesqlnulllayout"   class="easyui-layout" style="width:98%;;height:100%;">   
      
  
      <div data-options="region:'center'" style="padding:5px;background:#eee;">
      
      
   <div  class="easyui-tabs" style="width:100%;height:95%;">   
   
   
         <!-- 按文本导出 start-->
       <div title="按文本导入导出"  style="overflow:auto;padding:20px; ">   
    
	 <div  class="easyui-panel"  style="padding:30px 20px;height: 300px"    buttons="#dlg-codesqlnulldieFileImpsbuttonWens"   >  
	            
         
        <form id="codesqlnulldieFileImpsdownWen" method="post"  enctype="multipart/form-data" novalidate>
         <input type="hidden" name="codesqlnullrealWenName" id="codesqlnullrealWenName" class="easyui-validatebox" required="true"  />
          
            <div class="fitem">
                <label>导入文件:</label>
                <input type="file" name="sqlnullFile" id="sqlnullFile"   required="true" value=""  onchange="$('#codesqlnullrealWenName').val(this.value)"   />
            </div> 
            </br> </br>
            <div class="fitem">
              	     <font color='red'>用于运维导出insert语句时多余的数据结构null属性值会被过滤掉 其他保持原样输出(比如受理机构信息等脚本)</font>
            </div>
             </br> </br>
              <div class="fitem">
              	     <font color='red'>请导入utf-8格式文件 以防止乱码 </font>
            </div>
            </br> </br>
             <div class="fitem">
              	     <font color='red'>导出为gb2312文件(dba要求)</font>
            </div>
            </br> </br>
            
			  <div id="dlg-codesqlnulldieFileImpsbuttonWens">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codesqlnull.expWen()" style="width:90px" >生成</a>
        
    </div>
        </form>
    </div>
  
      
    </div> 
     <!-- 按文本导出 end--> 
</div>  
                                 
</div> 
</div> 

 
 

    
    
  
    
    <script type="text/javascript">
    var codesqlnull = {};
    
      
    
    codesqlnull.expWen=function(){
     
  	  $('#codesqlnulldieFileImpsdownWen').form('submit',{
  	        url: 'code/sqlnullFile.do',
  	        onSubmit: function(param){
  	         
  	            return $(this).form('validate');
  	        },
  	        success: function(data){
  	            var dd = eval('('+data+')');
  	             //console.log(dd);
  	            if (!dd.flag){
  	                $.messager.alert('error',dd.result); 
  	            } else {
  	            	$('#codesqlnulldieFileImpsdownWen').form('reset');
  	            	 
  	            	window.location="code/download.do?filename="+dd.result;
  	            }
  	        }
  	    });
  };
  </script>
  
</body>
</html>