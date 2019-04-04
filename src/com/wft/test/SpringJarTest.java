package com.wft.test;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

 

public class SpringJarTest {

	 
	
	public static void main(String[] args) throws Exception {
		
		
		/*ClassUtil classUtil = new ClassUtil();
		  List<Class> clazzs = classUtil.getClasssFromPackage("base");//
		  for (Class clazz : clazzs) {
		   // System.out.println(clazz.getName());
		  } */

		  File file = new  File("D:/公司资料/test/one-pack/20170908/20170920-war/sppay-service-war/WEB-INF/lib");
		  File[] fs = file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				//System.out.println(pathname.getAbsolutePath());
				return pathname.getName().startsWith("sppay-cle-");
			}
		});
		 // testjar();
		 // testplanNameOverride(fs);
		  //classUtil.getStream("Jar文件的路径", "Jar文件总的一个具体文件的路径");
		//  testetraparam(fs);
    }  
  
	
	/*
	private static void testfilecount(File[] fs) {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext( "classpath:spring/application.xml");  
		 
		  ClassUtil classUtil = new ClassUtil();
		  List<Class> clazzs = null;
		  Set<Class> clazzset = new HashSet<Class>();
		  for(File f:fs){
			  clazzs = classUtil.getClasssFromJarFile(f.getAbsolutePath(),"cn/swiftpass/core/server/cle/processor/clean/filegenerate");
			  for (Class clazz : clazzs) {
				   // System.out.println(clazz.getName());
				    if(  clazz.getSuperclass()==(AbstractCleanFileGenerateService.class)){
				    	clazzset.add(clazz);
				    }
				  }
		  }
	 
		  System.out.println("清分定制类"+clazzset.size());
		  for (Class clazz : clazzset) {
			  try {
				  //System.out.println(clazz.getName());
				  Object obj = ctx.getBean(clazz);
				  for(Method method:obj.getClass().getDeclaredMethods()){
					  //System.out.println("method:"+method.getName());
					  if("createLogCount".equalsIgnoreCase(method.getName())){
						  method.setAccessible(true);
						 // System.out.println("count:"+method.invoke(obj));
						  System.out.println( "update MY_BASIC_BANK set filecount="+method.invoke(obj)+" where lower(CLEAING_PROCESS)=lower('["+clazz.getSimpleName()+"]');");
					  }
				  };
				 
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			  
			  
			  }

	}
	
	
	private static void testplanNameOverride(File[] fs) {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext( "classpath:spring/application.xml");  
		 
		  ClassUtil classUtil = new ClassUtil();
		  List<Class> clazzs = null;
		  Set<Class> clazzset = new HashSet<Class>();
		  for(File f:fs){
			  clazzs = classUtil.getClasssFromJarFile(f.getAbsolutePath(),"cn/swiftpass/core/server/cle/processor/clean/");
			  for (Class clazz : clazzs) {
				    System.out.println(clazz.getName());
				    if(  clazz.getSuperclass()==(AbstractCleanRecordWriteHandle.class)){
				    	clazzset.add(clazz);
				    }
				  }
		  }
	 
		  System.out.println("清分定制类"+clazzset.size());
		  for (Class clazz : clazzset) {
			  try {
				  System.out.println(clazz.getName());
				  Object obj = ctx.getBean(clazz);
				  for(Method method:obj.getClass().getDeclaredMethods()){
					  //System.out.println("method:"+method.getName());
					  if("getAcceptOrgId".equalsIgnoreCase(method.getName())){
						  method.setAccessible(true);
						 System.out.println("getAcceptOrgId:"+method.invoke(obj));
						  System.out.println( "update MY_BASIC_BANK set plan_pame_override='定制' where acceptorg='"+method.invoke(obj)+"');");
					  }
				  };
				 
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			  
			  
			  }

	}
	
	
	private static void testetraparam(File[] fs) {
		 ApplicationContext ctx = new ClassPathXmlApplicationContext( "classpath:spring/application.xml");  
		 
		  ClassUtil classUtil = new ClassUtil();
		  List<Class> clazzs = null;
		  Set<Class> clazzset = new HashSet<Class>();
		  for(File f:fs){
			  clazzs = classUtil.getClasssFromJarFile(f.getAbsolutePath(),"cn/swiftpass/core/server/cle/processor/clean/filegenerate");
			  for (Class clazz : clazzs) {
				   // System.out.println(clazz.getName());
				    if(  clazz.getSuperclass()==(AbstractCleanFileGenerateService.class)){
				    	clazzset.add(clazz);
				    }
				  }
		  }
	 
		  System.out.println("清分定制类"+clazzset.size());
		  for (Class clazz : clazzset) {
			  try {
				  //System.out.println(clazz.getName());
				  Object obj = ctx.getBean(clazz);
				  for(Method method:obj.getClass().getDeclaredMethods()){
					  //System.out.println("method:"+method.getName());
					  if("createLogCount".equalsIgnoreCase(method.getName())){
						  method.setAccessible(true);
						 // System.out.println("count:"+method.invoke(obj));
						  System.out.println( "update MY_BASIC_BANK set filecount="+method.invoke(obj)+" where lower(CLEAING_PROCESS)=lower('["+clazz.getSimpleName()+"]');");
					  }
				  };
				  StringBuffer sb = new StringBuffer();
				  for(Field field:obj.getClass().getDeclaredFields()){
					  field.setAccessible(true);
					  System.out.println("field.getType():"+field.getType()+ "  field.getName():"+field.getName());
					  if(field.getType()==String.class){
						  //System.out.println("count:"+field.getName());
						  sb.append(field.getName());
						 
					  }
					 
				  }
				  System.out.println( "update MY_BASIC_BANK set isbroken="+sb.toString()+" where lower(CLEAING_PROCESS)=lower('["+clazz.getSimpleName()+"]');");
				 
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
			  
			  
			  }

	}
	
	
	private static void testjar( ) throws IOException {
		  
		  File file = new  File("D:/公司资料/test/one-pack/20170908/20170920-war/sppay-service-war/WEB-INF/lib");
		  File[] fs = file.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				//System.out.println(pathname.getAbsolutePath());
				return pathname.getName().startsWith("sppay-cle-");
			}
		});

		  ClassUtil classUtil = new ClassUtil();
		  List<Class> clazzs = null;
		  Set<Class> clazzset = new HashSet<Class>();
		  for(File f:fs){
			  clazzs = classUtil.getClasssFromJarFile(f.getAbsolutePath(),"cn/swiftpass/core/server/cle/processor/clean/filegenerate");
			  for (Class clazz : clazzs) {
				   // System.out.println(clazz.getName());
				    if(  clazz.getSuperclass()==(AbstractCleanFileGenerateService.class)){
				    	//System.out.println(clazz.getName());
				    
				    	 System.out.println( "update MY_BASIC_BANK set jar='"+f.getName()+"',md5='"+	MD5Util.getMD5(f)+"' where lower(CLEAING_PROCESS)=lower('["+clazz.getSimpleName()+"]');");
				    }
				  }
		  }
	}*/
}
