package BanksCore.Entities.Accounts;

import BanksCore.Entities.Client;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Interfaces.Account;
import java.util.Objects;
import java.util.UUID;
/**
 * Type of account with no commission, which you can use to take and deposit money
 */

public class DebitAccount implements Account {

  private final float percentAnnual;
  private final UUID id;
  private final Client client;
  private float moneyAmount;
  private boolean isReliable;
  private float moneyForLastMonth;
  private int dayOfMonth; // we count a month as 30 days

  public DebitAccount(float percentAnnual, UUID id, Client client, float moneyAmount) {
    this.percentAnnual = percentAnnual;
    this.id = id;
    this.client = client;
    this.moneyAmount = moneyAmount;
    moneyForLastMonth = 0;
    dayOfMonth = 0;
  }

  /**
   *  Get the unique ID of the account
   * @return the unique ID
   */
  @Override
  public UUID getId() {
    return id;
  }

  /**
   *  Get a boolean representing reliability of the account
   * @return reliability of the account
   */
  @Override
  public boolean isReliable() {
    return isReliable;
  }

  /**
   *  Changes reliability of the account to opposite
   */
  @Override
  public void changeReliability() {
    isReliable = !isReliable;
  }

  /**
   * Skip a day to see how account changes
   * @return new amount of money on the account
   */
  @Override
  public float skipDayAndReturnNewAmount(){
    moneyForLastMonth += moneyAmount * (percentAnnual / 365);
    if (dayOfMonth == 30) {
      dayOfMonth = 0;
      moneyAmount += moneyForLastMonth;
      moneyForLastMonth = 0;
    } else {
      dayOfMonth++;
    }

    return moneyAmount;
  }

  /**
   * Get exact amount of money left on the account
   * @return amount of money left on the account
   */
  @Override
  public float getMoney() {
    return moneyAmount;
  }

  /**
   * Allows to add money on the account
   * @param amount declares how much money will be added
   * @return new amount of money on the account
   */
  @Override
  public float addMoney(float amount) {
    return moneyAmount += amount;
  }

  /**
   * Takes money from the account
   * @param amount declares how much money will be withdrawn
   * @return new amount of money on the account
   * @throws NotEnoughMoneyException is thrown if account does not have enough money on the account.
   */
  @Override
  public float takeMoney(float amount) throws NotEnoughMoneyException {
    if (!isEnoughMoney(amount))
    {
      throw new NotEnoughMoneyException(this, amount);
    }

    moneyAmount -= amount;
    return moneyAmount;
  }

  /**
   * Checks is there enough money on the account
   * @param amount declares an amount you want to check
   * @return true -- if there is enough money on the account, false -- if not
   */
  @Override
  public boolean isEnoughMoney(float amount) {
    if (moneyAmount < amount)
    {
      return false;
    }

    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DebitAccount that)) {
      return false;
    }

    if (Float.compare(that.percentAnnual, percentAnnual) != 0) {
      return false;
    }
    if (Float.compare(that.moneyAmount, moneyAmount) != 0) {
      return false;
    }
    if (isReliable() != that.isReliable()) {
      return false;
    }
    if (Float.compare(that.moneyForLastMonth, moneyForLastMonth) != 0) {
      return false;
    }
    if (dayOfMonth != that.dayOfMonth) {
      return false;
    }
    if (!getId().equals(that.getId())) {
      return false;
    }
    return Objects.equals(client, that.client);
  }

  @Override
  public int hashCode() {
    int result = (percentAnnual != +0.0f ? Float.floatToIntBits(percentAnnual) : 0);
    result = 31 * result + getId().hashCode();
    result = 31 * result + (client != null ? client.hashCode() : 0);
    result = 31 * result + (moneyAmount != +0.0f ? Float.floatToIntBits(moneyAmount) : 0);
    result = 31 * result + (isReliable() ? 1 : 0);
    result =
        31 * result + (moneyForLastMonth != +0.0f ? Float.floatToIntBits(moneyForLastMonth) : 0);
    result = 31 * result + dayOfMonth;
    return result;
  }
}
