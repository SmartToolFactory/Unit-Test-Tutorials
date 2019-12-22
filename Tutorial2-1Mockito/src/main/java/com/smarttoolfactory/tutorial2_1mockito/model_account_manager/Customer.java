package com.smarttoolfactory.tutorial2_1mockito.model_account_manager;

public class Customer {

    private AccountManager accountManager;

    public long withdraw(long amount) throws NotEnoughFundsException {


        Account account = accountManager.findAccount(this);

        long balance = accountManager.getBalance(account);
        if (balance < amount) {
            throw new NotEnoughFundsException();
        }
        accountManager.withdraw(account, amount);
        return accountManager.getBalance(account);
    }

    public void setAccountManager(AccountManager accountManager) {
        this.accountManager = accountManager;
    }
}
