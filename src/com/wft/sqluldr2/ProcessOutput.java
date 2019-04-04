package com.wft.sqluldr2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

import org.apache.log4j.Logger;

public class ProcessOutput {
	
	private final static Logger log = Logger.getLogger(ExpSQL.class);
	
	/**
     * 打印进程输出
     * @param process 进程
     */
    public static void readProcessOutput(final Process process) {
        // 将进程的正常输出在 System.out 中打印，进程的错误输出在 System.err 中打印
        read(process.getInputStream(), System.out);
        read(process.getErrorStream(), System.err);
    }

    /**
     * 读取输入流
     * @param inputStream
     * @param out
     */
    private static void read(InputStream inputStream, PrintStream out) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                //out.println(line);
            	log.info("打印进程输出:{}"+line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }
}
