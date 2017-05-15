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
    Random rand = new Random();
    int diceRoll = rand.nextInt(6) + 1;
    if(roll < currRole.getBudget()){
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
      role.takesLeft = role.takesLeft - 1;//does this work?
      return true;
  }

  public boolean rehearse() {
    if(rehearseBonus+1 != currRole.getBudget()){
      this.rehearseBonus = this.rehearseBonus + 1;
    }
    return true;
  }

    public boolean upgradeWithDollars() {//why are the upgrade methods booleans?
    ArrayList<Integer> possibleRanks;
    if(rank == 6){
      System.out.println("No further upgrade possible.");
    }else{
      possibleRanks = CastingOffice.showPossibleRanksDollars(rank, money);//handle player input here
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
