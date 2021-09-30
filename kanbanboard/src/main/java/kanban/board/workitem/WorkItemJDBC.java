package kanban.board.workitem;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import kanban.board.RowMapperImplementation;


@Repository("workItemDAO")
public class WorkItemJDBC {
	
	private RowMapper<WorkItem> rowMapper;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	public WorkItemJDBC() {
		this.rowMapper = new RowMapperImplementation<WorkItem>(){
			@Override
			public WorkItem mapRow(ResultSet rs, int rowNum) throws SQLException {
				WorkItem workItem = new WorkItem();
				workItem.setTitle(rs.getString("title"));
				workItem.setDescription(rs.getString("description"));
				workItem.setUserID(rs.getInt("user"));
				workItem.setStatusID(rs.getInt("status"));
				workItem.setPK(rs.getInt("ID"));
				
				return workItem;
			}
		};		
	}
	
	
	public WorkItem workItem(final int pk){
		try{
			return jdbcTemplate.queryForObject("select * from workItem where id = ?", this.rowMapper, pk);
		}catch(Exception exc){
			return null;
		}
	}
	
	
	public List<WorkItem> workItemList(){
		return jdbcTemplate.query("select * from workItem", this.rowMapper);
	}
	
	public List<WorkItem> workItemByUserList(final Integer userID){
		return jdbcTemplate.query("select * from workItem where user = ?", this.rowMapper, userID);
	}
	
	public List<WorkItem> workItemByStatusList(final Integer statusID){
		return jdbcTemplate.query("select * from workItem where status = ?", this.rowMapper, statusID);
	}	
	
	
	@Transactional(isolation=Isolation.READ_COMMITTED, rollbackFor=Exception.class)
	public boolean save(final WorkItem workItem){
		if(workItem.getPK() < 1){
			int count = jdbcTemplate.update("insert into workItem (title, description, user, status) values (?, ?, ?, ?)",
					workItem.getTitle(), workItem.getDescription(), workItem.getUserID(), workItem.getStatusID());
			
			System.out.println("A new row has been inserted into the workITem table.");
			return count > 0;
		}else{
			int count = jdbcTemplate.update("update workItem set title = ?, description = ?, user = ?, status = ? where id = ?",
					workItem.getTitle(), workItem.getDescription(), workItem.getUserID(), workItem.getStatusID(), workItem.getPK());
			
			System.out.println("An existing row has been updated into the workITem table.");
			return count > 0;
		}
	}
}