import java.util.*;

public class Player {
  private int id;
  private int rank = 1;
  private int money = 0;
  private int credits = 0;
  private int rehearseBonus = 0;
  private Room currRoom;
  private Role currRole = null;
  private Scene currScene = null;
  private boolean moved; 

  public Player(int id, Room startingRoom) {
    currRoom = startingRoom;
    this.id = id;
  }

  public boolean moveTo(String newRoom) {
    /* get rid of newlines and excess whitespace */
    newRoom = newRoom.replace(System.getProperty("line.separator"), "");
    newRoom = newRoom.trim().replaceAll(" +", " ");

    boolean canMove = false;
    for(Room neighbor : currRoom.getAdjacentRooms()){
      if(newRoom.equals(neighbor.getName())) {
         setRoom(neighbor);
         canMove = true;
       }
    }
    return canMove;
  }

  public Room getLocation() {
    return currRoom;
  }

  public Scene getScene() {
    return currScene;
  }

  public int getID() {
    return id;
  }
  
  public boolean isInScene() {
    if (currScene == null) {
      return false;
    } else {
      return true;
    }
  }

  public void setRank(int rank){
    this.rank = rank;
  }
  
  public int getRank(){
    return rank;
  }
  
  public ArrayList<Role> getAvailableRoles(){
     ArrayList<Role> roles = currScene.getAvailableRoles();
     return roles;
  }
  
  public boolean takeRole(String newRole) {
    boolean canTakeRole = false;
    for(Role availableRole: getAvailableRoles()) {
      if(newRole.equals(availableRole.getName())) {
         currRole = availableRole;
         currRole.setPlayer(this);
         canTakeRole = true;
      }
    }
    return canTakeRole;
  }

  public boolean act() {
    Random rand = new Random();
    int diceRoll = rand.nextInt(6) + 1;
    System.out.println("You rolled " + diceRoll);
    if(diceRoll < currScene.getBudget()){
      System.out.println("Acting failed.");
      if(currRole.isOnCard() == false){
         money = money + 1;
      }
      return false;
    }else{
      System.out.println("Acting succeeded!");
      if(currRole.isOnCard() == true){
         credits = credits + 2;
      }else{
         money = money + 1;
         credits = credits + 1;
      }
      currScene.decrementShotCounter();
      return true;
    }
  }

  // not more than +5
  public boolean rehearse() {
    if(rehearseBonus+1 < currScene.getBudget()) { 
      this.rehearseBonus = this.rehearseBonus + 1;
    }
    return true;
  }

   public boolean upgradeWithDollars(int newRank) {
    ArrayList<Integer> possibleRanks;
    if(rank == 6){
      System.out.println("No further upgrade possible.");
    }else{
      possibleRanks = CastingOffice.showPossibleRanksDollars(rank, money);
      if(possibleRanks.contains(newRank)){
         if(newRank == 2){
            rank = 2;
            money = money - 4;
         }else if(newRank == 3){
            rank = 3;
            money = money - 10;
         }else if(newRank == 4){
            rank = 4;
            money = money - 18;
         }else if(newRank == 5){
            rank = 5;
            money = money - 28;
         }else{
            rank = 6;
            money = money - 40;
         }
         return true;
      }
      return false;
    }

    return false;
  }

  public boolean upgradeWithCredits(int newRank) {
   ArrayList<Integer> possibleRanks;
    if(rank == 6){
      System.out.println("No further upgrade possible.");
    }else{
      possibleRanks = CastingOffice.showPossibleRanksCredits(rank, credits);
      if(possibleRanks.contains(newRank)){
         if(newRank == 2){
            rank = 2;
            credits = credits - 5;
         }else if(newRank == 3){
            rank = 3;
            credits = credits - 10;
         }else if(newRank == 4){
            rank = 4;
            credits = credits - 15;
         }else if(newRank == 5){
            rank = 5;
            credits = credits - 20;
         }else{
            rank = 6;
            credits = credits - 25;
         }
         return true;
      }
      return false;
    }
    return false;
  }

  public void payDollars(int money) {
    this.money += money; 
  }

  public void payCredits(int credits) {
    this.credits += credits;
  }

  public int getDollars() {
    return money;
  }

  public int getCredits() {
    return credits;
  }

  public Role getRole() {
    return currRole;
  }

  public boolean canMove() {
    if (!moved && currRole == null) {
      return true;
    } else {
      return false;
    }
  }

  public void setRoom(Room room) {
    this.currRoom = room;
    
    if(room.isScene()) {
      currScene = (Scene) room;
    } else {
      currScene = null;
    }     
  }

  public void endTurn() {
    moved = false;
  }

  public void leaveRole() {
    currRole.setPlayer(null);
    currRole = null;
  }
}
