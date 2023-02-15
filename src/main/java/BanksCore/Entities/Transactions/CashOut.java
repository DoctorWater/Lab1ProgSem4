package BanksCore.Entities.Transactions;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.TransactionAlreadyRolledBackException;
import BanksCore.Interfaces.Account;
import BanksCore.Interfaces.Transaction;
import java.util.UUID;

/**
 * Realisation of Transaction interface, providing ability of cashing out money of the account
 */
public class CashOut implements Transaction {

  private UUID id;
  private Account account;
  private float moneyAmount;
  private boolean isRolledBack;

  public CashOut(UUID guid, Account account, float moneyAmount) {
    this.id = guid;
    this.account = account;
    this.moneyAmount = moneyAmount;
  }

  /**
   * Executes the transaction
   * @return Transaction object representing the operation
   * @throws NotEnoughMoneyException if the account has not enough money to accomplish the operation
   * @throws AccountDoesNotSupportOperationException if the account can not withdraw money
   */
  @Override
  public Transaction execute()
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException {
    account.takeMoney(moneyAmount);
    return this;
  }

  /**
   * Cancels the transaction's effect
   * @throws TransactionAlreadyRolledBackException if transaction is already rolled back
   */
  @Override
  public void rollBack() throws TransactionAlreadyRolledBackException {
    if (isRolledBack)
    {
      throw new TransactionAlreadyRolledBackException(this);
    }

    account.addMoney(moneyAmount);
    isRolledBack = true;
  }

  /**
   * Get the unique ID of the transaction
   * @return the unique ID of the transaction
   */
  @Override
  public UUID getId() {
    return id;
  }
}
