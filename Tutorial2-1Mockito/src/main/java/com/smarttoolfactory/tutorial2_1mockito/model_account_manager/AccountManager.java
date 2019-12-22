package com.smarttoolfactory.tutorial2_1mockito.model_account_manager;

public interface AccountManager {

	long getBalance(Account account);

	long withdraw(Account account, long amount);

	Account findAccount(Customer customer);

}
