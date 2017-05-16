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
      /* collect player info */
      player = game.getCurrentPlayer();
      String id = Integer.toString(player.getID());

      /* get input from user */
      boolean turnNotOver = true;
      while (turnNotOver) {
        // refresh info every time this loops 
        Room currRoom =  player.getLocation();
        int credits = player.getCredits();
        int money = player.getDollars();
        Role role = player.getRole();
        Scene scene = player.getScene();

        // put a new line because it looks better
        System.out.println("");

        // get input, convert to lowercase
        String input = userInput.next();
        input = input.toLowerCase();

        if (input.equals("who")) {
          String output = "ID: " + id + " ($" + money + ", " + credits + ")";
          if (role != null) {
            String roleName = role.getName();
            output += " working " + roleName;
          }

          System.out.println(output);
          userInput.reset();
        }

        else if (input.equals("where")) {
          String output = currRoom.getName();
          if (scene != null) {
            if (scene.isWrapped()) {
              output += ", scene wrapped";
            } else {
              role = player.getRole();
              if (role != null) {
                output += ", working on " + scene.getName(); 
              } else {
                output += ", which is still shooting\n";
                ArrayList<Role> roles = player.getAvailableRoles();
                if (roles.size() > 0) {
                  output += "Roles Availible:\n";
                } else {
                  output += "No more roles availible\n";
                }
                for (Role r : roles) {
                  output += r.getName() +": ";
                  output += "\"" + r.getLine() + "\"\n";
                }
              } // end roll == null 
            } // end scene not wrapped
          } // end scene not null
          if (player.canMove()) {
            output += "\ncan move to: ";
            ArrayList<String> nearby = currRoom.getNeighbors();
            for (int i = 0; i < nearby.size(); i++) {
              output += nearby.get(i);
              if (i != nearby.size() - 1) {
                output += ", ";
              }
            }
          }
          System.out.println(output);
        }

        else if (input.equals("move")) {
          String roomName = userInput.nextLine();
          if (roomName != null) {
            boolean moved = player.moveTo(roomName);
            if (moved) {
              System.out.println("moved to" + roomName);
            } else {
              System.out.println("Not a valid move!");
            }

          } else {
            System.out.println("Please specify a room");
          }
        }
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
