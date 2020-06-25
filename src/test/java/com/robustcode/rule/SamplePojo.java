package com.robustcode.rule;

public class SamplePojo {
	
	private final String abc;
	private final SampleSubPojo ssp;
	private final int a;
	
	
	
	public int getA() {
		return a;
	}

	public SamplePojo(String abc, int def, SampleSubPojo ssp) {
		super();
		this.abc = abc;
		this.ssp = ssp;
		this.a = def;
		
	}

	public String getAbc() {
		return this.abc;
	}
	
	public SampleSubPojo getSsp() {
		return ssp;
	}
	
	
	
	
	
}
