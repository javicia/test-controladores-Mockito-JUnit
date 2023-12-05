package com.pruebas.banco.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pruebas.banco.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

}
