package BanksCore.Interfaces;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.TransactionAlreadyRolledBackException;
import java.util.UUID;
/**
 * Comprehensive interface, uniting different types of transactions under Command pattern.
 */
public interface Transaction {

  /**
   * Executes the transaction with provided attributes.
   * @return representation of the transaction.
   * @throws NotEnoughMoneyException if transaction requires more money than the account is ready to provide.
   * @throws AccountDoesNotSupportOperationException if transaction invokes an operation unavailable for the provided account.
   */
  public Transaction execute()
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException;

  /**
   * Rolles back the transaction. Only available once per transaction.
   * @throws TransactionAlreadyRolledBackException if you try to roll back the transaction twice.
   * @throws NotEnoughMoneyException if there is no enough money to roll back the transaction.
   * @throws AccountDoesNotSupportOperationException if the account is not allowed to execute an operation which is a part of rolling back.
   */
  public void rollBack()
      throws TransactionAlreadyRolledBackException, NotEnoughMoneyException, AccountDoesNotSupportOperationException;
  public UUID getId();
}
