package BanksCore.Entities;

import BanksCore.Interfaces.Account;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.UUID;

/**
 * Organization, controlling a majority of clients and accounts
 */
public class Bank {
  private final UUID id;
  private final ArrayList<Account> accounts;
  private final ArrayList<Client> clients;
  private final String name;

  public Bank(UUID id, String name) {
    this.id = id;
    this.name = name;
    accounts = new ArrayList<>();
    clients = new ArrayList<>();
  }

  /**
   * Adds an account to the bank's collection.
   * @param account represents the account you want to add.
   */
  public void addAccount(Account account){
    accounts.add(account);
  }

  /**
   * Adds a client to the bank's collection.
   * @param client represents the client you want to add.
   */
  public void addClient(Client client){
    clients.add(client);
  }

  public Collection<Client> getAllClients(){
    return new ArrayList<>(clients);
  }

  public UUID getId() {
    return id;
  }

  public Collection<Account> getAccounts() {
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
    if (!accounts.equals(bank.accounts)) {
      return false;
    }
    if (!clients.equals(bank.clients)) {
      return false;
    }
    return Objects.equals(name, bank.name);
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
        ", clients=" + clients +
        ", name='" + name + '\'' +
        '}';
  }
}
