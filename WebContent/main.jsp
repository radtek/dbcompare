<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/mytag" prefix="tag" %> 

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sessionId = session.getId();
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>sql管理系统</title>
	
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="jquery-easyui-1.4.1/themes/icon.css">
	<script type="text/javascript" src="jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="jquery-easyui-1.4.1/TableShow.js"></script>
	<script type="text/javascript" src="jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	 
	  <script type="text/javascript"> 
	  var userName = '${user.id }';
	  var host = document.domain;
	 
	 function initWs() {
		    if (window.WebSocket) {
		        //websocket = new WebSocket(encodeURI('ws://'+host+':${websocket_port}'));
		        websocket = new WebSocket(encodeURI('ws://${websocket_port}.qcloudtest.swiftpass.cn'));
		        websocket.onopen = function() {
		            console.log('已连接');
		            websocket.send("online"+userName);
		            
		        };
		        websocket.onerror = function() {
		            console.log('连接发生错误');
		        };
		        websocket.onclose = function() {
		            console.log('已经断开连接');
		            initWs();
		        };
		        // 消息接收
		        websocket.onmessage = function(message) {
		            console.log(message);
		            if("userremind"==message.data){
		            	userremind();
		            } 
		            else if("cmppareremind"==message.data){
		            	cmppareremind();
		            }
		            else if("aysncDownError"==message.data){
		            	aysncDownError();
		            }
		            
		        };
		    } else {
		        alert("该浏览器不支持websocket。<br/>建议使用高版本的浏览器，<br/>如 IE10、火狐 、谷歌  、搜狗等");
		    }
		}
	 initWs();
        // 提醒
      function userremind(){
    	   $.messager.alert('提示','您的脚本已经生成,请到增量下载记录下载脚本...'); 
    	   $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
    	   //刷新
    	   if(typeof myDowns!='undefined'){
    		   myDowns.fsearch();
    	   }
    	   
      }
      // 提醒
      function cmppareremind(){
    	   $.messager.alert('提示','银行库比较完成,请刷新查看结果...'); 
    	   //刷新
    	   if(typeof myBasicBank!='undefined'){
    		   myBasicBank.fsearch();
    	   }
    	  
      }
      
      // 提醒
      function aysncDownError(){
    	   $.messager.alert('错误提示','某些脚本文件不存在,不能生成...'); 
    	   $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
    	  
      }
    
 </script>
 
</head>

