package com.hackathon.orangepod.atm.exceptions;

public class InvalidTokenException extends RuntimeException {

        public InvalidTokenException(String message) {
            super(message);
        }
}
