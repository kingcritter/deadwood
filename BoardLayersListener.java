/*

   Deadwood GUI helper file
   Author: Moushumi Sharmin
   This file shows how to create a simple GUI using Java Swing and Awt Library
   Classes Used: JFrame, JLabel, JButton, JLayeredPane

*/


import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.awt.event.*;
import java.util.*;


public class BoardLayersListener extends JFrame {

  // Private Attributes
  
  // JLabels
  JLabel boardlabel;
  JLabel playerlabel;
  JLabel mLabel;
  JLabel currentPlayerLabel;
  JLabel rehearseBonus;
  JLabel money;
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  JComboBox bMoveList;
  JButton bTakeRole;
  JComboBox bRoleList;
  JButton bUpgrade;
  JComboBox bUpgradeList;

  JButton bEndTurn;
  
  // JLayered Pane
  JLayeredPane bPane;

  /* list of player icons */
  HashMap<Integer, JLabel> playerLabels = new HashMap<>();
  HashMap<deadwood.Scene, JLabel> sceneCardLabels = new HashMap<>();
  HashMap<String, HashMap<Integer, JLabel>> shotLocations = new HashMap<>();
  
  static deadwood.GameBoard game;

  // Constructor
  public BoardLayersListener() {

    // Set the title of the JFrame
    super("Deadwood");
    // Set the exit option for the JFrame
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    // Create the JLayeredPane to hold the display, cards, role dice and buttons

    bPane = getLayeredPane();

    // Create the deadwood board
    boardlabel = new JLabel();
    ImageIcon icon =  new ImageIcon("board.jpg");
    boardlabel.setIcon(icon); 
    boardlabel.setBounds(0,0,icon.getIconWidth(),icon.getIconHeight());

    // Add the board to the lower layer
    bPane.add(boardlabel, new Integer(0));

    /* load in the scene cards and shot counters  */
    ArrayList<deadwood.Scene> sceneList  = game.getSceneList();
    for (deadwood.Scene s : sceneList) {
      deadwood.Card card = s.getCard();
      JLabel cardlabel = new JLabel();
      ImageIcon cIcon =  new ImageIcon("cards/back.png");
      cardlabel.setIcon(cIcon);

      sceneCardLabels.put(s, cardlabel);

      deadwood.Room.Area area = s.getArea();
      cardlabel.setBounds(area.x, area.y ,cIcon.getIconWidth(),cIcon.getIconHeight());
      cardlabel.setOpaque(true);

      // Add the card to the lower layer
      bPane.add(cardlabel, new Integer(1));

      HashMap<Integer, deadwood.Room.Area> shotCounters = s.getShotCounterAreas();
      HashMap<Integer, JLabel> shotCounterLabels = new HashMap<>();
      for (int i = 1; i <= s.getTotalTakes(); i++) {
        area = shotCounters.get(i);
        ImageIcon sIcon =  new ImageIcon("shot.png");
        JLabel shotLabel = new JLabel();
        shotLabel.setIcon(sIcon);
        shotLabel.setBounds(area.x, area.y ,sIcon.getIconWidth(),sIcon.getIconHeight());
        shotLabel.setOpaque(false);
        bPane.add(shotLabel, new Integer(1));
        shotCounterLabels.put(i, shotLabel);
      }
      shotLocations.put(s.getName(), shotCounterLabels);
    }

    // Set the size of the GUI
    setSize(icon.getIconWidth()+200,icon.getIconHeight());

    // Create the CUrr Player label
    JLabel infoCurPlayer = new JLabel("Current Player");
    infoCurPlayer.setBounds(icon.getIconWidth()+40,340,180,20);
    bPane.add(infoCurPlayer,new Integer(2));

    // displays dice color of curr player 
    currentPlayerLabel = new JLabel();
    currentPlayerLabel.setBounds(icon.getIconWidth()+70,360,46,46);
    bPane.add(currentPlayerLabel, new Integer(2));


    // Create the Menu for action buttons
    mLabel = new JLabel("MENU");
    mLabel.setBounds(icon.getIconWidth()+40,0,180,20);
    bPane.add(mLabel,new Integer(2));

    // Create Action buttons
    bAct = new JButton("ACT");
    bAct.setBackground(Color.white);
    bAct.setBounds(icon.getIconWidth()+10, 30,180, 20);
    bAct.addMouseListener(new boardMouseListener());

    bRehearse = new JButton("REHEARSE");
    bRehearse.setBackground(Color.white);
    bRehearse.setBounds(icon.getIconWidth()+10,60,180, 20);
    bRehearse.addMouseListener(new boardMouseListener());

    bMove = new JButton("MOVE");
    bMove.setBackground(Color.white);
    bMove.setBounds(icon.getIconWidth()+10,90,180, 20);
    bMove.addMouseListener(new boardMouseListener());

    bMoveList = new JComboBox();
    bMoveList.setBackground(Color.white);
    bMoveList.setBounds(icon.getIconWidth()+10,120,180, 20);
    bMoveList.addMouseListener(new boardMouseListener());

    bTakeRole = new JButton("TAKE ROLE");
    bTakeRole.setBackground(Color.white);
    bTakeRole.setBounds(icon.getIconWidth()+10,150,180, 20);
    bTakeRole.addMouseListener(new boardMouseListener());

    bRoleList = new JComboBox();
    bRoleList.setBackground(Color.white);
    bRoleList.setBounds(icon.getIconWidth()+10,180,180, 20);
    bRoleList.addMouseListener(new boardMouseListener());

    bUpgrade = new JButton("UPGRADE");
    bUpgrade.setBackground(Color.white);
    bUpgrade.setBounds(icon.getIconWidth()+10,210,180, 20);
    bUpgrade.addMouseListener(new boardMouseListener());

    bUpgradeList = new JComboBox();
    bUpgradeList.setBackground(Color.white);
    bUpgradeList.setBounds(icon.getIconWidth()+10,240,180, 20);
    bUpgradeList.addMouseListener(new boardMouseListener());

    bEndTurn = new JButton("End Turn");
    bEndTurn.setBackground(Color.white);
    bEndTurn.setBounds(icon.getIconWidth()+10,270,180, 20);
    bEndTurn.addMouseListener(new boardMouseListener());

    rehearseBonus = new JLabel("REHEASE BONUS");
    rehearseBonus.setBounds(icon.getIconWidth()+40,300,180,20);
    bPane.add(rehearseBonus,new Integer(2));

    money = new JLabel("monies!");
    money.setBounds(icon.getIconWidth()+30,320,180,20);
    bPane.add(money,new Integer(2));


    // Place the action buttons in the top layer
    bPane.add(bAct, new Integer(2));
    bPane.add(bRehearse, new Integer(2));
    bPane.add(bMove, new Integer(2));
    bPane.add(bMoveList, new Integer(2));
    bPane.add(bEndTurn, new Integer(2));
    bPane.add(bTakeRole, new Integer(2));
    bPane.add(bRoleList, new Integer(2));
    bPane.add(bUpgrade, new Integer(2));
    bPane.add(bUpgradeList, new Integer(2));

    /* initialize the player hashmap and draw them */
    loadPlayers();

    /* set the move combobox */
    updateMoveComboBox();
    updateRoleComboBox();
    updatePlayerInfo();
    greyOutButtons();
  }

