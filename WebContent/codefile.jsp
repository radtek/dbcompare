<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>清分文件加解密</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/color.css">
 
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
 
	 
</head>
<body>
     
      <div  class="easyui-panel"  style="padding:30px 20px; "        >  
            
        <div class="ftitle"></div>
        <form id="codefilewnWen" method="post"  enctype="multipart/form-data" novalidate>
         <input type="hidden" name="codefileName" id="codefileName" class="easyui-validatebox" required="true"  />
          <div class="fitem" style="padding-top: 20px;">
            <label>&nbsp;银行:</label>
           <select id="codefilebank" class="easyui-combobox" name="codefilebank" style="width:200px;">   
			    <option value="--">--</option>
			    <option value="福建农信">福建农信</option>   
			     <option value="徽商银行">徽商银行</option>      
			     <option value="渤海南京">渤海南京</option>
			      <option value="北京银行">北京银行</option>  
			       <option value="成都农商行">成都农商行</option>  
			        <option value="厦门建行">厦门建行</option>
					<option value="银总">银总</option>					
		 
			</select>
			   <font color="red">非必填 部分银行秘钥自动带上<br/> </font> 
			 </div> 
          <div class="fitem" style="padding-top: 20px;">
                <label>&nbsp;秘钥:</label>
                <input name="codefilepwd"  id="codefilepwd" class="easyui-textbox"  type="password"   style="width:200px;" required="true" value="" data-options="prompt:'秘钥',iconCls:'icon-lock',iconWidth:38" />
                  <font color="red">秘钥可自行填写<br/> </font> 
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>算法:</label>
              		 AES<input type="radio" name="stype" value="AES" checked="checked"/> &nbsp;&nbsp;
             	  <font color="red"> 除了可选择银行列表 其他暂只支持AES算法<br/> </font> 
            </div> 
            
            <div class="fitem" style="padding-top: 20px;">
                <label>类型:</label>
              		 解密<input type="radio" name="type" value="0" checked="checked"/> &nbsp;&nbsp;
              		  加密<input type="radio" name="type" value="1"  /> &nbsp;&nbsp;
             	 
            </div> 
             <div class="fitem" style="padding-top: 20px;">
                <label>导入文件:</label>
                <input type="file" name="uploadcodefile" id="uploadcodefile"   style="width:200px;" required="true" value=""  onchange="$('#codefileName').val(this.value)"   />
              <font color="red"> 清分文件<br/> </font> 
            </div> 
            
             
        </form>
    </div>
    <div  >
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="codefile.expWen()"  style="width:200px"  >生成</a>
         
    </div>

 
 

    
    
  
    
    <script type="text/javascript">
    var codefile = {};
    //银行==密码
    codefile.bank={
    		"福建农信":"QUVTMTIzNDU2Nzg5QUJDRA==",
    		"徽商银行":"HS6U25D34j61981s",
    		"渤海南京":"eAvC67p06WIVlf9xmtjI2g==",
    		"北京银行":"EWM_BankOfBeiJing",
    		"成都农商行":"3a7b1c0d0e4f51016DCE0136A3609E8E",
    		"厦门建行":"lcjoi3pigmlo-exlwslmbvaa",
			"银总":"12345678ABCDEFGH"
    };
    
    codefile.expWen=function(){
    	  $('#codefilewnWen').form('submit',{
    	        url: 'code/codefile.do',
    	        onSubmit: function(){
    	            return $(this).form('validate');
    	        },
    	        success: function(data){
    	            var dd = eval('('+data+')');
    	             //console.log(dd);
    	            if (!dd.flag){
    	                $.messager.alert('error',dd.result); 
    	            } else {
    	            	$('#codefilewnWen').form('reset');
    	            	 
    	            	window.location="code/download.do?filename="+dd.result;
    	            }
    	        }
    	    });
    };
  
    $(function(){
    	$('#codefilebank').combobox({    
    		 onSelect: function(rec){
    			   var pwd =  codefile.bank[rec.value];
    			 
    	            $('#codefilepwd').textbox('setValue',pwd) ;   
    	     } 
    	});
    });
 
  </script>
  
</body>
</html>