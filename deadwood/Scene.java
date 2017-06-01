import java.util.*;

public class Scene extends Room {
  private boolean wrapped = false;
  private ArrayList<Role> roles;
  private int takesTotal;
  private int takesLeft;
  private Card card;
  private boolean isScene = true;
  private HashMap<Integer, Room.Area> takesArea;

  /* creates the scene */
  public Scene(String name, ArrayList<String> neighbors, 
               ArrayList<Role> roles, int takes,
               HashMap<Integer, Room.Area> takesArea, Room.Area cardArea) {
    super(name, neighbors, cardArea);
    this.roles = roles;
    this.takesTotal = takes;
    this.takesLeft = takes;
    this.takesArea = takesArea;
  }

  /* Calls functions to pay players and remove them from their roles */
  public void wrapScene() {
    wrapped = true;

    /* pay out to players working the on-card roles*/
    payOnCardBonus();

    /* pay the players working scene roles an ammount equal to role's rank */
    for (Role r : roles) {
      Player p = r.getPlayer();
      if (p != null) {
        p.payDollars(r.getRank());
      }
    }

    /* remove players from scene roles*/
    for (Role r : roles) {
      Player p = r.getPlayer();
      if (p != null) {
        p.leaveRole();
        r.setPlayer(null);
      }
    }

    /* remove players from on-card roles */
    for (Role r : getOnCardRoles()) {
      Player p = r.getPlayer();
      if (p != null) {
        p.leaveRole();
        r.setPlayer(null);
      }
    }

  }

  /* pay out on card bonuses to players */
  private void payOnCardBonus() {
    Random rand = new Random();

    /* roll as many dice as the budget is */
    Stack<Integer> dice = new Stack<>();
    for (int i = 0; i < getBudget(); i++) {
      int die = rand.nextInt(6) + 1;
      dice.push(die);
    }

    /* sort dice from lowest to highest  */
    Collections.sort(dice);

    /* create queue of roles and add all on card roles to it */
    ArrayList<Role> roles = new ArrayList<>();
    for (Role r : getOnCardRoles()) {
      roles.add(r); 
    }

    /* sort roles by rank */
    Collections.sort(roles);

    /* note: using the roles list a FIFO queue*/
    while (dice.empty() == false) {
      /* pop the top dice and top role */
      int die = dice.pop();
      Role r = roles.remove(roles.size() - 1);

      /* pay the player inthat role*/
      Player p = r.getPlayer();
      if (p != null) {
        p.payDollars(die);
      }

      /* put the role back into the beginning of the queue */
      roles.add(0, r);
    }
  }

  /* if wrapped, return empty list */
  public ArrayList<Role> getAvailableRoles() {
    ArrayList<Role> availableRoles = new ArrayList<Role>();
    if (!wrapped) {
      for(Role sceneRole: roles){
        if(sceneRole.getPlayer() == null){
          availableRoles.add(sceneRole);
        }
      }
      for(Role onCardRole: getOnCardRoles()){
        if(onCardRole.getPlayer() == null){
          availableRoles.add(onCardRole);
        }
      }
    }
    return availableRoles;
  }

  public ArrayList<Role> getAllRoles() {
    ArrayList<Role> all = new ArrayList<>();

    for (Role r : getSceneRoles()) {
      all.add(r);
    }

    for (Role r : getOnCardRoles()) {
      all.add(r);
    } 

    return all;
  }

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

  public int getTakesLeft() {
    return takesLeft;
  }

  public HashMap<Integer, Room.Area> getTakesArea() {
    return takesArea;
  }

  public void decrementShotCounter() {
    takesLeft--;
    if (takesLeft <= 0) {
      wrapScene();
    }
  }

  /* returns the wrapped state */
  public boolean isWrapped() {
    return wrapped;
  }

  public void setCard(Card card) {
    this.card = card;
  }
  public boolean isScene() {
    return isScene;
  }

  public String getCardName() {
    return card.getTitle();
  } 

  public void reset() {
    wrapped = false;
    takesLeft = takesTotal;
  }

}
