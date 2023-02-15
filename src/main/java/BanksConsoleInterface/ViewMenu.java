package BanksConsoleInterface;


import BanksCore.Entities.Accounts.CreditAccount;
import BanksCore.Entities.Accounts.DebitAccount;
import BanksCore.Entities.Accounts.DepositAccount;
import BanksCore.Entities.Bank;
import BanksCore.Entities.Client;
import BanksCore.Exceptions.*;
import BanksCore.Interfaces.IAccount;
import BanksCore.Services.CentralBank;

import java.sql.Date;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.UUID;

public class ViewMenu {

  private String input;
  private final CentralBank cb;
  private final Scanner scanner = new Scanner(System.in);

  public ViewMenu(CentralBank cb) {
    this.cb = cb;
  }

  public void showMenuAndGetInput() {
    while (true) {
      System.out.println("Please, select number from the presented options: \n" +
          "1. Create a new bank. \n" +
          "2. Create a new client of a certain bank. \n" +
          "3. Create a new account of a certain client of a certain bank. \n" +
          "4. Make a transaction between existing accounts. \n" +
          "5. Skip some time. \n" +
          "6. Show all banks. \n" +
          "7. Show all clients of a bank. \n" +
          "8. Show all accounts of a client in a certain bank. \n" +
          "9. Roll back transaction by ID. \n" +
          "10. Show all transactions.");
      input = scanner.nextLine();
      if (!input.equals("")) {
        try {
          int i = Integer.parseInt(input);
          switch (i) {
            case 1:
              createBankMenu();
              break;
            case 2:
              createClientMenu();
              break;
            case 3:
              createAccountMenu();
              break;
            case 4:
              transferMoney();
              break;
            case 5:
              skipSomeTime();
              break;
            case 6:
              showAllBanks();
              break;
            case 7:
              showAllClients();
              break;
            case 8:
              showAllAccounts();
              break;
            case 9:
              rollBackTransaction();
              break;
            case 10:
              showAllTransactions();
              break;
            default:
              throw new IllegalArgumentException();
          }

          break;
        } catch (IllegalArgumentException | NullPointerException e) {
          System.out.println("Input is wrong, try again.");
        }
      } else {
        System.out.println("Input is wrong, try again.");
      }
    }
  }

  private void createBankMenu() {
    try {
      while (true) {
        System.out.println("Please, enter the bank's name.");
        input = scanner.nextLine();
        if (input.equals("")) {
          throw new InputIsIncorrectException();
        }

        cb.addBank(input);
        break;
      }
    } catch (NullPointerException e) {
      System.out.println("Input is wrong, try again.");
    } catch (InputIsIncorrectException e) {
      System.out.println(e.getMessage());
    }
  }

