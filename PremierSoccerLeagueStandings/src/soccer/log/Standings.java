package soccer.log;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Standings{
   
   private Map<String, AtomicInteger> teamAndPointsMap;
   
   
   public Standings(){
      this.teamAndPointsMap = new LinkedHashMap<>();
   }
   
   
   public boolean isDraw(final Team team1, final Team team2){
      return team1.getScore() == team2.getScore();
   }
   
   public boolean isLoss(final Team team1, final Team team2){
      return team1.getScore() < team2.getScore();
   }
   
   public boolean isWin(final Team team1, final Team team2){
      return team1.getScore() > team2.getScore();
   }
   
   
   public void allocateTeamPoints(final String[] matchResultArray, final AtomicInteger primaryKeyGenerator, final Standings standings){
      Team homeTeam = buildTeamAndScore(true, matchResultArray, primaryKeyGenerator);
      Team awayTeam = buildTeamAndScore(false, matchResultArray, primaryKeyGenerator);
      //System.out.println("allocateTeamPoints: standings.isDraw(homeTeam, awayTeam) = " + standings.isDraw(homeTeam, awayTeam) + " | standings.isWin(homeTeam, awayTeam) = " + standings.isWin(homeTeam, awayTeam) + " | homeTeam = " + homeTeam + " | awayTeam = " + awayTeam);
      
      if(standings.isDraw(homeTeam, awayTeam) == true){
         standings.giveOnePointToEachTeam(homeTeam, awayTeam);
      }else if(standings.isWin(homeTeam, awayTeam) == true){
         standings.giveThreePointsToWinningTeam(homeTeam);
         standings.giveZeroPointsToLosingTeam(awayTeam);
      }else{
         standings.giveThreePointsToWinningTeam(awayTeam);
         standings.giveZeroPointsToLosingTeam(homeTeam);
      }
   }
   
   public void giveOnePointToEachTeam(final Team team1, final Team team2){
      this.teamAndPointsMap.computeIfAbsent(team1.getName(), point -> new AtomicInteger(0)).incrementAndGet();
      this.teamAndPointsMap.computeIfAbsent(team2.getName(), point -> new AtomicInteger(0)).incrementAndGet();
   }
   
   public void giveThreePointsToWinningTeam(final Team team){
      this.teamAndPointsMap.computeIfAbsent(team.getName(), point -> new AtomicInteger(0)).addAndGet(3);
   }
   
   public void giveZeroPointsToLosingTeam(final Team team){
      this.teamAndPointsMap.computeIfAbsent(team.getName(), point -> new AtomicInteger(0)).addAndGet(0);
   }
   
   public Map<String, AtomicInteger> printStandings(){
      this.teamAndPointsMap = this.teamAndPointsMap.entrySet()
               .stream()
               .sorted(Map.Entry.<String, AtomicInteger>comparingByValue(Comparator.comparing(AtomicInteger::get).reversed()).thenComparing(Map.Entry.comparingByKey(Comparator.comparing(String::toString))))
               .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,(oldValue, newValue) -> oldValue, LinkedHashMap::new));
      
      int standingCount = 0;
      
      for (Map.Entry<String, AtomicInteger> entry : this.teamAndPointsMap.entrySet()) {
         standingCount++;
         //System.out.println("printStandings: 2) entry.getKey() = " + entry.getKey() + " | entry.getValue() = " + entry.getValue());
         System.out.println(standingCount + ". " + entry.getKey() + ", " + entry.getValue() + " pts");
      }
      
      return this.teamAndPointsMap; //We need to return the map for the unit test
   }
   
   
   private Team buildTeamAndScore(final boolean isHomeTeam, final String[] matchResultArray, final AtomicInteger primaryKeyGenerator){
      String[] teamScoreArray;
      
      if(isHomeTeam == true){
         teamScoreArray = matchResultArray[0].split(" ");
      }else{
         teamScoreArray = matchResultArray[1].split(" ");
      }
         
      StringBuilder teamNameBuilder = new StringBuilder();
      Integer score = 0;
      int count = 0;
      
      if(teamScoreArray.length > 2){
         for(String awayTeamScore: teamScoreArray){
            count++;
            
            if(count == teamScoreArray.length){
               score = Integer.valueOf(awayTeamScore);
               break;
            }
            
            if(teamNameBuilder.length() > 0){
               teamNameBuilder.append(" ");
            }
            
            teamNameBuilder.append(awayTeamScore);
         }
      }else{
         teamNameBuilder.append(teamScoreArray[0]);
         score = Integer.valueOf(teamScoreArray[1]);
      }
      
      Team team = new Team(primaryKeyGenerator.incrementAndGet());
      team.setName(teamNameBuilder.toString());
      team.setScore(score);
      
      return team;
   }
}