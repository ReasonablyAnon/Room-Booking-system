package application;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/*import javafx.collections.FXCollections;
import javafx.collections.ObservableList;*/

public class Student extends User implements Serializable {

	
	/**
	 *  For Handling students Requests
	 */
	public ArrayList<Request> myRequests;
	/**
	 * For Handling Courses which Student selects
	 */
	public ArrayList<Course> myCourses;
	/**
	 * For Handling the TimeTable customizable which student can himself make
	 */
	public  ArrayList<TimeTable> myTimeTable = new ArrayList<TimeTable>();
	/**
	 * For Displaying the courses in search bar, in which student will type keywords for searching
	 */
	public ArrayList<TimeTable> coursesOfSearch = new ArrayList<TimeTable>();
	
	public Student(String email, String name, String password) {
		super(email, name, password);
		myRequests = new ArrayList<Request>();
		myCourses = new ArrayList<Course>();
	}
	
	
	
	
	
	
	public static Student validate(String name, String password, String usertype) throws ClassNotFoundException, IOException {
			
			System.out.println("Name : " + name);
			String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Student.ser";
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			ArrayList<Student> readUsers = new ArrayList<Student>();
			try
		    { 
		        readUsers = (ArrayList<Student>) in.readObject();
		        in.close();
		    }
		    catch (IOException e)
		    {
		        System.out.println("didnt work");
		        e.printStackTrace();
		    }
			for(Student student: readUsers) {
				System.out.println("Student for this iteration is : " + student.getName());
		    	if(student.getName().equals(name)) {
		    		System.out.println("username is right");
		    		if(student.getPassword().equals(password)) {
		    			System.out.println("Logged in "); return student;
		    		}
		    		else return null;
		    	}
			}
			return null;
	}
	
