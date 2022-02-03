package soccer.log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutePremierSoccerLeagueStanding{
   
   public ExecutePremierSoccerLeagueStanding(){
      //Add something
   }
   
   
   public static void main(final String[] args){ //Run this with ant as follows: ant ExecutePremierSoccerLeagueStanding -Dargs="SampleInput.txt"
      Standings standings = new Standings();
      AtomicInteger primaryKeyGenerator = new AtomicInteger();
      //System.out.println("main: args = " + Arrays.toString(args));
      
      for(String inputFilePath : args){
         //System.out.println("main: inputFilePath = " + inputFilePath + " | inputFilePath.lastIndexOf('.') = " + inputFilePath.lastIndexOf('.'));
         try{
            File importFile = new File(inputFilePath);
            //System.out.println("main: importFile = " + importFile);
            
            readAndProcessFile(standings, primaryKeyGenerator, importFile);
         }catch(Exception exc){
            System.err.println("main: An error occurred while building the file : inputFilePath = " + inputFilePath + " | exc.getMessage() = " + exc.getMessage());
            break;
         }
         
      }
      
      standings.printStandings();
   }
   
   private static void readAndProcessFile(final Standings standings, final AtomicInteger primaryKeyGenerator, final File importFile){
      //System.out.println("readAndProcessFile: importFile = " + importFile);
      try(BufferedReader reader = new BufferedReader(new FileReader(importFile))){
         String line = reader.readLine();
         //System.out.println("readAndProcessFile: line = " + line + (line != null ? " | line.trim().equals(\"\") = " + line.trim().equals("") : ""));
         
         while(line != null && line.trim().equals("") == false){
            String[] firstMatchResultArray = line.split(", ");
            standings.allocateTeamPoints(firstMatchResultArray, primaryKeyGenerator, standings);
            line = reader.readLine();
         }
      }catch(Exception exc){
         System.err.println("readAndProcessFile: An error occurred while reading the file : importFile = " + importFile + " | exc.getMessage() = " + exc.getMessage());
      }
   }
}