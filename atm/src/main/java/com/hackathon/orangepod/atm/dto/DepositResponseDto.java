package com.hackathon.orangepod.atm.DTO;

import lombok.Data;

@Data
public class DepositResponseDto {

	private Long accountId;
	private String accountNumber;
	private Double balance;

}
