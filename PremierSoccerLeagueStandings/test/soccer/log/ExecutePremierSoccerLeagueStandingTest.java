package soccer.log;

import static org.junit.Assert.assertTrue;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;



public class ExecutePremierSoccerLeagueStandingTest{
   
   @Test
   public void testAccuratePremierSoccerLeagueStanding(){
      
      Standings standings = new Standings();
      AtomicInteger primaryKeyGenerator = new AtomicInteger();

      String[] firstMatchResultArray = "Lions 3, Snakes 3".split(", ");
      standings.allocateTeamPoints(firstMatchResultArray, primaryKeyGenerator, standings);

      String[] secondMatchResultArray = "Tarantulas 1, FC Awesome 0".split(", ");
      standings.allocateTeamPoints(secondMatchResultArray, primaryKeyGenerator, standings);

      String[] thirdMatchResultArray = "Lions 1, FC Awesome 1".split(", ");
      standings.allocateTeamPoints(thirdMatchResultArray, primaryKeyGenerator, standings);

      String[] fourthMatchResultArray = "Tarantulas 3, Snakes 1".split(", ");
      standings.allocateTeamPoints(fourthMatchResultArray, primaryKeyGenerator, standings);

      String[] fithMatchResultArray = "Lions 4, Grouches 0".split(", ");
      standings.allocateTeamPoints(fithMatchResultArray, primaryKeyGenerator, standings);
      
      Map<String, AtomicInteger> printStandingsMap = standings.printStandings();
      int standingCount = 0;
      
      for (Map.Entry<String, AtomicInteger> entry : printStandingsMap.entrySet()) {
         standingCount++;
         
         if(standingCount == 1){
            assertTrue("Team name should be Tarantulas but it is " + entry.getKey() + ". Points for the team should be 6, but it is " + entry.getValue() + ". Please fix", "Tarantulas".equalsIgnoreCase(entry.getKey()) == true &&  entry.getValue().get() == 6);
         }else if(standingCount == 2){
            assertTrue("Team name should be Lions but it is " + entry.getKey() + ". Points for the team should be 5, but it is " + entry.getValue() + ". Please fix", "Lions".equalsIgnoreCase(entry.getKey()) == true &&  entry.getValue().get() == 5);
         }else if(standingCount == 3){
            assertTrue("Team name should be FC Awesome but it is " + entry.getKey() + ". Points for the team should be 1, but it is " + entry.getValue() + ". Please fix", "FC Awesome".equalsIgnoreCase(entry.getKey()) == true &&  entry.getValue().get() == 1);
         }else if(standingCount == 4){
            assertTrue("Team name should be Snakes but it is " + entry.getKey() + ". Points for the team should be 1, but it is " + entry.getValue() + ". Please fix", "Snakes".equalsIgnoreCase(entry.getKey()) == true &&  entry.getValue().get() == 1);
         }else if(standingCount == 5){
            assertTrue("Team name should be Grouches but it is " + entry.getKey() + ". Points for the team should be 0, but it is " + entry.getValue() + ". Please fix", "Grouches".equalsIgnoreCase(entry.getKey()) == true &&  entry.getValue().get() == 0);
         }
      }
      
      assertTrue("printStandingsMap size should be 5 but it is " + printStandingsMap.size() + ". Please fix", printStandingsMap.size() == 5);
   }
}