package application;

import java.io.Serializable;
import java.util.ArrayList;
/*
import javafx.beans.property.String;*/

/**
 * @author Sahil Hassan
 * @author Ashwat Kumar
 *
 */

public class TimeTable implements Serializable
{
	 	private final String days;
	    private final String subject;
	    private final String time;
	    private final String location;
	    private final String type;

	    /**
	     * @param d
	     * @param subject
	     * @param time
	     * @param v
	     * @param type
	     * 
	     * Constructor Class For Making TimeTable Object which will be reflected in TableView in StudentFXML file
	     */
	    
	    public TimeTable( String d, String subject , String time , String v , String type )
	    {
	        this.days = d;
	        this.subject = subject;
	        this.time = time;
	        this.location = v;
	        this.type = type;
	        //System.out.println("Time table constructor : \t" + this.subject+"  \t"+this.location);
	    }
	    
		/**
		 * getter for days
		 * @return
		 */
		public String getDays() {
	        return days;
	    }
	    /**
	     * getter for subjects
	     * @return
	     */
	    public String getSubject() {
	        return subject;
	    }
	    /**
	     * getter for location
	     * @return
	     */
	    public String getLocation() {
	        return location;
	    }
	    /**
	     * getter for time
	     * @return
	     */
	    public String getTime() {
	        return time;
	    }
	    /**
	     * getter for type
	     * @return
	     */
	    public String getType() {
	    	return type;
	    }
}
