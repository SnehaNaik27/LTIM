<<<<<<< HEAD
package com.hackathon.orangepod.atm.dto;
=======
package com.hackathon.orangepod.atm.DTO;
>>>>>>> ea6fdba9b20467bc502b9b31d53cdf2c7ed47168

public class UserLogoutRequestDTO {

    private String accountNumber;
    private String token;

    public UserLogoutRequestDTO(String accountNumber, String token) {
        this.accountNumber = accountNumber;
        this.token = token;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
