package com.pruebas.banco;

import java.math.BigDecimal;
import java.util.Optional;

import com.pruebas.banco.entity.Account;
import com.pruebas.banco.entity.Bank;

public class Dates {

	
	public static Optional<Account> createAccount001(){
		return Optional.of(new Account(1L, "Javier", new BigDecimal("100000")));
	}
	
	public static Optional<Account> createAccount002(){
		return Optional.of(new Account(2L, "Marina", new BigDecimal("80000")));
	}
	
	public static Optional<Bank> createBank(){
		return Optional.of(new Bank(1L, "ING", 0));
	}
}
