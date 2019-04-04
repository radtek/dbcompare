package com.wft.db;

/**
 * @author admin
 * 序列 seq_table or table_seq
 * Select Sequence_Name as SEQ  From user_sequences Where Sequence_Name =  'seq.getPreSeq()'  or Sequence_Name =  'seq.getSuffixSeq()'
 */
public class Seq {

	private static final String SEQ="SEQ";
	
	private String preSeq;
	private String suffixSeq;
	
	public Seq(String table) {
		 this.preSeq = SEQ+"_"+table;
		 this.suffixSeq = table+"_" +SEQ;
	}

	public String getPreSeq() {
		return preSeq.toUpperCase();
	}

	public String getSuffixSeq() {
		return suffixSeq.toUpperCase();
	}
	 
	
}
