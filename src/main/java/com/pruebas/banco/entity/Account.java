package com.pruebas.banco.entity;

import java.math.BigDecimal;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.pruebas.banco.exception.EnoughMoneyException;


@Entity
@Table(name = "account")
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String client;
	private BigDecimal balance;

	public Account() {
		super();
	}

	public Account(Long id, String client, BigDecimal balance) {
		super();
		this.id = id;
		this.client = client;
		this.balance = balance;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	// Metodo que resta el saldo para actualizar el saldo(realizamos débito)
	public void makeDebit(BigDecimal total) {
		BigDecimal newBalance = this.balance.subtract(total);
		if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
			throw new EnoughMoneyException("Dinero insuficiente en la cuenta bancaria");
		}
		this.balance = newBalance;
	}

	// Metodo para realizar credito
	public void makeCredit(BigDecimal total) {
		this.balance = balance.add(total);
	}

	@Override
	public int hashCode() {
		return Objects.hash(balance, client, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(balance, other.balance) && Objects.equals(client, other.client)
				&& Objects.equals(id, other.id);
	}

}
