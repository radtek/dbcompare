<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
   <table id="contentgrid"></table> 
   <script type="text/javascript">
   
 
   $(function(){
	   var options={}; 
	   options.columns=[[]];
	   $.post("content/findAllCloumns.do?table=${param.table}&url=${param.url}&name=${param.name}&pwd=${param.pwd}&schame=${param.schame}",function(data){
		   
		   if(!!data.rows){
			   $.each(data.rows,function(i,obj){
				  // console.log(obj);
				   options.columns[0].push({field:obj.code,title:obj.name,width:"10%"});
			   });
			  // console.log(JSON.stringify(options.columns));
			  getData();
		   }
	   },"json");
	   
	   function getData(){
		   //初始化  
	       $("#contentgrid").datagrid({  
	           type: 'POST',  
	           nowrap: false,  
	           striped: true,  
	           fit:true,  
	           width:1024,  
	           height:500,  
	           url:'content/findAlldatas.do?table=${param.table}&url=${param.url}&name=${param.name}&pwd=${param.pwd}&schame=${param.schame}',  
	           pageSize:50,  
	           remoteSort: false,  
	           pagination:true,  
	           rownumbers:true,  
	           singleSelect:true, 
	           columns:options.columns 
	           
	           
	       });   
	         
	   }
     
       
   });  
    
         
     
   
   
   </script>
</body>
</html>