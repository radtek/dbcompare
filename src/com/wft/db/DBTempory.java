package com.wft.db;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.exception.AppException;
/**
 * @author admin
 * 注意查询结果有缓存 key 含有shema 需要清楚缓存时通过shema删除
 */
public class DBTempory {
 
	private final static Logger log = Logger.getLogger(DBTempory.class);
	
	private static final String DRIVER_CLASS = "oracle.jdbc.driver.OracleDriver";
	private  String url  ;//"jdbc:oracle:thin:@139.199.174.101:1521:pcloud";
	private  String user ;//dbview";
	private  String pwd ;//"dbbie12w";
	private  String shema ;//"dbbie12w";
    private  Connection con = null;
    private  boolean isCheckOrSeq =true ;//是否比较check约束和序列 默认比较 这边主要用来某些地方不需要比较check约束和序列
    
    static{
    	  try {
              Class.forName(DRIVER_CLASS);
          } catch (Exception ex) {
              log.info(ex.getMessage());
          }
    }
    
    public DBTempory(String url, String user,String pwd,String shema) {
    	this.url = url;
		this.user = user;
		this.pwd = pwd;
		this.shema = shema;
		getOracleConnection();
	 
	}

	public  void getOracleConnection() {
 
        try {
            if(con!=null&&!con.isClosed()){
            	log.info(this.url+" 连接有效");
            	 return ;
            }
            con=DriverManager.getConnection(this.url,this.user,this.pwd);
            
        } catch (Exception ex) {
            log.info(ex.getMessage());
            throw new AppException("数据库连接异常:"+ex.getMessage()+" db"+this.toString());
        }
       
    }
 
   
    /***
     *打印某一用户下某一表 的表结构信息
     * @throws SQLException
     */
    public  List<Column> sysoutOracleTCloumns(String Table,String Owner) throws SQLException{
    	Object obj = CacheUtil.getInstance().get(Owner+"sysoutOracleTCloumns:"+Table);
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
            sb.append("     and a.owner='"+Owner.toUpperCase()+"'");
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
            throw new AppException("数据库连接异常:"+e.getMessage()+" db"+this.toString());
        }finally{
        	if(rs!=null){
        		 rs.close();
         	}
	       	if(stmt!=null){
	       		stmt.close();
	        	}
		    /*if(con!=null){
		      		 con.close();
		       	}*/
           
        }
        
       log.info("sysoutOracleTCloumns: "+Table+" "+columns);
        // 输出
        for(HashMap<String,String> map : columns){
            String Name = map.get("Name");
            String DataLen = map.get("DataLen");
            String Code = map.get("Code");
           // String Comment = map.get("Comment");
            String DataType = map.get("DataType");
            String lastAnalyzed = map.get("lastAnalyzed");		
           
            String Primary = map.get("Primary");
            String Mandatory = map.get("Mandatory");
            String DataScale = map.get("DataScale");
            Integer scale  = null;
            if(StringUtils.isNotBlank(DataScale)){
            	scale = Integer.valueOf(DataScale);
            }
            Column column = new Column(Name,Code,DataType,Primary,Mandatory,DataLen,scale);
            column.setLastAnalyzed(lastAnalyzed);
            cs.add(column);
        }
    	 
    	 //此处缓存不能太久 防止缓存比对不出来
        CacheUtil.getInstance().set(Owner+"sysoutOracleTCloumns:"+Table,cs,CacheUtil.default_long_overdue_10m);
        
