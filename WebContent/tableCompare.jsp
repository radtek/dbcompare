<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
    <table id="comdg"  class="easyui-datagrid" style="height: 550px;"
            url="dbs/findAllCompareCloumns.do?table=${param.table}&url=${param.url}&name=${param.name}&pwd=${param.pwd}&schame=${param.schame}&endTime=${param.endTime}"
            toolbar="#toolbar" pagination="true"  pageSize="200"  pageList="[100,200,300,400,500]"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,remoteSort:false,rowStyler:function(index,row){   
   	        if (!row.compare){   
   	            return 'background-color:red;';   
   	        }}" > 
   	    
        <thead>
            <tr>
            	
                <th field="name" width="100">中文名</th>
                <th field="code" width="50" sortable="true">列名</th>
                <th field="dataType" width="50">数据类型</th>
                   <th field="dataLen" width="50">数据长度</th>
                <th field="primary" width="50" formatter="tableCompare.fprimary">是否主键</th>
              
                 <th field="mandatory" width="50" formatter="tableCompare.fmandatory">是否允许null</th>
                  <th field="lastAnalyzed" width="50"  >创建时间</th>
                  <th field="compare" width="50"  sortable="true"  >基准差异</th>
               
            </tr>
        </thead>
    </table>
   <script type="text/javascript">
   
   	var tableCompare = {};
   	//是否主键
      tableCompare.fprimary=function(value,row,index){
			if ('TRUE'==value){
				return "<font color='blue'>"+value+"</font>";
			} else {
				return value;
			}
      };
  	
      //是否允许null
       tableCompare.fmandatory=function(value,row,index){
			if ('FALSE'==value){
				return "<font color='blue'>"+value+"</font>";
			} else {
				return value;
			}
    };
   
      </script>
</body>
</html>