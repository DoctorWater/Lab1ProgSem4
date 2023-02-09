package Interfaces;

import Exceptions.NotEnoughMoneyException;
import java.util.UUID;

public interface IAccount {
  UUID getId();
  boolean isReliable();
  void changeReliability();
  float skipDayAndReturnNewAmount() throws NotEnoughMoneyException;
  float getMoney();
  float addMoney(float amount);
  float takeMoney(float amount) throws NotEnoughMoneyException;
  boolean isEnoughMoney(float amount);
}
