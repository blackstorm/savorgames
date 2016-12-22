package com.savorgames.api.v1.resp;

import java.util.Map;

public class ResValidFailEntity extends BaseResEntity{
	
	private static final long serialVersionUID = 413719067735762027L;
	
	private Map<String,String> errors;

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
}
