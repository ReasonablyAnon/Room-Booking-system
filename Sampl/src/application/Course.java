package application;



import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;

/**
 * @author Sahil Hassan
 *
 */
public class Course implements Serializable
{
    private final SimpleStringProperty code;
    private final SimpleStringProperty name;
    private final SimpleStringProperty instructor;
    private final SimpleStringProperty postConditions;
    private static ArrayList<Course> courses = new ArrayList<Course>();

    /**
     * @param code
     * @param name
     * @param instructor
     * @param postConditions
     */
    public Course(String code, String name, String instructor , String postConditions)
    {
        this.code = new SimpleStringProperty(code);
        this.name = new SimpleStringProperty(name);
        this.instructor = new SimpleStringProperty(instructor);
        this.postConditions = new SimpleStringProperty(postConditions);
    }
	/**
	 * @return
	 */
	public String getCode() {
        return code.get();
    }
    /**
     * @return
     */
    public String getName() {
        return name.get();
    }
    /**
     * @return
     */
    public String getInstructor() {
        return instructor.get();
    }
    /**
     * @return
     */
    public String getPostConditions() {
        return postConditions.get();
    }
    /**
     * @return
     */
    public int getTotalCourses() {
    	return 17;
    	//return courses.size();
    }
}

