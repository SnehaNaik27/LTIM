package com.hackathon.orangepod.atm.exceptions;

public class WithdrawalLimitReachedException extends RuntimeException {

        public WithdrawalLimitReachedException(String message) {
            super(message);
        }
}
