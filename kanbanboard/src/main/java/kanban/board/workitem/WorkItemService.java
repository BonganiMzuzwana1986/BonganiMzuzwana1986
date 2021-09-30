package kanban.board.workitem;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service(value="workItemService")
public class WorkItemService {
	
	@Resource(name="workItemDAO") private WorkItemJDBC workItemJDBC;
	
	
	public WorkItemService() {		
	}
	
	
	public WorkItem workItem(final Integer pk){
		if(pk != null){
			return this.workItemJDBC.workItem(pk);
		}else{
			return null;
		}
	}
	
	
	public List<WorkItem> workItemList(){
		return this.workItemJDBC.workItemList();
	}
	
	public List<WorkItem> workItemByUserList(final Integer userID){
		return this.workItemJDBC.workItemByUserList(userID);
	}
	
	public List<WorkItem> workItemByStatusList(final Integer statusID){
		return this.workItemJDBC.workItemByStatusList(statusID);
	}
	
	
	public boolean save(final WorkItem workItem){
		workItem.clearChangeTrackingMap();
		return this.workItemJDBC.save(workItem);
	}
}
