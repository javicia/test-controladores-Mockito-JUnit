package com.pruebas.banco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pruebas.banco.controller.AccountController;
import com.pruebas.banco.entity.Account;
import com.pruebas.banco.entity.TransactionDTO;
import com.pruebas.banco.service.AccountService;

//import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AccountService accountService;
	
	//Mapeamos objetos a JSON
	ObjectMapper objectMapper;
	
	//Configuración para que antes que realice cualquier test, crea una distancia de objectMapper
	@BeforeEach
	public void sepUp() {
		objectMapper = new ObjectMapper();
	}
	
	@Test
	void testDetails() throws Exception {
		when(accountService.findById(1L)).thenReturn(Dates.createAccount001().orElseThrow());
		mockMvc.perform(get("/api/accounts/1").contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.client").value("Javier"))
		.andExpect(jsonPath("$.balance").value("100000"));
		
		verify(accountService).findById(1L);
	}
	
	@Test
	public void  testTransferMoney() throws Exception {
		TransactionDTO transactionDTO = new TransactionDTO();
		transactionDTO.setAccountOriginId(1L);
		transactionDTO.setAccountDestinyId(2L);
		transactionDTO.setTotal(new BigDecimal("80"));
		transactionDTO.setBankId(1L);
		
		System.out.println(objectMapper.writeValueAsString(transactionDTO));
		
		//Datos que yo voy a esperar
		Map<String, Object> response = new HashMap<>();
		response.put("date", LocalDate.now().toString());
		response.put("status", "OK");
		response.put("menssage", "Transferencia realizada con éxito");
		response.put("transactionDTO", transactionDTO);
		
		System.out.println(objectMapper.writeValueAsString(response));
		
		//Verificamos que vamos a recibir
		mockMvc.perform(post("/api/accounts/transfer").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(transactionDTO)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.date").value(LocalDate.now().toString()))
				.andExpect(jsonPath("$.menssage").value("Transferencia realizada con éxito"))
				.andExpect(jsonPath("$.transactionDTO.accountOriginId").value(transactionDTO.getAccountOriginId()))
				.andExpect(content().json(objectMapper.writeValueAsString(response)));
				
	}
	
	@Test
	public void listAccount() throws Exception {
		List<Account> accounds = Arrays
				.asList(Dates.createAccount001().orElseThrow(), Dates.createAccount002().orElseThrow());
		when(accountService.listAll()).thenReturn(accounds);
		
		mockMvc.perform(get("/api/accounts").contentType(MediaType.APPLICATION_JSON))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$[0].client").value("Javier"))
		.andExpect(jsonPath("$[1].client").value("Marina"))
		.andExpect(jsonPath("$[0].balance").value("100000"))
		.andExpect(jsonPath("$[1].balance").value("80000"))
		.andExpect(jsonPath("$",hasSize(2)))
		.andExpect(content().json(objectMapper.writeValueAsString(accounds)));
		
		verify(accountService).listAll();
	}
	
	@Test
	void saveAccount() throws JsonProcessingException, Exception {
		Account account = new Account(null, "Elena", new BigDecimal("3000"));
		when(accountService.save(any())).then(invocation -> {
			Account a = invocation.getArgument(0);
			a.setId(3L);
			return a;
		});
		
		mockMvc.perform(post("/api/accounts/save").contentType(MediaType.APPLICATION_JSON)
		.content(objectMapper.writeValueAsString(account)))
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.id",is(3)))
	    .andExpect(jsonPath("$.client",is("Elena")))
	    .andExpect(jsonPath("$.balance",is(3000)));
		
		verify(accountService).save(any());
	}
}

