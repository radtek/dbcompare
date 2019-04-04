package com.wft.sqluldr2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.wft.db.IgnoreTable;
import com.wft.util.UploadPathUtil;

/**
 * @author admin
 * sqluldr2导出语句
 */
public class ExpSQL {

	private final static Logger log = Logger.getLogger(ExpSQL.class);
	
	private ExecutorService threadExec = Executors.newFixedThreadPool(10);    
	private AllBaseDataVo baseDataVo;
	
	 public ExpSQL() {
		 initParam();
	 }
	
	 
	 /**
     * 基础数据全量导出
     * @param expTaskDto
     * @param alwaysWaitFlag
     * @return
     */
    public ExpTaskResultVo beginExp() {
    	
    	try {
    		ExpTaskResultVo result = new ExpTaskResultVo();;
        	// 第一步 生成导出目录
        	String directory = this.getDirectory(UUID.randomUUID().toString());
         
        	log.info(String.format("基础数据全量导出，第一步 生成导出目录：【%s】", directory));
        	
        	// 第二步 获取执行SQL
        	Map<String,String> sqls = getExpSQL(directory);
        	if (null == sqls) return result;
        	log.info(String.format("基础数据全量导出，第二步 获取执行SQL：【%s】条.", sqls.size()));

        	// 第三步 生成任务记录
        	List<FutureTask<ExpTaskResultVo>> tasks = new ArrayList<FutureTask<ExpTaskResultVo>>();
        	for(String table : sqls.keySet()){
        		String sql = sqls.get(table);
        		FutureTask<ExpTaskResultVo> futureTask = new FutureTask<ExpTaskResultVo>( executeExp(sql) );
                threadExec.submit(futureTask);
                tasks.add(futureTask);
        	}
        	for(FutureTask<ExpTaskResultVo> task : tasks){
    			try {
    				ExpTaskResultVo eVo = task.get();
    				if(eVo.isExit()){
    	    			log.info(String.format("基础数据导出失败【%s】.", eVo.getExtResultDesc()));
    	    			result.setExit(true);
    	    		
    	    		}
    			} catch(Exception e) {
    				e.printStackTrace();
    			}
        		
        	}
        	if(result.isExit()){
        		log.info(String.format("基础数据导出失败"));
        		return null;
        	}
        	// 第四步 对文件进行打包
        	List<File> files = new ArrayList<File>();
        	String[] fileNames = {};
        	File file = new File(directory);
        	if (file.isDirectory()) {
        		fileNames = file.list();
        	}
        	for(String fileName : fileNames){
        		File tmpFile = new File(directory, fileName);
        		files.add(tmpFile);
        	}
        	String destFileName = UUID.randomUUID().toString()+".zip";
    		String destFilePath = directory +  destFileName;
    		try {
    			ZipUtil.zip(destFilePath, files, "utf-8", true);
    			log.info(String.format("基础数据全量导出，第四步 对文件进行打包, 文件地址：【{%s}】", destFilePath));
    			 
    		} catch (Exception e) {
    			log.info(String.format("基础数据全量导出失败，第四步 更新下载文件任务记录，更新状态：【6-处理失败】，异常:\r\n{%s}",e));
    			return null;
    		}
    		result.setFilePath(destFilePath);
        	return result;
		}finally{
			destoryResource();
		}
    	
    }
    
    
    /**
     * 初始化系统参数
     * @return
     */
    private void initParam(){
    	baseDataVo = new AllBaseDataVo();
    	baseDataVo.setBaseTable(IgnoreTable.ignoreTableMap.keySet().toArray(new String[0]));
    }
	 
    private Callable<ExpTaskResultVo> executeExp( final String sql) {
    	return  new ExecuteExpRunnable(sql);
    }
    
