package Services;

import Entities.Bank;
import Entities.Client;
import Entities.Transactions.Transfer;
import Exceptions.*;
import Interfaces.IAccount;
import Interfaces.ITransaction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Stream;

public class CentralBank {

  private final List<Bank> banks = new ArrayList<>();
  private final List<ITransaction> transactions = new ArrayList<ITransaction>();

  public Bank AddBank(String name) {
    var bank = new Bank(UUID.randomUUID(), name);
    banks.add(bank);
    return bank;
  }

  public void TransferMoneyBetweenAccounts(String sourceId, String destinationId, float amount)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException, TooManyOrNoneAccountsWereFoundException {
    IAccount source = GetAccountById(UUID.fromString(sourceId));
    IAccount destination = GetAccountById(UUID.fromString(destinationId));
    var transfer = new Transfer(UUID.randomUUID(), source, destination, amount);
    transactions.add(transfer);
    transfer.execute();
  }

  public void RollBackTransaction(String transactionId)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException, TransactionAlreadyRolledBackException, NoTransactionFoundException {
    ITransaction transaction = GetTransactionById(UUID.fromString(transactionId));
    transaction.rollBack();
  }

  public IAccount GetAccountById(UUID id) throws TooManyOrNoneAccountsWereFoundException {
    try {
      Stream<Client> clientStream = banks.stream().flatMap(bank -> bank.getAllClients().stream());
      return clientStream.flatMap(client -> client.getAccounts().stream())
          .filter(currentAccount -> currentAccount.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new TooManyOrNoneAccountsWereFoundException(id);
    }
  }

  public ITransaction GetTransactionById(UUID id) throws NoTransactionFoundException {
    try {
      return transactions.stream()
          .filter(currentTransaction -> currentTransaction.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new NoTransactionFoundException(id);
    }

  }

  public Bank GetBankById(UUID id) throws NoBankFoundException {
    try {
      return banks.stream()
          .filter(currentBank -> currentBank.getId().equals(id)).findFirst()
          .orElseThrow();
    } catch (NoSuchElementException e) {
      throw new NoBankFoundException(id);
    }
  }

  public void SkipDays(int amount) throws NotEnoughMoneyException {
    List<IAccount> allAccounts = banks.stream().flatMap(bank -> bank.getAccounts().stream()).toList();
    for (int i=0; i<amount; i++){
      for (IAccount account: allAccounts) {
        account.skipDayAndReturnNewAmount();
      }
    }
  }

  public Collection<Bank> GetAllBanks() {
    return new ArrayList<>(banks);
  }

  public Collection<ITransaction> GetAllTransactions() {
    return new ArrayList<>(transactions);
  }
}
