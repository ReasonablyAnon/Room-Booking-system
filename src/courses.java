

import javafx.beans.property.SimpleStringProperty;

public class courses
{
    private final SimpleStringProperty Code;
    private final SimpleStringProperty Name;
    private final SimpleStringProperty Instructor;
    private final SimpleStringProperty PC1;

    courses(String Code, String Name, String Instructor , String PC1)
    {
        this.Code = new SimpleStringProperty(Code);
        this.Name = new SimpleStringProperty(Name);
        this.Instructor = new SimpleStringProperty(Instructor);
        this.PC1 = new SimpleStringProperty(PC1);
    }
	public String getCode() {
        return Code.get();
    }
    public String getName() {
        return Name.get();
    }
    public String getInstructor() {
        return Instructor.get();
    }
    public String getPC1() {
        return PC1.get();
    }
}

