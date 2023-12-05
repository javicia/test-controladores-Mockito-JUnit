package com.pruebas.banco.service;

import java.math.BigDecimal;
import java.util.List;

import com.pruebas.banco.entity.Account;

public interface AccountService {

	public List<Account> listAll();

	public Account findById(Long id);

	public Account save(Account account);

	public int monitorTotalTransfers(Long bankId);

	public BigDecimal checkBalance(Long accountId);

	public void transferMoney(Long numberAccountOrigin, Long numberAccountDestiny, BigDecimal total, Long bankId);
}
