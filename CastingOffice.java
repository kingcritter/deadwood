import java.util.*;

public class CastingOffice extends Room {
   private ArrayList<Integer> ranks = {2, 3, 4, 5, 6};
   private ArrayList<Integer> costDollars = {4, 10, 18, 28, 40};
   private ArrayList<Integer> costCredits = {5, 10, 15, 20, 25};
   
   public CastingOffice(String name, ArrayList<String> neighbors){
      super(name, neighbors);
   }
   
   public ArrayList<Integer> showPossibleRanksDollars(int playerRank, int playerDollars){
      ArrayList<Integer> possibleRanks;
      for(int i=playerRank+1; i<ranks.length; i++){
         if(playerDollars >= costDollars.get(i)){
            System.out.println(ranks.get(i) + " = " +costDollars.get(i) + " dollars");
            possibleRanks.add(ranks.get(i));
         } 
      }
      return
   }
   
   public ArrayList<Integer> showPossibleRanksCredits(int playerRank, int playerCredits){
      ArrayList<Integer> possibleRanks;
      for(int i=playerRank+1; i<ranks.length; i++){
         if(playerCredits >= costCredits.get(i)){
            System.out.println(ranks.get(i) + " = " +costCredits.get(i) + " dollars");
            possibleRanks.add(ranks.get(i));
         } 
      }
   }
