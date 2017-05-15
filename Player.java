public class Player {
  private int rank;
  private int money;
  private int credits;
  private int rehearseBonus;
  private Room currRoom;
  private Role currRole;
  private boolean moved; 

  public Player(Room startingRoom) {

  }

  // idea for this: base movement on directions. Client will always
  // list rooms in NESW order (clockwise form top) and assign id's 
  // 1 through 4. This number will be entered by user and passed to 
  // this function. 
  public boolean moveTo(int id) {
    if(id == 1){
      currRoom = currRoom.neighbors.get(1);
    }else if(id == 2){
      currRoom = currRoom.neighbors.get(2);
    }else if(id == 3){
      currRoom = currRoom.neighbors.get(3);
    }else{
      currRoom = currRoom.neighbors.get(4);
    }
    return true;
  }

  public String getLocation() {
    return this.currRoom;
  }

  public void setRank(int rank){
    this.rank = rank;
  }
  
  public int getRank(){
    return rank;
  }
  
  public void takeRole(int roleId) {

  }

  public boolean act() {
    return false;

  }

  // not more than +5
  public boolean rehearse() {
    if(rehearseBonus+1 != currRole.budget){//does this work?
      this.rehearseBonus = this.rehearseBonus + 1;
    }
    return true;
  }

  public boolean upgradeWithDollars() {//why are the upgrade methods booleans?
    if(rank == 6){
      System.out.println("No further upgrade possible.");
    }else{
      CastingOffice.showPossibleRanksDollars(rank, money);//handle player input here
    return true;
  }

  public boolean upgradeWithCredits() {
   if(rank == 6){
      System.out.println("No further upgrade possible.");
   }else{
      CastingOffice.showPossibleRanksCredits(rank, credits);
   }
   return true; 
  }

  public void payDollars(int money) {
    this.money += money; //should this be += ?
  }

  public void payCredits(int credits) {
    this.credits += credits;//should this be += ?
  }

  public boolean canMove() {
    if (!moved && currRole == null) {
      return true;
    } else {
      return false;
    }
  }

  public void endTurn() {
    moved = false;
  }

  public void leaveRole() {
    currRoll = null;
  }
}
