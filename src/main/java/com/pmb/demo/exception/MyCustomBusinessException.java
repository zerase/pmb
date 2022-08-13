package com.pmb.demo.exception;

public class MyCustomBusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	public MyCustomBusinessException() {
		super();
	}

	public MyCustomBusinessException(String message) {
		super(message);
	}

}
