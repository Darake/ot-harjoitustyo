
package deadlinesbegone.dao;

import deadlinesbegone.domain.Course;
import java.sql.*;

/**
 * Class provides SQL actions for Courses.
 *
 * @param <T> 
 */
public class SQLCourseDao extends AbstractNamedObjectDao<Course> {
    
    public SQLCourseDao(Database database, String tableName) {
        super(database, tableName);
    }

    @Override
    public Course create(Course course) throws SQLException {
        try (Connection conn = database.getConnection()) {
            String sql = "INSERT INTO Course (name) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, course.getName());
            stmt.executeUpdate();
        }
        
        return findByName(course.getName());
    }

    @Override
    protected Course createFromRow(ResultSet rs) throws SQLException {
        return new Course(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    public void update(Course object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
