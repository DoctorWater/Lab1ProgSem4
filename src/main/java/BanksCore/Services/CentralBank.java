package BanksCore.Services;

import BanksCore.Entities.Bank;
import BanksCore.Entities.Client;
import BanksCore.Entities.Transactions.Transfer;
import BanksCore.Exceptions.*;
import BanksCore.Interfaces.Account;
import BanksCore.Interfaces.Transaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

public class CentralBank {

  private final List<Bank> banks = new ArrayList<>();
  private final List<Transaction> transactions = new ArrayList<Transaction>();

  /**
   * Adds a new bank to the central bank's collection.
   * @param name represents a name of the bank.
   * @return created bank.
   */
  public Bank addBank(String name) {
    var bank = new Bank(UUID.randomUUID(), name);
    banks.add(bank);
    return bank;
  }

  /**
   *
   * @param sourceId represents ID of the account from which the money will be withdrawn.
   * @param destinationId represents ID of the account to which the money will be added.
   * @param amount represent the amount of money to transfer.
   * @throws NotEnoughMoneyException if there is no enough money on the source account.
   * @throws AccountDoesNotSupportOperationException if one of the accounts does not support transfer.
   * @throws TooManyOrNoneAccountsWereFoundException if one or both accounts' IDs were not found.
   * @throws NumberFormatException if a user input literals instead of numeric.
   * @throws InputIsIncorrectException if a user input incorrect values.
   */
  public void transferMoneyBetweenAccounts(String sourceId, String destinationId, float amount)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException, TooManyOrNoneAccountsWereFoundException, NumberFormatException, InputIsIncorrectException {
    if (amount > 0) {
      Account source = getAccountById(UUID.fromString(sourceId));
      Account destination = getAccountById(UUID.fromString(destinationId));
      var transfer = new Transfer(UUID.randomUUID(), source, destination, amount);
      transactions.add(transfer);
      transfer.execute();
    } else {
      throw new InputIsIncorrectException();
    }
  }

  /**
   * Rolles back the transaction found by its ID.
   * @param transactionId represent an ID of the transaction you want to roll back.
   * @throws NotEnoughMoneyException if the account has not enough money to roll back the transaction.
   * @throws AccountDoesNotSupportOperationException if the rollback requires an operation which is not supported by one of the accounts.
   * @throws TransactionAlreadyRolledBackException if the transaction has already been rolled back.
   * @throws NoTransactionFoundException if there is no registered transaction with the provided ID.
   */
  public void rollBackTransaction(String transactionId)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException, TransactionAlreadyRolledBackException, NoTransactionFoundException {
    Transaction transaction = getTransactionById(UUID.fromString(transactionId));
    transaction.rollBack();
  }

  /**
   * Finds an account with the provided ID.
   * @param id represents the ID of the account to be found.
   * @return the found account.
   * @throws TooManyOrNoneAccountsWereFoundException if there is no account with the provided ID or something went wrong.
   */
  public Account getAccountById(UUID id) throws TooManyOrNoneAccountsWereFoundException {
    try {
      Stream<Client> clientStream = banks.stream().flatMap(bank -> bank.getAllClients().stream());
      return clientStream.flatMap(client -> client.getAccounts().stream())
          .filter(currentAccount -> currentAccount.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new TooManyOrNoneAccountsWereFoundException(id);
    }
  }

  /**
   * Finds a transaction with the provided ID.
   * @param id represents the ID of the transaction to be found.
   * @return the found transaction.
   * @throws NoTransactionFoundException if there is no transaction with the provided ID.
   */
  public Transaction getTransactionById(UUID id) throws NoTransactionFoundException {
    try {
      return transactions.stream()
          .filter(currentTransaction -> currentTransaction.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new NoTransactionFoundException(id);
    }

  }

  /**
   * Finds a bank with the provided ID.
   * @param id represents the ID of the bank to be found.
   * @return the found bank.
   * @throws NoBankFoundException if there is no bank with the provided ID.
   */
  public Bank getBankById(UUID id) throws NoBankFoundException {
    try {
      return banks.stream()
          .filter(currentBank -> currentBank.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new NoBankFoundException(id);
    }
  }

  /**
   * Skips some provided amount of days for all accounts in all banks.
   * @param amount represents the amount of days to skip.
   * @throws NotEnoughMoneyException if some account has not enough money to pay for service.
   */
  public void skipDays(int amount) throws NotEnoughMoneyException {
    List<Account> allAccounts = banks.stream().flatMap(bank -> bank.getAccounts().stream())
        .toList();
    for (int i = 0; i < amount; i++) {
      for (Account account : allAccounts) {
        account.skipDayAndReturnNewAmount();
      }
    }
  }

  public Collection<Bank> getAllBanks() {
    return new ArrayList<>(banks);
  }

  public Collection<Transaction> getAllTransactions() {
    return new ArrayList<>(transactions);
  }
}
