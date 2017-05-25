import java.util.*;

public class Deadwood {
  private static GameBoard game;
  private static Player player;
  private static String id;
  private static Room currRoom;
  private static int credits;
  private static int money;
  private static Role role;
  private static Scene scene;
  private static String input;
  private static Scanner userInput;

  public static void main(String[] args) {
    //get number of players
    userInput = new Scanner(System.in);
    System.out.println("Please enter number of players: ");
    int numPlayers = userInput.nextInt();
    //initialize board
    game = new GameBoard(numPlayers);

    System.out.format("Game Started, %d days\n", game.getTotalDays());

    while (game.isRunning()) {
      /* collect player info */
      player = game.getCurrentPlayer();
      id = Integer.toString(player.getID());

      /* get input from user */
      boolean turnNotOver = true;
      boolean beginningOfTurn = true;
      while (turnNotOver) {
        // refresh info every time this loops 
        currRoom =  player.getLocation();
        credits = player.getCredits();
        money = player.getDollars();
        role = player.getRole();
        scene = player.getScene();

        /* at start of turn, print player info */
        if (beginningOfTurn) {
          commandWho();
          commandWhere();
          beginningOfTurn = false;
        }

        // put a new line because it looks better
        System.out.println("");

        // get input, convert to lowercase
        System.out.print(">>> ");
        input = userInput.next();
        input = input.toLowerCase();

        if (input.equals("who")) {
          commandWho();
        }

        else if (input.equals("where")) {
          commandWhere();
        }

        else if (input.equals("move")) {
          commandMove();
        }

        else if (input.equals("work")) {
          commandWork();
        }

        else if (input.equals("upgrade")) {
          commandUpgrade();
        }

        else if (input.equals("rehearse")) {
          commandRehearse();
        }

        else if (input.equals("act")) {
          commandAct();
        }

        else if (input.equals("end")) {
          System.out.println("You have ended your turn.");
          game.nextTurn();
          turnNotOver = false;
          beginningOfTurn = true;
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

    public static void commandWho() {
      String output = "ID: " + id + ", rank: " + player.getRank();
      output += " ($" + money + ", " + credits + " credits)";
      if (role != null) {
        String roleName = role.getName();
        output += " working " + roleName;
        output += " (+" + player.getRehearseBonus() + ")";
      }

      System.out.println(output);
      userInput.reset();
    }
    public static void commandWhere() {
      String output = currRoom.getName();
      if (scene != null) {
        if (scene.isWrapped()) {
          output += ", scene wrapped";
        } else {
          role = player.getRole();
          if (role != null) {
            output += ", working on " + scene.getCardName(); 
            output += ", budget: " + String.valueOf(scene.getBudget());
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
              output += "(Rank: " + r.getRank() + ", ";
              if (r.isOnCard()) {
                output += "on card";
              } else {
                output += "off card";
              }
              output += ") ";
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

    public static void commandMove() {
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

    public static void commandWork() {
      String roleName = userInput.nextLine();
      if (roleName != null) {
        boolean roleTaken = player.takeRole(roleName);
        if (roleTaken) {
          System.out.println("You are now working on a role");
        } else {
          System.out.println("Not a valid role");
        }
      }
    }

    public static void commandAct() {
      boolean success = player.act();
      if (success) {
        System.out.println("Acting succeded!");
      } else {
        System.out.println("Acting failed :(");
      }
    }

    public static void commandRehearse() {
      boolean success = player.rehearse();
      if (success) {
        System.out.println ("you rehearsed");
      } else {
        System.out.println ("you failed to rehearse");
      }
    }

    public static void commandUpgrade() {
      String type = userInput.next();
      if (type != null) {
        if (userInput.hasNextInt()) {
          int level = userInput.nextInt();
          if (type.equals(" $")) {
            player.upgradeWithDollars(level);

          } else if (type.equals(" cr")) {
            player.upgradeWithCredits(level);
          }
        }
      }
    }
}
