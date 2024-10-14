package com.hackathon.orangepod.atm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.orangepod.atm.dto.DepositRequestDto;
import com.hackathon.orangepod.atm.dto.DepositResponseDto;
import com.hackathon.orangepod.atm.exception.AccountNotFoundException;
import com.hackathon.orangepod.atm.service.DepositService;

@RestController
@RequestMapping("/api/account")
public class DepositController {

	@Autowired
	private DepositService depositService;

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@PostMapping("/deposit")
	public ResponseEntity<String> deposit(@RequestBody DepositRequestDto depositRequestDto) {

		try {
			DepositResponseDto depositResponseDto = depositService.deposit(depositRequestDto);

			String response = jacksonObjectMapper.writeValueAsString(depositResponseDto);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (AccountNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

}
