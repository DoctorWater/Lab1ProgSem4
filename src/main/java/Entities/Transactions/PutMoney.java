package Entities.Transactions;

import Exceptions.AccountDoesNotSupportOperationException;
import Exceptions.NotEnoughMoneyException;
import Exceptions.TransactionAlreadyRolledBackException;
import Interfaces.IAccount;
import Interfaces.ITransaction;
import java.util.UUID;

public class PutMoney implements ITransaction {
  private UUID id;
  private IAccount account;
  private float moneyAmount;
  private boolean isRolledBack = false;

  public PutMoney(UUID id, IAccount account, float moneyAmount)
  {
    this.id = id;
    this.account = account;
    this.moneyAmount = moneyAmount;
  }

  public ITransaction execute()
  {
    account.addMoney(moneyAmount);
    return this;
  }

  public void rollBack()
      throws TransactionAlreadyRolledBackException, NotEnoughMoneyException, AccountDoesNotSupportOperationException {
    if (isRolledBack)
    {
      throw new TransactionAlreadyRolledBackException(this);
    }

    account.takeMoney(moneyAmount);
    isRolledBack = true;
  }

  public UUID getId()
  {
    return id;
  }
}
