package kanban.board.user;


public class User {
	
	private String name;
	private String email;
	private int pk;
	

	public User() {
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public void setName(final String newName) {
		this.name = newName;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public void setEmail(final String newEmail) {
		this.email = newEmail;
	}
	
	public final int getPK(){
		return this.pk;
	}
	
	public final void setPK(final int newPK){
		this.pk = newPK;
	}
	
	
	public String toString() {
		return this.name + " >> " + this.email + " >> " + this.pk;
	}
}
