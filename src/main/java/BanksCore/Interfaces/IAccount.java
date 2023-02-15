package BanksCore.Interfaces;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import java.util.UUID;

/**
 * Defines a template for all accounts used by banks and clients in the system.
 */
public interface IAccount {
  /**
   * Gets a unique ID of the account.
   * @return a unique ID of the account.
   */
  UUID getId();

  /**
   * Gets a reliability of the account.
   * @return a boolean defining reliability of the account.
   */
  boolean isReliable();

  /**
   * Changes reliability of the account to the opposite.
   */
  void changeReliability();

  /**
   *
   * @return
   * @throws NotEnoughMoneyException
   */
  float skipDayAndReturnNewAmount() throws NotEnoughMoneyException;
  float getMoney();
  float addMoney(float amount);
  float takeMoney(float amount)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException;
  boolean isEnoughMoney(float amount);
}
