package BanksCore.Interfaces;

import BanksCore.Exceptions.AccountDoesNotSupportOperationException;
import BanksCore.Exceptions.NotEnoughMoneyException;
import BanksCore.Exceptions.TransactionAlreadyRolledBackException;
import java.util.UUID;

public interface ITransaction {
  public ITransaction execute()
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException;
  public void rollBack()
      throws TransactionAlreadyRolledBackException, NotEnoughMoneyException, AccountDoesNotSupportOperationException;
  public UUID getId();
}
