package BanksCore.Entities.Transactions;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.TransactionAlreadyRolledBackException;
import BanksCore.Interfaces.Account;
import BanksCore.Interfaces.Transaction;
import java.util.UUID;



public class Transfer implements Transaction {
  private UUID id;
  private Account sourceAccount;
  private Account destinationAccount;
  private float amountOfMoney;
  private boolean isRolledBack;

  public Transfer(UUID id, Account sourceAccount, Account destinationAccount,
      float amountOfMoney) {
    this.id = id;
    this.sourceAccount = sourceAccount;
    this.destinationAccount = destinationAccount;
    this.amountOfMoney = amountOfMoney;
    isRolledBack = false;
  }

  @Override
  public Transaction execute()
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException {
    sourceAccount.takeMoney(amountOfMoney);
    destinationAccount.addMoney(amountOfMoney);
    return this;
  }

  @Override
  public void rollBack()
      throws TransactionAlreadyRolledBackException, NotEnoughMoneyException, AccountDoesNotSupportOperationException {
    if (isRolledBack) {
      throw new TransactionAlreadyRolledBackException(this);
    }

    destinationAccount.takeMoney(amountOfMoney);
    sourceAccount.addMoney(amountOfMoney);
    isRolledBack = true;
  }

  @Override
  public UUID getId() {
    return null;
  }
}
