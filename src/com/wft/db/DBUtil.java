package com.wft.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.exception.AppException;
import com.wft.util.SystemMessage;
 
/**
 * @author admin
 * 注意查询结果有缓存
 * 不能用于多线程 静态的方法 会影响程序
 */
@SuppressWarnings("unchecked")
public class DBUtil {

	private final static Logger log = Logger.getLogger(DBUtil.class);
	
    static class Ora{
        static final String DRIVER_CLASS = SystemMessage.getString("driverClassName");
        static final String DATABASE_URL = SystemMessage.getString("url");
        static final String DATABASE_USER =SystemMessage.getString("username");
        static final String DATABASE_PASSWORD =SystemMessage.getString("password");
      

    }

    private static BasicDataSource pool = null;
    private static Connection con = null;
   // private volatile static   Boolean init  = false;
    
    //初始化连接池BasicDataSource
    static{
 
   	    pool = new BasicDataSource();// 连接池
        pool.setUsername(Ora.DATABASE_USER);
        pool.setPassword(Ora.DATABASE_PASSWORD);
        pool.setDriverClassName(Ora.DRIVER_CLASS);
        pool.setUrl(Ora.DATABASE_URL);
        pool.setInitialSize(10);
        pool.setMaxIdle(20);
        pool.setTestOnBorrow(true);
        pool.setTestOnReturn(true);
        pool.setTestWhileIdle(true);
        pool.setValidationQuery("SELECT 1 FROM DUAL");
         
        
    }
    private static Connection getOracleConnection() {
        try {
        	/*
        	if(!init){
        		 Class.forName(Ora.DRIVER_CLASS);
                 init = true;
        	}
        	 con=DriverManager.getConnection(Ora.DATABASE_URL,Ora.DATABASE_USER,Ora.DATABASE_PASSWORD);*/
        	 con = pool.getConnection();
        	 
        	 return con;
           
        } catch (Exception ex) {
        	ex.printStackTrace();
        	//init  = false;
            log.info(ex.getMessage());
            throw new AppException("数据库连接异常:"+ex.getMessage());
        }
        
    }
 
   
    /***
     * 通过某一表获取所有列
     * 查询会缓存Table+"_basic"
     * @throws SQLException
     */
    public static List<Column> getOracleTCloumns(String Table,String startTime,String endTime ) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Table+startTime+endTime+"_basic");
    	if(obj!=null){//缓存
    		 log.info("sysoutOracleTCloumns从缓存中拿");
			List<Column> results = (List<Column>) obj;
    		return results;
    	}
    	 getOracleConnection();
        List<LinkedHashMap<String,String>> columns = new ArrayList<LinkedHashMap<String,String>>();
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
            	LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
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
		   	 if(con!=null){
	      		 con.close();
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
            String DataScale = map.get("DataScale");
            Integer scale  = null;
            if(StringUtils.isNotBlank(DataScale)){
            	scale = Integer.valueOf(DataScale);
            }
            //String sequence = map.get("sequence");
            Column column =  new Column(Name,Code,DataType,Primary,Mandatory,DataLen,scale);
            column.setLastAnalyzed(lastAnalyzed);
            cs.add(column);
        }
        
        CacheUtil.getInstance().set(Table+startTime+endTime+"_basic",cs);
        
        
        return cs;
    }
 
    /***
     * 获取所有表名
     * @throws SQLException
     */
    public static List<Column> getAllTableNames() throws SQLException{
    	Object obj = CacheUtil.getInstance().get("basicdb");
    	if(obj!=null){//缓存
    		 log.info("getAllTableNames从缓存中拿");
    		 List<Column> results = (List<Column>) obj;
    		return results;
    	}
    	getOracleConnection();
        List<HashMap<String,String>> columns = new ArrayList<HashMap<String,String>>();
        List<Column> cs = new ArrayList<Column>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            String sql=" select  TABLE_NAME ,COMMENTS,TABLE_TYPE　from user_tab_comments   ";
            log.info("getAllTableNames:"+sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()){
            	if((IgnoreTable.ignoreTableMap.containsKey(rs.getString("TABLE_NAME").toUpperCase()))){
            		continue;
            	}
            	if((IgnoreTable.isIgnoreDigitTable(rs.getString("TABLE_NAME").toUpperCase()))){
            		log.info("表名含有ignore_table_reg正则 忽视:"+rs.getString("TABLE_NAME").toUpperCase());
            		continue;
            	}
            	if(!(IgnoreTable.isneedTable(rs.getString("TABLE_NAME").toUpperCase()))){
            		log.info("表名没含有need_table_reg正则 忽视:"+rs.getString("TABLE_NAME").toUpperCase());
            		continue;
            	}
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("Name", rs.getString("COMMENTS"));
                map.put("Code", rs.getString("TABLE_NAME"));
                map.put("DataType", rs.getString("TABLE_TYPE"));
               
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
		    if(con!=null){
		      		 con.close();
		       	}
        }
 
        // 输出
        for(HashMap<String,String> map : columns){
            String Name = map.get("Name");
            String Code = map.get("Code");
            String DataType = map.get("DataType");
            cs.add(new Column(Name,Code,DataType));
        }
        CacheUtil.getInstance().set( "basicdb",cs);
        
        return cs;
    }
 
    
    /***
     * 通过表模糊获取所有表名 条件查询 不会缓存
     * @throws SQLException
     */
    public static List<Column> getAllTableNamesByTableName(String tableName) throws SQLException{
    	getOracleConnection();
        List<HashMap<String,String>> columns = new ArrayList<HashMap<String,String>>();
        List<Column> cs = new ArrayList<Column>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = con.createStatement();
            String sql = null;
            if(StringUtils.isNotBlank(tableName)){
            	sql=" select  TABLE_NAME ,COMMENTS,TABLE_TYPE　from user_tab_comments where 1=1  and   TABLE_NAME like '%"+tableName.toUpperCase()+"%'  ";
            }else{
            	 sql=" select  TABLE_NAME ,COMMENTS,TABLE_TYPE　from user_tab_comments   ";
            }
           
            log.info("getAllTableNames:"+sql);
            rs = stmt.executeQuery(sql);
            while (rs.next()){
            	if((IgnoreTable.ignoreTableMap.containsKey(rs.getString("TABLE_NAME").toUpperCase()))){
            		continue;
            	}
            	if((IgnoreTable.isIgnoreDigitTable(rs.getString("TABLE_NAME").toUpperCase()))){
            		log.info("表名含有ignore_table_reg正则 忽视:"+rs.getString("TABLE_NAME").toUpperCase());
            		continue;
            	}
            	if(!(IgnoreTable.isneedTable(rs.getString("TABLE_NAME").toUpperCase()))){
            		log.info("表名没含有need_table_reg正则 忽视:"+rs.getString("TABLE_NAME").toUpperCase());
            		continue;
            	}
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("Name", rs.getString("COMMENTS"));
                map.put("Code", rs.getString("TABLE_NAME"));
                map.put("DataType", rs.getString("TABLE_TYPE"));
               
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
		    if(con!=null){
		      		 con.close();
		       	}
        }
 
        // 输出
        for(HashMap<String,String> map : columns){
            String Name = map.get("Name");
            String Code = map.get("Code");
            String DataType = map.get("DataType");
            cs.add(new Column(Name,Code,DataType));
        }
       
        return cs;
    }
 
    
    
    /***
     * 通过表获取表的实际数据
     * @throws SQLException
     */
    public static List<Map<String,String>> getDatasByTable(String Table) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Table+"_data");
    	if(obj!=null){//缓存
    		List<Map<String,String>> results = (List<Map<String,String>>) obj;
    		 log.info("getDatasByTable从缓存中拿");
    		return results;
    	}
    	 getOracleConnection();
        List<Map<String,String>> ls = new ArrayList<Map<String,String>>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
            String sql="select * from "+Table;
            log.info(sql);
            rs = stmt.executeQuery(sql);
            // 获取列名  
            ResultSetMetaData metaData = rs.getMetaData();   
            // 获取数据 
            
            while (rs.next()) {
            	Map<String, String> dataMap = new HashMap<String,String>();
            	for(int i = 1 ; i<= metaData.getColumnCount() ; i++){ 
            	 
            		//获得指定列的列名 
            		String columnName = metaData.getColumnName(i); 
            		//获得指定列的列值 
            		String columnValue = rs.getString(i); 
            	 
            		dataMap.put(columnName, columnValue);
            	}
            	ls.add(dataMap);
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
	        if(con!=null){
		      	con.close();
		     }
           
        }
 
        CacheUtil.getInstance().set(Table+"_data",ls);
        
        return ls;
    }
    
    
    /***
     * 通过表获取表的序列
     * @throws SQLException
     */
    public static String getSequenceByTable(String Table,String schema) throws SQLException{
    	String result = "";
    	Seq seq = new Seq(Table);
    	StringBuffer sql = new StringBuffer();
    	sql.append("Select 'create  sequence  "+schema+".' || Sequence_Name || ' minvalue ' ||Min_Value|| ' maxvalue '|| Max_Value || ' start with ' ||");
    	sql.append("     Last_Number || ' increment by ' || Increment_By || ' cache ' || DECODE (Cache_Size, 0, 5, Cache_Size) || ' ;' as SEQ ");
    	sql.append("From user_sequences ");
    	sql.append("Where Sequence_Name =  '"+seq.getPreSeq()+"'  or Sequence_Name =  '"+seq.getSuffixSeq()+"'");
    	Object obj = CacheUtil.getInstance().get(Table+"_seq_create");
    	if(obj!=null){//缓存
    		result = (String) obj;
    		 log.info("getSequenceByTable从缓存中拿");
    		return result;
    	}
    	 getOracleConnection();
     
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
	        if(con!=null){
		      	con.close();
		     }
           
        }
 
        CacheUtil.getInstance().set(Table+"_seq_create",result);
        return result;
    }
    
    /***
     * 通过表获取表的约束
     * @throws SQLException
     */
    public static List<Constraints> getConstraintsByTable(String Table) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Table+"_constraints");
    	if(obj!=null){//缓存
    		 List<Constraints> results = ( List<Constraints>) obj;
    		 log.info("getConstraintsByTable从缓存中拿");
    		return results;
    	}
    	 getOracleConnection();
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
	        if(con!=null){
		      	con.close();
		     }
           
        }
 
       
        List<Constraints> results = new ArrayList<Constraints>(mapcs.values()); 
        mapcs.clear();
        mapcs = null;
        CacheUtil.getInstance().set(Table+"_constraints",results);
        
        return results;
    }
    
    
    
    /***
     * 通过表获取表的索引
     * @throws SQLException
     */
    public static List<Indexs> getIndexsByTable(String Table) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Table+"_indexs");
    	if(obj!=null){//缓存
    		 List<Indexs> results = ( List<Indexs>) obj;
    		 log.info("getIndexsByTable从缓存中拿");
    		return results;
    	}
    	 getOracleConnection();
        Map<String,Indexs> mapcs =   new HashMap<String,Indexs>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           StringBuffer sql = new StringBuffer();
           sql.append("select t.INDEX_NAME,T.TABLE_NAME,T.COLUMN_NAME,i.UNIQUENESS  from user_ind_columns t,user_indexes i where t.index_name = i.index_name  and i.UNIQUENESS!='UNIQUE' ");
           sql.append("  and t.table_name= '"+Table.toUpperCase()+"'");
          
            log.info(sql);
            rs = stmt.executeQuery(sql.toString());
           
            // 获取数据 
            while (rs.next()) {
             
            	String indexName = rs.getString("INDEX_NAME") ;
            	String tableName = rs.getString("TABLE_NAME") ;
            	String columnName = rs.getString("COLUMN_NAME") ;
            	String type = rs.getString("UNIQUENESS") ;
            	Indexs indexs = mapcs.get(indexName);
            	if(indexs==null){//约束信息数据库是分开的，需要通过constraint_name名字组合
            		indexs = new Indexs();
            		indexs.setIndexName(indexName);
            		indexs.setTableName(tableName);
            		indexs.setType(type);
            		indexs.addColumnNames(columnName);
            		mapcs.put(indexName, indexs);
            	}else{
            		indexs.addColumnNames(columnName);
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
	        if(con!=null){
		      	con.close();
		     }
           
        }
 
       
        List<Indexs> results = new ArrayList<Indexs>(mapcs.values()); 
        mapcs.clear();
        mapcs = null;
        CacheUtil.getInstance().set(Table+"_indexs",results);
        
        return results;
    }
    
   
     
    /***
     * 通过表获取表的索引
     * @throws SQLException
     */
    public static List<Indexs> getIndexsByTableContainPrimarykey(String Table) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Table+"_ppindexs");
    	if(obj!=null){//缓存
    		 List<Indexs> results = ( List<Indexs>) obj;
    		 log.info("getIndexsByTable从缓存中拿");
    		return results;
    	}
    	 getOracleConnection();
        Map<String,Indexs> mapcs =   new LinkedHashMap<String,Indexs>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           StringBuffer sql = new StringBuffer();
           sql.append("select t.INDEX_NAME,T.TABLE_NAME,T.COLUMN_NAME,i.UNIQUENESS  from user_ind_columns t,user_indexes i where t.index_name = i.index_name   ");
           sql.append("  and t.table_name= '"+Table.toUpperCase()+"' ORDER BY i.UNIQUENESS desc");
          
            log.info(sql);
            rs = stmt.executeQuery(sql.toString());
           
            // 获取数据 
            while (rs.next()) {
             
            	String indexName = rs.getString("INDEX_NAME") ;
            	String tableName = rs.getString("TABLE_NAME") ;
            	String columnName = rs.getString("COLUMN_NAME") ;
            	String type = rs.getString("UNIQUENESS") ;
            	Indexs indexs = mapcs.get(indexName);
            	if(indexs==null){//约束信息数据库是分开的，需要通过constraint_name名字组合
            		indexs = new Indexs();
            		indexs.setIndexName(indexName);
            		indexs.setTableName(tableName);
            		indexs.setType(type);
            		indexs.addColumnNames(columnName);
            		mapcs.put(indexName, indexs);
            	}else{
            		indexs.addColumnNames(columnName);
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
	        if(con!=null){
		      	con.close();
		     }
           
        }
 
       
        List<Indexs> results = new ArrayList<Indexs>(mapcs.values()); 
        mapcs.clear();
        mapcs = null;
        CacheUtil.getInstance().set(Table+"_ppindexs",results);
        
        return results;
    }
    
   
    public static void main(String[] args) throws Exception{
 
    	/*for(int i=0;i<1;i++){
    		log.info(getOracleTCloumns("CMS_FUNC"));
        	//log.info(getAllTableNamesAndCols());
           
    	}*/
    	 
    	
    	long st = System.currentTimeMillis();
    	//getOracleTCloumns("PAY_DAILY_BILL","2016-01-01 00:00:00","2018-06-12 23:59:59");
    	//System.out.println(getAllTableNamesAndCols(null,null,"CMS_ORG_SECONDARY_PWD"));
    	 //System.out.println(getConstraintsByTable("MSG_PRE_SEND_INFO"));
    	System.out.println(getSequenceByTable("CMS_MERCHANT","zzy"));
    	long st1 = System.currentTimeMillis();
    	log.info(st1-st);
 
    }
 
 
}