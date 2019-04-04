<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 
    <table id="tabledg"  class="easyui-datagrid" style="height: 600px;"
            url="dbs/findAllCloumns.do?table=${param.table}"
            toolbar="#toolbar" pagination="true"  pageSize="200"  pageList="[100,200,300,400,500]"
            rownumbers="true" fitColumns="true" singleSelect="true"
            data-options="fit:false,border:false,remoteSort:false" >
        <thead>
            <tr>
            	
                <th field="name" width="100">中文名</th>
                <th field="code" width="50" sortable="true">列名</th>
                <th field="dataType" width="50">数据类型</th>
                 <th field="dataLen" width="50">数据长度</th>
				 <th field="scale" width="50" formatter="standTable.fscale">数据精度</th>
                <th field="primary" width="50" formatter="standTable.fprimary">是否主键</th>
                 <th field="mandatory" width="50" formatter="standTable.fmandatory">是否允许null</th>
                  <th field="lastAnalyzed" width="50"  >创建时间</th>
            </tr>
        </thead>
    </table>
     
      <script type="text/javascript">
      var standTable={};
      //是否主键
      standTable.fprimary=function(value,row,index){
			if ('TRUE'==value){
				return "<font color='blue'>"+value+"</font>";
			} else {
				return value;
			}
      };
      //是否允许null
       standTable.fmandatory=function(value,row,index){
			if ('FALSE'==value){
				return "<font color='blue'>"+value+"</font>";
			} else {
				return value;
			}
    };
	 // 
      standTable.fscale=function(value,row,index){
			if (value>0){
				return "<font color='blue'>"+value+"</font>";
			} else {
				return '';
			}
      };
      </script>
      
</body>
</html>