package soccer.log;


public class Team{
   
   private String name;
   private int score;
   private int id;
   
   
   public Team(final int newId){
      this.id = newId;
   }
   
   
   public String getName(){
      return this.name;
   }
   
   public void setName(final String newName){
      this.name = newName;
   }
   
   
   public int getScore(){
      return this.score;
   }
   
   public void setScore(final int newScore){
      this.score = newScore;
   }
   
   public int getId(){
      return this.id;
   }
   
   public void setId(final int newId){
      this.id = newId;
   }
   
   
   @Override
   public String toString(){
      return "toString: id = " + this.id + " | name = " + this.name + " | score = " + this.score;
   }
   
   
   @Override
   public boolean equals(final Object object) {
      if(this == object){
         return true;
      }
      
      if(object == null || getClass() != object.getClass()){
         return false;
      }
      
      return this.name.equalsIgnoreCase(((Team)object).getName());
   }
   
   
   @Override
   public int hashCode() {
       int result = 0;
       result = 31 * result + this.id;
       return result;
   }
}