package com.wft.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.exception.AppException;
import com.wft.util.SystemMessage;
 
/**
 * @author admin
 * 注意查询结果有缓存
 * 用于批量 多线程
 */
@SuppressWarnings("unchecked")
public class DBUtilBatch {

	private final static Logger log = Logger.getLogger(DBUtilBatch.class);
	
    static class Ora{
        static final String DRIVER_CLASS = SystemMessage.getString("driverClassName");
        static final String DATABASE_URL = SystemMessage.getString("url");
        static final String DATABASE_USER =SystemMessage.getString("username");
        static final String DATABASE_PASSWORD =SystemMessage.getString("password");
      

    }
 
    private  Connection con = null;
    private volatile    boolean init  = false;
    
    public DBUtilBatch() {
    	getOracleConnection();
	}
    private  void getOracleConnection() {
        try {
        	
        	if(!init){
        		 Class.forName(Ora.DRIVER_CLASS);
                 init = true;
        	}
        	 con=DriverManager.getConnection(Ora.DATABASE_URL,Ora.DATABASE_USER,Ora.DATABASE_PASSWORD);
         
           
        } catch (Exception ex) {
        	ex.printStackTrace();
        	//init  = false;
            log.info(ex.getMessage());
            throw new AppException("数据库连接异常:"+ex.getMessage());
        }
        
    }
 
   
   
   
    
    
    /***
     * 获取所有的表明和表下面所有列 及其相关序列和约束
     * startTime 2017-05-09 23:59:59 YYYY-MM-DD HH24:MI:SS
     * endTime 2017-05-09 23:59:59 YYYY-MM-DD HH24:MI:SS
     * @throws SQLException
     */
    public   Map<String,Table> getAllTableNamesAndCols(String startTime,String endTime,List<String> tablelist) throws SQLException{
    	Object obj = CacheUtil.getInstance().get("allTableNamesAndCols"+startTime+endTime+ArrayUtils.toString(tablelist) );
    	if(obj!=null){//缓存
    		 log.info("getAllTableNamesAndCols从缓存中拿");
    		Map<String,Table> results = (Map<String, Table>) obj;
    		return results;
    	}
        getOracleConnection();
        Map<String,Table> maps = new HashMap<String,Table>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
        	String sql=" select  TABLE_NAME ,COMMENTS,TABLE_TYPE　from user_tab_comments ";
            if(tablelist!=null&&tablelist.size()>0){
            	StringBuffer tabwh = new StringBuffer();
            	for(String table:tablelist){
            		tabwh.append("'"+table+"',");
            	}
            	tabwh.append("'S'");
            	sql=" select  TABLE_NAME ,COMMENTS,TABLE_TYPE　from user_tab_comments where TABLE_NAME in ("+tabwh.toString()+") ";
            }
            stmt = con.createStatement();
         
            log.info("getAllTableNamesAndCols sql:"+sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()){
            	if(IgnoreTable.ignoreTableMap.containsKey(rs.getString("TABLE_NAME").toUpperCase())){
            		continue;
            	}
            	if(IgnoreTable.isIgnoreDigitTable(rs.getString("TABLE_NAME").toUpperCase())){
            		continue;
            	}
            	if(!(IgnoreTable.isneedTable(rs.getString("TABLE_NAME").toUpperCase()))){
            		log.info("表名没含有need_table_reg正则 忽视:"+rs.getString("TABLE_NAME").toUpperCase());
            		continue;
            	}
            	Table table = new Table(rs.getString("TABLE_NAME"), rs.getString("COMMENTS"));
            	log.info(table.getName());
            	//表字段
            	/*table.setCols(getCloumns(table.getName(),startTime,endTime));*/
            	table.setCols(getOracleTCloumns(table.getName(),startTime,endTime));
            	//序列
            	table.setSeq(getSeq(table.getName()));
            	//约束
            	table.setConstraints(getConstraints(table.getName()));
            	maps.put(table.getName(), table);
               }//rs.next

        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
        	if(rs!=null){
       		 rs.close();
        	}
	       	if(stmt!=null){
	       		stmt.close();
	        }
		   if(con!=null){
		      	con.close();
		      }
        }
 
