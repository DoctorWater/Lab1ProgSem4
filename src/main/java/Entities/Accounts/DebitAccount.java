package Entities.Accounts;

import Entities.Client;
import Exceptions.NotEnoughMoneyException;
import Interfaces.IAccount;
import java.util.UUID;

public class DebitAccount implements IAccount {

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

  @Override
  public float getMoney() {
    return moneyAmount;
  }

  @Override
  public float addMoney(float amount) {
    return moneyAmount += amount;
  }

  @Override
  public float takeMoney(float amount) throws NotEnoughMoneyException {
    if (!isEnoughMoney(amount))
    {
      throw new NotEnoughMoneyException(this, amount);
    }

    moneyAmount -= amount;
    return moneyAmount;
  }

  @Override
  public boolean isEnoughMoney(float amount) {
    if (moneyAmount < amount)
    {
      return false;
    }

    return true;
  }
}
