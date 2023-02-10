package BanksCore.Entities;

import BanksCore.Interfaces.IAccount;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

public class Client {

  private final UUID id;
  private final ArrayList<IAccount> accounts;
  private final String name;
  private final Date dateOfBirth;
  private String passportNumber;
  private String address;
  private boolean isReliable;

  public Client(UUID id, String name, Date dateOfBirth, String passportNumber, String address) {
    this.id = id;
    this.accounts = new ArrayList<>();
    this.name = name;
    this.dateOfBirth = dateOfBirth;
    this.passportNumber = passportNumber;
    this.address = address;
    this.isReliable = !passportNumber.equals("") && !address.equals("");
  }

  public UUID getId() {
    return id;
  }

  public Collection<IAccount> getAccounts() {
    return new ArrayList<>(accounts);
  }

  public String getName() {
    return name;
  }


  public Date getDateOfBirth() {
    return dateOfBirth;
  }


  public String getPassportNumber() {
    return passportNumber;
  }

  public void setPassportNumber(String number) {
    passportNumber = number;
    isReliable = !passportNumber.equals("") && !address.equals("");
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
    isReliable = !passportNumber.equals("") && !this.address.equals("");
  }


  public boolean isReliable() {
    return isReliable;
  }


  public void addAccount(IAccount account) {
    accounts.add(account);
  }

  public Collection<IAccount> getAllAccounts() {
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

    Client client = (Client) o;

    if (isReliable != client.isReliable) {
      return false;
    }
    if (!id.equals(client.id)) {
      return false;
    }
    if (accounts != null ? !accounts.equals(client.accounts) : client.accounts != null) {
      return false;
    }
    if (name != null ? !name.equals(client.name) : client.name != null) {
      return false;
    }
    if (dateOfBirth != null ? !dateOfBirth.equals(client.dateOfBirth)
        : client.dateOfBirth != null) {
      return false;
    }
    if (passportNumber != null ? !passportNumber.equals(client.passportNumber)
        : client.passportNumber != null) {
      return false;
    }
    return address != null ? address.equals(client.address) : client.address == null;
  }

  @Override
  public int hashCode() {
    int result = id.hashCode();
    result = 31 * result + (accounts != null ? accounts.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
    result = 31 * result + (passportNumber != null ? passportNumber.hashCode() : 0);
    result = 31 * result + (address != null ? address.hashCode() : 0);
    result = 31 * result + (isReliable ? 1 : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Client{" +
        "id=" + id +
        ", accounts=" + accounts +
        ", name='" + name + '\'' +
        ", dateOfBirth=" + dateOfBirth +
        ", passportNumber='" + passportNumber + '\'' +
        ", address='" + address + '\'' +
        ", isReliable=" + isReliable +
        '}';
  }
}