        return cs;
    }
 
    
    /***
     * 通过表获取表的实际数据
     * @throws SQLException
     */
    public   List<Map<String,String>> getDatasByTable(String sql ) throws SQLException{
      
        List<Map<String,String>> ls = new ArrayList<Map<String,String>>();
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           
            //log.info(sql);
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
            	//log.info(dataMap);
            	ls.add(dataMap);
            }
            
        }
        catch (SQLException e){
            e.printStackTrace();
            throw new AppException("数据库连接异常:"+e.getMessage()+" db"+this.toString());
        }finally{
        	if(rs!=null){
       		 rs.close();
        	}
	       	if(stmt!=null){
	       		stmt.close();
	        }
	        
	       
           
        }
 
        
        return ls;
    }
    
    
    /***
     * 通过表获取库所有的序列
     * @throws SQLException
     */
    public   Set<String> getSequenceByTable() throws SQLException{
    	Object obj = CacheUtil.getInstance().get(this.shema+"getSequenceByTable");
    	if(obj!=null){//缓存
    		 log.info("getSequenceByTable从缓存中拿");
    		 Set<String> results = (Set<String>) obj;
    		return results;
    	}
    	Set<String> seqsets = new HashSet<String>();
    	StringBuffer sql = new StringBuffer();
    	sql.append("select Sequence_Name from user_sequences");
     
        Statement stmt = null;
        ResultSet rs = null;
        try{
           stmt = con.createStatement();
           
            log.info(sql);
            rs = stmt.executeQuery(sql.toString());
           
            while (rs.next()) {
            	 
            	 String result =  rs.getString("Sequence_Name");
            	 seqsets.add(result);
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
	        /*if(con!=null){
		      	con.close();
		     }*/
           
        }
     
    	//此处缓存不能太久 防止缓存比对不出来
        CacheUtil.getInstance().set(this.shema+"getSequenceByTable",seqsets,CacheUtil.default_long_overdue_10m);
        
        return seqsets;
    }
    
    
    /***
     * 通过某一表获取SEQ信息
     * 不能单独调用
    
     * @throws SQLException
     */
	public   String getSeq(String Table)throws SQLException {
		Object obj = CacheUtil.getInstance().get(this.shema+"getSeq:"+Table);
    	if(obj!=null){//缓存
    		 log.info("getSequenceByTable从缓存中拿");
    		 String results = (String) obj;
    		return results;
    	}
		String result = "";
    	Seq seq = new Seq(Table);
    	StringBuffer sql = new StringBuffer();
    	sql.append("Select Sequence_Name as SEQ ");
    	sql.append("From user_sequences ");
    	sql.append("Where Sequence_Name =  '"+seq.getPreSeq()+"'  or Sequence_Name =  '"+seq.getSuffixSeq()+"'");
    	 
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
	        }/*if(con!=null){
		      	con.close();
		     }*/
	         
        }
      //此处缓存不能太久 防止缓存比对不出来
        CacheUtil.getInstance().set(this.shema+"getSeq:"+Table,result,CacheUtil.default_long_overdue_10m);
        
        return result;
	}

	
    /***
     * 通过表获取表的约束
     * @throws SQLException
     */
    public  List<Constraints> getConstraintsByTable(String Table) throws SQLException{
		Object obj = CacheUtil.getInstance().get(this.shema+"getConstraintsByTable:"+Table);
    	if(obj!=null){//缓存
    		 log.info("getConstraintsByTable从缓存中拿");
    		 List<Constraints> results = (List<Constraints>) obj;
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
	       /* if(con!=null){
		      	con.close();
		     }*/
           
        }
 
       
        List<Constraints> results = new ArrayList<Constraints>(mapcs.values()); 
      //此处缓存不能太久 防止缓存比对不出来
        CacheUtil.getInstance().set(this.shema+"getConstraintsByTable:"+Table,results,CacheUtil.default_long_overdue_10m);
        
        return results;
    }
    
    
    /***
     *  检查某一用户空间下 某些表是否存在这些列
     *  返回map key-表  ColExist-(是否存在表,是否存在列)
     *  多线程会调用
     * @throws SQLException
     */
    public  Map<String,ColExist> checkTableNames(String schame,Map<String,Table> colMaps/*Map<String,List<Column>> colMaps*/,String ...tables) throws SQLException{
       
    	Object obj = CacheUtil.getInstance().get(schame+"checkTableNames:"+schame+ArrayUtils.toString(tables));
    	if(obj!=null){//缓存
    		 log.info("checkTableNames从缓存中拿");
    		 Map<String,ColExist> results = (Map<String,ColExist>) obj;
    		return results;
    	}
    	
        Map<String,ColExist> map = new HashMap<String,ColExist>();
        Statement stmt = null;
        ResultSet rs = null;
       
       try {
        for(String tableName:tables){
        	tableName = tableName.toUpperCase();
        	Table tTable = colMaps.get(tableName);
        	ColExist colExist = new ColExist();
        	List<Column> cols = tTable.getCols();
        	StringBuilder sb =   new StringBuilder();
        	if(cols!=null){
        		
            	 for(int i=0,len=cols.size(); i<len;i++){
            		 Column col = cols.get(i);
            		 sb.append(col.getCode());
            		 if(i!=len-1){
            			 sb.append(", ");
            		 }
            	 }
            	 log.info("checkTableNames colmun:"+sb);
        	 }
        	 String sql = null;
        	 String columsql = sb.toString();
        	 boolean isExisTable = true;//是否存在表 不存在表 就不用比较序列和索引了
        	 if(StringUtils.isNotBlank(columsql)){
        		 sql=" select "+columsql+" from "+schame.toUpperCase()+"."+tableName.toUpperCase()+" t where ROWNUM = 1";
        	 }else{
        		// sql=" select count(1) from "+schame.toUpperCase()+"."+table.toUpperCase()+" t";
        	 }
           	 try{
                    stmt = con.createStatement();
                    log.info("checkTableNames sqL:"+sql);
                    if(StringUtils.isNotBlank(sql)){
                    	rs = stmt.executeQuery(sql); 
                    }
                     
                }
                catch (SQLException e){
                    //e.printStackTrace();这里要做特别判断 close报错 software报错
                	log.error("checkTableNames SQLException:"+e.getMessage());
                    if(e.toString().contains("ORA-00942")){//表不存在 ,(约束 序列一定不存在 所以赋值true)防止重复生成
                    	colExist.setTableCompare(false);
                    	colExist.setCheckSame(true);
                    	colExist.setSeqSame(true);
                    	isExisTable = false;
                    }else{//列不存在ORA-00904: "HEBAO_AREA_CODE": invalid identifier
                    	colExist.setColCompare(false);
                    }
                  
                }
           	 	//如果user和schame根本没有权限查不到这些信息,再去比较毫无意义
           	 	if(this.user.equalsIgnoreCase(this.shema)){
           	 	if(isCheckOrSeq&&isExisTable ){//需要比较约束时 且表存在 才 去是否比较check约束和序列 
	        		//检查约束  是否一样
	            	boolean checkSame = checkSame(tTable);
	            	colExist.setCheckSame(checkSame);
	            	//检查序列  是否一样
	            	boolean seqSame = seqSame(tTable);
	            	colExist.setSeqSame(seqSame);
	        	}
           	 	}
	           	
           	   map.put(tableName, colExist);
           }
		} catch (Exception e) {
			 
		}finally{
        	if(rs!=null){
         		 rs.close();
          	}
        	if(stmt!=null){
        		stmt.close();
         	}
	      /* 	if(con!=null){
	      		 con.close();
	       	}*/
       }
       //此处缓存不能太久 防止缓存比对不出来
       CacheUtil.getInstance().set(schame+"checkTableNames:"+schame+ArrayUtils.toString(tables),map,CacheUtil.default_long_overdue_10m);
       
        log.info("restul:"+map);
        return map;
    }
    

   //检查序列  是否一样 要么都为空 相同 要么都存在相同
	private boolean seqSame(Table tTable) throws SQLException {
		boolean result = false;
		String dbseq = tTable.getSeq();
		String tempseq = getSeq(tTable.getName().toUpperCase());
		 log.info("检查  序列seqSame tempseq:"+dbseq);
		 log.info("检查  序列seqSame tempseq:"+tempseq);
		if(StringUtils.isBlank(dbseq)&&StringUtils.isBlank(tempseq)){
			result = true;
		}
		if(!StringUtils.isBlank(dbseq)&&!StringUtils.isBlank(tempseq)){
			result = true;
		}
		return result;
	}

	//检查约束  是否一样
	 @SuppressWarnings("unchecked")
    private boolean checkSame(Table tTable) throws SQLException {
    	boolean result = false;
    	 List<Constraints> dbConstraints = tTable.getConstraints();
    	 List<Constraints> tempConstraints = getConstraintsByTable(tTable.getName().toUpperCase());
		//如果补集个数不等于比较库 说明有差异
    	 Collection<Constraints> interConstraints = CollectionUtils.union(  dbConstraints,tempConstraints);
    	 log.info("检查约束 ccheckSame:"+interConstraints);
    	 int tempConstraintssSize = tempConstraints.size();
    	 int interConstraintsSize = interConstraints.size();
    	 if(tempConstraintssSize==interConstraintsSize){
    		 result = true;
    	 }
		return result;
	}

	//关闭con colse
    public void close(){
		  try {
			  if(con!=null&&!con.isClosed()){
				  con.close();
				} 
		  }catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
		  }
    }
    @Override
	public String toString() {
		return "DBTempory [url=" + url + ", user=" + user + ", pwd=" + pwd
				+ ", shema=" + shema + ", con=" + con + "]";
	}

    
    
    
	public boolean isCheckOrSeq() {
		return isCheckOrSeq;
	}

	public void setCheckOrSeq(boolean isCheckOrSeq) {
		this.isCheckOrSeq = isCheckOrSeq;
	}

	public String getShema() {
		return shema;
	}
 

	public static void main(String[] args) throws SQLException, UnsupportedEncodingException{
    	DBTempory t = new DBTempory("jdbc:oracle:thin:@123.207.88.156:1521:test2","o2o_spdb_pri","dfgdad2hg","o2o_spdb_pri");
    	 
    	//colMaps.put("WOP_PARTNER_PRODUCT_REL", t.sysoutOracleTCloumns("WOP_PRODUCT_PARAM_VALUE", "tayh"));
    	// t.checkTableNames("ZZY",colMaps,"WOP_PRODUCT_PARAM_VALUE","WOP_PARTNER_PRODUCT_REL","WOP_PARTNER_PRODUCT1");
       // t.sysoutOracleTCloumns("CMS_BANK_VALID_CONF", "dbview");
    	System.out.println(t.getConstraintsByTable("MSG_PRE_SEND_INFO")); 
    }
 
 
}