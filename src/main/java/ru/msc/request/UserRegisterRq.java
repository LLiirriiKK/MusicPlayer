package ru.msc.request;

public class UserRegisterRq {
    private String email;

    private String login;

    private String password;

    private String confirmPassword;

    public UserRegisterRq() {
    }

    public UserRegisterRq(String email, String login, String password, String confirmPassword) {
        this.email = email;
        this.login = login;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
