package BanksCore.Entities.Transactions;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.TransactionAlreadyRolledBackException;
import BanksCore.Interfaces.Account;
import BanksCore.Interfaces.Transaction;
import java.util.UUID;

/**
 * Realisation of Transaction interface, providing ability of putting money on the account from an outer source
 */
public class PutMoney implements Transaction {
  private UUID id;
  private Account account;
  private float moneyAmount;
  private boolean isRolledBack = false;

  public PutMoney(UUID id, Account account, float moneyAmount)
  {
    this.id = id;
    this.account = account;
    this.moneyAmount = moneyAmount;
  }

  /**
   * Executes the transaction
   * @return Transaction object representing the operation
   */
  public Transaction execute()
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
