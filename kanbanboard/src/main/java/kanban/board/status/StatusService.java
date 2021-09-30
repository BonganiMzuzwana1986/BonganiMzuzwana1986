package kanban.board.status;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service(value="statusService")
public class StatusService {
	
	@Resource(name="statusDAO") private StatusJDBC statusJDBC;
	
	
	public StatusService() {		
	}
	
	
	public Status status(final Integer pk){
		if(pk != null){
			return this.statusJDBC.status(pk);
		}else{
			return null;
		}
	}
	
	
	public List<Status> statusList(){
		return this.statusJDBC.statusList();
	}
}
