package com.wft.sqluldr2;

/**
 * @author admin
 * 测试ExpSQL
 */
public class Sqluldr2Test {

	public static void main(String[] args) {
		testExpSQL();
	}
	 
	private static void testExpSQL() {
		ExpSQL expSQL = new ExpSQL();
		System.out.println(expSQL.beginExp());
	}
}
