package ru.msc.response;

public class AuthResponse {
    private String token;

    private String userEmail;

    private String userLogin;

    private String message;

    public AuthResponse(String token, String userEmail, String userLogin, String message) {
        this.token = token;
        this.userEmail = userEmail;
        this.userLogin = userLogin;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
}
