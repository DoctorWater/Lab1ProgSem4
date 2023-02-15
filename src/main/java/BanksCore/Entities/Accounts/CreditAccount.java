package BanksCore.Entities.Accounts;

import BanksCore.Entities.Client;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Interfaces.Account;
import java.util.UUID;

/**
 * Type of account which you can use to borrow money
 */

public class CreditAccount implements Account {

  private UUID id;
  private float limit;
  private Client client;
  private float moneyAmount;
  private float commission;
  private boolean isReliable;

  public CreditAccount(UUID id, float limit, float commission, float moneyAmount, Client client) {
    this.id = id;
    this.limit = limit;
    this.client = client;
    this.moneyAmount = moneyAmount;
    this.commission = commission;
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
  public float skipDayAndReturnNewAmount() throws NotEnoughMoneyException {
    if (moneyAmount < 0) {
      takeMoney(commission);
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
    moneyAmount += amount;
    return moneyAmount;
  }

  /**
   * Withdraws money from the account
   * @param amount declares how much money will be withdrawn
   * @return new amount of money on the account
   * @throws NotEnoughMoneyException is thrown if account does not have enough money on the account.
   */
  @Override
  public float takeMoney(float amount) throws NotEnoughMoneyException {
    if (isEnoughMoney(amount)) {
      moneyAmount -= amount;
      return moneyAmount;
    }
    throw new NotEnoughMoneyException(this, amount);
  }

  /**
   * Checks is there enough money on the account
   * @param amount declares an amount you want to check
   * @return true -- if there is enough money on the account, false -- if not
   */
  @Override
  public boolean isEnoughMoney(float amount) {
    if (moneyAmount + Math.abs(limit) < amount) {
      return false;
    }

    return true;
  }

  /**
   * Gets the limit of the account
   * @return the limit of the account
   */
  public float getLimit() {
    return limit;
  }

  /**
   * Gets the owner of the account
   * @return the owner of the account
   */

  public Client getClient() {
    return client;
  }

  /**
   * Gets the money amount of the account
   * @return the money amount of the account
   */

  public float getMoneyAmount() {
    return moneyAmount;
  }

  /**
   * Gets the commission per day of the account
   * @return the commission per day of the account
   */

  public float getCommission() {
    return commission;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    CreditAccount that = (CreditAccount) o;

    if (Float.compare(that.limit, limit) != 0) {
      return false;
    }
    if (Float.compare(that.moneyAmount, moneyAmount) != 0) {
      return false;
    }
    if (Float.compare(that.commission, commission) != 0) {
      return false;
    }
    if (isReliable != that.isReliable) {
      return false;
    }
    if (!id.equals(that.id)) {
      return false;
    }
    return client != null ? client.equals(that.client) : that.client == null;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (limit != +0.0f ? Float.floatToIntBits(limit) : 0);
    result = 31 * result + (client != null ? client.hashCode() : 0);
    result = 31 * result + (moneyAmount != +0.0f ? Float.floatToIntBits(moneyAmount) : 0);
    result = 31 * result + (commission != +0.0f ? Float.floatToIntBits(commission) : 0);
    result = 31 * result + (isReliable ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "CreditAccount{" +
        "id=" + id +
        ", limit=" + limit +
        ", client=" + client +
        ", moneyAmount=" + moneyAmount +
        ", commission=" + commission +
        ", isReliable=" + isReliable +
        '}';
  }
}
