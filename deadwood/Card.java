package deadwood;
import java.util.*;

public class Card {
  private int budget;
  private ArrayList<Role> onCardRoles;
  private String title;
  private String flavorText;
  private int cardNumber;
  private String image;

  public Card(int budget, ArrayList<Role> roles, String title, 
              String flavorText, int cardNumber, String image) {
    this.budget = budget;
    this.onCardRoles = roles;
    this.title = title;
    this.flavorText = flavorText;
    this.cardNumber = cardNumber;
    this.image = image;
  }

  public String getImage() {
    return image;
  }

  public int getCardNumber() {
    return cardNumber;
  }

  public int getBudget() {
    return budget;
  }

  public ArrayList<Role> getRoles() {
    return onCardRoles;
  }

  public String getTitle() {
    return title;
  }

  public String getFlavorText() {
    return flavorText;
  }

}