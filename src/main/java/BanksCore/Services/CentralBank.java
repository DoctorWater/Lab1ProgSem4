package BanksCore.Services;

import BanksCore.Entities.Bank;
import BanksCore.Entities.Client;
import BanksCore.Entities.Transactions.Transfer;
import BanksCore.Exceptions.*;
import BanksCore.Interfaces.IAccount;
import BanksCore.Interfaces.ITransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

public class CentralBank {

  private final List<Bank> banks = new ArrayList<>();
  private final List<ITransaction> transactions = new ArrayList<ITransaction>();

  public Bank addBank(String name) {
    var bank = new Bank(UUID.randomUUID(), name);
    banks.add(bank);
    return bank;
  }

  public void transferMoneyBetweenAccounts(String sourceId, String destinationId, float amount)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException, TooManyOrNoneAccountsWereFoundException, NumberFormatException, InputIsIncorrectException {
    if (amount > 0) {
      IAccount source = getAccountById(UUID.fromString(sourceId));
      IAccount destination = getAccountById(UUID.fromString(destinationId));
      var transfer = new Transfer(UUID.randomUUID(), source, destination, amount);
      transactions.add(transfer);
      transfer.execute();
    } else {
      throw new InputIsIncorrectException();
    }
  }

  public void rollBackTransaction(String transactionId)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException, TransactionAlreadyRolledBackException, NoTransactionFoundException {
    ITransaction transaction = getTransactionById(UUID.fromString(transactionId));
    transaction.rollBack();
  }

  public IAccount getAccountById(UUID id) throws TooManyOrNoneAccountsWereFoundException {
    try {
      Stream<Client> clientStream = banks.stream().flatMap(bank -> bank.getAllClients().stream());
      return clientStream.flatMap(client -> client.getAccounts().stream())
          .filter(currentAccount -> currentAccount.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new TooManyOrNoneAccountsWereFoundException(id);
    }
  }

  public ITransaction getTransactionById(UUID id) throws NoTransactionFoundException {
    try {
      return transactions.stream()
          .filter(currentTransaction -> currentTransaction.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new NoTransactionFoundException(id);
    }

  }

  public Bank getBankById(UUID id) throws NoBankFoundException {
    try {
      return banks.stream()
          .filter(currentBank -> currentBank.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new NoBankFoundException(id);
    }
  }

  public void skipDays(int amount) throws NotEnoughMoneyException {
    List<IAccount> allAccounts = banks.stream().flatMap(bank -> bank.getAccounts().stream())
        .toList();
    for (int i = 0; i < amount; i++) {
      for (IAccount account : allAccounts) {
        account.skipDayAndReturnNewAmount();
      }
    }
  }

  public Collection<Bank> getAllBanks() {
    return new ArrayList<>(banks);
  }

  public Collection<ITransaction> getAllTransactions() {
    return new ArrayList<>(transactions);
  }
}