	/*public ArrayList<Course> showCourses(String searchString) throws IOException {
		System.out.println("Search string is : " + searchString);
		searchString = searchString.toLowerCase();
		String csvFile = "C:/Users/Sahil Hassan/Documents/Coursework/CSE201 Advanced Programming/AP Project 1/Data/TestingPc.csv";
		System.out.println(csvFile);
	    String fieldDelimiter = ",";
	    BufferedReader bufferedReader;
	    FileReader fileReader = new FileReader(csvFile);
        bufferedReader = new BufferedReader(fileReader);
        //System.out.println("No BufferedReader");
        String line;
        ArrayList<Course> data = new ArrayList<Course>();
        //ObservableList<Course> data1 = FXCollections.emptyObservableList();
	    int loopCount=1;
	    String[] field = bufferedReader.readLine().split(fieldDelimiter);
	    Course course = new Course("Course Code", "Course Name", "Instructor", "Post Conditions");
	    data.add(course);
	    
	    
        while (( line = bufferedReader.readLine()) != null )
        {
        	System.out.println("Loop : " + (loopCount++));
        	System.out.println(line);
        	
                String[] fields = line.split(fieldDelimiter, -1);
                if(fields.length <6)
                	continue;
                
                String postCondition = "";
                String code = fields[0].replaceAll("\"", "").replace("/", "\n");
                String name = fields[1];
                String instructor = fields[2].replaceAll("\"", "");
                postCondition = "";
                for(int i=15; i< fields.length; i++) {
                	postCondition += fields[i];
                }
                postCondition = line.substring(line.lastIndexOf("\"", line.length()-2), line.length()-2);
                System.out.println("Name : " + name);
                System.out.println("Code : " + code);
                System.out.println("Instructor : " + instructor);
                System.out.println("Post Conditions : " + postCondition);
                postCondition = fields[3] + "\n"  + fields[4] + "\n" + fields[5] + "\n"  + fields[6] + "\n"  + fields[7];
                //postCondition = fields[fields.length-1];
                String what = fields[3] + "\n"  + fields[4] + "\n" + fields[5] + "\n"  + fields[6] + "\n"  + fields[7];
                what = postCondition.toLowerCase();
                course = new Course( code, name, instructor, postCondition);
                
                System.out.println("Adding course to data1");
                data1.add(course);
                System.out.println("Add course to data");
                if(what.contains(searchString)) data.add(course);
                
        }
        System.out.println("Show courses");
        bufferedReader.close();
        return data;
		
	}*/
	public static boolean addUser(Student newStudent) throws IOException, ClassNotFoundException {
		
    	String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Student.ser";
    	System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Student> readUsers = new ArrayList<Student>();
		try
	    { 
			
				readUsers= (ArrayList<Student>) in.readObject();
    		
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		//boolean emailRegistered = false;
		for(User user: readUsers) {
			System.out.println(user.getEmail());
			if(user.getEmail().equalsIgnoreCase(newStudent.getEmail())){
				System.out.println("Email registered");
				//emailRegistered = true;
				return false;
				/*Alert alert = new Alert(Alert.AlertType.INFORMATION);
	            alert.setContentText("Email already registered");
	            alert.showAndWait();
				userName.setText("");
				password.setText("");
				email.setText("");*/
			}
		}
		readUsers.add(newStudent);
    		
		String Path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Student.ser";
		FileOutputStream fileOut =  new FileOutputStream(Path);
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		try {
			out.writeObject(readUsers);
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}
		
		out.close();
		System.out.println("Added user.");
		for(User user: readUsers) {
			System.out.print(user.getEmail() + "\t");
			System.out.println("Email : " + user.getEmail());
				
			
		}
		return true;

	}

	
	public void logout() throws IOException, ClassNotFoundException {
		Request.clear();
		for(TimeTable tt : myTimeTable) {
			System.out.println(tt.getDays() + tt.getLocation() + tt.getTime());
		}
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Student.ser";
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Student> readUsers = new ArrayList<Student>();
		try
	    {
	        readUsers = (ArrayList<Student>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		int i=0;
		for(i=0; i<readUsers.size(); i++) {
			System.out.println("Student for this iteration is : " + readUsers.get(i).getEmail());
			if(this.getEmail().equals(readUsers.get(i).getEmail())){
				readUsers.set(i, this);
				break;
				
			}
		}
		System.out.println(readUsers.get(i).myCourses.toArray().toString());
		String Path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Student.ser";
		FileOutputStream fileOut =  new FileOutputStream(Path);
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		try {
			out.writeObject(readUsers);
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}
		
		out.close();
		
	}
	
	
	public void AddCourse( String course , boolean isSearchPart ) throws IOException
	{
		//course = course.substring(1, course.length()-1);
		String csvFile = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\project(1).csv";
        String FieldDelimiter = ",";
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(csvFile));
        String line;
        String[] fields;
        String[][] subjects = new String[17][10];
        int lineCount = 0;
        while (( line = bufferedReader.readLine()) != null )
        {
                fields = line.split(FieldDelimiter, 10);
                for ( int i = 0 ; i < fields.length ; i++ )
                {
                	subjects[lineCount][i] = fields[i];
                }
                lineCount++;
        }
        bufferedReader.close();
        for ( int i = 1 ; i < 17 ; i++ )
        {
        	if ( subjects[i][0].equals(course) )
        	{
        		for ( int j = 3 ; j <= 7 ; j++ )
        		{
/*        			System.out.println(isSearchPart+" SearchPart "+coursesOfSearch.isEmpty()+" CsEmpty "+" AddCourse Wala ");
        			for ( TimeTable t : coursesOfSearch )
        			{
        				System.out.println(t.getcllass()+"  "+t.getDays()+"  "+t.getTime()+" coursesOnSearchWaali");
        			}*/
        			if ( subjects[i][j].length() != 0 )
        			{
        				String res = subjects[i][j];
                		String rum = res.substring(res.length()-4);
                		rum = rum.substring(1,4);
                		String allottedtime = res.substring(0,res.length()-4);
                		//System.out.println(rum+"   "+allottedtime+"  "+course+" AddHogya In TT or CS ");
        				if ( j == 3 )
        				{
        					if ( isSearchPart == false )
        					{
        						boolean danger = Clash("Monday",allottedtime);
        						if ( danger == false )
        							myTimeTable.add(new TimeTable("Monday", course , allottedtime , rum ,"Lecture"));
        						else
        						{
        							Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	        	            alert.setContentText("Time Clash.");
        	        	            alert.showAndWait();
        						}
        					}
        					else
        					{
        						coursesOfSearch.add(new TimeTable("Monday", course , allottedtime , rum ,"Lecture"));
        					}
        				}
        				else if ( j == 4 )
        				{
        					if ( isSearchPart == false )
        					{
        						boolean danger = Clash("Tuesday",allottedtime);
        						if ( danger == false )
        							myTimeTable.add(new TimeTable("Tuesday",course,allottedtime,rum,"Lecture"));
        						else
        						{
        							Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	        	            alert.setContentText("Time Clash.");
        	        	            alert.showAndWait();
        						}
        					}
        					else
        					{
        						coursesOfSearch.add(new TimeTable("Tuesday",course,allottedtime,rum,"Lecture"));
        					}
        				}
        				else if ( j == 5 )
        				{
        					if ( isSearchPart == false )
        					{
        						boolean danger = Clash("Wednesday",allottedtime);
        						if ( danger == false )
        							myTimeTable.add(new TimeTable("Wednesday",course,allottedtime,rum,"Lecture"));
        						else
        						{
        							Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	        	            alert.setContentText("Time Clash.");
        	        	            alert.showAndWait();
        						}
        					}
        					else
        					{
        						coursesOfSearch.add(new TimeTable("Wednesday",course,allottedtime,rum,"Lecture"));
        					}
        				}
        				else if ( j == 6 )
        				{
        					if ( isSearchPart == false )
        					{
        						boolean danger = Clash("Thursday",allottedtime);
        						if ( danger == false )
        							myTimeTable.add(new TimeTable("Thursday",course,allottedtime,rum,"Lecture"));
        						else
        						{
        							Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	        	            alert.setContentText("Time Clash.");
        	        	            alert.showAndWait();
        						}
        					}
        					else
        					{
        						coursesOfSearch.add(new TimeTable("Thursday",course,allottedtime,rum,"Lecture"));
        					}
        				}
        				else if ( j == 7 )
        				{
        					if ( isSearchPart == false )
        					{
        						boolean danger = Clash("Friday",allottedtime);
        						if ( danger == false )
        							myTimeTable.add(new TimeTable("Friday",course,allottedtime,rum,"Lecture"));
        						else
        						{
        							Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	        	            alert.setContentText("Time Clash.");
        	        	            alert.showAndWait();
        						}
        					}
        					else
        					{
        						coursesOfSearch.add(new TimeTable("Friday",course,allottedtime,rum,"Lecture"));
        					}
        				}
        			}
        		}}}}
	
/*	public static void main( String[] args ) throws IOException
	{
		myTimeTable.add(new TimeTable("Monday","Theatre Aprreciation","4:00-5:30","C-11","Lecture"));
		ArrayList<Course> res = showCourses("meta");
	}*/
	
	/**
	 * To return timetable for student controller where there will be sorting basedon days and time
	 * @return
	 */
	public ArrayList<TimeTable> showTT()
	{
		//myTimeTable.add(new TimeTable("Monday","Theatre Aprreciation","4:00-5:30","C-11","Lecture"));
		//System.out.println(" AA HI GAYA ");
/*		for ( TimeTable t : myTimeTable )
		{
			System.out.println(t.getSubject()+"  "+t.getDays()+"  "+t.getTime()+" ASLI TIMETABLE ");
		}*/
		return myTimeTable;
	}
		
	/**
	 *
	 * @param searchString
	 * @return
	 * @throws IOException
	 * 
	 * This is the method where Courses will be displayed after the input of some keywords given by student
	 * . The courses displayed here in tableview will first get checked , that any pre registered course in his timetable
	 * 's schedule isn't clashing with courses being displayed. It ensures not to display clashing courses.
	 */
	public ArrayList<Course> showCourses(String searchString) throws IOException 
	{
		//System.out.println("Search string is : " + searchString);
		ArrayList<TimeTable> temp = new ArrayList<TimeTable>();
		ArrayList<Course> allCourses = new ArrayList<Course>();
		//searchString = searchString.toLowerCase();
		String csvFile = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\TestingPc.csv";
		System.out.println(csvFile);
	    String fieldDelimiter = ",";
	    BufferedReader bufferedReader;
	    FileReader fileReader = new FileReader(csvFile);
        bufferedReader = new BufferedReader(fileReader);
        String line;
        ArrayList<Course> data = new ArrayList<Course>();
	    String[] field = bufferedReader.readLine().split(fieldDelimiter);
	    while (( line = bufferedReader.readLine()) != null )
        {
        		String[] fields = line.split(fieldDelimiter, -1);
        		String postCondition = "";
        		if(line!=null && fields.length>4) {
	           		for ( int i = 3 ; i < fields.length ; i++ )
	        		{
	           			postCondition = postCondition+" "+fields[i];
	        		}
	           		String code = fields[0].replaceAll("\"", "").replace("/", "\n");
	                String name = fields[1];
	                String instructor = fields[2].replaceAll("\"", "");
	                Course course = new Course( code, name, instructor, postCondition);
	                allCourses.add(course);
	                if(postCondition.contains(searchString)) 
	                {
	                		data.add(course);
	                }
        		}
        }
	    System.out.println("Close first loop.");
        bufferedReader.close();
        boolean emptyString = false;
        for ( Course c : data )
        {
        	System.out.println("Course now is : " + c.getName());
        	String naam = c.getName();
        	if(naam.length() ==0) {
        		emptyString = true;
        		break;
        	}
        	naam = naam.substring(1, naam.length()-1);
        	AddCourse(naam,true);
        }
        
        ArrayList<Course> withoutClash = new ArrayList<Course>();
        if(emptyString) return allCourses;
        if ( coursesOfSearch.isEmpty() || myTimeTable.isEmpty() )
        {
        	return data;
        }
        else
        {
	        for ( TimeTable tt1 : coursesOfSearch )
	        {
	            boolean clash = false;
	         	for ( TimeTable tt2 : myTimeTable )
	        	{
	        		if( tt1.getDays().equals(tt2.getDays()) )
	        		{
	        			String[] timeslot1 = tt1.getTime().split("-");
	        			String[] timeslot2 = tt2.getTime().split("-");
	        			int initialTT = convertTimeToInt(timeslot1[0]);
	        			int initialCS = convertTimeToInt(timeslot2[0]);
	        			int finalTT = convertTimeToInt(timeslot1[1]);
	        			int finalCS = convertTimeToInt(timeslot2[1]);
	        			if ( tt1.getTime().equals(tt2.getTime()) )
	        			{
	        				System.out.println(" CLASHHHED ");
	        				clash = true;
	        				break;
	        			}
	        			else if(
	    	    				(	(initialCS < finalTT ) 	&& (initialCS >= initialTT )	)
	    	    			|| 	(	(finalCS > initialTT) 	&& (finalCS<= finalTT)		)
	    	    			||  (	(initialCS < initialTT) && (finalCS > finalTT)		)
	    	    		) {
	        				System.out.println(" CLASHHHED ");
	        				clash = true;
	        				break;
	        			}
	        		}
	        	}
	        	if ( clash == false )
	        	{
	        		System.out.println(" INSERTING IN WithoutClash ");
	        		for ( Course u : data )
	        		{
	        			if( u.getName().contains(tt1.getSubject()) )
	        			{
	        				withoutClash.add(u);
	        				break;
	        			}
	        		}
	        	}
        }
        coursesOfSearch.clear();
/*        System.out.println(coursesOfSearch.isEmpty()+" TIMETABLE WALA ");
		for ( Course t : withoutClash )
		{
			System.out.println(t.getName()+"  "+t.getCode()+" Without Clash Waali ");
		}*/
        return withoutClash;
        }
	}
	
	/**
	 * @param inp
	 * @param tt1
	 * @return
	 * 
	 * this is the method where clashes between timetable and searchcoures are checked
	 */
	public boolean Clash( String inp , String tt1 )
	{
		boolean clash = false;
         	for ( TimeTable tt2 : myTimeTable )
        	{
        		if( inp.equals(tt2.getDays()) )
        		{
        			String[] timeslot1 = tt1.split("-");
        			String[] timeslot2 = tt2.getTime().split("-");
        			int initialTT = convertTimeToInt(timeslot1[0]);
        			int initialCS = convertTimeToInt(timeslot2[0]);
        			int finalTT = convertTimeToInt(timeslot1[1]);
        			int finalCS = convertTimeToInt(timeslot2[1]);
        			if ( tt1.equals(tt2.getTime()) )
        			{
        				System.out.println(" CLASHHHED ");
        				clash = true;
        				break;
        			}
        			/*else if ( ( initialCS <= initialTT && finalTT >= finalCS && finalCS >= initialTT ) || 
        					( initialTT <= initialCS && finalCS <= finalTT ) 
        					 || ( initialTT <= initialCS && finalCS >= finalTT && initialCS <= finalTT ) 
        					|| ( initialCS <= initialTT && finalCS >= finalTT ) )
        			{*/
        			else if(
    	    				(	(initialCS < finalTT ) 	&& (initialCS >= initialTT )	)
    	    			|| 	(	(finalCS > initialTT) 	&& (finalCS<= finalTT)		)
    	    			||  (	(initialCS < initialTT) && (finalCS > finalTT)		)
    	    		) {
        				System.out.println(" CLASHHHED ");
        				clash = true;
        				break;
        			}
        		}
        	}
         	System.out.println(clash+" DANGER'S VALUE ");
         	if ( clash == true )
         	{
         		return true;
         	}
         	else
         	{
         		return false;
         	}
	}






	public ArrayList<TimeTable> getTimeTable() {
		return myTimeTable;
		
	}
}
