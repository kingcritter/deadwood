package deadwood;
import java.util.*;

public class CastingOffice extends Room {
   private static int[] ranks = {2, 3, 4, 5, 6};
   private static int[] costDollars = {4, 10, 18, 28, 40};
   private static int[] costCredits = {5, 10, 15, 20, 25};
   
   public CastingOffice(String name, ArrayList<String> neighbors, Room.Area area){
      super(name, neighbors, area);
   }
   
   public static ArrayList<Integer> showPossibleRanksDollars(int playerRank, int playerDollars){
      ArrayList<Integer> possibleRanks = new ArrayList<>();
      for(int i=playerRank+1; i<ranks.length; i++){
         if(playerDollars >= costDollars[i]){
            System.out.println(ranks[i] + " = " +costDollars[i] + " dollars");
            possibleRanks.add(ranks[i]);
         } 
      }
      return possibleRanks;
   }
   
   public static ArrayList<Integer> showPossibleRanksCredits(int playerRank, int playerCredits){
      ArrayList<Integer> possibleRanks = new ArrayList<>();
      for(int i=playerRank+1; i<ranks.length; i++){
         if(playerCredits >= costCredits[i]){
            System.out.println(ranks[i] + " = " +costCredits[i] + " dollars");
            possibleRanks.add(ranks[i]);
         } 
      }
      return possibleRanks;
   }
 }
