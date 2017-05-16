import java.util.*;

public class GameBoard {
  private int currDay = 0;
  private int totalDays = 4;
  private Player currPlayer;
  private ArrayList<Player> playerList;
  private ArrayList<Room> roomList;
  private ArrayList<Scene> sceneList;
  private Room trailer;
  private CastingOffice castingOffice;
  private ArrayList<Card> cardDeck;
  private int playerIndex = 0;
  private int numPlayers;
  private boolean running = true;

  public GameBoard(int numPlayers) {
    this.numPlayers = numPlayers;
    playerList = new ArrayList<>();

    /* pull in data from XML files */
    initializeFromXML();

    /* ling Room object references to eachother */
    linkRooms();

    /* distribute cards to all the scenes */
    Collections.shuffle(cardDeck);
    int card = 0;
    for (Scene s : sceneList) {
      s.setCard(cardDeck.get(card));
      card++;
    }

    /* generate all the players */
    for (int i = 0; i < numPlayers; i++) {
      Player p = new Player(i, trailer);      
      playerList.add(p);
    }

    /* randomize the player order */
    Collections.shuffle(playerList);

    /* start with first player */
    currPlayer = playerList.get(playerIndex);

    /* set rules based on player number */
    modifyRules();

  }

  /* After the XML parsing in DataReader is complete, we have
     a roomList, but the rooms only have a list of strings saying
     what their neighbors are. This links them up with actual room
     object references.  */
  private void linkRooms() {
    for (Room room : roomList) {
      for (String neighbor : room.getNeighbors()) {
        for (Room room2 : roomList) {
          if (neighbor.equals(room2.getName())) {
            room.addAdjacent(room2);
            break;
          }
        }
      }
    }
  }

  /* set rules based on player number */
  public void modifyRules() {
    if (numPlayers < 4) {
      totalDays = 3;
    } else if (numPlayers == 5) {
      setPlayerStats(2, 1);
    } else if (numPlayers == 6) {
      setPlayerStats(4, 1);
    } else if (numPlayers > 6) {
      setPlayerStats(0, 2);
    }
  }

  /* randomly distribute cards to scenes and reset scenes */
  private void refreshScenes() {
    Collections.shuffle(cardDeck);
    int card = 0;
    for (Scene s : sceneList) {
      /* remove players from roles */
      for(Role r : s.getAllRoles()) {
        if (r.getPlayer() != null) {
          r.getPlayer().leaveRole();
        }
      }

      s.setCard(cardDeck.get(card));
      card++;
      s.reset();
    }
  }

  private void initializeFromXML() {
    DataReader dr = new DataReader();
    sceneList = dr.getSceneList();
    trailer = dr.getTrailer();
    castingOffice = dr.getCastingOffice();
    cardDeck = dr.getCardList();

    /* generate roomlist */
    roomList = new ArrayList<>();
    for (Scene s : sceneList) {
      roomList.add(s);
    }
    roomList.add(trailer);
    roomList.add(castingOffice);

  }

  /* called by modifyRules() */
  private void setPlayerStats(int credits, int rank) {
    for (Player p : playerList) {
      p.payCredits(credits);
      p.setRank(rank);
    }
  }

  public void nextTurn(Role role) {
    /* check next day/win conditions */
    int scenesWrapped = 0;
    for (Scene s : sceneList) {
      if (s.isWrapped()) {
        scenesWrapped++;
      }
    }

    if (scenesWrapped == (sceneList.size() - 1)) {
      if (currDay == totalDays) {
        endGame();
      } else {
        endDay();
      }
    }

    /* increment playerIndex and loop back to zero if needed*/
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
    /* set players back to trailer, remove them from roles */
    for (Player p : playerList) {
      p.setRoom(trailer);
      p.leaveRole();
    }
    
    /* put new cards out and reset scenes  */
    refreshScenes();
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

  public Player getCurrentPlayer() {
    return currPlayer;
  } 
}