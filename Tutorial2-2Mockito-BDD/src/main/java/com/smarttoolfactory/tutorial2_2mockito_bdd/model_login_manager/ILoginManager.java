package com.smarttoolfactory.tutorial2_2mockito_bdd.model_login_manager;

public interface ILoginManager {

    User login(String email, String password);

    String validate(String email, String password);

}