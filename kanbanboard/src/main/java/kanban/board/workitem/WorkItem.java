package kanban.board.workitem;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import kanban.board.status.Status;
import kanban.board.status.StatusService;
import kanban.board.user.User;
import kanban.board.user.UserService;


@Component
public class WorkItem {
	
	private String title;
	private String description;
	private int userID;
	private int statusID;
	private int pk;
	
	private Map<String, Integer> changeTrackingMap;
	
    public static ApplicationContext applicationContext;
	
    public WorkItem() {
	}
	
    
    @Autowired
    private void setApplicationContext(ApplicationContext applicationContext0) {
        applicationContext = applicationContext0;
    }
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(final String newTitle) {
		this.title = newTitle;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public void setDescription(final String newDescription) {
		this.description = newDescription;
	}
	
	public final int getUserID(){
		return this.userID;
	}
	
	public final void setUserID(final int newUserID){
		this.userID = newUserID;
	}
	
	public final User getUser(){
		//System.out.println("Service >> " + (UserService)applicationContext.getBean(UserService.class));
		return (((UserService)applicationContext.getBean(UserService.class))).user(this.userID);
	}
	
	public final int getStatusID(){
		return this.statusID;
	}
	
	public final void setStatusID(final int newStatusID){
		if(this.pk > 0) {
			trackChange("StatusID", this.statusID, newStatusID);
		}
		
		this.statusID = newStatusID;
		//System.out.println("setStatusID: pk = " + pk);
	}
	
	public final Status getStatus(){
		return (((StatusService)applicationContext.getBean(StatusService.class))).status(this.statusID);
	}
	
	public final int getPK(){
		return this.pk;
	}
	
	public final void setPK(final int newPK){
		this.pk = newPK;
	}
	
	
	public final void trackChange(final String fieldName, final Integer oldValue, final Integer newValue){
		if(oldValue.compareTo(newValue) != 0){
			if(this.changeTrackingMap == null){
				this.changeTrackingMap = new HashMap<>();
				this.changeTrackingMap.put(fieldName, oldValue);
			}else if(!this.changeTrackingMap.containsKey(fieldName)){
				this.changeTrackingMap.put(fieldName, oldValue);
			}
		}
	}
	
	public final void clearChangeTrackingMap(){
		if(this.changeTrackingMap != null){
			this.changeTrackingMap.clear();
		}
	}
	
	
	public final boolean containsChange(final String fieldName){
		return this.changeTrackingMap != null && this.changeTrackingMap.containsKey(fieldName);
	}
	
	
	/** @return the value before the change took place, before the setter has been executed. */
	public final Integer oldChangeValue(final String fieldName){
		if(this.changeTrackingMap != null){
			return this.changeTrackingMap.get(fieldName);
		}else{
			throw new IllegalArgumentException("Method oldChangeValue may not be called before checking via containsChange first.");
		}
	}

	
	@Override
	public String toString() {
		return title + " >> " + this.description + " >> " + this.userID + " >> " + this.statusID + " >> " + this.pk;
	}
}
