import java.util.*;

public class CLIClient {
  private static GameBoard game;
  private static Player player;

  public static void main(String[] args) {
    //get number of players
    Scanner userInput = new Scanner(System.in);
    System.out.println("Please enter number of players: ");
    int numPlayers = userInput.nextInt();
    //initialize board
    game = new GameBoard(numPlayers);

    while (game.isRunning()) {
      player = game.getCurrentPlayer();
      Room currRoom =  player.getLocation();
      int credits = player.getCredits();
      int money = player.getDollars();
      
      boolean turnNotOver = true;
      while (turnNotOver) {
        String input = userInput.next();
        System.out.println(input);        
      }  
    }

    //while running
      //while player turn (should we have a variable that tracks player or just go through gameboard?)
        //show possible commands (need variables to track remaining possible actions
        //get command from player
        //check if command is valid
        //if 'who'
          //get player(should we return a number?)
          //get role if player is in one
        //if 'where'
          //get current room
          //get scene if room has one
        //if 'move room'
          //attempt to move rooms
        //if 'work part'
          //attempt to take role
        //if 'upgrade $ level'
          //attempt to upgrade (there is a conditional for if upgrade is possible)
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