<body class="easyui-layout"  >
  <div id="cc" class="easyui-layout" style="width:99%;height:99%;">   
    <div data-options="region:'north',border:false" style="height:50px;">
    	<img alt="logo" src="images/logo.png" style="padding:  0 5px 5px 0 ;"/>
    	<p style="float: right;padding:  0 0 0 0 ;" >${user.userName } <td>  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="floginOut()">登出</a> </td>
         </p>
    </div>   
    <div data-options="region:'west',title:'菜单',split:true" style="width:200px;">
    	<div id="aa" class="easyui-accordion" data-options="fit:true,border:false">  
    	    <div title="测试专用" data-options="iconCls:'icon-help'" style="padding:10px;"> 
    		  <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('内容比较',8)">内容比较</a> <br/>  <br/>      
    		</div> 
    		 
    		<div title="表管理" data-options="iconCls:'icon-save',selected:true" style="overflow:auto;padding:10px;">   
		        <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('基础表管理',1)">基础表管理</a> <br/> 
		        <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('基础表维护',2)">基础表维护</a> <br/>
		        <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('表结构比较',3)">表结构比较</a> <br/> 
		        <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('增量下载记录',4)">增量下载记录</a> <br/>  
		         <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('标准库下载',5)">标准库下载</a> <br/>  
		           <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('银行列表',7)">银行列表</a> <br/>  
    		</div>   
    		
    		
    		 <div title="配置模板管理" data-options="iconCls:'icon-tip'" style="padding:10px;"> 
    		
        		   <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('模板记录',41)">模板记录</a> <br/>   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('模板比较',43)">模板比较</a> <br/>   
        		   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('模板配置',42)">模板配置</a> <br/>   
        		   
    		</div> 
    		
    		 <div title="加解密工具" data-options="iconCls:'icon-tip'" style="padding:10px;"> 
    		
        		   <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('清分文件加解密',61)">清分文件加解密</a> <br/>   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('敏感信息加解密',62)">敏感信息加解密</a> <br/>   
        		   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('数据库加解密',63)">数据库加解密</a> <br/>   
        		   <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('通用AES加解密',64)">通用AES加解密</a> <br/>   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('sql文件null属性过滤',65)">sql文件null属性过滤</a> <br/>   
        		   
    		</div> 
    		
    		 <div title="数据迁移管理" data-options="iconCls:'icon-tip'" style="padding:10px;"> 
    		
        		   <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('数据库设置',51)">数据库设置</a> <br/>   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('导出内容',52)">导出内容</a> <br/>   
        		   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('导入内容',53)">导入内容</a> <br/>   
        		   
    		</div> 
    		 
    		 <div title="系统管理" data-options="iconCls:'icon-print'" style="padding:10px;"> 
    		
        		   <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('用户信息',100)">用户信息</a> <br/>   
        		   
        		    <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('参数记录',101)">参数记录</a> <br/>   
        		   <tag:permissiontag>
        		   <a   href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true" onclick="menu('生成用户',102)">生成用户</a> <br/>   
        		    </tag:permissiontag>
    		</div> 
    		     
</div> 
    </div>  
     
    <div data-options="region:'center',border:false,plain:true">
    	<div id="tt" class="easyui-tabs" fit=true>   
		    <div title="首页" style="text-align: center;font-size: 20px;">   
		        <p>欢迎来到wft sql后台管理系统</p>
		       <p> 使用方式:</p>
		      <p>  A.比对增量下载：</p>
		        	<p>针对线上环境:1.表结构比较-》2.输入比对库(可选择时间点),点击比对-》3.创建脚本(查看压缩文件里readme.txt说明是否有漏)</p>
		        	<p>针对测试环境:1.表结构比较-》2.输入比对库(可选择时间点),点击比对-》3.创建脚本-》4.执行后再比对如果还有差异,点击创建DDL补丁脚本</p>
		        <p>B.时间跨度增量</p>
		        	如果记得增量时间，可以直接从基础表维护菜单里点击按创建时间跨度下载脚本</p>
		       <p> C.新银行：</p>
		        	<p>标准库下载-》标准脚本下载    </p>
		        <p> D.测试专用：</p>
		        	<p>内容比较-》输入线上库、本地库,点击比对(可以比对系统参数表、清分日结表、清分类配置表、合并方案表)   </p>
		       <p>ps:比对原理都是通过时间比对出差异,在时间范围区间拉取增量脚本,所以会存在误差   </p>
		    </div>
		    
		</div> 
   </div>
   
    <div data-options="region:'south',border:true" style="height:5px;">
    	   
    </div>
    
       
