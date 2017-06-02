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
  JLabel cardlabel;
  JLabel playerlabel;
  JLabel mLabel;
  
  //JButtons
  JButton bAct;
  JButton bRehearse;
  JButton bMove;
  JComboBox bMoveList;
  JButton bEndTurn;
  
  // JLayered Pane
  JLayeredPane bPane;

  /* list of player icons */
  HashMap<Integer, JLabel> playerIcons = new HashMap<>();
  
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

    ArrayList<deadwood.Scene> sceneList  = game.getSceneList();
    for (deadwood.Scene s : sceneList) {
      deadwood.Card card = s.getCard();
      cardlabel = new JLabel();
      ImageIcon cIcon =  new ImageIcon("cards/" + 
        String.valueOf(card.getCardNumber()) + ".png");
      cardlabel.setIcon(cIcon); 

      deadwood.Room.Area area = s.getArea();
      cardlabel.setBounds(area.x, area.y ,cIcon.getIconWidth(),cIcon.getIconHeight());
      cardlabel.setOpaque(true);

      /* debug */
      System.out.format("Name: %s, X: %d, Y:%d\n", s.getName(), area.x, area.y);

      // Add the board to the lower layer
      bPane.add(cardlabel, new Integer(1));

      System.out.println("added card");
    }

    // Set the size of the GUI
    setSize(icon.getIconWidth()+200,icon.getIconHeight());


    // Create the Menu for action buttons
    mLabel = new JLabel("MENU");
    mLabel.setBounds(icon.getIconWidth()+40,0,100,20);
    bPane.add(mLabel,new Integer(2));

    // Create Action buttons
    bAct = new JButton("ACT");
    bAct.setBackground(Color.white);
    bAct.setBounds(icon.getIconWidth()+10, 30,100, 20);
    bAct.addMouseListener(new boardMouseListener());

    bRehearse = new JButton("REHEARSE");
    bRehearse.setBackground(Color.white);
    bRehearse.setBounds(icon.getIconWidth()+10,60,100, 20);
    bRehearse.addMouseListener(new boardMouseListener());

    bMove = new JButton("MOVE");
    bMove.setBackground(Color.white);
    bMove.setBounds(icon.getIconWidth()+10,90,100, 20);
    bMove.addMouseListener(new boardMouseListener());

    bMoveList = new JComboBox();
    bMoveList.setBackground(Color.white);
    bMoveList.setBounds(icon.getIconWidth()+10,120,100, 20);
    bMoveList.addMouseListener(new boardMouseListener());

    bEndTurn = new JButton("End Turn");
    bEndTurn.setBackground(Color.white);
    bEndTurn.setBounds(icon.getIconWidth()+10,150,100, 20);
    bEndTurn.addMouseListener(new boardMouseListener());


    // Place the action buttons in the top layer
    bPane.add(bAct, new Integer(2));
    bPane.add(bRehearse, new Integer(2));
    bPane.add(bMove, new Integer(2));
    bPane.add(bMoveList, new Integer(2));
    bPane.add(bEndTurn, new Integer(2));

    /* initialize the player hashmap and draw them */
    drawPlayers();

    /* set the move combobox */
    updateMoveComboBox();
  }

  private void updateMoveComboBox() {
    bMoveList.removeAllItems();

    deadwood.Player p = game.getCurrentPlayer();
    deadwood.Room r = p.getLocation();
    for (String roomName : r.getNeighbors()) {
      bMoveList.addItem(roomName);
    }
  }

  private void drawPlayers() {
    ArrayList<deadwood.Player> playerList = game.getPlayerList();
    for (deadwood.Player p : playerList) {
      deadwood.Room room = p.getLocation();
      deadwood.Room.Area area = room.getArea();
      //Add a dice to represent a player. 
      //Role for Crusty the prospector. The x and y co-ordiantes are taken from Board.xml file
      playerlabel = new JLabel();
      ImageIcon pIcon = new ImageIcon("r2.png");
      playerlabel.setIcon(pIcon);
      //playerlabel.setBounds(114,227,pIcon.getIconWidth(),pIcon.getIconHeight());  
      playerlabel.setBounds(area.x + (46 * p.getID()),area.y,46,46);
      bPane.add(playerlabel,new Integer(3));

      if (!playerIcons.containsValue(p.getID())) {
        playerIcons.put(p.getID(), playerlabel);
      }
    }
  }
  
  // This class implements Mouse Events
  
  class boardMouseListener implements MouseListener{
  
    // Code for the different button clicks
    public void mouseClicked(MouseEvent e) {
         
      if (e.getSource()== bAct){
        System.out.println("Acting is Selected\n");
      }
      else if (e.getSource()== bRehearse){
        System.out.println("Rehearse is Selected\n");
      }
      else if (e.getSource()== bMove){
        String newRoom = (String) bMoveList.getSelectedItem();
        deadwood.Player p = game.getCurrentPlayer();
        p.moveTo(newRoom);
        /* get the new room and room area */
        deadwood.Room room = p.getLocation();
        deadwood.Room.Area area = room.getArea();
        /* set the p icon to new room */
        JLabel pIcon = playerIcons.get(p.getID());
        pIcon.setBounds(area.x + (46 * p.getID()),area.y,46,46);
        updateMoveComboBox();

        System.out.println("Move is Selected\n");
      }
      else if (e.getSource() == bEndTurn) {
        game.nextTurn();
        updateMoveComboBox();
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
    game = new deadwood.GameBoard(2);
    BoardLayersListener board = new BoardLayersListener();
    board.setVisible(true);
  }
}