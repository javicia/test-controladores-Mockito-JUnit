package com.pruebas.banco.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pruebas.banco.entity.Account;
import com.pruebas.banco.entity.TransactionDTO;
import com.pruebas.banco.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	
	@GetMapping
	@ResponseStatus(code= HttpStatus.OK)
	public List<Account> getAccount() {
		return accountService.listAll();
	}
	
	
	@GetMapping("/{id}")
	@ResponseStatus(code= HttpStatus.OK)
	public Account detailsAccount(@PathVariable Long id) {
		return accountService.findById(id);
	}
	
	//Json que se va a enviar
	@PostMapping("/transfer")
	public ResponseEntity<?> tranfersMoney(@RequestBody TransactionDTO transactionDTO){
		accountService.transferMoney(transactionDTO.getAccountOriginId(), transactionDTO.getAccountDestinyId(), transactionDTO.getTotal(), transactionDTO.getBankId());
		
		Map<String, Object> response = new HashMap<>();
		response.put("date", LocalDate.now().toString());
		response.put("status", "OK");
		response.put("menssage", "Transferencia realizada con éxito");
		response.put("transactionDTO", transactionDTO);
		
		return ResponseEntity.ok(response);
	}
}
