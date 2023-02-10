package Entities.Accounts;

import Entities.Client;
import Exceptions.AccountDoesNotSupportOperationException;
import Exceptions.PercentageListIsCorruptedException;
import Exceptions.SomeArgumentIsNullException;
import Interfaces.IAccount;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DepositAccount implements IAccount {

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

  public UUID getId() {
    return id;
  }

  public boolean isReliable() {
    return isReliable;
  }

  public void changeReliability() {
    isReliable = !isReliable;
  }

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

  public float getMoney() {
    return moneyAmount;
  }

  public float addMoney(float amount) {
    moneyAmount += amount;
    return moneyAmount;
  }

  public float takeMoney(float amount) throws AccountDoesNotSupportOperationException {
    throw new AccountDoesNotSupportOperationException(this);
  }

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
