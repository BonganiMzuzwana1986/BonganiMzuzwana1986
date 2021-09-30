package kanban.board.status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import kanban.board.RowMapperImplementation;


@Repository("statusDAO")
public class StatusJDBC {
	
	private RowMapper<Status> rowMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public StatusJDBC() {
		this.rowMapper = new RowMapperImplementation<Status>(){
			@Override
			public Status mapRow(ResultSet rs, int rowNum) throws SQLException {
				Status status = new Status();
				status.setName(rs.getString("name"));
				status.setPK(rs.getInt("ID"));
				
				return status;
			}
		};		
	}
	
	
	public Status status(final int pk){
		try{
			return jdbcTemplate.queryForObject("select * from status where id = ?", this.rowMapper, pk);
		}catch(Exception exc){
			return null;
		}
	}
	
	
	public List<Status> statusList(){
		return jdbcTemplate.query("select * from status", this.rowMapper);
	}
}
