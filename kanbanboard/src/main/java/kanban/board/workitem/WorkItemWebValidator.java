package kanban.board.workitem;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import kanban.board.status.Status;
import kanban.board.status.StatusService;


@Controller("workItemWebValidator")
public class WorkItemWebValidator implements Validator {
	
   @Resource(name="statusService") private StatusService statusService;
   @Resource(name="workItemService") private WorkItemService workItemService;
   
	
   public WorkItemWebValidator(){
   }
   
   
   @Override
   public boolean supports(final Class<?> clazz){
      return WorkItem.class.isAssignableFrom(clazz);
   }
   
   
   @Override
   public void validate(final Object object, final Errors errors){
      final WorkItem workItem = (WorkItem)object;
      
      if(workItem.getTitle() == null || workItem.getTitle().trim().equals("")){
    	  errors.reject("", "Title may not be blank. Please capture a title");
      }
      
      if(workItem.getDescription() == null || workItem.getDescription().trim().equals("")){
    	  errors.reject("", "Description may not be blank. Please capture a description");
      }
      
      validateSkippingOfStatus(errors, workItem);
      
      if(workItem.getStatusID() == Status.DOING) {
	      List<Integer> todoStatusList = new LinkedList<>();
	      
	      for(WorkItem tmpWorkItem : workItemService.workItemByUserList(workItem.getUserID())) {
	    	  if(workItem.getPK() != tmpWorkItem.getPK() && tmpWorkItem.getStatusID() == Status.DOING) {
	    		  todoStatusList.add(tmpWorkItem.getStatusID());
	    	  }
	      }
	      
	      if(todoStatusList.size() == 3) {
	    	  errors.reject("", "The board can only contain 3 work items in the Doing column for a given user. Please complete the other work items first for status: " + statusService.status(Status.DOING));
	      }
      }
   }
   
   public void validateSkippingOfStatus(final Errors errors, final WorkItem workItem) {
	   //System.out.println("workItem.containsChange(\"StatusID\") = " + workItem.containsChange("StatusID") + " | workItem.getStatusID() = " + workItem.getStatusID());
	   if(workItem.containsChange("StatusID") == true) {
		   //System.out.println("workItem.oldChangeValue(\"StatusID\") = " + workItem.oldChangeValue("StatusID"));
		   if((workItem.oldChangeValue("StatusID") == Status.TODO && workItem.getStatusID() == Status.DONE)
				   || (workItem.oldChangeValue("StatusID") == Status.DONE && workItem.getStatusID() == Status.TODO)) {
			   errors.reject("", "Invalid status captured. Please capture a status such as: Doing");
		   }
	   }
   }
}