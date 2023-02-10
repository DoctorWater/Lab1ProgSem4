package Entities.Transactions;

import Exceptions.AccountDoesNotSupportOperationException;
import Exceptions.NotEnoughMoneyException;
import Exceptions.TransactionAlreadyRolledBackException;
import Interfaces.IAccount;
import Interfaces.ITransaction;
import java.util.UUID;

public class Transfer implements ITransaction {

  private boolean isRolledBack;
  private UUID id;
  private IAccount sourceAccount;
  private IAccount destinationAccount;
  private float amountOfMoney;

  public Transfer(UUID id, IAccount sourceAccount, IAccount destinationAccount,
      float amountOfMoney) {
    this.id = id;
    this.sourceAccount = sourceAccount;
    this.destinationAccount = destinationAccount;
    this.amountOfMoney = amountOfMoney;
    isRolledBack = false;
  }

  @Override
  public ITransaction execute()
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
