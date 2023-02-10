package BanksCore.Interfaces;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import java.util.UUID;

public interface IAccount {
  UUID getId();
  boolean isReliable();
  void changeReliability();
  float skipDayAndReturnNewAmount() throws NotEnoughMoneyException;
  float getMoney();
  float addMoney(float amount);
  float takeMoney(float amount)
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException;
  boolean isEnoughMoney(float amount);
}