</div>  
 
  <div id="mm" class="easyui-menu" style="width:120px;">
         <div id="mm-tabclose" data-options="name:1">关闭</div>
        <div id="mm-tabcloseall" data-options="name:2">全部关闭</div>
        <!-- <div id="mm-tabcloseother" data-options="name:3">除此之外全部关闭</div>
        <div class="menu-sep"></div>
        <div id="mm-tabcloseright" data-options="name:4">当前页右侧全部关闭</div>
        <div id="mm-tabcloseleft" data-options="name:5">当前页左侧全部关闭</div> -->

    </div>
    
    
     
	<script type="text/javascript">
	//菜单框
	function menu(title,type){
			if($("#tt").tabs("exists",title)){
				$("#tt").tabs("select",title);
			}else{
				var url = "";
				 
				switch(type){
					case 1:
						url = "standTables.jsp";
						break;
					case 2:
						url = "fileHistorys.jsp";
						break;
					case 3:
						url = "tableCompares.jsp";
						break;
					case 4:
						url = "myDowns.jsp";
						break;
					case 5:
						url = "basicTables.jsp";
						break;
					case 100:
						url = "users.jsp";
						break;
					case 7:
						url = "myBasicBanks.jsp";
						break;
					case 8:
						url = "contentCompares.jsp";
						break;
					case 101:
						url = "myParamters.jsp";
						break;
					case 102:
						url = "usergens.jsp";
						break;
					case 41:
						url = "myBankConfs.jsp";
						break;
					case 42:
						url = "myBankConftpls.jsp";
						break;
					case 43:
						url = "myBankConftplCompares.jsp";
						break;
					case 61:
						url = "codefile.jsp";
						break;
					case 62:
						url = "codesensitive.jsp";
						break;
					case 63:
						url = "codedb.jsp";
						break;
					case 64:
						url = "codeaes.jsp";
						break;
					case 65:
						url = "codesqlnull.jsp";
						break;
					case 51:
						url = "tests.jsp";
						break;
					default:
						url = "";
				}
 
				 openTab(title,url);
			 
			}
		}
	
	/**
	 * 在iFrame中打开一个新tab
	 * @param title
	 * @param href
	 */
	function openTab(title,href){
	    var e = $('#tt').tabs('exists',title);
	    if(e){
	        $("#tt").tabs('select',title);

	        var tab = $("#tt").tabs('getSelected');
	        $('#tt').tabs('update',{
	            tab:tab,
	            options:{
	                title:title,
	                //content:'<iframe name="indextab" scrolling="auto" src="'+href+'" frameborder="0" style="width:800px;height:100%;"></iframe>',
	               href:href,
	                closable:true,
	                selected:true
	            }
	        });
	    }else{
	        $('#tt').tabs('add',{
	            title:title,
	            //content:'<iframe name="indextab" scrolling="auto" src="'+href+'" frameborder="0" style="width:100%;height:100%;"></iframe>',
	             href:href,
	            iconCls:'',
	            closable:true
	        });
	    }
	}

	//登出
	function floginOut(){
		    	$.post('user/login_out.do',function(dd){
		    		if (dd.flag){
		                $.messager.show({
		                    title: 'info',
		                    msg: dd.result,
		                    width:'300px',
		                    timeout:2000,
		                    height:'250px'
		                });
		                window.location='login.jsp';
		    		} 
		    	},"json");
 } 
		
	
 $(document).ready(function () {
	            //监听右键事件，创建右键菜单
	            $('#tt').tabs({
	                onContextMenu:function(e, title,index){
	                    e.preventDefault();
	                    if(index>0){
	                        $('#mm').menu('show', {
	                            left: e.pageX,
	                            top: e.pageY
	                        }).data("tabTitle", title);
	                    }
	                }
	            });
	            //右键菜单click
	            $("#mm").menu({
	                onClick : function (item) {
	                    closeTab(this, item.name);
	                }
	            });
 });
 
 //删除Tabs
 function closeTab(menu, type){
     var allTabs = $("#tt").tabs('tabs');
     var allTabtitle = [];
     $.each(allTabs,function(i,n){
         var opt=$(n).panel('options');
         if(opt.closable)
             allTabtitle.push(opt.title);
     });

     switch (type){
         case 1 :
             var curTabTitle = $(menu).data("tabTitle");
             $("#tt").tabs("close", curTabTitle);
             return false;
         break;
         case 2 :
             for(var i=0;i<allTabtitle.length;i++){
                 $('#tt').tabs('close', allTabtitle[i]);
             }
         break;
         case 3 :
     
         break;
         case 4 :
     
         break;
         case 5 :
     
         break;
     }
     
 }
</script>
</body>
</html>