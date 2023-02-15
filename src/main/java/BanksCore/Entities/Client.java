package BanksCore.Entities;

import BanksCore.Interfaces.IAccount;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * This class defines an owner of accounts and a bank client.
 */
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

    /**
     * Gets a unique ID of the client.
     *
     * @return a unique ID.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets a full list of the client's accounts.
     *
     * @return collection of client's accounts.
     */
    public Collection<IAccount> getAccounts() {
        return new ArrayList<>(accounts);
    }

    /**
     * Gets a name of the client.
     *
     * @return a name of the client.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets a date of birth of the client.
     *
     * @return a date of birth of the client.
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Gets a passport number of the client as a string.
     *
     * @return a passport number of the client.
     */
    public String getPassportNumber() {
        return passportNumber;
    }

    /**
     * Sets a passport number of the client.
     * @param number represents a passport number of the client as a string.
     */
    public void setPassportNumber(String number) {
        passportNumber = number;
        isReliable = !passportNumber.equals("") && !address.equals("");
    }

    /**
     * Gets a postal address of the client as a string.
     *
     * @return a postal address of the client.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets a postal address of the client.
     * @param address represents a postal address of the client.
     */
    public void setAddress(String address) {
        this.address = address;
        isReliable = !passportNumber.equals("") && !this.address.equals("");
    }

    /**
     * Gets a boolean defining reliability of the client.
     * @return reliability of the client.
     */
    public boolean isReliable() {
        return isReliable;
    }

    /**
     * Adds an account to the client.
     * @param account defines an account which will be added.
     */
    public void addAccount(IAccount account) {
        accounts.add(account);
    }

    /**
     * Gets a full list of all accounts associated with the client.
     * @return a full list of all accounts associated with the client.
     */
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
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", passportNumber='" + passportNumber + '\'' +
                ", address='" + address + '\'' +
                ", isReliable=" + isReliable +
                '}';
    }
}
