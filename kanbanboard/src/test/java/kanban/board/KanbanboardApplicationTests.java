package kanban.board;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import kanban.board.status.Status;
import kanban.board.workitem.WorkItem;
import kanban.board.workitem.WorkItemWebValidator;

@SpringBootTest
class KanbanboardApplicationTests {
	
	@Test
	void testCannotSkipAStatus() {
		WorkItem workItem = new WorkItem();
		workItem.setStatusID(Status.TODO);
		workItem.setPK(1);
		workItem.setStatusID(Status.DONE);
		
		WorkItemWebValidator workItemWebValidator = new WorkItemWebValidator();
		BindingResult result = new BeanPropertyBindingResult(workItem, "");
		//System.out.println("1) result.hasErrors() = " + result.hasErrors());
		
		workItemWebValidator.validateSkippingOfStatus(result, workItem);
		//System.out.println("1) result.hasErrors() = " + result.hasErrors());
		
		assertTrue(result.hasErrors(), "Invalid status captured has not been validated. Please correct the code or the unit test.");
	}
}
