package BanksTest;

import BanksCore.Entities.*;
import BanksCore.Entities.Accounts.DebitAccount;
import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.InputIsIncorrectException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.TooManyOrNoneAccountsWereFoundException;
import BanksCore.Services.CentralBank;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.fail;

public class TestClass {
  CentralBank cb = new CentralBank();

  @Test
  @DisplayName("New bank (successful)")
  void creatingNewBank_successfullyCreated(){
    cb.addBank("bank");
    Assertions.assertEquals( 1, cb.getAllBanks().size());
  }

  @Test
  @DisplayName("New client (successful)")
  void creatingNewClient_successfullyCreated(){
    Bank bank = cb.addBank("bank");
    bank.addClient(new Client(UUID.randomUUID(), "Steve", new Date(), "1234", "St. Petersburg"));
    Assertions.assertEquals( 1, bank.getAllClients().size());
  }

  @Test
  @DisplayName("Transaction between two accounts (successful)")
  void transactionBetweenTwoAccounts_successfully(){
    try {
      Bank bank = cb.addBank("bank");
      Client client = new Client(UUID.randomUUID(), "Steve", new Date(), "1234", "St. Petersburg");
      DebitAccount account1 = new DebitAccount(0.1f, UUID.randomUUID(), client, 10000);
      DebitAccount account2 = new DebitAccount(0.1f, UUID.randomUUID(), client, 10000);
      bank.addClient(client);
      bank.addAccount(account1);
      bank.addAccount(account2);
      client.addAccount(account1);
      client.addAccount(account2);

      cb.transferMoneyBetweenAccounts(account1.getId().toString(), account2.getId().toString(),
          100f);
      Assertions.assertEquals(10100f, account2.getMoney());
    }
    catch (NotEnoughMoneyException | AccountDoesNotSupportOperationException |
           TooManyOrNoneAccountsWereFoundException | InputIsIncorrectException e) {
      fail();
    }
  }

  @ParameterizedTest
  @DisplayName("Transaction between two accounts (failed)")
  @ValueSource(floats = {100f, -100f})
  void transactionBetweenTwoAccounts_failed(float amount){
      Bank bank = cb.addBank("bank");
      Client client = new Client(UUID.randomUUID(), "Steve", new Date(), "1234", "St. Petersburg");
      DebitAccount account1 = new DebitAccount(0.1f, UUID.randomUUID(), client, 90f);
      DebitAccount account2 = new DebitAccount(0.1f, UUID.randomUUID(), client, 10000f);
      bank.addClient(client);
      bank.addAccount(account1);
      bank.addAccount(account2);
      client.addAccount(account1);
      client.addAccount(account2);
      Exception exception = assertThrows(Exception.class, () -> cb.transferMoneyBetweenAccounts(account1.getId().toString(), account2.getId().toString(),
          amount));
      assertTrue(exception.getMessage().contentEquals("Account " + account1.getId() + " has less money than " + amount + ".") | exception.getMessage().contentEquals("Check your input, please."));
  }
}
