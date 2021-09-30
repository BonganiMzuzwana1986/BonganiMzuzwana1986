package kanban.board.user;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;


@Service(value="userService")
public class UserService {
	
	@Resource(name="userDAO") private UserJDBC userJDBC;
	
	
	public UserService() {		
	}
	
	
	public User user(final Integer pk){
		if(pk != null){
			return this.userJDBC.user(pk);
		}else{
			return null;
		}
	}
	
	
	public List<User> userList(){
		return this.userJDBC.userList();
	}
}
