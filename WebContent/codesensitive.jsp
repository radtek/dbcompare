<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>敏感信息加解密</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
 
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
 
	 
</head>
<body>
  <div  id="codesensitivelayout"   class="easyui-layout" style="width:98%;;height:100%;">   
     <div data-options="region:'north'" style="padding:5px;background:#eee;height:200px;padding-top: 15px;"  >
      <div class="fitem">
                <label>请输入秘钥(json)
                 <font color="red">格式:【{"fourElementSignKey":"pHvSwWgG","accountSignKey":"10000160","idcardSignKey":"00000160"} 】 </font> 
                :</label>
                 <input type="text" name="codesensitivepwd" id="codesensitivepwd" style="width:90%;height:50px"   class="easyui-textbox"  required="true"   data-options="prompt:'请输入秘钥(json格式)',multiline:true"  /> <br/>
              <font color="red">秘钥来源:数据库中查询【select * from schem.cms_init_parameter t where t.parameter_name = 'CRYPT_SENSITIVE_CONFIG'】 </font> 
               
     </div> 
     </br>
     <div class="fitem">
     <label>解密操作:</label>
               		解密<input type="radio" name="codesensitivetype" value="de" checked="checked"/> &nbsp;&nbsp;
              		  加密<input type="radio" name="codesensitivetype" value="en"  /> &nbsp;&nbsp;
     </div> 
     <div class="fitem" style="padding-top: 20px;">
            <label>&nbsp;敏感操作:</label>
           <select id="codesensitivebank" class="easyui-combobox" name="codesensitivebank" style="width:200px;">   
			  
			    <option value="accountSignKey">结算账号</option>   
			     <option value="idcardSignKey">证件号码</option>      
			     <option value="fourElementSignKey">四要素</option>
			      
			</select>
			   <font color="red">选择对应的敏感字段，现阶段仅支持【结算卡号、证件号码、四要素】 </font> 
              
	</div> 
   </div>
  
      <div data-options="region:'center'" style="padding:5px;background:#eee;">
      
      
   <div  class="easyui-tabs" style="width:100%;height:95%;">   
   
    <div title="直接输入" data-options="" style="overflow:auto;padding:20px;">   
        <!-- 直接输入start -->
       <div   class="easyui-panel"  style="padding:30px 20px;"     buttons="#dlg-codesensitivefiledownbuttons"   >
  
        <form id="codesensitivefiledown" method="post"  novalidate>
        
         	<div class="fitem">
                <label>输入信息:</label>
                <input type="text" name="codesensitiveinput" id="codesensitiveinput" style="width:100%;height:80px"   class="easyui-textbox"  required="true"   data-options="prompt:'多个以,分割',multiline:true"  /> 
            </div> 
              <div class="fitem" >
                 </br>
            </div> 
          
             
             <div id="dlg-codesensitivefiledownbuttons">
        		<a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codesensitive.codesensitiveinput()" style="width:90px" >处理</a>
         
   			 </div>
   			 
   			   <div class="fitem" >
                 </br>
            </div> 
            
   			 <div class="fitem">
                <label>输出信息:</label>
                <input type="text" name="codesensitiveoutput" id="codesensitiveoutput" style="width:100%;height:80px"   class="easyui-textbox"      data-options="prompt:'',multiline:true"  /><br/> <br/><br/>
            </div>
        </form>
    
   		 </div>  
   		    
    </div>   
     <!-- 直接输入end -->
     
         <!-- 按文本导出 start-->
       <div title="按文本导入导出"  style="overflow:auto;padding:20px; ">   
    
	 <div  class="easyui-panel"  style="padding:30px 20px;height: 300px"    buttons="#dlg-codesensitivedieFileImpsbuttonWens"   >  
	            
         
        <form id="codesensitivedieFileImpsdownWen" method="post"  enctype="multipart/form-data" novalidate>
         <input type="hidden" name="codesensitiverealWenName" id="codesensitiverealWenName" class="easyui-validatebox" required="true"  />
          
            <div class="fitem">
                <label>导入文件:</label>
                <input type="file" name="codesensitiveuploadFile" id="codesensitiveuploadFile"   required="true" value=""  onchange="$('#codesensitiverealWenName').val(this.value)"   />
            </div> 
            <div class="fitem">
              	     
            </div>
              <div class="fitem"> 
              	   <font color="red">文本方式 ,每行代表一条记录信息 <a href="/pic/codePath/template.txt">模板</a><br/>
              	    </font> 
            </div>
              
			  <div id="dlg-codesensitivedieFileImpsbuttonWens">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codesensitive.expWen()" style="width:90px" >生成</a>
        
    </div>
        </form>
    </div>
  
      
    </div> 
     <!-- 按文本导出 end--> 
</div>  
                                 
</div> 
</div> 

 
 

    
    
  
    
    <script type="text/javascript">
    var codesensitive = {};
    //加解密
    codesensitive.codesensitiveinput=function(){
    	var codesensitiveinput =  $('#codesensitiveinput').textbox('getValue') ;
    	var codesensitivepwd =  $('#codesensitivepwd').textbox('getValue') ;
    	var codesensitivetype =  $("input[name='codesensitivetype']:checked").val() ;
    	var codesensitivebank =  $('#codesensitivebank').textbox('getValue') ;
    	$.post("code/codesensitiveinput.do",{"codesensitiveinput":codesensitiveinput,"codesensitivepwd":codesensitivepwd,"codesensitivetype":codesensitivetype,"codesensitivebank":codesensitivebank},function(json){
    		var result = json.result;
    		 $('#codesensitiveoutput').textbox('setValue',result) ;  
    		 
    	},"json");
    		
    	 
    };
      
    
    codesensitive.expWen=function(){
    	var codesensitiveinput =  $('#codesensitiveinput').textbox('getValue') ;
    	var codesensitivepwd =  $('#codesensitivepwd').textbox('getValue') ;
    	var codesensitivetype =  $("input[name='codesensitivetype']:checked").val() ;
    	var codesensitivebank =  $('#codesensitivebank').textbox('getValue') ;
  	  $('#codesensitivedieFileImpsdownWen').form('submit',{
  	        url: 'code/codesensitiveuploadFile.do',
  	        onSubmit: function(param){
  	        	param.codesensitiveinput=codesensitiveinput;
  	        	param.codesensitivepwd=codesensitivepwd;
  	        	param.codesensitivetype=codesensitivetype;
  	        	param.codesensitivebank=codesensitivebank;
  	            return $(this).form('validate');
  	        },
  	        success: function(data){
  	            var dd = eval('('+data+')');
  	             //console.log(dd);
  	            if (!dd.flag){
  	                $.messager.alert('error',dd.result); 
  	            } else {
  	            	$('#codesensitivedieFileImpsdownWen').form('reset');
  	            	 
  	            	window.location="code/download.do?filename="+dd.result;
  	            }
  	        }
  	    });
  };
  </script>
  
</body>
</html>