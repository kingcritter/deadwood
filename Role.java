public class Role implements Comparable<Role> {
  private int id; 
  private int rank;
  private Player player;
  private boolean onCard;

  /* create role with specified rank */
  public Role(int rank) {
    this.rank = rank;
  }

  /* return the ID*/
  public int getId() {
    return this.id;
  }

  /* return the rank */
  public int getRank() {
    return this.rank;
  }

  /* return the player */
  public Player getPlayer() {
    return this.player;
  }

  public boolean isOnCard() {
    return onCard;
  }

  /* sets the id */
  public void setId(int id) {
    this.id = id;
  }

  /* sets the player */
  public void setPlayer(Player p) {
    this.player = p;
  }

  public void setOnCard(boolean b) {
    this.onCard = b;
  }

  /* implementing compareTo allows Roles to be sorted;
     sorting is by rank, and the natural order is low to high*/
  @Override
  public int compareTo(Role other) {
    int otherRank = other.getRank();
    if (this.rank < otherRank) {
      return -1;
    } else if (this.rank > otherRank) {
      return 1;
    } else {
      return 0;
    }
  }
}