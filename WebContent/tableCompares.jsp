<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>表结构比较</title>
	
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
        <form id="ckfm" method="post" novalidate>
            <div class="fitem" style="padding-top: 20px;">
                <label>连接地址:</label>
                <input name="url" id="url" class="easyui-textbox"   required="true" value="123.207.118.153:1521:ntest" />
                  <span style="color: red">ip:port:sid</span> 
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>&nbsp;&nbsp;用户名:</label>
                <input name="name" id="name" class="easyui-textbox"    required="true"  value="zhangzhengyi" data-options="prompt:'用户名',iconWidth:38">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>&nbsp;用户密码:</label>
                <input name="pwd"  id="pwd" class="easyui-textbox"  type="password"   required="true" value="zhangzhengyi" data-options="prompt:'密码',iconCls:'icon-lock',iconWidth:38">
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>用户空间:</label>
                <input name="schame" id="schame" class="easyui-textbox"    required="true"  value="zhangzhengyi" data-options="prompt:'实列名',iconWidth:38">
            </div>
             <div class="fitem" style="padding-top: 20px;">
                <label>截止时间:</label>
                <input name="endTime" id="eeendTime" class="easyui-datebox"  value="${upload_time.pval }" />
                <p > <span style="color: red">${unified_time.remark }:${unified_time.pval }</span></p>
                <p > <span style="color: red">截止时间必填(当天日期表示最新即db结构比较end_time不带条件)</span></p>
                 <p ><span style="color: red">注意:用户空间schame每次比较会</span> <span style="color: blue">缓存十分钟左右</span></p>
            </div>
            <div class="fitem" style="padding-top: 20px;">
                <label>快速选择:</label>
                <select id="quickSel"  class="" name="quickSel"  required="true"  >
                  <!-- <option value="" selected="selected">--</option>
                 	<option value="0" >测试库</option>
                 	<option value="1">私有云专属库1</option>
                 	<option value="2">私有云专属库2</option>
                 	<option value="3">私有云网络库</option> -->
                 </select>
               
            </div>
            <div class="fitem" style="padding-top: 20px;" >
               <a id="checkA" href="javascript:void(0)" style="padding-left: 10px;" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tableCompares.check()">结构比对</a>
               <a id="clearA" href="javascript:void(0)" style="padding-left: 10px;" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tableCompares.clear()">清除缓存</a>  
            </div>
            
           
            
        </form>
       
            
          <div>
           <p style="color: blue"> <span style="color: red">红色false</span>表示表有缺失，<span style="color: yellow">黄色false</span>表示该表有字段缺失(可进一步查看表结构差异)</p>
          <p style="color: blue"> <span style="color: gray">灰色false</span>表示序列有缺失，<span style="color: pink">粉红色false</span>表示表约束不一致</p>
          </div>
    </div>   
      
    <div data-options="region:'north',title:'功能区',split:true" style="height:60px;">   
      <div id="toolbar1">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tableCompares.viewTable()">表结构差异</a>
      
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tableCompares.createTable()">创建脚本</a>
      
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="tableCompares.createDDLTableWin()">创建补丁DDL(先执行创建脚本)</a>
      
    </div>
    </div>
    <div data-options="region:'center',title:'列表区'" style="padding:5px;background:#eee;">
    	<table id="tablecomsdg"  class="easyui-datagrid" style="height: 98%;"
             pagination="fales"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,singleSelect:true,remoteSort:false" >
        <thead>
            <tr>
            	<th field="id" width="50"  data-options="checkbox:true">表名</th>
                <th field="name" width="50">表名name</th>
                <th field="code" width="50" sortable="true">表名code</th>
                 <th field="tableCompare" width="50"  sortable="true" >表差异</th>
                 <th field="colCompare" width="50"  sortable="true" >列差异</th>
                  <th field="checkSame" width="50"  sortable="true" >约束差异</th>
                   <th field="seqSame" width="50"  sortable="true" >序列差异</th>
                  
            </tr>
        </thead>
    </table>
    
    
    </div>   
