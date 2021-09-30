package kanban.board.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kanban.board.RowMapperImplementation;


@Repository("userDAO")
public class UserJDBC {
	
	private RowMapper<User> rowMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public UserJDBC() {
		this.rowMapper = new RowMapperImplementation<User>(){
			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				user.setName(rs.getString("Name"));
				user.setEmail(rs.getString("Email"));
				user.setPK(rs.getInt("ID"));
				
				return user;
			}
		};		
	}
	
	
	public User user(final int pk){
		try{
			return jdbcTemplate.queryForObject("select * from user where id = ?", this.rowMapper, pk);
		}catch(Exception exc){
			return null;
		}
	}
	
	
	public List<User> userList(){
		return jdbcTemplate.query("select * from user", this.rowMapper);
	}
}
