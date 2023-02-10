package BanksCore.Entities.Accounts;

import BanksCore.Entities.Client;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Interfaces.IAccount;
import java.util.UUID;

public class CreditAccount implements IAccount {

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

  @Override
  public UUID getId() {
    return id;
  }

  @Override
  public boolean isReliable() {
    return isReliable;
  }

  @Override
  public void changeReliability() {
    isReliable = !isReliable;
  }

  @Override
  public float skipDayAndReturnNewAmount() throws NotEnoughMoneyException {
    if (moneyAmount < 0) {
      takeMoney(commission);
    }
    return moneyAmount;
  }

  @Override
  public float getMoney() {
    return moneyAmount;
  }

  @Override
  public float addMoney(float amount) {
    moneyAmount += amount;
    return moneyAmount;
  }

  @Override
  public float takeMoney(float amount) throws NotEnoughMoneyException {
    if (isEnoughMoney(amount)) {
      moneyAmount -= amount;
      return moneyAmount;
    }
    throw new NotEnoughMoneyException(this, amount);
  }

  @Override
  public boolean isEnoughMoney(float amount) {
    if (moneyAmount + Math.abs(limit) < amount) {
      return false;
    }

    return true;
  }

  public float getLimit() {
    return limit;
  }

  public Client getClient() {
    return client;
  }

  public float getMoneyAmount() {
    return moneyAmount;
  }

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