  private void loadPlayers() {
    /* quick dirt way to get icons */
    ArrayList<deadwood.Player> playerList = game.getPlayerList();
    for (deadwood.Player p : playerList) {
      deadwood.Room room = p.getLocation();
      deadwood.Room.Area area = room.getArea();

      playerlabel = new JLabel();
      ImageIcon pIcon = getPlayerIcon(p);
      playerlabel.setIcon(pIcon);
      playerlabel.setBounds(area.x + (46 * p.getID()),area.y,46,46);
      bPane.add(playerlabel,new Integer(3));

      /* place player JLabels into hashmap indexed by ID*/
      playerLabels.put(p.getID(), playerlabel);
      
    }
  }

  private void updateCard(deadwood.Scene s) {
    if (s != null) {
      JLabel label = sceneCardLabels.get(s);
      if (!s.visited()) {
        s.visit();
        ImageIcon cardIcon = new ImageIcon("cards/" + s.getCard().getImage());
        label.setIcon(cardIcon);
      } else if (s.isWrapped()) {
        label.setVisible(false);
      }
    }
  }

  private void upgradeList() {
    deadwood.Player p = game.getCurrentPlayer();
    bUpgradeList.removeAllItems();
    if (p.getLocation().getName().equalsIgnoreCase("office")) {
      HashMap<Integer, Integer> dollarRanks = deadwood.CastingOffice.showPossibleRanksDollars(p.getRank(), p.getDollars());
      HashMap<Integer, Integer> creditRanks = deadwood.CastingOffice.showPossibleRanksCredits(p.getRank(), p.getCredits());
      for(int i = 0; i < 5; i++){
        if(dollarRanks.containsKey(i)){
          bUpgradeList.addItem("Rank " + i + " $ " + dollarRanks.get(i));
        }
        if(creditRanks.containsKey(i)){
          bUpgradeList.addItem("Rank " + i + " C " + creditRanks.get(i));
        }
      }
    }

  }

