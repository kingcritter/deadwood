import java.util.*;

public class Room {
  private String name;
  private boolean isScene = false;
  // private Room north;
  // private Room south;
  // private Room east;
  // private Room west;
  private ArrayList<String> neighbors;
  private ArrayList<Room> neighborRooms;

  /* creates room with specified name.*/
  public Room(String name, ArrayList<String> neighbors) {
    this.name = name;
    this.neighbors = neighbors;
    neighborRooms = new ArrayList<>();
  }

  /* returns the list of rooms in the order NESW (clockwise from top) */
  public ArrayList<Room> getAdjacentRooms() {
    //ArrayList<Room> roomList = new ArrayList<>();

    // roomList.add(north);
    // roomList.add(east);
    // roomList.add(south);
    // roomList.add(west);

    return neighborRooms;
  }

  public void addAdjacent(Room room) {
    neighborRooms.add(room);
  }

  /* returns the name of the room */
  public String getName() {
    return this.name;
  }
  
  public boolean isScene(){
    return isScene;
  }

  public ArrayList<String> getNeighbors() {
    return neighbors;
  }

  /* sets the rooms in the order NESW (clockwise from top) */
  // public void setRooms(Room n, Room e, Room s, Room w) {
  //   this.north = n;
  //   this.south = s;
  //   this.west = w;
  //   this.east = e;
  // }

}
