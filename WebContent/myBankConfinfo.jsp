 <%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 

<body class="easyui-layout" style="height:100%;width: 100%">   
   <div data-options="region:'north',title:'平台',split:true" style="height:10%;">
   
   <div  class="easyui-tabs" id="bankconf-tabs"   data-options=""    >   
    <div title="3.5平台公有云平台"  style="padding:10px; ">   
            
    </div>   
    <div title="支付平台" data-options="" style="padding:10px; ">   
          
    </div>   
    <div title="Spay终端" data-options="" style="padding:10px; ">   
           
    </div>  
      <div title="私有云平台" data-options="" style="padding:10px;">   
        
    </div>   
    
    
</div> 
   
    </div>   
    
    <div data-options="region:'center',title:'center title'" style="background:#eee;height:85%;">
     <table id="bankconf_dg"  class="easyui-datagrid" singleSelect="true" style="height:98%" data-options="onLoadSuccess: ontplLoadSuccess"   >  
        <thead>
            <tr>
            	<th field="moudle" width="10%"  >模块分类</th>
                <th field="project" width="15%"  sortable="true">项目分类</th>
                <th field="desc" width="10%" sortable="true"  >业务描述</th>
                <th field="name" width="15%"  sortable="true">服务名称</th>
                 <th field="ip" width="10%"   sortable="true">应用IP</th>
                <th field="path" width="20%"  >项目部署路径</th>
                  <th field="tname" width=15%  ">包名称</th>
                   <th field="port" width="5%"  >应用端口</th>
            </tr>
        </thead>
        
       </table>
    </div>   
</body>  

   <script type="text/javascript">
 
   $(function(){
	   
	   var tables = {};
	   var bankconf_v1 = {};
	   var bankconf_v2 = {};
	   var bankconf_v3 = {};
	   var bankconf_v4 = {};
	 
	   //远程加载数据
	    function posttpldata(callback) {
	    	 $.post("myBankConf/findById.do?id=${param.id}",function(data){
	  		   tables = $.parseJSON(data.rows.excelJson)['val'];
	  		   bankconf_v1 = {"total":tables['3.5平台公有云平台'].length,"rows":tables['3.5平台公有云平台']};
	  		   bankconf_v2 = {"total":tables['支付平台'].length,"rows":tables['支付平台']};
	  		   bankconf_v3 = {"total":tables['Spay终端'].length,"rows":tables['Spay终端']};
	  		   bankconf_v4 = {"total":tables['私有云平台'].length,"rows":tables['私有云平台']};
	  		 console.log(bankconf_v1); console.log(bankconf_v2); console.log(bankconf_v3); console.log(bankconf_v4);
	  		   if(callback){
	  			 callback();
	  		   }
	  		 $('#bankconf_dg').datagrid('loadData',bankconf_v1);

	  	   },"json");
	    };
	  
	    
	   //切换tabs
	    function tabstplchange(title){ 
	    	$('#bankconf_dg').datagrid('loadData',{total:0,rows:[]}); 
		        if(title=='3.5平台公有云平台'){
		        	  
		        	  $('#bankconf_dg').datagrid('loadData',bankconf_v1);
		        }else if(title=='支付平台'){
		        	 
		        	 $('#bankconf_dg').datagrid('loadData',bankconf_v2);
		        }
				else if(title=='Spay终端'){
					 
					 $('#bankconf_dg').datagrid('loadData',bankconf_v3);
		        }  
				else if(title=='私有云平台'){
					 $('#bankconf_dg').datagrid('loadData',bankconf_v4);
				}  
		   }   
	    
	
	   //开始加载
	    posttpldata(function(){
	    	 $('#bankconf-tabs').tabs({
		    	 
		 	    	onSelect: function(title){
		 	    	
		 	    		tabstplchange(title);
		 	    	  }
	 	    	});
	    });
	   
    
   });  
    
 
   //合并单元格
   function ontplLoadSuccess(data){
	 
	       var mark=1;
	       var projectmark=1;
	  	for (var i=1; i <data.rows.length; i++) {
	  		if (data.rows[i]['moudle'] == data.rows[i-1]['moudle']) {
	  			mark += 1;
	  			$(this).datagrid('mergeCells',{
	  				index: i+1-mark,
	  				field: 'moudle',
	  				rowspan:mark 
	  			});
	  		}else{
	  			mark=1; 
	  		}
	  		
	  		if (data.rows[i]['project'] == data.rows[i-1]['project']) {
	  			projectmark += 1;
	  			$(this).datagrid('mergeCells',{
	  				index: i+1-projectmark,
	  				field: 'project',
	  				rowspan:projectmark
	  			});
	  		}else{
	  			projectmark=1; 
	  		}
		}
  	 
	  	
	  	var trs = $(this).prev().find('div.datagrid-body').find('tr');
       for(var j = 0; j < trs.length; j++){
    	   for (var i = 0; i < trs[j].cells.length; i++) {
               if ($.trim(trs[j].cells[i].firstChild.innerHTML) ==''){
            	   trs[j].cells[i].style.cssText = 'background:#f00;color:#fff';
               }
                  
           }
       }
       
   }
   
	 
   </script>
</body>
</html>