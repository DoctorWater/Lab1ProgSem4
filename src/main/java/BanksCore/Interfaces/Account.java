package BanksCore.Interfaces;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import java.util.UUID;

/**
 * Defines a template for all account types used by banks and clients in the system.
 */
public interface Account {
  UUID getId();

  boolean isReliable();

  /**
   * Changes reliability of the account to the opposite.
   */
  void changeReliability();

  /**
   * Skips some number of days for the account.
   * @return new amount of money on the account
   * @throws NotEnoughMoneyException if the account demands money for service and has not enough.
   */
  float skipDayAndReturnNewAmount() throws NotEnoughMoneyException;
  float getMoney();

  /**
   * Adds some money on the account.
   * @param amount represents amount of money to add.
   * @return new amount of money.
   */
  float addMoney(float amount);

  /**
   * Withdraws money from the account.
   * @param amount represents how much money will be withdrawn.
   * @return new amount of money on the account.
   * @throws NotEnoughMoneyException indicates that money to withdraw is bigger than money left on the account.
   * @throws AccountDoesNotSupportOperationException indicates that account does not support withdrawal.
   */
  float takeMoney(float amount)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException;

  /**
   * Checks if it's enough money on the account.
   * @param amount amount you want to check
   * @return true if it's enough, false -- if not.
   */
  boolean isEnoughMoney(float amount);
}
