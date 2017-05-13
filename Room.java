import java.util.*;

public class Room {
  private String name;
  private Room north;
  private Room south;
  private Room east;
  private Room west;

  /* creates room with specified name.*/
  public Room(String name) {
    this.name = name;
  }

  /* returns the list of rooms in the order NESW (clockwise from top) */
  public ArrayList<Room> showAdjacentRooms() {
    ArrayList<Room> roomList = new ArrayList<>();

    roomList.add(north);
    roomList.add(east);
    roomList.add(south);
    roomList.add(west);

    return roomList;
  }

  /* returns the name of the room */
  public String getName() {
    return this.name;
  }

  /* sets the rooms in the order NESW (clockwise from top) */
  public void setRooms(Room n, Room e, Room s, Room w) {
    this.north = n;
    this.south = s;
    this.west = w;
    this.east = e;
  }

}