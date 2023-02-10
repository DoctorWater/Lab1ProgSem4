package BanksConsoleInterface;

import BanksCore.Services.CentralBank;

public class MainClass {

  public static void main(String[] args) {
    var cb = new CentralBank();
    var menu = new ViewMenu(cb);

    while (true) {
      menu.showMenuAndGetInput();
    }
  }
}
