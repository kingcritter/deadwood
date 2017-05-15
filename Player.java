import.java.Random;

public class Player {
  private int rank = 1;
  private int money = 0;
  private int credits = 0;
  private int rehearseBonus = 0;
  private Room currRoom;
  private Role currRole = null;
  private Scene currScene = null;
  private boolean moved; 

  public Player(Room startingRoom) {
    currRoom = startingRoom;
  }

  // idea for this: base movement on directions. Client will always
  // list rooms in NESW order (clockwise form top) and assign id's 
  // 1 through 4. This number will be entered by user and passed to 
  // this function. 
  public boolean moveTo(string newRoom) {
    boolean canMove = false;
    for(Room neighbor: currRoom.getAdjacent()){
      if(newRoom.equals(neighbor.getName()){
         currRoom = neighbor;
         canMove = true;
         if(neighbor.isScene()){
            currScene = (Scene) neighbor;
         }else{
            currScene = null;
         }
       }
    }
    return canMove;
  }

  public String getLocation() {
    return currRoom;
  }
  
  public boolean isInScene(){
    if(currScene == null){
      return false;
    }else{
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
  
  public boolean takeRole(string newRole) {
    boolean canTakeRole = false;
    for(Role availableRole: getAvailableRoles()){
      if(newRole.equals(availableRole.getName()){
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
    if(diceRoll < currRole.getBudget()){
      if(currRole.onCard == false){
         money = money + 1;
      }
      return false;
    }else{
      if(currRole.onCard == true){
         credits = credits + 2;
      }else{
         money = money + 1;
         credit = credit + 1;
      }
      currScene.takesLeft = currScene.takesLeft - 1;
      return true;
  }

  // not more than +5
  public boolean rehearse() {
    if(rehearseBonus+1 != currScene.getBudget)(){
      this.rehearseBonus = this.rehearseBonus + 1;
    }
    return true;
  }

  public boolean upgradeWithDollars() {
    ArrayList<Integer> possibleRanks;
    if(rank == 6){
      System.out.println("No further upgrade possible.");
    }else{
      possibleRanks = CastingOffice.showPossibleRanksDollars(rank, money);
      System.out.println("Possible ranks:");
      for(int i = 0; i < possibleRanks.length; i++){
         System.out.println(possibleRanks.get(i));
      }
      System.out.println("Enter the rank you want to upgrade to.");
      boolean getInput = true;
      while(getInput == true){
         Scanner scanner = new Scanner(System.in);
         int newRank = scanner.nextLine();
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
         }else if(newRank == 6){
            rank = 6;
            money = money - 40;
         }else{
            System.out.println("Please enter one of the possible ranks.");
         }
      }
    return true;
  }

  public boolean upgradeWithCredits() {
   ArrayList<Integer> possibleRanks;
    if(rank == 6){
      System.out.println("No further upgrade possible.");
    }else{
      possibleRanks = CastingOffice.showPossibleRanksCredits(rank, credits);
      System.out.println("Possible ranks:");
      for(int i = 0; i < possibleRanks.length; i++){
         System.out.println(possibleRanks.get(i));
      }
      System.out.println("Enter the rank you want to upgrade to.");
      boolean getInput = true;
      while(getInput == true){
         Scanner scanner = new Scanner(System.in);
         int newRank = scanner.nextLine();
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
         }else if(newRank == 6){
            rank = 6;
            credits = credits - 25;
         }else{
            System.out.println("Please enter one of the possible ranks.");
         }
      }
   return true; 
  }

  public void payDollars(int money) {
    this.money += money; 
  }

  public void payCredits(int credits) {
    this.credits += credits;
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