</div> 

    <!-- 查看表结构差异 -->
     <div id="ddcom"> </div>
      
      <!-- 创建补丁ddl弹出框 -->  
     <div id="ddlWin" class="easyui-dialog"  style="padding:10px 20px; width:500px;height:200px;"    
            closed="true" buttons="#dlg-ddlWinbuttons" modal="true" >
        <div class="ftitle"></div>
        <form id="fmddlWin" method="post"   novalidate>
           <div class="fitem">
                <label>选择需要创建的类型:</label>
                <input type="checkbox" name="genTypes"      value="0"  checked="true" />表
                  <input type="checkbox" name="genTypes"    value="1"  checked="true"  />列
                   <input type="checkbox" name="genTypes"      value="2"  checked="true" />约束
                  <input type="checkbox" name="genTypes"    value="3"  checked="true"  />序列
                  
            </div> 
              
        </form>
        
        
    </div>
    <div id="dlg-ddlWinbuttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="tableCompares.createDDLTable()" style="width:90px" >开始生成</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#ddlWin').dialog('close')" style="width:90px">取消</a>
    </div>
    
    <script type="text/javascript">
     var tableCompares={};
     tableCompares.dbtypes=null;
     
     $(function(){
    	 
     	$.post("dbType/findAll.do",{},function(json){
     		 
     		var html = "";
     		var arrary = json.rows;
     		tableCompares.dbtypes = arrary;
     		for(var i=0,len=arrary.length;i<len;i++){
     			var db = arrary[i];
     			html += " <option type='radio'  value='"+db.type+"' >" +db.name+"</option>";
     			
     		}
     		$("#quickSel").html(html);
     	},"json");
     	
        //快速选择框
       	$('#quickSel').change(function(){
       		var val  = this.value;
       		tableCompares.quickSel(val);
       	}); 
     	
     });
     
    //表结构差异A|0缺表 B|1缺列 C|2缺约束  D|3缺序列
    var compareTables = "";
   //是否差异提醒
    var compareTableFlag = false;
    
	
    
    $('#tablecomsdg').datagrid({   
	    rowStyler:function(index,row){   
	        if (!row.tableCompare){   
	            return 'background-color:red;';   
	        }
	        if (!row.colCompare){   
	            return 'background-color:yellow;';   
	        }  
	        if (!row.checkSame){   
	            return 'background-color:pink;';   
	        }  
	        if (!row.seqSame){   
	            return 'background-color:gray;';   
	        }  
	    }   
	});
    
    //清楚缓存
    tableCompares.clear=function(){
    	 var schame = $('#schame').textbox('getValue');
    	 $.post("dbs/clear.do?keys="+schame,function(data){
    		 $.messager.alert('警告',data.result); 
    		 //表结构差异
		     compareTables = "";
     	},"json");
    };
    //比对表
     tableCompares.check=function(){
    	 //是否差异提醒
	   	compareTableFlag = false;
	   	 //表结构差异
	     compareTables = "";
	     $.messager.progress({ 
   	       title: '提示', 
   	       msg: '比对中，请稍候……', 
   	       text: '' 
   	    });
	   	$('#ckfm').form('submit',{
	           url: "dbs/findAllCompare.do",
	           onSubmit: function(){
	        	   //先刷新tablecomsdg
	        	   //$('#tablecomsdg').datagrid('loadData',[]);
	        	   var flag = $(this).form('validate');
	        	   if(flag){
	        		   //进度条
	        		   	$("#ckfm :input").attr("readonly", "readonly");
	        		     //掩藏比对工具按钮
	        		   	 $("#checkA").hide();
	        		   	 $("#clearA").hide();
	        		   	
	        	   }else{
	        		   $.messager.progress('close');	// 如果提交成功则隐藏进度条
	        	   }
	               return flag;
	           },
	           error: function (textStatus) {
	                //console.error(textStatus);
	                $.messager.alert('警告',textStatus+"....."); 
	            },
	            complete: function (XMLHttpRequest,status) {
	                if(status == 'timeout') {
	                   
	                    $.alert("网络超时，请等待后 再比较", function () {
	                    	$.messager.progress('close');	// 如果提交成功则隐藏进度条
	                    });
	                }
	            },
	           success: function(result){
	            
	             var result = eval('('+result+')');
	             $.messager.progress('close');	
	              $("#ckfm :input").removeAttr("readonly");
 	              //显示比对工具按钮
 	             $("#checkA").show();
 	       	 	$("#clearA").show();
	             if(result.flag){
	            	 var tables = result.rows;
	 	         	for(var i=0;i<tables.length;i++){
	 	         		var table = tables[i];
	 	         		if(!table.tableCompare){//缺表
	 	         			 //表结构差异表结构差异A|0缺表 B|1缺列 C|2缺约束  D|3缺序列
	 	                    compareTables +=table.code+"|0,";
	 	                    compareTableFlag = true;
	 	         		}else if(!table.colCompare){//缺列
	 	         			compareTables +=table.code+"|1,";
	 	         			  compareTableFlag = true;
	 	         		}
	 	         		else if(!table.checkSame){//缺约束
	 	         			 compareTables +=table.code+"|2,";
	 	         			  compareTableFlag = true;
	 	         		}
	 	         		else if(!table.seqSame){//缺序列
	 	         			compareTables +=table.code+"|3,";
	 	         			  compareTableFlag = true;
	 	         		}
	 	         	}
	 	         	//console.log(compareTables);
	 	            $('#tablecomsdg').datagrid('loadData',tables);
	 				if(compareTableFlag){
	 					$.messager.alert('警告','与标准库有差异,请具体查看...'); 
	 				}else{
	 					$.messager.alert('提示','恭喜,数据库是最新的啦!!!'); 
	 				}
	             }else{
	            	 $.messager.alert('提示',result.result); 
	             }
	         	
	           }
	       },'json');
   };
    
    //表结构差异
     tableCompares.viewTable=function(){
            var row = $('#tablecomsdg').datagrid('getSelected');
            var url = $('#url').textbox('getValue');	// 禁用只读模式
           
            var name =$('#name').textbox('getValue');
            var pwd = $('#pwd').textbox('getValue');
            var schame = $('#schame').textbox('getValue');
            var eeendTime = $('#eeendTime').textbox('getValue');
            if (row){ 
         
            	$('#ddcom').dialog({
            	    title: row.code,
            	    width: 850,
            	    height: 600,
            	    closed: false,
            	    cache: false,
            	    href:  'tableCompare.jsp?table='+row.code+'&url='+url+'&name='+name+'&pwd='+pwd+'&schame='+schame+"&endTime="+eeendTime,
            	    modal: true
            	});
            }else{
            	 
            	 $.messager.alert('警告','无法创建...,请选比对后再创建');  
            }
        };
       
        //创建脚本
         tableCompares.createTable=function(){
        	$.messager.confirm('确认','在您确认创建脚本前,1.是否需要清除缓存,2.请前往【<font color="red">增量下载记录</font>】菜单查看是否已有下载记录时间区间,<font color="red">以免重复下载</font>？',function(r){    
        	    if (r){    
        	    	    var url = $('#url').textbox('getValue');	// 禁用只读模式
        	            var name =$('#name').textbox('getValue');
        	            var pwd = $('#pwd').textbox('getValue');
        	        	var schame = $('#schame').textbox('getValue');
        	            var eeendTime = $('#eeendTime').textbox('getValue');
        	        	 if($.trim(schame)&&$.trim(compareTables)){
        	        		 $('#tablecomsdg').datagrid('loading');
        	        		
        	        		  	$("#ckfm :input").attr("readonly", "readonly");
        	        		  //掩藏比对工具按钮
        	        		   	 $("#checkA").hide();
        	        			 $("#clearA").hide();
        	        		 $.post("dbs/generateScript.do",{
        	        			 "url":url,
        	        			 "name":name,
        	        			 "pwd":pwd,
        	        			 "schame":schame,
        	        			 "tables":compareTables,
        	        			 "endTime":eeendTime
        	        		 },function(data){
        	        			 $('#tablecomsdg').datagrid('loaded');
        	        			
        	        			  $("#ckfm :input").removeAttr("readonly");
        	                      //掩藏比对工具按钮
        	                     $("#checkA").show();
        	                	 $("#clearA").show();
    	        				 //表结构差异
    	        			     compareTables = "";
    	        				 if(data.flag){
    	        					 $.messager.progress({ 
    	        				   	       title: '提示', 
    	        				   	       msg: '脚本创建中，请稍候……', 
    	        				   	       text: '' 
    	        				   	    });
    	        				 }else{
    	        					 $.messager.alert('提示',data.result);
    	        				 }
        	        		 },"json");
        	        	 }else{
        	        		 $.messager.alert('警告','无法创建...,请选比对后再创建'); 
        	        	 }
        	    }    
        	});
        	
        };
        
        //创建补丁DDL脚本Win
        tableCompares.createDDLTableWin=function(){
        	$('#ddlWin').dialog('open').dialog('setTitle','创建补丁DDL脚本');
        };
        //创建补丁DDL脚本
         tableCompares.createDDLTable=function(){
        	$.messager.confirm('确认','创建补丁DDL脚本之前,是否需要清除缓存',function(r){    
        	    if (r){    
        	    	    var url = $('#url').textbox('getValue');	// 禁用只读模式
        	            var name =$('#name').textbox('getValue');
        	            var pwd = $('#pwd').textbox('getValue');
        	        	var schame = $('#schame').textbox('getValue'); 
        				//需要生成的类型
        	        	var genTypes_array=new Array();  
        	        	$('#fmddlWin').find("input[name='genTypes']:checked").each(function(){  
        	        		genTypes_array.push($(this).val());//向数组中添加元素  
        	        	});  
        	        	var genTypes=genTypes_array.join(',');//将数组元素连接起来以构建一个字符串  
        	         
        	        	var eeendTime = $('#eeendTime').textbox('getValue');
        	        	 if($.trim(schame)&&$.trim(compareTables)){
        	        		 $('#tablecomsdg').datagrid('loading');
        	        			$("#ckfm :input").attr("readonly", "readonly");
          	        		  //掩藏比对工具按钮
          	        		   	 $("#checkA").hide();
          	        			 $("#clearA").hide();
        	        		 $.post("dbs/generateTempDDLScript.do",{
        	        			 "url":url,
        	        			 "name":name,
        	        			 "pwd":pwd,
        	        			 "schame":schame,
        	        			 "tables":compareTables,
        	        			 "endTime":eeendTime,
        	        			 "genTypes":genTypes
        	        		 },function(data){
        	        			 //$('#ddlWin').dialog('close');
        	        			 $('#tablecomsdg').datagrid('loaded');
        	        			 $("#ckfm :input").removeAttr("readonly");
       	                      	//掩藏比对工具按钮
       	                     	$("#checkA").show();
       	                	    $("#clearA").show();
        	        			 if (data.flag){
        	        				 $.messager.alert('提示',data.result);
        	        				 //表结构差异
        	        			     compareTables = "";
        	                          
        	                     }else{
        	                    	 $.messager.show({
        	                             title: 'Error',
        	                             msg: data.result,
        	                             width:'300px',
        	                             timeout:10000,
        	                             height:'250px'
        	                         });
        	                     } 
        	        		 },"json");
        	        	 }else{
        	        		 $.messager.alert('警告','无法创建...,请选比对后再创建'); 
        	        	 }
        	    }    
        	});
        };
      
        
     

       //选择
        tableCompares.quickSel=function(value){
       	 if(tableCompares.dbtypes){
        		for(var i=0,len=tableCompares.dbtypes.length;i<len;i++){
        			var db = tableCompares.dbtypes[i];
        			if(value==db.type){
        				$('#ckfm').form('load',{
       					url:db.url,
       					name:db.userName,
       					pwd:db.pwd,
       					schame:''				 
       				});
        			}
        		}
        	}

	 
       };

       
    </script>
   
</body>
</html>