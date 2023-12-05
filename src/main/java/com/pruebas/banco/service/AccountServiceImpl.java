package com.pruebas.banco.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pruebas.banco.entity.Account;
import com.pruebas.banco.entity.Bank;
import com.pruebas.banco.repository.AccountRepository;
import com.pruebas.banco.repository.BankRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private BankRepository bankRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Account> listAll() {
		return accountRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Account findById(Long id) {
		return accountRepository.findById(id).orElseThrow();
	}

	@Override
	@Transactional
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	@Transactional(readOnly = true)
	public int monitorTotalTransfers(Long bankId) {
		Bank bank = bankRepository.findById(bankId).orElseThrow();
		return bank.getTotalTransfers();
	}

	@Override
	@Transactional(readOnly = true)
	public BigDecimal checkBalance(Long accountId) {
		Account account = accountRepository.findById(accountId).orElseThrow();
		return account.getBalance();
	}

	@Override
	public void transferMoney(Long numberAccountOrigin, Long numberAccountDestiny, BigDecimal total, Long bankId) {

		// Restamos en cuenta de origen el dinero que realiza la transferencia o cargo
		Account accountOrigin = accountRepository.findById(numberAccountOrigin).orElseThrow();
		accountOrigin.makeDebit(total);
		accountRepository.save(accountOrigin);

		// Sumamos el dinero de la tranferencia de la cuenta de origen
		Account accountDestiny = accountRepository.findById(numberAccountDestiny).orElseThrow();
		accountDestiny.makeCredit(total);
		accountRepository.save(accountDestiny);

		// Guardamos el numeros de transferencias guardadas
		Bank bank = bankRepository.findById(bankId).orElseThrow();
		int totalTranfers = bank.getTotalTransfers();
		bank.setTotalTransfers(++totalTranfers);
		bankRepository.save(bank);
	}
}
