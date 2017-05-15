import java.util.*;

public class GameBoard {
  private int currDay = 0;
  private int totalDays = 4;
  private Player currPlayer;
  private ArrayList<Player> playerList;
  private ArrayList<Room> roomList;
  private ArrayList<Scene> sceneList;
  private Room trailer;
  private int playerIndex = 0;
  private int numPlayers;
  private running = true;

  public GameBoard(int numPlayers) {
    // pull xml shit


    this.numPlayers = numPlayers;

    /* generate all the players */
    for (int i = 0; i < numPlayers; i++) {
      Player p = new Player(null);      
      playerList.add(p);
    }

    /* randomize the order */
    Collections.shuffle(playerList);

    /* start with first player */
    currPlayer = playerList.get(playerIndex);

    /* set rules based on player number */
    modifyRules();
  }

  /* set rules based on player number */
  public void modifyRules() {
    if (numPlayers < 4) {
      totalDays = 3;
    } else if (numPlayers = 5) {
      setPlayerStats(2, 1);
    } else if (numPlayers = 6) {
      setPlayerStats(4, 1);
    } else if (numPlayers > 6) {
      setPlayerStats(0, 2);
    }
  }

  /* called by modifyRules() */
  private void setPlayerStats(int credits, int rank) {
    for (Player p : playerList) {
      p.payCredits(credits);
      p.setRank(rank);
    }
  }

  public void nextTurn(Role role) {
    // check next day/win conditions
    int scenesWrapped = 0;
    for (Scene s : sceneList) {
      if (s.isWrapped()) {
        scenesWrapped++;
      }
    }

    if (scenesWrapped = (sceneList.size() - 1)) {
      if (currDay == totalDays) {
        endGame();
      } else {
        endDay();
      }
    }

    /* increment and loop back to zero if needed*/
    playerIndex++;
    if (playerIndex >= numPlayers) {
      playerIndex = 0;
    }

    currPlayer = playerList.get(playerIndex);
  }

  private void endGame() {
    running = false;
  }

  public void endDay() {
    // set players back to trailer, remove them from roles
    for (Player p : playerList) {
      p.moveTo(trailer);
      p.leaveRole();
    }
    
    // put new cards out

    // reset scenes (remove shot counters, clear actors, etc)

  }

  /* Score = dollars + credits + (5 * rank) */
  public void totalScores() {
    HashMap<Player, Integer> scores = new HashMap<>();
    
    for (Player p : playerList) {
      Integer score = 0;
      score += p.getCredits();
      score += p.getDollars();
      score += 5 * p.getRank();

      scores.put(p, score);
    }

  }

  public boolean isRunning() {
    return running;
  }
}