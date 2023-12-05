package com.pruebas.banco.exception;

public class EnoughMoneyException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EnoughMoneyException(String menssage) {
		super(menssage);
	}
}
