public class Card {
  private int budget;
  private ArrayList<Role> onCardRoles;

  /* Create the card with the specified information */
  public Card(int budget, List<Roles> roles) {
    this.budget = budget;
    this.onCardRoles = roles;
  }

  /* returns the budget */
  public int getBudget() {
    return this.budget;
  }

  /* returns the list of roles on the card */
  public List<Role> getRoles() {
    return onCardRoles;
  }

}