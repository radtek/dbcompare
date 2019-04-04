<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>通用aes加解密</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
 
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
 
	 
</head>
<body>
  <div  id="codeaeslayout"   class="easyui-layout" style="width:98%;;height:100%;">   
     <div data-options="region:'north'" style="padding:5px;background:#eee;height:200px;padding-top: 15px;"  >
      <div class="fitem">
                <label>请输入秘钥 
                 </label>
                 <input type="text" name="codeaespwd" id="codeaespwd" style="width:90%;height:50px"  value="E8NLfxcUyFMKt22Rn5k1PQ==" class="easyui-textbox"  required="true"   data-options="prompt:'请输入秘钥',multiline:true"  /> <br/>
               
     </div> 
     </br>
     <div class="fitem">
     <label>解密操作:</label>
               		解密<input type="radio" name="codeaestype" value="de" checked="checked"/> &nbsp;&nbsp;
              		  加密<input type="radio" name="codeaestype" value="en"  /> &nbsp;&nbsp;
     </div> 
      
   </div>
  
      <div data-options="region:'center'" style="padding:5px;background:#eee;">
      
      
   <div  class="easyui-tabs" style="width:100%;height:95%;">   
   
    <div title="直接输入" data-options="" style="overflow:auto;padding:20px;">   
        <!-- 直接输入start -->
       <div   class="easyui-panel"  style="padding:30px 20px;"     buttons="#dlg-codeaesfiledownbuttons"   >
  
        <form id="codeaesfiledown" method="post"  novalidate>
        
         	<div class="fitem">
                <label>输入信息:</label>
                <input type="text" name="codeaesinput" id="codeaesinput" style="width:100%;height:80px"   class="easyui-textbox"  required="true"   data-options="prompt:'一次输入一个',multiline:true"  /> 
            </div> 
              <div class="fitem" >
                 </br>
            </div> 
          
             
             <div id="dlg-codeaesfiledownbuttons">
        		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codeaes.codeaesinput()" style="width:90px" >处理</a>
         
   			 </div>
   			 
   			   <div class="fitem" >
                 </br>
            </div> 
            
   			 <div class="fitem">
                <label>输出信息:</label>
                <input type="text" name="codeaesoutput" id="codeaesoutput" style="width:100%;height:80px"   class="easyui-textbox"      data-options="prompt:'',multiline:true"  /><br/> <br/><br/>
            </div>
        </form>
    
   		 </div>  
   		    
    </div>   
     <!-- 直接输入end -->
     
         <!-- 按文本导出 start-->
       <div title="按文本导入导出"  style="overflow:auto;padding:20px; ">   
    
	 <div  class="easyui-panel"  style="padding:30px 20px;height: 300px"    buttons="#dlg-codeaesdieFileImpsbuttonWens"   >  
	            
         
        <form id="codeaesdieFileImpsdownWen" method="post"  enctype="multipart/form-data" novalidate>
         <input type="hidden" name="codeaesrealWenName" id="codeaesrealWenName" class="easyui-validatebox" required="true"  />
          
            <div class="fitem">
                <label>导入文件:</label>
                <input type="file" name="codeaesuploadFile" id="codeaesuploadFile"   required="true" value=""  onchange="$('#codeaesrealWenName').val(this.value)"   />
            </div> 
            <div class="fitem">
              	     
            </div>
             
              
			  <div id="dlg-codeaesdieFileImpsbuttonWens">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codeaes.expWen()" style="width:90px" >生成</a>
        
    </div>
        </form>
    </div>
  
      
    </div> 
     <!-- 按文本导出 end--> 
</div>  
                                 
</div> 
</div> 

 
 

    
    
  
    
    <script type="text/javascript">
    var codeaes = {};
    //加解密
    codeaes.codeaesinput=function(){
    	var codeaesinput =  $('#codeaesinput').textbox('getValue') ;
    	var codeaespwd =  $('#codeaespwd').textbox('getValue') ;
    	var codeaestype =  $("input[name='codeaestype']:checked").val() ;
    	$.post("code/codeaesinput.do",{"codeaesinput":codeaesinput,"codeaespwd":codeaespwd,"codeaestype":codeaestype},function(json){
    		var result = json.result;
    		 $('#codeaesoutput').textbox('setValue',result) ;  
    		 
    	},"json");
    		
    	 
    };
      
    
    codeaes.expWen=function(){
    	var codeaesinput =  $('#codeaesinput').textbox('getValue') ;
    	var codeaespwd =  $('#codeaespwd').textbox('getValue') ;
    	var codeaestype =  $("input[name='codeaestype']:checked").val() ;
    
  	  $('#codeaesdieFileImpsdownWen').form('submit',{
  	        url: 'code/codeaesuploadFile.do',
  	        onSubmit: function(param){
  	        	param.codeaesinput=codeaesinput;
  	        	param.codeaespwd=codeaespwd;
  	        	param.codeaestype=codeaestype;
  	        
  	            return $(this).form('validate');
  	        },
  	        success: function(data){
  	            var dd = eval('('+data+')');
  	             //console.log(dd);
  	            if (!dd.flag){
  	                $.messager.alert('error',dd.result); 
  	            } else {
  	            	$('#codeaesdieFileImpsdownWen').form('reset');
  	            	 
  	            	window.location="code/download.do?filename="+dd.result;
  	            }
  	        }
  	    });
  };
  </script>
  
</body>
</html>