	 /**
     * 生成导出SQL
     * @return
     */
    private Map<String,String> getExpSQL(String directory) {
    	String[] tables = baseDataVo.getBaseTable();
    	Map<String,String> sqls = new HashMap<String,String>();
    	//shell脚本存放地址
    	String shellDir = this.getDirectory(UUID.randomUUID().toString());
    	log.info("shell 脚本存放地址："+shellDir);
    	for(String table : tables){
    		StringBuffer cmdStr = new StringBuffer("sqluldr2 ");
    		cmdStr.append("user=\"").append(baseDataVo.getExpUser()).append("/").append(baseDataVo.getExpPwd()).append("@").append(baseDataVo.getExpIp()).append("/").append(baseDataVo.getExpsid()).append("\" ");
    		cmdStr.append("query=\"select t.* from ").append(table).append(" t\" ");
    		cmdStr.append("field=\"").append(baseDataVo.getSeparate()).append("\" ");//数据分割符
    		cmdStr.append("record=0x0a ");
    		//按记录数切分文件的功能取决于三个命令行选项: FILE, ROWS, BATCH
    		cmdStr.append("file=\"").append(directory).append(table).append("-%b.txt\" ");
    		cmdStr.append("rows=100000 ");
    		cmdStr.append("batch=yes ");
    		//用户生成ctl文件
    		/*cmdStr.append("table=\"").append(table).append("\" ");
    		cmdStr.append("control=\"").append(directory).append(table).append("_sqlldr.ctl\" ");*/
    		cmdStr.append("safe=yes head=yes ");
    		cmdStr.append("charset=UTF8 ");
    		cmdStr.append("array=100 ");
    		cmdStr.append("read=218 ");
    		cmdStr.append("serial=0 ");
    		cmdStr.append("escape=0x5c quote=0x22 null=null format=mysql ");
    		String sql = cmdStr.toString();
    		log.info("cmdStr: {}"+sql);
    		
    		String path  = shellDir + table + ".sh";
    		try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(path, false));
				bw.write(sql);
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		String javaCmd = "bash " + path;
    		log.info("sqluldr2.sh脚本地址：:"+path+", java程序执行的命令：{}"+javaCmd);
    		
    		if (isWindows()) {//如果当前操作系统为windows
    			sqls.put(table, sql);
    		}else{
    			sqls.put(table, javaCmd);
    		}
    	}
    	
    	return sqls.size() > 0 ? sqls : null;
    }
    
	 
	private void destoryResource() {
		threadExec.shutdown(); // Disable new tasks from being submitted
		try {
			// Wait a while for existing tasks to terminate
			if (!threadExec.awaitTermination(60, TimeUnit.SECONDS)) {
				threadExec.shutdownNow(); // Cancel currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!threadExec.awaitTermination(60, TimeUnit.SECONDS)) {
					log.error("线程池未正常终止");
					//System.err.println("Pool did not terminate");
				}
			}
		} catch (InterruptedException ie) {
			// (Re-)Cancel if current thread also interrupted
			threadExec.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}
    
 	
    
    /**
     * 获取共享目录路径 （根目录 + 随机目录）
     * @return String
     **/
    private String getDirectory(String child){
    	String directory = UploadPathUtil.getSqluldr2Path();
    	StringBuffer dir = new StringBuffer(directory);
        //构建子目录
        if (StringUtils.isNotBlank(child)) {
        	dir.append(child).append(File.separator);
        }
        	
        File file = new File(dir.toString());
        if (!file.exists())
        	file.mkdirs();
        String realPath = file.getPath();
        realPath = realPath.endsWith(File.separator) ? realPath  : realPath + File.separator;
        return realPath;
    }
	
    /**
	 * 判断当前系统的操作系统类型
	 * @return
	 */
	private boolean isWindows (){
		String os = System.getProperty("os.name");
		log.info("当前操作系统：{}"+ os);
		return System.getProperty("os.name").toUpperCase().contains("WINDOWS");
	}
	
	
	public AllBaseDataVo getBaseDataVo() {
		return baseDataVo;
	}
	public void setBaseDataVo(AllBaseDataVo baseDataVo) {
		this.baseDataVo = baseDataVo;
	}
	
	
	
}
