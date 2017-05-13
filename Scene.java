import java.util.*;

public class Scene extends Room {
  private boolean wrapped = false;
  private ArrayList<Role> roles;
  private int shotCounter;
  private Card card;

  /* creates the scene */
  public Scene(List<Roles> roles, int shotCounter) {
    this.roles = roles;
    this.shotCounter = shotCounter;
  }

  /* Calls functions to pay players and remove them from their roles */
  public void wrapScene() {
    wrapped = true;

    /* pay out to players owrking the on-card roles*/
    payOnCardBonus();

    /* pay the players working scene roles an ammount equal to role's rank */
    for (Role r : roles) {
      Player p = r.getPlayer();
      if (p != null) {
        p.pay(r.getRank());
      }
    }

    /* remove players from scene roles*/
    for (Role r : roles) {
      r.getPlayer().leaveRoll();
      r.setPlayer(null);
    }

    /* remove players from on-card roles */
    for (Role r : getOnCardRoles()) {
      r.getPlayer().leaveRoll();
      r.setPlayer(null);
    }

  }

  /* pay out on card bonuses to players */
  private void payOnCardBonus() {
    Random rand = new Random();

    /* roll as many dice as the budget is */
    Stack<int> dice = new Stack<>();
    for (i = 0; i < getBudget(); i++) {
      int die = rand.nextInt(6) + 1;
      dice.push(die);
    }

    /* sort dice from lowest to hieghest */
    Collections.sort(dice);

    /* create queue of roles and add all on card roles to it */
    Queue<Role> roles = new Queue<>();
    for (Role r : getOnCardRoles()) {
      roles.add(r); 
    }

    /* sort roles by rank */
    Collections.sort(roles);

    while (dice.empty() == false) {
      /* pop the top dice and top role */
      int die = dice.pop();
      Role r = roles.remove();

      /* pay the player inthat role*/
      Player p = r.getPlayer();
      if (p != null) {
        p.pay(die);
      }

      /* put the player back into the beginning of the queue */
      roles.add(p);
    }
  }

  // public ArrayList<Role> getAvailableRoles() {
  //   return null;
  // }

  /* lists all roles, filled and unfilled, not on the card */
  public ArrayList<Role> getSceneRoles() {
    return roles;
  }

  /* lists all roles on card, filled and unfilled */
  public ArrayList<Role> getOnCardRoles() {
    return card.getRoles();
  }

  /* returns the budget from the card */
  public int getBudget() {
    return card.getBudget();
  }

  /* returns the wrapped state */
  public boolean isWrapped() {
    return wrapped;
  }

}