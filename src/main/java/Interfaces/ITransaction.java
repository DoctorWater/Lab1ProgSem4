package Interfaces;

import Exceptions.AccountDoesNotSupportOperationException;
import Exceptions.NotEnoughMoneyException;
import Exceptions.TransactionAlreadyRolledBackException;
import java.util.UUID;

public interface ITransaction {
  public ITransaction execute()
      throws NotEnoughMoneyException, AccountDoesNotSupportOperationException;
  public void rollBack()
      throws TransactionAlreadyRolledBackException, NotEnoughMoneyException, AccountDoesNotSupportOperationException;
  public UUID getId();
}
