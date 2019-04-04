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
    <title>银行列表</title>
	
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
  <div id="aa" class="easyui-layout" style="width:98%;;height:100%;;">   
    <div data-options="region:'north',title:'功能区',split:true" style="height:100px;padding-top: 10px;"> 
         <div style="padding-left: 20px;padding-bottom: 5px;">
	    <form id="fmbanksearch" >
                
				<label>用户空间:</label>
             	 <input  name="sechma"   type= "text" class= "easyui-box" > </input>
                
               <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBasicBank.fsearch()">条件查询</a> 
                <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="javascript:$('#fmbanksearch').form('reset')">清除</a>  
        </form>
	   </div>
        <div   style="padding-left: 20px;">
	        <tag:permissiontag> 
	        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBasicBank.addOpen()">添加银行</a> 
	          
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBasicBank.update()">变更状态</a>
	          <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBasicBank.check()">检测表差异</a> 
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBasicBank.checkMerge()">清分检测</a> 
	         <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="myBasicBank.checkMonitor()">服务down机检测</a> 
	         
	         </tag:permissiontag>
	    	</div>
    </div>
    	 
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    <table id="mybankdg"  class="easyui-datagrid" style="height: 98%;"
            url="myBasicBank/findAll.do"
             pagination="true" pageSize="100" pageList="[10,50,100,150,200]"
            rownumbers="true" fitColumns="true"  
            data-options="fit:false,border:false,singleSelect:false,remoteSort:false," >
        <thead>
            <tr>
            	<th field="" width="50"  data-options="checkbox:true">表名</th>
                <th field="sechma" width="50"  sortable="true">用户空间</th>
                <th field="realName" width="50" sortable="true">实列名</th>
                <th field="mold" width="50" formatter="myBasicBank.fmold"  sortable="true">类型</th>
                <th field="merge" width="60"   sortable="true">合并方案</th>
   				<th field="planName" width="100"   sortable="true">日结方案</th>
   				<th field="cleaingProcess" width="80"   sortable="true">清分类</th> 
   				<th field="nullfile" width="30"   sortable="true" >空文件</th>
   				 <th field="isbroken" width="30"     sortable="true">技术费分离</th>
              
                <!--  <th field="checkResult" width="30" formatter="myBasicBank.fcheckResult"  sortable="true">差异</th>
               -->
                    <th field="checkContent" width="50" sortable="true">检测内容</th>
                    <!-- <th field="createTime" width="50" formatter="myBasicBank.fcreateTime" sortable="true">创建时间</th> -->
                 <th field="createTime" width="30" formatter="myBasicBank.fcheckcontent">差异</th>
                 <th field="fls3" width="50" formatter="myBasicBank.ffls3">登录信息</th>
                  <th field="id" width="50" formatter="myBasicBank.fid">银行小档案</th>
                  <th field="webok" width="30"   sortable="true">网站</th>
            </tr>
        </thead>
    </table>
    
    
    </div>   