  private ImageIcon getPlayerIcon(deadwood.Player p) {
    String colors[] = {"b", "g", "c", "o", "p", "r", "v", "y"};
    ImageIcon pIcon = new ImageIcon("dice/" + colors[p.getID()] + 
      String.valueOf(p.getRank()) + ".png");
    return pIcon;

  }

  private void updateMoveComboBox() {
    bMoveList.removeAllItems();

    deadwood.Player p = game.getCurrentPlayer();
    deadwood.Room r = p.getLocation();
    for (String roomName : r.getNeighbors()) {
      bMoveList.addItem(roomName);
    }
  }

  private void updatePlayerInfo() {
    deadwood.Player p = game.getCurrentPlayer();
    ImageIcon pIcon = getPlayerIcon(p);
    currentPlayerLabel.setIcon(pIcon);
    rehearseBonus.setText("rehearse bonus = " + p.getRehearseBonus());
    money.setText("money = " + p.getDollars() + " credits = " + p.getCredits());

    JLabel pLabel = playerLabels.get(p.getID());
    pLabel.setIcon(pIcon);
  }

  private void updateRoleComboBox() {
    bRoleList.removeAllItems();

    deadwood.Player p = game.getCurrentPlayer();
    deadwood.Scene s = p.getScene();
    if (s != null) {
      for (deadwood.Role role : p.getAvailableRoles()) {
        bRoleList.addItem(role.getName());
      }
    }
  }

  private void updateShotCounters(deadwood.Scene s) {
    HashMap<Integer, JLabel> shots = shotLocations.get(s.getName());
    for (int i = 1; i <= s.getTotalTakes() - s.getTakesLeft(); i++) {
      JLabel label = shots.get(i);
      label.setVisible(false);
    }
  }

  private void greyOutButtons() {
    if (game.getCurrentPlayer().canMove()) {
      bMove.setEnabled(true);
    } else {
      bMove.setEnabled(false);
    }

    if (game.getCurrentPlayer().canAct()) {
      bAct.setEnabled(true);
      bRehearse.setEnabled(true);
    } else {
      bAct.setEnabled(false);
      bRehearse.setEnabled(false);
    }
  }

  private void updatePlayerLocation(deadwood.Player p) {
    /* get the new room and room area */
    deadwood.Room room = p.getLocation();
    deadwood.Room.Area area = room.getArea();
    
    /* if it's a scene, the icons should go under the card; otherwise,
       place at the top of the room */
    int y = 0;
    if (room.isScene()) {
      y = area.y + area.h;
    } else {
      y = area.y;
    }

    /* set the p icon to new room */
    JLabel pIcon = playerLabels.get(p.getID());
    pIcon.setBounds(area.x + (44 * p.getID()),y,46,46);
  }

