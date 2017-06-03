package deadwood;
import java.util.*;

public class CastingOffice extends Room {
   private static int[] ranks = {2, 3, 4, 5, 6};
   private static int[] costDollars = {4, 10, 18, 28, 40};
   private static int[] costCredits = {5, 10, 15, 20, 25};
   
   public CastingOffice(String name, ArrayList<String> neighbors, Room.Area area){
      super(name, neighbors, area);
   }
   
   public static HashMap<Integer, Integer> showPossibleRanksDollars(int playerRank, int playerDollars){
      HashMap<Integer, Integer> possibleRanks = new HashMap<>();
      for(int i=playerRank-1; i<ranks.length; i++){
         System.out.println("Rank:" + playerRank + " p$:" + playerDollars + " i:" + i);
         if(playerDollars >= costDollars[i]){
            System.out.println(ranks[i] + " = " +costDollars[i] + " dollars");
            possibleRanks.put(ranks[i], costDollars[i]);
         } 
      }
      return possibleRanks;
   }
   
   public static HashMap<Integer, Integer> showPossibleRanksCredits(int playerRank, int playerCredits){
      HashMap<Integer, Integer> possibleRanks = new HashMap<>();
      for(int i=playerRank-1; i<ranks.length; i++){
         if(playerCredits >= costCredits[i]){
            System.out.println(ranks[i] + " = " +costCredits[i] + " dollars");
            possibleRanks.put(ranks[i], costCredits[i]);
         } 
      }
      return possibleRanks;
   }
 }
