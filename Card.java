import java.util.*;

public class Card {
  private int budget;
  private ArrayList<Role> onCardRoles;
  private String title;
  private String flavorText;

  public Card(int budget, ArrayList<Role> roles, 
              String title, String flavorText) {
    this.budget = budget;
    this.onCardRoles = roles;
    this.title = title;
    this.flavorText = flavorText;
  }

  public int getBudget() {
    return budget;
  }

  public List<Role> getRoles() {
    return onCardRoles;
  }

  public String getTitle() {
    return title;
  }

  public String getFlavorText() {
    return flavorText;
  }

}