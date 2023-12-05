package com.pruebas.banco.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pruebas.banco.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long>{

	//COnsulta para buscar un cliente
	@Query("select a from Account a where a.client=?1")
	public Optional<Account> findByClient(String client);
}
