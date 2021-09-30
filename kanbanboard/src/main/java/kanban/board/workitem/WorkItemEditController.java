package kanban.board.workitem;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import kanban.board.status.StatusService;
import kanban.board.user.UserService;


@Controller("workItemEditController")
@RequestMapping({"/workItemEdit"})
@SessionAttributes(types=WorkItem.class)
public class WorkItemEditController {

	@Resource(name="userService") private UserService userService;
	@Resource(name="statusService") private StatusService statusService;
	@Resource(name="workItemService") private WorkItemService workItemService;
	@Resource(name="workItemWebValidator") private WorkItemWebValidator workItemWebValidator;
	
	
	public WorkItemEditController() {
	}
	
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView showForm(@RequestParam(value="pk", required=false) final Integer pk){
		Map<String, Object> model = new HashMap<>();
		WorkItem workItem;
		//System.out.println("showForm: pk = " + pk);
		
		if(pk !=null && pk > 0){
			workItem = this.workItemService.workItem(pk);
		}else{
			workItem = new WorkItem();
		}
		
		model.put("workItem", workItem);
		model.put("userList", this.userService.userList());
		model.put("statusList", this.statusService.statusList());
		
		return new ModelAndView("workItemEdit", model);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ModelAndView onSubmit(@ModelAttribute("workItem") final WorkItem workItem, final BindingResult result, final SessionStatus sessionStatus){
		this.workItemWebValidator.validate(workItem, result);
		
		if(result.hasErrors()){
			Map<String, Object> model = new HashMap<>();
			
			model.put("workItem", workItem);
			model.put("userList", this.userService.userList());
			model.put("statusList", this.statusService.statusList());
			
			return new ModelAndView("workItemEdit", model);
		}else{
			this.workItemService.save(workItem);			
			sessionStatus.setComplete();
			return new ModelAndView("redirect:/home");
		}
	}
}