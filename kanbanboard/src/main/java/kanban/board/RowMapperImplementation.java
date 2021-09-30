package kanban.board;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public abstract class RowMapperImplementation<T> implements RowMapper<T> {

	public RowMapperImplementation() {
	}

	@Override
	public T mapRow(ResultSet rs, int rowNum) throws SQLException {
		return null;
	}
}
