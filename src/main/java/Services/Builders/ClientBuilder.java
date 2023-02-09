package Services.Builders;

import Entities.Client;
import java.util.Date;
import java.util.UUID;

public class ClientBuilder {
  private String name;
  private Date date;
  private String passportNumber;
  private String address;

  public ClientBuilder AssignName(String name){
    this.name = name;
    return this;
  }

  public ClientBuilder AssignBirthDate(Date birthDate){
    this.date = birthDate;
    return this;
  }

  public ClientBuilder AssignPassportNumber(String passportNumber){
    this.passportNumber = passportNumber;
    return this;
  }

  public ClientBuilder AssignAddress(String address){
    this.address = address;
    return this;
  }

  public Client BuildClient(){
    return new Client(UUID.randomUUID(), name, date, passportNumber, address);
  }
}
