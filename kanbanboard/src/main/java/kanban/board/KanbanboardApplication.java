package kanban.board;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import kanban.board.user.UserService;


@SpringBootApplication
public class KanbanboardApplication implements CommandLineRunner {

	//@Autowired private JdbcTemplate jdbcTemplate;
	@Resource(name="userService") private UserService userService;
	
	
	public static void main(String[] args) {
		SpringApplication.run(KanbanboardApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		String sql = "insert into user (name, email) values (?, ?)";
		
//		int result = jdbcTemplate.update(sql, "Bongani", "bongani.mzuzwana@gmail.com");
//		
//		if(result > 0) {
//			System.out.println("A new row has been inserted into the user table.");
//		}
//		
//		System.out.println("userService = " + userService);
//		System.out.println("userService.user(1) = " + userService.user(1));
//		System.out.println("userService.userList() = " + userService.userList());
	}
}
