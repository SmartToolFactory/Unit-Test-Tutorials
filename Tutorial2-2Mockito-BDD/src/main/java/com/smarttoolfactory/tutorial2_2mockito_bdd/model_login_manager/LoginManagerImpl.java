package com.smarttoolfactory.tutorial2_2mockito_bdd.model_login_manager;

public class LoginManagerImpl {

    ILoginManager loginManager;

    public User login(String email, String password) {
        return loginManager.login(email, password);
    }

    public String validate(String email, String password) {
        return loginManager.validate(email, password);
    }
}