        CacheUtil.getInstance().set("allTableNamesAndCols"+startTime+endTime+ArrayUtils.toString(tablelist),maps,7200);
        
        return maps;
    }
    
    //关闭con colse
    public   void close(){
		  try {
			  if(con!=null&&!con.isClosed()){
				  con.close();
				} 
		  }catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		  }
    }
  

    /***
     * 通过某一表获取所有列
     * 查询会缓存Table+"_basic"
     * @throws SQLException
     */
    private   List<Column> getOracleTCloumns(String Table,String startTime,String endTime ) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Table+startTime+endTime+"_basic");
    	if(obj!=null){//缓存
    		 log.info("sysoutOracleTCloumns从缓存中拿");
			List<Column> results = (List<Column>) obj;
    		return results;
    	}
    	 
       
        List<HashMap<String,String>> columns = new ArrayList<HashMap<String,String>>();
        List<Column> cs = new ArrayList<Column>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           StringBuffer sb = new StringBuffer();
           sb.append("select   comments as \"Name\", a.DATA_LENGTH as \"DataLen\", a.column_name \"Code\",a.DATA_TYPE as \"DataType\",a.DATA_SCALE as \"DataScale\", a.last_analyzed as \"lastAnalyzed\",");
           sb.append("  b.comments as \"Comment\",");
           sb.append( "  decode(c.column_name,null,'FALSE','TRUE') as \"Primary\",");
           sb.append( "  decode(a.NULLABLE,'N','FALSE','Y','TRUE','') as \"Mandatory\",");
           sb.append("    '' \"sequence\"");
           sb.append( " from all_tab_columns a,all_col_comments b,(");
           sb.append("   select a.constraint_name, a.column_name");
           sb.append("    from user_cons_columns a, user_constraints b");
           sb.append("     where a.constraint_name = b.constraint_name");
           sb.append("    and b.constraint_type = 'P'");
           sb.append("    and a.table_name = '"+Table.toUpperCase()+"' ) c");
           sb.append("   where ");
           sb.append( "     a.Table_Name=b.table_Name ");
           sb.append("     and a.column_name=b.column_name");
           sb.append("     and a.Table_Name='"+Table.toUpperCase()+"'");
           sb.append("     and a.owner=b.owner ");
           if(StringUtils.isNotBlank(startTime)){
        	   sb.append("  and a.last_analyzed >=TO_DATE('"+startTime+"','YYYY-MM-DD HH24:MI:SS') ");
           }
           if(StringUtils.isNotBlank(endTime)){
        	   sb.append("  and a.last_analyzed <=TO_DATE('"+endTime+"','YYYY-MM-DD HH24:MI:SS') ");
           }
           sb.append("     and a.owner='"+Ora.DATABASE_USER.toUpperCase()+"'");
           sb.append("     and a.COLUMN_NAME = c.column_name(+) order by a.COLUMN_ID");
            
            log.info("StringBuffer:"+sb);
            rs = stmt.executeQuery(sb.toString());
            while (rs.next()){
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("Name", rs.getString("Name"));
                map.put("DataLen", rs.getString("DataLen"));
                map.put("Code", rs.getString("Code"));
                map.put("DataType", rs.getString("DataType"));
                map.put("lastAnalyzed", rs.getString("lastAnalyzed"));
                map.put("Comment", rs.getString("Comment"));
                map.put("Primary", rs.getString("Primary"));
                map.put("Mandatory", rs.getString("Mandatory"));
                map.put("DataScale", rs.getString("DataScale"));
                columns.add(map);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
        	if(rs!=null){
       		 rs.close();
        	}
	       	if(stmt!=null){
	       		stmt.close();
	        }
	        
		   
           
        }
 
        log.info("getOracleTCloumns: "+Table+" "+columns);
        // 输出
        for(HashMap<String,String> map : columns){
            String Name = map.get("Name");
            String DataLen = map.get("DataLen");
            String Code = map.get("Code");
            //String Comment = map.get("Comment");
            String DataType = map.get("DataType");
            String lastAnalyzed = map.get("lastAnalyzed");
            String Primary = map.get("Primary");
            String Mandatory = map.get("Mandatory");
            //String sequence = map.get("sequence");
            String DataScale = map.get("DataScale");
            Integer scale  = null;
            if(StringUtils.isNotBlank(DataScale)){
            	scale = Integer.valueOf(DataScale);
            }
            Column column =  new Column(Name,Code,DataType,Primary,Mandatory,DataLen,scale);
            column.setLastAnalyzed(lastAnalyzed);
            cs.add(column);
        }
        
        CacheUtil.getInstance().set(Table+startTime+endTime+"_basic",cs);
        
        
        return cs;
    }
 
    /***
     * 通过某一表获取序列信息
     * 不能单独调用
     * @throws SQLException
     */ 
    private   List<Constraints> getConstraints(String Table)throws SQLException {
    	Object obj = CacheUtil.getInstance().get(Table+"_constraints");
    	if(obj!=null){//缓存
    		 List<Constraints> results = ( List<Constraints>) obj;
    		 log.info("getConstraints从缓存中拿");
    		return results;
    	}
        Map<String,Constraints> mapcs =   new HashMap<String,Constraints>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           StringBuffer sql = new StringBuffer();
           sql.append("select CU.constraint_name,CU.table_name,CU.column_name,au.CONSTRAINT_TYPE,au.SEARCH_CONDITION from user_cons_columns cu, user_constraints au ");
           sql.append(" where cu.constraint_name=au.constraint_name  ");
           sql.append(" and cu.table_name= '"+Table.toUpperCase()+"'");
          
            log.info(sql);
            rs = stmt.executeQuery(sql.toString());
           
            // 获取数据 
            while (rs.next()) {
             
            	String constraintName = rs.getString("constraint_name") ;
            	String tableName = rs.getString("table_name") ;
            	String columnName = rs.getString("column_name") ;
            	String constraintType = rs.getString("CONSTRAINT_TYPE") ;
            	String searchCondition = rs.getString("SEARCH_CONDITION") ;
            	Constraints constraints = mapcs.get(constraintName);
            	if(constraints==null){//约束信息数据库是分开的，需要通过constraint_name名字组合
            		constraints = new Constraints();
            		constraints.setConstraintName(constraintName);
            		constraints.setTableName(tableName);
            		constraints.addColumnNames(columnName);
            		constraints.setConstraintType(constraintType);
            		constraints.setSearchCondition(searchCondition);
            		mapcs.put(constraintName, constraints);
            	}else{
            		constraints.addColumnNames(columnName);
            	}
            }
            
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
        	if(rs!=null){
       		 rs.close();
        	}
	       	if(stmt!=null){
	       		stmt.close();
	        }

        }
        
        List<Constraints> results = new ArrayList<Constraints>(mapcs.values()); 
        mapcs.clear();
        mapcs = null;
        CacheUtil.getInstance().set(Table+"_constraints",results);
        return  results;   
	}

    
    /***
     * 通过某一表获取SEQ信息
     * 不能单独调用
    
     * @throws SQLException
     */
	private   String getSeq(String Table)throws SQLException {
		String result = "";
    	Seq seq = new Seq(Table);
    	StringBuffer sql = new StringBuffer();
    	sql.append("Select Sequence_Name as SEQ ");
    	sql.append("From user_sequences ");
    	sql.append("Where Sequence_Name =  '"+seq.getPreSeq()+"'  or Sequence_Name =  '"+seq.getSuffixSeq()+"'");
    	Object obj = CacheUtil.getInstance().get(Table+"_seq_name");
    	if(obj!=null){//缓存
    		result = (String) obj;
    		 log.info("getSequenceByTable从缓存中拿");
    		return result;
    	}
    	 
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           
            log.info(sql);
            rs = stmt.executeQuery(sql.toString());
           
            while (rs.next()) {
            	 
            	result =  rs.getString("SEQ");
            	break;
            }
            
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
        	if(rs!=null){
       		 rs.close();
        	}
	       	if(stmt!=null){
	       		stmt.close();
	        }
	         
        }
 
        CacheUtil.getInstance().set(Table+"_seq_name",result);
        return result;
	}


	/***
     * 通过某一表获取列Code和lastAnalyzed
     * 不能单独调用
     * time 2017-05-09 23:59:59 YYYY-MM-DD HH24:MI:SS
     * @throws SQLException
     */
    private   List<Column> getCloumns(String Table,String startTime,String endTime) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Table+startTime+endTime);
    	if(obj!=null){//缓存
    		 List<Column> results = ( List<Column>) obj;
    		 log.info("getCloumns从缓存中拿");
    		return results;
    	}
        List<Column> cs = new ArrayList<Column>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           StringBuffer sb = new StringBuffer();
           sb.append("select    a.column_name \"Code\", a.last_analyzed as \"lastAnalyzed\"");
           sb.append( " from all_tab_columns a,all_col_comments b,(");
           sb.append("   select a.constraint_name, a.column_name");
           sb.append("    from user_cons_columns a, user_constraints b");
           sb.append("     where a.constraint_name = b.constraint_name");
           sb.append("    and b.constraint_type = 'P'");
           sb.append("    and a.table_name = '"+Table.toUpperCase()+"' ) c");
           sb.append("   where ");
           sb.append( "     a.Table_Name=b.table_Name ");
           sb.append("     and a.column_name=b.column_name");
           sb.append("     and a.Table_Name='"+Table.toUpperCase()+"'");
           sb.append("     and a.owner=b.owner ");
           if(StringUtils.isNotBlank(startTime)){
        	   sb.append("  and a.last_analyzed >=TO_DATE('"+startTime+"','YYYY-MM-DD HH24:MI:SS') ");
           }
           if(StringUtils.isNotBlank(endTime)){
        	   sb.append("  and a.last_analyzed <=TO_DATE('"+endTime+"','YYYY-MM-DD HH24:MI:SS') ");
           }
           sb.append("     and a.owner='"+Ora.DATABASE_USER.toUpperCase()+"'");
           sb.append("     and a.COLUMN_NAME = c.column_name(+) order by a.COLUMN_ID");
            
            log.info("StringBuffer:"+sb);
  
            rs = stmt.executeQuery(sb.toString());
            while (rs.next()){
                Column col = new Column();
                col.setCode(rs.getString("Code"));
                col.setLastAnalyzed(rs.getString("lastAnalyzed"));
                cs.add(col);
              
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }finally{
        	if(rs!=null){
       		 rs.close();
        	}
	       	if(stmt!=null){
	       		stmt.close();
	        }
		 
           
        }
 
        //缓存
        CacheUtil.getInstance().set(Table+startTime+endTime,cs);
        
        return cs;
    }
    
    
   
    public static void main(String[] args) throws Exception{
 
    	/*for(int i=0;i<1;i++){
    		log.info(getOracleTCloumns("CMS_FUNC"));
        	//log.info(getAllTableNamesAndCols());
           
    	}*/
    	 
    	
    	long st = System.currentTimeMillis();
    	//getOracleTCloumns("PAY_DAILY_BILL","2016-01-01 00:00:00","2018-06-12 23:59:59");
    	//System.out.println(getAllTableNamesAndCols(null,null,"CMS_ORG_SECONDARY_PWD"));
    	 
    	//System.out.println(getSequenceByTable("CMS_MERCHANT","zzy"));
    	long st1 = System.currentTimeMillis();
    	log.info(st1-st);
 
    }
 
 
}