package deadlinesbegone.domain;

import deadlinesbegone.dao.Dao;
import deadlinesbegone.dao.Database;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class DeadlinesBegoneServiceTest {
    
    public DeadlinesBegoneService appService;
    public Properties properties;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Before
    public void setUp() {
        Database db = new Database();
        Properties properties = new Properties();
        Dao courseDao = new FakeCourseDao();
        Dao assignmentDao = new FakeAssignmentDao();
        appService = new DeadlinesBegoneService(db, properties, courseDao, assignmentDao);
        
        this.properties = properties;
    }
    
    @Test
    public void databaseExistsReturnsTrueWhenSuchPropertyExists() {
        properties.setProperty("database", "db.period");
        
        assertEquals(true, appService.databaseExists());
    }
    
    @Test
    public void databaseExistsReturnsFalseWhenNoSuchPropertyExists() {
        assertEquals(false, appService.databaseExists());
    }
    
    @Test
    public void getUndoneAssignmentsOnlyReturnUndoneAssignments() throws SQLException {
        appService.getAssignments();
        List<Assignment> list = appService.getUndoneAssignments();
        
        assertEquals(2, list.size());
    }
    
    @Test
    public void deleteCourseRemovesCourse() throws SQLException {
        appService.getAssignments();
        appService.deleteCourse(new Course(1, "Ohjelmistotekniikka"));
        List<Course> courses = appService.getCourses();
        
        assertEquals(1, courses.size());
    }
}
