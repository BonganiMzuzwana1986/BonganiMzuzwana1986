package kanban.board;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import kanban.board.status.StatusService;
import kanban.board.user.UserService;
import kanban.board.workitem.WorkItemService;

@Controller
@ResponseBody
public class ViewController {
	
	@Resource(name="userService") private UserService userService;
	@Resource(name="statusService") private StatusService statusService;
	@Resource(name="workItemService") private WorkItemService workItemService;
	
	
	@GetMapping(value="/home")
	public ModelAndView home() {		
		Map<String, Object> model = new HashMap<>();
		//System.out.println("thymeleafTemplate");
		
		model.put("userList", userService.userList());
		model.put("statusList", statusService.statusList());
		model.put("workItemList", workItemService.workItemList());
		
		return new ModelAndView("home", model);
	}
	
	@GetMapping(value="/workItemByUserView")
	public ModelAndView workItemByUserView(@RequestParam(value="pk", required=false) final Integer pk) {		
		Map<String, Object> model = new HashMap<>();
		//System.out.println("thymeleafTemplate");
		
		model.put("workItemList", workItemService.workItemByUserList(pk));
		model.put("user", userService.user(pk));
		
		return new ModelAndView("workItemByUserView", model);
	}
	
	@GetMapping(value="/workItemByStatusView")
	public ModelAndView workItemByStatusView(@RequestParam(value="pk", required=false) final Integer pk) {		
		Map<String, Object> model = new HashMap<>();
		//System.out.println("thymeleafTemplate");
		
		model.put("workItemList", workItemService.workItemByStatusList(pk));
		model.put("status", statusService.status(pk));
		
		return new ModelAndView("workItemByStatusView", model);
	}
}