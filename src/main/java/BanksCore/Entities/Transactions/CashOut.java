package BanksCore.Entities.Transactions;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.TransactionAlreadyRolledBackException;
import BanksCore.Interfaces.IAccount;
import BanksCore.Interfaces.ITransaction;
import java.util.UUID;

public class CashOut implements ITransaction {

  private UUID id;
  private IAccount account;
  private float moneyAmount;
  private boolean isRolledBack;

  public CashOut(UUID guid, IAccount account, float moneyAmount) {
    this.id = guid;
    this.account = account;
    this.moneyAmount = moneyAmount;
  }

  @Override
  public ITransaction execute()
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException {
    account.takeMoney(moneyAmount);
    return this;
  }

  @Override
  public void rollBack() throws TransactionAlreadyRolledBackException {
    if (isRolledBack)
    {
      throw new TransactionAlreadyRolledBackException(this);
    }

    account.addMoney(moneyAmount);
    isRolledBack = true;
  }

  @Override
  public UUID getId() {
    return id;
  }
}
