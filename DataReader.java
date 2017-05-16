import javax.xml.parsers.DocumentBuilder; 
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Entity;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.io.File;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException; 
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.*;


import java.util.*;

public class DataReader {
  private static final String BOARDFILE = "board.xml";
  private static final String CARDSFILE = "cards.xml";

  private ArrayList<String> getNeighbors(Element e) {
    ArrayList<String> neighbors = new ArrayList<>();

    NodeList nl = e.getElementsByTagName("neighbors");
    Element ne = (Element) nl.item(0);
    nl = ne.getElementsByTagName("neighbor");

    for (int i = 0; i < nl.getLength(); i++) {
      Element el = (Element) nl.item(i);
      String name = el.getAttribute("name");

      neighbors.add(name);
    }

    return neighbors;
  }

  private ArrayList<Role> getRoles(NodeList nl) {
    ArrayList<Role> roles = new ArrayList<>();

    for (int j = 0; j < nl.getLength(); j++) {
      Element part = (Element) nl.item(j);

      String name = part.getAttribute("name");
      int rank = Integer.parseInt(part.getAttribute("level"));
      String line = part.getElementsByTagName("line").item(0).getTextContent();
      
      Role role = new Role(rank, name, line, true);
      roles.add(role);
    }

    return roles;
  }

  public ArrayList<Scene> getSceneList() {
    /* get the root element and generate a list of set nodes */
    Element root = parseFile(BOARDFILE);
    NodeList setNodeList = root.getElementsByTagName("set");

    ArrayList<Scene> sceneList = new ArrayList<>();

    /* loop though the set nodes */
    for (int i = 0; i < setNodeList.getLength(); i++) {
      Node n = setNodeList.item(i);
      Element e = (Element) n;
      
      /* parse the list of neighboring rooms */
      ArrayList<String> neighbors = getNeighbors(e);

      /* grab the title of the scene */
      String title = e.getAttribute("name");

      /* parse parts */
      NodeList nl = e.getElementsByTagName("parts");
      Element ne = (Element) nl.item(0);
      nl = ne.getElementsByTagName("part");
      ArrayList<Role> roles = getRoles(nl);

      /* get the take number */
      nl = e.getElementsByTagName("takes");
      ne = (Element) nl.item(0);
      nl = ne.getElementsByTagName("take");
      /* just grab first one because they're all in order */
      ne = (Element) nl.item(0);
      int takes = Integer.parseInt(ne.getAttribute("number"));

      /* finally, create the scene object and add it to the list */
      Scene scene = new Scene(title, neighbors, roles, takes);
      sceneList.add(scene);

    }

    return sceneList;
  }

  public Room getTrailer() {
    Element root = parseFile(BOARDFILE);
    NodeList list = root.getElementsByTagName("trailer");
    Element e = (Element) list.item(0);

    ArrayList<String> neighbors = getNeighbors(e);

    Room trailer = new Room("Trailer", neighbors);
    return trailer;
  }

  public CastingOffice getCastingOffice() {
    Element root = parseFile(BOARDFILE);
    NodeList list = root.getElementsByTagName("office");
    Element e = (Element) list.item(0);

    ArrayList<String> neighbors = getNeighbors(e);

    CastingOffice office = new CastingOffice("Casting Office", neighbors);
    return office;
  }

  public ArrayList<Card> getCardList() {
    /* get the root element and generate a list of card nodes */
    Element root = parseFile(CARDSFILE);
    NodeList list = root.getElementsByTagName("card");

    ArrayList<Card> cardList = new ArrayList<>();

    /* loop though the card nodes */
    for (int i = 0; i < list.getLength(); i++) {
      Node n = list.item(i);
      Element e = (Element) n;

      String title = e.getAttribute("name");
      int budget = Integer.parseInt(e.getAttribute("budget"));
      String flavorText = e.getElementsByTagName("scene").item(0).getTextContent();
      ArrayList<Role> roles;
      
      /* get rid of newlines and excess whitespace */
      flavorText = flavorText.replace(System.getProperty("line.separator"), "");
      flavorText = flavorText.trim().replaceAll(" +", " ");
          
      NodeList roleNodes = e.getElementsByTagName("part");

      roles = getRoles(roleNodes);
      

      Card card = new Card(budget, roles, title, flavorText);
      cardList.add(card);

    }
    
    return cardList;
  }
  
  private Element parseFile(String filename) {
    Element root = null;
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder(); 
      Document doc = db.parse(new File(filename));
      
      root = doc.getDocumentElement();      
    } catch (Exception e) {
      // ignore for now
      System.out.println("XML Parsing went awry");
      System.exit(0);
    }

    return root;
  }

  public static void main(String[] args) {
    DataReader dr = new DataReader();
    ArrayList<Card> cards = dr.getCardList();

    // for (Card card : cards) {
    //   System.out.println(card.getTitle());
    //   for (Role role : card.getRoles()) {
    //     System.out.format("    %s%n", role.getName());
    //   }
    // }

    ArrayList<Scene> scenes = dr.getSceneList();
    for (Scene scene : scenes) {
      System.out.println(scene.getOnCardRoles());
      // for (Role r : scene.getOnCardRoles()) {
      //   System.out.println(r.getName());
      // }
    }
  }
}