  private void createClientMenu() {
    while (true) {
      try {
        System.out.println("Please, enter the bank's ID.");
        input = scanner.nextLine();
        if (input.equals("")) {
          throw new InputIsIncorrectException();
        }

        var bankId = UUID.fromString(input);
        Bank bank = cb.getBankById(bankId);
        System.out.println("Please, enter the client's name.");
        String name = scanner.nextLine();
        System.out.println("Please, enter the client's date of birth.");
        input = scanner.nextLine();
        if (input.equals("") || name.equals("")) {
          System.out.println("Input is wrong, try again.");
        } else {
          var date = Date.valueOf(input);
          System.out.println("Please, enter the client's passport number.");
          String passportNumber = scanner.nextLine();
          System.out.println("Please, enter the client's address.");
          String address = scanner.nextLine();
          var client = new Client(UUID.randomUUID(), name, date, passportNumber, address);
          bank.addClient(client);
          break;
        }
      } catch (NullPointerException e) {
        System.out.println("Input is wrong, try again.");
      } catch (IllegalArgumentException e) {
        System.out.println("Your date is wrong, check it, please.");
      } catch (NoBankFoundException | InputIsIncorrectException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void createAccountMenu() {
    while (true) {
      try {
        System.out.println("Please, enter the bank's ID.");
        input = scanner.nextLine();
        System.out.println("Please, enter the client's ID.");
        String clientId = scanner.nextLine();
        if (input.equals("") | clientId.equals("")) {
          throw new InputIsIncorrectException();
        }

        Bank bank = cb.getBankById(UUID.fromString(input));
        Client client = bank.getAllClients().stream()
            .filter(currentClient -> currentClient.getId().equals(UUID.fromString(clientId)))
            .findFirst().orElseThrow();
        while (true) {
          System.out.println("Please write what kind of account you want to create: \n" +
              "1. Debit account. \n" +
              "2. Credit account. \n" +
              "3. Deposit account.");
          input = scanner.nextLine();
          IAccount account;
          switch (input) {
            case "1":
              account = createDebitAccount(client);
              client.addAccount(account);
              bank.addAccount(account);
              break;
            case "2":
              account = createCreditAccount(client);
              client.addAccount(account);
              bank.addAccount(account);
              break;
            case "3":
              account = createDepositAccount(client);
              client.addAccount(account);
              bank.addAccount(account);
              break;
            default:
              System.out.println("Input is wrong, try again.");
              break;
          }

          if (input.equals("1") | input.equals("2") | input.equals("3")) {
            break;
          }
        }
        break;
      } catch (NullPointerException e) {
        System.out.println("Input is wrong, try again.");
      } catch (NoBankFoundException | InputIsIncorrectException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private CreditAccount createCreditAccount(Client client) {
    while (true) {
      try {
        System.out.println("Please write the limit.");
        String limit = scanner.nextLine();
        if (limit.equals("")) {
          throw new InputIsIncorrectException();
        }

        System.out.println("Please write the initial money amount.");
        String moneyAmount = scanner.nextLine();
        if (moneyAmount.equals("")) {
          throw new InputIsIncorrectException();
        }

        System.out.println("Please write the commission.");
        String commission = scanner.nextLine();
        if (commission.equals("")) {
          throw new InputIsIncorrectException();
        }

        return new CreditAccount(UUID.randomUUID(), Float.parseFloat(limit),
            Float.parseFloat(moneyAmount), Float.parseFloat(commission), client);
      } catch (NumberFormatException | NullPointerException e) {
        System.out.println("Input is wrong, try again.");
      } catch (InputIsIncorrectException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private DebitAccount createDebitAccount(Client client) {
    while (true) {
      try {
        System.out.println("Please write the initial money amount.");
        String moneyAmount = scanner.nextLine();
        if (moneyAmount.equals("")) {
          throw new InputIsIncorrectException();
        }

        System.out.println("Please write the percentage per year (as a fraction).");
        String percents = scanner.nextLine();
        if (percents.equals("")) {
          throw new InputIsIncorrectException();
        }

        return new DebitAccount(Float.parseFloat(percents), UUID.randomUUID(), client,
            Float.parseFloat(moneyAmount));
      } catch (NumberFormatException | NullPointerException e) {
        System.out.println("Input is wrong, try again.");
      } catch (InputIsIncorrectException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private DepositAccount createDepositAccount(Client client) {
    while (true) {
      try {
        System.out.println("Please write the initial money amount.");
        String moneyAmount = scanner.nextLine();
        if (moneyAmount.equals("")) {
          throw new InputIsIncorrectException();
        }

        System.out.println(
            "Please write the percentage per year less initial sum to greater ones (as a fraction and from new line).");
        var percents = new ArrayList<Float>();
        for (int i = 0; i < 4; i++) {
          input = scanner.nextLine();
          var parsedValue = Float.parseFloat(scanner.nextLine());
          percents.add(parsedValue);
        }

        var account = new DepositAccount(UUID.randomUUID(), percents, Float.parseFloat(moneyAmount),
            client);
        return account;
      } catch (NumberFormatException | NullPointerException e) {
        System.out.println("Input is wrong, try again.");
      } catch (InputIsIncorrectException | PercentageListIsCorruptedException |
               SomeArgumentIsNullException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void showAllBanks() {
    System.out.println(cb.getAllBanks());
  }

  private void showAllTransactions() {
    System.out.println(cb.getAllTransactions());
  }

  private void showAllClients() {
    while (true) {
      try {
        System.out.println("Please write the bank's id.");
        var guid = UUID.fromString(scanner.nextLine());
        Bank bank = cb.getBankById(guid);
        System.out.println(bank.getAllClients());
        break;
      } catch (NoBankFoundException e) {
        System.out.println(e.getMessage());
      } catch (NumberFormatException | NullPointerException e) {
        System.out.println("Input is wrong, try again.");
      }
    }
  }

  private void showAllAccounts() {
    while (true) {
      try {
        System.out.println("Please write the bank's id.");
        var bankId = UUID.fromString(scanner.nextLine());
        System.out.println("Please write the client's id.");
        var clientId = UUID.fromString(scanner.nextLine());
        Bank bank = cb.getBankById(bankId);
        Client client = bank.getAllClients().stream()
            .filter(currentClient -> currentClient.getId().equals(clientId)).findFirst()
            .orElseThrow();
        System.out.println(client.getAllAccounts());
        break;
      } catch (NoBankFoundException e) {
        System.out.println(e.getMessage());
      } catch (NoSuchElementException e) {
        System.out.println("No client with this ID was found.");
      }
    }
  }

  private void skipSomeTime() {
    while (true) {
      try {
        System.out.println("Please enter amount of days you want to skip");
        int days = Integer.parseInt(scanner.nextLine());
        cb.skipDays(days);
        break;
      } catch (NumberFormatException | NullPointerException e) {
        System.out.println("Input is wrong.");
      } catch (NotEnoughMoneyException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private void rollBackTransaction() {
    while (true) {
      try {
        System.out.println("Please enter the transaction's ID");
        cb.rollBackTransaction(scanner.nextLine());
      } catch (NotEnoughMoneyException | AccountDoesNotSupportOperationException |
               TransactionAlreadyRolledBackException | NoTransactionFoundException e) {
        System.out.println(e.getMessage());
      } catch (NullPointerException e) {
        System.out.println("Input is wrong.");
      }
    }
  }

  private void transferMoney() {
    while (true) {
      try {
        System.out.println("Please enter the source's ID");
        String sourceId = scanner.nextLine();
        System.out.println("Please enter the destinations's ID");
        String destinationsId = scanner.nextLine();
        System.out.println("Please enter the money amount");
        float moneyAmount = Float.parseFloat(scanner.nextLine());
        cb.transferMoneyBetweenAccounts(sourceId, destinationsId, moneyAmount);
      } catch (NumberFormatException | NullPointerException | InputIsIncorrectException e) {
        System.out.println("Input is wrong.");
      } catch (NotEnoughMoneyException | AccountDoesNotSupportOperationException |
               TooManyOrNoneAccountsWereFoundException e) {
        System.out.println(e.getMessage());
      }
    }
  }
}