</div> 


 <div id="detailBankDg" class="easyui-dialog"  style=" width:85%;height:85%;"    
            closed="true" buttons="#dlg-detailBankDg" modal="true" >
           <div id="fdetailBank"></div>
    </div>
    <div id="dlg-detailBankDg">
       
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#detailBankDg').dialog('close')" style="width:90px">取消</a>
    </div>
     
     
      
     <div id="dlgbank" class="easyui-dialog"  style="padding:10px 20px; width:500px;height:300px;"    
            closed="true" buttons="#dlg-bankbuttons" modal="true" >
        <div class="ftitle"></div>
        <form id="fmbank" method="post"   novalidate>
        
          <div class="fitem">
                <label>表空间:</label>
                <input name="sechma"   class="easyui-textbox" required="true"   />
            </div>
           
            
            <div class="fitem">
                <label>银行名称:</label>
                <input name="realName" id="realName" class="easyui-textbox" required="true"  />
            </div> 
              <div class="fitem">
                <label>库类型:</label>
                 <div id="dbtypes">
               
                  </div>
            </div> 
              
            
          
        </form>
        
        
    </div>
    <div id="dlg-bankbuttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="myBasicBank.save()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgbank').dialog('close')" style="width:90px">取消</a>
    </div>
    
   
   
   
     
     <div id="dlgbankinfo" class="easyui-dialog"  style="padding:10px 20px; width:900px;height:650px;"    
            closed="true"  buttons="#dlg-bankbuttoninfos"  modal="true" >
        <div class="ftitle"></div>
        <form id="fmbankinfo" method="post"   novalidate>
        <input name="id"  type="hidden"   />
          <table>
          
             <tr>
             	<td>银行名称:</td>
             	<td><input name="realName"   class="easyui-textbox"  style="width:250px;"   /></td>
             	<td>表空间:</td>
             	<td><input name="sechma"   class="easyui-textbox"   style="width:250px;"   /></td>
             </tr>
              
              <tr>
              	<td>受理机构号:</td>
             	<td><input name="acceptorg"   class="easyui-textbox"   style="width:250px;"   /></td>
             	<td>系统标识符:</td>
             	<td><input name="platform"   class="easyui-textbox"   style="width:250px;"    /></td>
             	
             </tr>
             
              <tr>
              <td>jar包:</td>
             	<td><input name="jar"   class="easyui-textbox"   style="width:250px;"   /></td>
             	<td>md5:</td>
             	<td><input name="md5"   class="easyui-textbox"   style="width:250px;"   /></td>
              
             </tr>
             
              <tr> <td colspan="4"><hr /></td></tr>
               <tr>
             
             	<td>银行类别:</td>
             	<td><input name="bankType"   class="easyui-textbox"   style="width:250px;"   /></td>
             </tr>
              <tr>
             	<td>清分文件类型:</td>
             	<td><input name="filetype"   class="easyui-textbox"  style="width:250px;"    /></td>
             	<td>清分文件个数:</td>
             	<td><input name="filecount"   class="easyui-textbox"   style="width:250px;"   /></td>
             </tr>
              
              <tr>
             	<td>是否回盘:</td>
             	<td><input name="needback"   class="easyui-textbox"   style="width:250px;"   /></td>
             		<td>回盘码:</td>
             	<td><input name="needcode"   class="easyui-textbox"   style="width:250px;"   /></td>
             </tr>
              
              <tr>
             	<td>回盘文件名:</td>
             	<td><input name="needfile"   class="easyui-textbox"   style="width:250px;"   /></td>
             </tr>
              <tr>
             	<td>合并方案:</td>
             	<td><input name="merge"   class="easyui-textbox"   style="width:250px;"   /></td>
             	<td>空文件:</td>
             	<td><input name="nullfile"   class="easyui-textbox"  style="width:250px;"    /></td>
             </tr>
              <tr>
             	<td>日结方案:</td>
             	<td><input name="planName"   class="easyui-textbox"   style="width:250px;"   /></td>
             	<td>日结是否重写:</td>
             	<td><input name="planPameOverride"   class="easyui-textbox"  style="width:250px;"    /></td>
             </tr>
              <tr>
             	<td>清分类:</td>
             	<td><input name="cleaingProcess"   class="easyui-textbox"  style="width:250px;"    /></td>
             	<td>清分文件是否定制:</td>
             	<td><input name="cleaingProcessOverride"   class="easyui-textbox"   style="width:250px;"   /></td>
             </tr>
              <tr>
             	
             </tr>
             
             
             <tr>
             	<td>是否冻结:</td>
             	<td><input name="isfreez"   class="easyui-textbox"  style="width:250px;"    /></td>
             	<td>是否补贴:</td>
             	<td><input name="issubdiy"   class="easyui-textbox"   style="width:250px;"   /></td>
             </tr>
             
             <tr>
             	<td>威富通服务费是否分离:</td>
             	<td><input name="isbroken"   class="easyui-textbox"  style="width:250px;"    /></td>
             </tr>
              <tr>
             	<td>ftp类型:</td>
             	<td><input name="ftp"   class="easyui-textbox"  style="width:250px;"    /></td>
             </tr>
              <tr>
             	<td>通知类型:</td>
             	<td><input name="notify"   class="easyui-textbox"   style="width:250px;"   /></td>
             	<td>是否自动清分:</td>
             	<td><input name="autoclean"   class="easyui-textbox"  style="width:250px;"    /></td>
             </tr>
              <tr> <td colspan="4"><hr /></td></tr>
              <tr>
             	<td>后台service端口:</td>
             	<td><input name="servicePort"   class="easyui-textbox"   style="width:250px;"   /></td>
             	 	<td>域名:</td>
             	<td><input name="webSite"   class="easyui-textbox"   style="width:250px;"   /></td>
             	
             </tr>
              
              
             <tr>
             	<td>down机是否提醒:</td>
             	<td>  <!-- <input name="issend"   class="easyui-textbox"   style="width:250px;"   /> -->
             	 是 <input type="radio" name="issend" value="1"/> &nbsp;&nbsp;
             	 否 <input type="radio" name="issend" value="0"/>  
             	</td>
             	 	<td>down机扫描次数:</td>
             	<td><input name="sendcount"   class="easyui-textbox"   style="width:250px;"   /></td>
             	
             </tr>
           
               <tr>
             	<td>清分文件涉及额外参数:</td>
             	<td>
              
             	 <input class="easyui-textbox"  name="etraparam" data-options="multiline:true"  style="width:250px;height:50px">
             </tr>
          </table>
          
          
        </form>
        
        
    </div>
   
   
     <div id="dlg-bankbuttoninfos">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="myBasicBank.modify()" style="width:90px" >保存</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgbankinfo').dialog('close')" style="width:90px">取消</a>
    </div>
    
    <script type="text/javascript">
    
    var myBasicBank = {};
    myBasicBank.dbtypes=null;
    $(function(){
    	$.post("dbType/findAll.do",{},function(json){
    		 
    		var html = "";
    		var arrary = json.rows;
    		 myBasicBank.dbtypes = arrary;
    		for(var i=0,len=arrary.length;i<len;i++){
    			var db = arrary[i];
    			if(i==0){
    				html += " <input type='radio' name='type'    value='"+db.type+"'  checked='true'  />"+db.name;
    			}else{
    				html += " <input type='radio' name='type'    value='"+db.type+"'   />"+db.name;
    			}
    			
    		}
    		$("#dbtypes").html(html);
    	},"json");
    });
    //是否检测
    myBasicBank.fcheckIs=function(value,row,index){
    	if(value=='0'){
    		return "<font  >是</font>";
    	}
    	if(value=='1'){
    		return "<font  color='red'>否</font>";
    	}
    	 
    	return "";
    };
    //类型'类型 0测试库 1私有云专属库 2私有云网络库';
    myBasicBank.fmold = function(value,row,index){
    	if(myBasicBank.dbtypes){
    		for(var i=0,len=myBasicBank.dbtypes.length;i<len;i++){
    			var db = myBasicBank.dbtypes[i];
    			if(value==db.type){
    				return "<font>"+db.name+"</font>";
    			}
    		}
    	}
    	return "<font color='red'>未知</font>";
    };
    //检测结果'检测结果 0-未比较 1-比较无差异 2-比较有差异';
   myBasicBank.fcheckResult = function(value,row,index){
	   if(value=='0'){
   			return "<font color='blue'>未</font>";
   		}
   		if(value=='1'){
   			return "<font >无</font>";
   		}
   		if(value=='2'){
   			return "<font color='red'>有</font>";
   		}
   	return "";
    };
    
  
   //checkcontent
   myBasicBank.fcheckcontent=function(value,row,index){
   	var html  = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='myBasicBank.viewDetail(\"realName\")'>差异</a>&nbsp;&nbsp;";
	 	html = html.replace("realName", row.checkContent);
	 	if(row.physicsFlag==2){
	 		html += " <font color='red'>作废</font>";
	 	}
		return html;
  };
   
   //银行小档案
   myBasicBank.fid=function(value,row,index){
   	var html  = "&nbsp;&nbsp;<a href='javascript:void(0);' onclick='myBasicBank.viewbankInfo(\"realName\")'>银行小档案</a>&nbsp;&nbsp;";
	 	html = html.replace("realName", row.id);
		return html;
  };
   
   myBasicBank.ffls3=function(value,row,index){
   	var html  = '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="myBasicBank.viewLogin(\'realName\')">查看</a>&nbsp;&nbsp;';
	 	html = html.replace("realName", row.id);
		return html;
  };
   
   
 
   //刷新
   myBasicBank.fsearch=function(){
		 $('#mybankdg').datagrid('loading');
       $('#fmbanksearch').form('submit',{
           url: "myBasicBank/findAll.do",
           onSubmit: function(){
           	var flag = $(this).form('validate');
               return flag;
           },
           success: function(data){
               var dd = eval('('+data+')');
                //console.log(dd);
                	 $('#mybankdg').datagrid('loaded');
                 $('#mybankdg').datagrid('loadData',dd.rows);    // reload the user data
            }

       });
   };
   
    
   
   //按id号变更状态
    myBasicBank.update=function(){
   	 
   	$.messager.confirm('确认','是否变更该状态？',function(t){    
   	    if (t){    
   	    	 var rows = $('#mybankdg').datagrid('getSelections');
                if (rows.length){ 
                	 var size = rows.length;
                	 var ids = "";
                	 if(size==1){
                		ids += rows[0].id;
                		$.post("myBasicBank/disable.do",{"id":ids},function(dd){
                			 
                			$('#mybankdg').datagrid('reload');    // reload the user data
                		},"json");
                	 }else{
                		 $.messager.alert('警告','一次只能变更一个脚本'); 
                	 }
                	 
                }else{
               	 $.messager.alert('警告','请选择变更的脚本'); 
                	 
                }//
   	    }    
   	});

   };
   //修改银行档案
   myBasicBank.modify=function(){
	   $('#fmbankinfo').form('submit',{
           url: 'myBasicBank/modify.do',
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
               	
                   $('#dlgbankinfo').dialog('close');        // close the dialog
                   $.messager.show({
                       title: 'OK',
                       msg: "成功",
                       width:'300px',
                       timeout:5000,
                       height:'250px'
                   });
                   
               }
           }
       });
   };
   
   //检测差异
   myBasicBank.check = function(){
	  	$.messager.confirm('确认',' 【${unified_time.remark }:${unified_time.pval }】需要探测很久,是否检测差异？',function(t){    
	   	    if (t){    
	   	    	$.post("myBasicBank/check.do?endTime=${unified_time.pval}",function(dd){
	   	    	 	//$.messager.alert('提示','正在检测...'); 
        			//$('#mybankdg').datagrid('reload');    // reload the user data
        		},"json");
	   	    }    
	   	});
   };
   
 //合并方案
  myBasicBank.checkMerge = function(){
	  $.messager.confirm('确认','需要探测很久,是否检测？',function(t){    
	   	    if (t){    
	   	    	$.post("myBasicBank/checkMerge.do",function(dd){
	   	    	 	//$.messager.alert('提示','正在检测...'); 
      			 
      		},"json");
	   	    }    
	   	});
 };
 
