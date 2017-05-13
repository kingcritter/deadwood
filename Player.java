public class Player {
  private int rank;
  private int money;
  private int credits;
  private int rehearseBonus;
  private Room currRoom;
  private Role currRoll;
  private boolean moved; 

  public Player(Room startingRoom) {

  }

  // idea for this: base movement on directions. Client will always
  // list rooms in NESW order (clockwise form top) and assign id's 
  // 1 through 4. This number will be entered by user and passed to 
  // this function. 
  public boolean moveTo(int id) {
    return true;
  }

  public String getLocation() {
    return true;
  }

  public void takeRole(int roleId) {

  }

  public boolean act() {
    return false;

  }

  // not more than +5
  public boolean rehearse() {
    return true;
  }

  public boolean upgradeWithDollars() {
    return true;
  }

  public boolean upgradeWithCredits() {
   return true; 
  }

  public boolean canMove() {
    if (!moved && currRoll == null) {
      return true;
    } else {
      return false;
    }
  }

  public void endTurn() {
    moved = false;
  }
}