package com.smarttoolfactory.tutorial2_1mockito;

import com.smarttoolfactory.tutorial2_1mockito.model_account_manager.Account;
import com.smarttoolfactory.tutorial2_1mockito.model_account_manager.AccountManager;
import com.smarttoolfactory.tutorial2_1mockito.model_account_manager.Customer;
import com.smarttoolfactory.tutorial2_1mockito.model_account_manager.NotEnoughFundsException;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;


public class TestAccountManager {


    private Customer customer;
    private AccountManager accountManager;
    private Account account;
    private long withdrawAmount2000 = 2000L;

    @Before
    public void setUp() {
        customer = new Customer();
        accountManager = mock(AccountManager.class);
        customer.setAccountManager(accountManager);
        account = mock(Account.class);
    }


    @Test(expected = NotEnoughFundsException.class)
    public void withdrawButNotEnoughFunds() throws NotEnoughFundsException {

        // Arrange
        long balanceAmount200 = 200L;

        when(accountManager.getBalance(account)).thenReturn(balanceAmount200);

        // Act
        try {
            print("Customer.withdraw(" + withdrawAmount2000 + ") should fail with NotEnoughFundsException");
            customer.withdraw(withdrawAmount2000);
        } catch (NotEnoughFundsException e) {

            // Assert
            print("NotEnoughFundsException is thrown");

            verify(accountManager).findAccount(customer);
            print("Verified findAccount(customer) is called");

            // 🔥 This one PASSES, because withdraw throws Exception so it's considered not INVOKED
            verify(accountManager, times(0)).withdraw(account, withdrawAmount2000);
            print("Verified withdraw(account, " + withdrawAmount2000 + ") is not called");

            // 🔥 This one FAILS
//            verify(accountManager, times(1)).withdraw(account, withdrawAmount2000);

            throw e;
        }
    }

    @Test
    public void withdraw() throws NotEnoughFundsException {


        // Arrange
        long balanceAmount3000 = 3000L;

        // 🔥 Customer calls findAccount() method inside withdraw() method
        when(accountManager.findAccount(customer)).thenReturn(account);
        // Get account of this customer and set balance to 3000
        when(accountManager.getBalance(account)).thenReturn(balanceAmount3000);

        printBalance(balanceAmount3000);

        // Act
        customer.withdraw(withdrawAmount2000);

        // Assert
        verify(accountManager).withdraw(account, withdrawAmount2000);
        print("Verified withdraw(account, " + withdrawAmount2000 + ") is Called");

        verify(accountManager, times(2)).getBalance(account);
        print("Verified getBalance(account) is called twice");

        verify(accountManager).withdraw(account, withdrawAmount2000);
        print("Verified withdraw(account, " + withdrawAmount2000 + ") is called just once");

        verify(accountManager, atLeastOnce()).findAccount(customer);
        print("Verified findAccount(account) is called atleast once");
    }

    @Test
    public void withdrawAndVerifyOrder() throws NotEnoughFundsException {

        // Arrange
        long balanceAmount3000 = 3000L;

        // 🔥 Customer calls findAccount() method inside withdraw() method
        when(accountManager.findAccount(customer)).thenReturn(account);

        print("Train getBalance(account) to return " + balanceAmount3000);
        when(accountManager.getBalance(account)).thenReturn(balanceAmount3000);

        printBalance(balanceAmount3000);

        print("Customer.withdraw(" + withdrawAmount2000 + ")");

        // Act
        customer.withdraw(withdrawAmount2000);

        // Assert
        print("Verify order of method calls");
        InOrder order = inOrder(accountManager);

        order.verify(accountManager).findAccount(customer);
        print("Verified findAccount(account) is called");

        order.verify(accountManager).getBalance(account);
        print("Verified getBalance(account) is called");

        order.verify(accountManager).withdraw(account, withdrawAmount2000);
        print("Verified withdraw(account, " + withdrawAmount2000 + ") is called");

        order.verify(accountManager).getBalance(account);
        print("Verified getBalance(account) is called one more time after withdraw");


        verify(accountManager,times(2)).getBalance(any());

        verifyNoMoreInteractions(accountManager);
        print("verified no more calls are executed on the mock object");
    }

    private static void print(String text) {
        System.out.println(text);
    }

    private void printBalance(long balance) {
        print("Balance is " + balance + " and withdrawl amount " + withdrawAmount2000);
    }
}