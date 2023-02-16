package BanksCore.Entities.Accounts;

import BanksCore.Entities.Client;
import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.PercentageListIsCorruptedException;
import BanksCore.Exceptions.SomeArgumentIsNullException;
import BanksCore.Interfaces.Account;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Type of account which you can use to store money and take them all after lifetime of the account expires
 */
public class DepositAccount implements Account {

  private UUID id;
  private float percentAnnual;
  private Client client;
  private float moneyAmount;
  private boolean isReliable;
  private float moneyForLastMonth = 0;
  private int dayOfMonth = 0; // we count a month as 30 days

  public DepositAccount(UUID id, List<Float> percentsAnnual, float moneyAmount, Client client)
      throws SomeArgumentIsNullException, PercentageListIsCorruptedException {
    if (percentsAnnual == null) {
      throw new SomeArgumentIsNullException();
    }

    if (percentsAnnual.size() != 4) {
      throw new PercentageListIsCorruptedException(percentsAnnual.size());
    }

    this.id = id;
    this.moneyAmount = moneyAmount;
    percentAnnual = DeterminePercent(percentsAnnual);
    if (client == null) {
      throw new SomeArgumentIsNullException();
    } else {
      this.client = client;
    }
    isReliable = client.isReliable();
  }

  /**
   *  Get the unique ID of the account
   * @return the unique ID
   */
  public UUID getId() {
    return id;
  }

  /**
   *  Get a boolean representing reliability of the account
   * @return reliability of the account
   */
  public boolean isReliable() {
    return isReliable;
  }

  /**
   *  Changes reliability of the account to opposite
   */
  public void changeReliability() {
    isReliable = !isReliable;
  }

  /**
   * Skip a day to see how account changes
   * @return new amount of money on the account
   */
  public float skipDayAndReturnNewAmount() {
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
  public float getMoney() {
    return moneyAmount;
  }

  /**
   * Allows to add money on the account
   * @param amount declares how much money will be added
   * @return new amount of money on the account
   */
  public float addMoney(float amount) {
    moneyAmount += amount;
    return moneyAmount;
  }

  /**
   * Money from this account can not be taken!!!
   * @param amount declares how much money will be withdrawn
   * @return new amount of money on the account
   * @throws AccountDoesNotSupportOperationException is thrown every time this method is called
   */
  public float takeMoney(float amount) throws AccountDoesNotSupportOperationException {
    throw new AccountDoesNotSupportOperationException(this);
  }

  /**
   * Checks is there enough money on the account
   * @param amount declares an amount you want to check
   * @return true -- if there is enough money on the account, false -- if not
   */
  public boolean isEnoughMoney(float amount) {
    return !(moneyAmount < amount);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DepositAccount that)) {
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

  private float DeterminePercent(List<Float> percentsAnnual) {
    final float FIRST_LEVEL = 50000;
    final float SECOND_LEVEL = 200000;
    final float THIRD_LEVEL = 500000;
    if (moneyAmount < FIRST_LEVEL) {
      return percentsAnnual.get(0);
    }

    if (moneyAmount > FIRST_LEVEL & moneyAmount < SECOND_LEVEL) {
      return percentsAnnual.get(1);
    }

    if (moneyAmount > SECOND_LEVEL & moneyAmount < THIRD_LEVEL) {
      return percentsAnnual.get(2);
    }

    return percentsAnnual.get(3);
  }

  @Override
  public int hashCode() {
    int result = getId().hashCode();
    result = 31 * result + (percentAnnual != +0.0f ? Float.floatToIntBits(percentAnnual) : 0);
    result = 31 * result + (client != null ? client.hashCode() : 0);
    result = 31 * result + (moneyAmount != +0.0f ? Float.floatToIntBits(moneyAmount) : 0);
    result = 31 * result + (isReliable() ? 1 : 0);
    result =
        31 * result + (moneyForLastMonth != +0.0f ? Float.floatToIntBits(moneyForLastMonth) : 0);
    result = 31 * result + dayOfMonth;
    return result;
  }

  @Override
  public String toString() {
    return "DepositAccount{" +
        "id=" + id +
        ", percentAnnual=" + percentAnnual +
        ", client=" + client +
        ", moneyAmount=" + moneyAmount +
        ", isReliable=" + isReliable +
        ", moneyForLastMonth=" + moneyForLastMonth +
        ", dayOfMonth=" + dayOfMonth +
        '}';
  }
}