//服务检测
 myBasicBank.checkMonitor = function(){
	  $.messager.confirm('确认','需要探测很久,是否检测服务？',function(t){    
	   	    if (t){    
	   	    	$.post("myBasicBank/checkMonitor.do",function(dd){
	   	    	 	//$.messager.alert('提示','正在检测...'); 
     			 
     		},"json");
	   	    }    
	   	});
};
 
 
 //查看脚本详细
   myBasicBank.viewDetail=function(checkContent){
	   if($.trim(checkContent)&&"undefined"!=checkContent){
		   //console.log(dd);
			 $('#detailBankDg').dialog('open').dialog('setTitle','详细:');
			 
			 $("#fdetailBank").html(checkContent);
	   }else{
		   $.messager.alert('提示','没有发现差异,检测内容为空...'); 
	   }
	  
  };
  
  


  //登录信息
    myBasicBank.viewLogin=function(val){
	  $.post("myBasicBank/findById.do?id="+val,function(data){
		  $('#detailBankDg').dialog('open').dialog('setTitle','详细:');
			 $("#fdetailBank").html(data.content);
	  },"json");
 	   
   };
  
  //添加
  myBasicBank.addOpen=function(){
	  $('#dlgbank').dialog('open').dialog('setTitle','新增' );
      $('#fmbank').form('reset');
      
  };
  //保存
  myBasicBank.save=function(){
         $('#fmbank').form('submit',{
             url: 'myBasicBank/add.do',
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
                 	
                     $('#dlgbank').dialog('close');        // close the dialog
                     $.messager.show({
                         title: 'OK',
                         msg: "成功",
                         width:'300px',
                         timeout:5000,
                         height:'250px'
                     });
                     myBasicBank.fsearch();    // reload the user data
                 }
             }
         });
     };

     //银行小档案
     myBasicBank.viewbankInfo=function(val){
   	  $.post("myBasicBank/findById.do?id="+val,function(data){
   		  $('#dlgbankinfo').dialog('open').dialog('setTitle','银行小档案');
   		 $('#fmbankinfo').form('reset');
         $('#fmbankinfo').form("load",data.row);
   	  },"json");
     };
    </script>
  
</body>
</html>