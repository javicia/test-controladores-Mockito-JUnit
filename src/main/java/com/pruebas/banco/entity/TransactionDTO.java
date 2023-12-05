package com.pruebas.banco.entity;

import java.math.BigDecimal;

public class TransactionDTO {

	private Long accountOriginId;
	private Long accountDestinyId;
	private BigDecimal total;
	private long bankId;

	public TransactionDTO() {
		super();
	}

	public Long getAccountOriginId() {
		return accountOriginId;
	}

	public void setAccountOriginId(Long accountOriginId) {
		this.accountOriginId = accountOriginId;
	}

	public Long getAccountDestinyId() {
		return accountDestinyId;
	}

	public void setAccountDestinyId(Long accountDestinyId) {
		this.accountDestinyId = accountDestinyId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

}
