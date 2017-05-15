public class CLIClient {
  private GameBoard game;

  public static void main(String[] args) {
    //get number of players
    Scanner userInput = new Scanner(System.in);
    System.out.println("Please enter number of players: ");
    int numPlayers = userInput.nextInt();
    //initialize board
    game = new Gameboard(numPlayers);
    //modify game rules
    //distribute initial cards
    //while running
      //while player turn
        //show possible commands (need variables to track remaining possible actions
        //get command from player
        //check if command is valid
        //if 'who'
          //get player and role(if any)
        //if 'where'
          //get location and scene(if any)
        //if 'move room'
          //attempt to move rooms
        //if 'work part'
          //attempt to take role
        //if 'upgrade $ level'
          //attempt to upgrade
        //if 'upgrade cr level'
          //attempt to upgrade
        //if 'rehearse'
          //rehearse scene
        //if 'act'
          //attempt to act
        //if 'end'
          //call next turn
  }
}
