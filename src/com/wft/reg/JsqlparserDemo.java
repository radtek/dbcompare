package com.wft.reg;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.wft.exception.AppException;
import com.wft.util.CommonUtil;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitorAdapter;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;

public class JsqlparserDemo {
	
	public static CCJSqlParserManager pm = new CCJSqlParserManager();
	
	public static void main(String[] args) throws  Exception {
		String sql = "insert into unionpay.tra_pay_type (PAY_TYPE_ID, PAY_TYPE_NAME, API_CODE, PAY_CENTER_ID, DATA_SOURCE, REMARK, CREATE_USER, CREATE_EMP, CREATE_TIME, UPDATE_TIME) values ('null', '扫码支付(中付支付科技6)-微信', 'pay.weixin.native', 593, 1, null, null, 'yongbin.yang2016', sysdate, sysdate);";
        System.out.println(parse(sql));
        
        System.out.println(Arrays.toString(parseFile(new File("C:/Users/admin/Desktop/insert_合利宝20180726.sql")).toArray()));
        
    }

	 public static List<String> parseFile(File file) throws Exception{
		 List<String> arrs = new ArrayList<String>();
		 String[] sqls = parseFromFile(file.getAbsolutePath());
		 
		 for(String sql:sqls){
			 
			 arrs.add(parse(sql));
			 arrs.add(" ");//添加空格 方便查看
		 }
		 return arrs;
	 }
	
    public static String parse(String sql) throws JSQLParserException{
    	Statement statement = null;
        try {
        	 statement = pm.parse(new StringReader(sql));
		} catch (Exception e) {
			//解析注释会报错
			return sql;
		}
       

        if (statement instanceof Insert) {
            //获得Insert对象
        	Insert insertStatement = (Insert) statement;
        	final List<Column> cmns = insertStatement.getColumns();
        	final List<Column> cmnrs =  new ArrayList<Column>();
        	ExpressionList expressionList =  new ExpressionList();
        	final List<Expression> expressions = new ArrayList<Expression>();
        	expressionList.setExpressions(expressions);
        	
        	ItemsList itemsList = insertStatement.getItemsList();
        	itemsList.accept(new ItemsListVisitorAdapter() {
				@Override
				public void visit(ExpressionList expressionList) {
					List<Expression> expressiones = expressionList.getExpressions();
					 
					 for(int i=0;i< expressiones.size();i++){
						 Expression e = expressiones.get(i);
						 if("null".equalsIgnoreCase(e.toString())){
							  //干掉null属性
							 //cmns.remove(i);
						 }else{
							 expressions.add(e);
							 cmnrs.add(cmns.get(i));
						 }
					 }
				 
				}
			 
			});
        	
        	insertStatement.setColumns(cmnrs);
        	insertStatement.setItemsList(expressionList);
        	 
        	return insertStatement.toString()+";";
        }
       
        
        return sql+";";
    }

     
    public static String[]  parseFromFile(String fileName) throws IOException {
		Set<String> sets = new LinkedHashSet<String>(); 
		File file = new File(fileName);
		StringBuilder sb = new StringBuilder();
		List<String> ls = FileUtils.readLines(file, CommonUtil.IN_CODE);
		for (String sql : ls) {
			String comment = sql.trim();
			if (StringUtils.isNotBlank(sql)) {
				if (comment.startsWith("--")||comment.startsWith("#")||comment.startsWith("/")) {//--开头表示注释或者#  /*，跳过
					//log.info("comment:" + comment);
					//注释保持原样
					sets.add(comment);
					continue;
				}
				if (comment.endsWith(";")) {//分号结束表示一个sql语句
				 
					int end = comment.lastIndexOf(";");
					String lastStr = comment.substring(0, end );
					sb.append(lastStr + " ");
					sets.add(sb.toString());
					sb = new StringBuilder();
				} else {
					sb.append(comment + " ");
				}
			}
		}
		if(StringUtils.isNotBlank(sb.toString())){//最后一个sql语句必须有分号结束符,否则抛异常
			throw new AppException("语法错误,没有添加分号:"+sb.toString());
			//sets.add(sb.toString());
		}
 
	 
		return sets.toArray(new String[sets.size()]);
		 
	}
}
