package Entities;

import Interfaces.IAccount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Bank {
  private final UUID id;
  private final ArrayList<IAccount> accounts;
  private final ArrayList<Client> clients;
  private final String name;

  public Bank(UUID id, String name) {
    this.id = id;
    this.name = name;
    accounts = new ArrayList<>();
    clients = new ArrayList<>();
  }

  public void addAccount(IAccount account){
    accounts.add(account);
  }

  public void addClient(Client client){
    clients.add(client);
  }

  public Collection<Client> getAllClients(){
    return new ArrayList<>(clients);
  }

  public UUID getId() {
    return id;
  }

  public Collection<IAccount> getAccounts() {
    return new ArrayList<>(accounts);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Bank bank = (Bank) o;

    if (!id.equals(bank.id)) {
      return false;
    }
    if (accounts != null ? !accounts.equals(bank.accounts) : bank.accounts != null) {
      return false;
    }
    if (clients != null ? !clients.equals(bank.clients) : bank.clients != null) {
      return false;
    }
    return name != null ? name.equals(bank.name) : bank.name == null;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
    result = 31 * result + (clients != null ? clients.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Bank{" +
        "id=" + id +
        ", accounts=" + accounts +
        ", clients=" + clients +
        ", name='" + name + '\'' +
        '}';
  }
}
