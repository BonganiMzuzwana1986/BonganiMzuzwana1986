package kanban.board.status;

public class Status {
	
	public static final int TODO  =  1;
	public static final int DOING =  2;
	public static final int DONE  =  3;
	
	private String name;
	protected int pk;
	

	public Status() {
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public void setName(final String newName) {
		this.name = newName;
	}
	
	public final int getPK(){
		return this.pk;
	}
	
	public final void setPK(final int newPK){
		this.pk = newPK;
	}
	
	
	public String toString() {
		return this.name + " >> " + this.pk;
	}
}