  private void setRole(deadwood.Player p){
    int id = p.getID();
    deadwood.Role r = p.getRole();
    deadwood.Room.Area rArea = r.getArea();
    JLabel label = playerLabels.get(id);
    deadwood.Scene scene = p.getScene();
    deadwood.Room.Area sArea = scene.getArea();
    if(r.isOnCard()){
      label.setBounds(sArea.x + rArea.x + 1, sArea.y + rArea.y - 2, 46, 46);
    }else{
      label.setBounds(rArea.x + 3, rArea.y, 46, 46);
    }
  }

  private void sceneWrapper(deadwood.Player p){
    if(p.getScene().isWrapped()){
      JLabel label = playerLabels.get(p.getID());
      ArrayList<deadwood.Player> players = game.getPlayerList();
      for(deadwood.Player p2 : players){
        if(p2.getScene() == p.getScene()){
          updatePlayerLocation(p2);
        }
      }
    }
  }
  
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
  
    // Code for the different button clicks
    public void mouseClicked(MouseEvent e) {
         
      if (e.getSource()== bAct){
        deadwood.Player p = game.getCurrentPlayer();
        p.act();
        updateShotCounters(p.getScene());
        greyOutButtons();
        updateCard(p.getScene());
        System.out.println("Acting is Selected\n");
        sceneWrapper(p);
        updatePlayerInfo();

      }
      else if (e.getSource()== bRehearse){
        deadwood.Player p = game.getCurrentPlayer();
        p.rehearse();
        greyOutButtons();
        updatePlayerInfo();
        System.out.println("Rehearse is Selected\n");
      }
      else if (e.getSource()== bMove){
        String newRoom = (String) bMoveList.getSelectedItem();
        deadwood.Player p = game.getCurrentPlayer();
        p.moveTo(newRoom);
        updatePlayerLocation(p);
        updateMoveComboBox();
        updateRoleComboBox();
        updateCard(p.getScene());
        upgradeList();

        greyOutButtons();
        System.out.println("Move is Selected\n");
      }
      else if (e.getSource() == bEndTurn) {
        game.nextTurn();
        greyOutButtons();
        updateMoveComboBox();
        updateRoleComboBox();
        updatePlayerInfo();
      }
      else if (e.getSource() == bTakeRole) {
        String newRole = (String) bRoleList.getSelectedItem();
        deadwood.Player p = game.getCurrentPlayer();
        p.takeRole(newRole);
        greyOutButtons();
        updateRoleComboBox();
        updatePlayerInfo();
        setRole(p);
      }
        else if (e.getSource() == bUpgrade) {
        deadwood.Player p = game.getCurrentPlayer();
        String newRankChoice = (String) bUpgradeList.getSelectedItem();
        String[] newRank = newRankChoice.split(" ");
        if(newRank[2] == "$"){
          int dollarCost = Integer.parseInt(newRank[3]);
          p.payDollars(-dollarCost);
        }else if(newRank[2] == "C"){
          int creditCost = Integer.parseInt(newRank[3]);
          p.payCredits(-creditCost);
        }
        p.setRank(Integer.parseInt(newRank[1]));
        greyOutButtons();
        updateRoleComboBox();
        updatePlayerInfo();
        setRole(p);
      }
    }
    public void mousePressed(MouseEvent e) {
    }
    public void mouseReleased(MouseEvent e) {
    }
    public void mouseEntered(MouseEvent e) {
    }
    public void mouseExited(MouseEvent e) {
    }
   }


  public static void main(String[] args) {
    if(args.length > 0){
      game = new deadwood.GameBoard(Integer.parseInt(args[0]));
    }else{
      game = new deadwood.GameBoard(2);
    }
    BoardLayersListener board = new BoardLayersListener();
    board.setVisible(true);
  }
}