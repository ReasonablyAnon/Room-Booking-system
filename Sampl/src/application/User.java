package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;



public class User implements Serializable {

	private String name;
	private String email;
	private String password;
	public ArrayList<Request> myRequests = new ArrayList<Request>();
	private static ArrayList<User> Users = new ArrayList<User>();
	static HashMap<String,Integer> mapbook = new HashMap<String,Integer>();
	
	
	/**
	 * @param name
	 * @param email
	 * @param password
	 */
	public User(String email, String name, String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
	}
	protected void addRequest(Request request) {
		myRequests.add(request);
	}
	
	/**
	 * @param name
	 * @param password
	 * @param usertype
	 * @return User who is logging in, null if credentials are wrong.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static User validate(String name, String password, String usertype) throws ClassNotFoundException, IOException {
		
		System.out.println("Name : " + name);
		String path = "C:\\Users\\HP\\Downloads\\Submission\\Submission\\Data\\";
		FileInputStream fileIn = new FileInputStream(path + usertype + ".ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		if(usertype.equals("Student")) {
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
		}
		ArrayList<User> readUsers = new ArrayList<User>();
	    try
	    { 
	        readUsers = (ArrayList<User>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
	    System.out.println("At the loop ");
	    for(User user: readUsers) {
	    	System.out.println("User for this iteration is : " + user.name);
	    	if(user.name.equals(name)) {
	    		System.out.println("username is right");
	    		if(user.password.equals(password)) {
	    			System.out.println("Logged in "); return user;
	    		}
	    		else return null;
	    	}
	    		
	    }
		
		return null;
		
	}
	
	public static void addUser(String email, String name, String password) {
		Users.add(new User(email, name, password));
		
	}
	
	
	/**
	 * @param accepted
	 * @return this User's requests
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Request> checkMyRequests(boolean accepted) throws IOException, ClassNotFoundException{
		ArrayList<Request> requests = new ArrayList<Request>();
		ArrayList<Request> finals = new ArrayList<Request>();
		String path = "C:\\Users\\HP\\Downloads\\Submission\\Submission\\Data\\";
		System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path + "Requests.ser");
		/*if(fileIn == null) {
			System.out.println("File not found");
		}*/
		ObjectInputStream in = new ObjectInputStream(fileIn);
	    try
	    { 
	        requests = (ArrayList<Request>) in.readObject();
	    }
	    catch (IOException e)
	    {
	        System.out.println("Could not deserialize requests");
	        e.printStackTrace();
	    }
	    in.close();
	    System.out.println("Checking boookings for : " + this.getEmail());
	    for(Request request: requests) {
	    	if(this.getEmail().equals(request.getUserEmail())) {
	    		System.out.print(accepted + " request is : " + request.getDATE() + " " +  request.getStartTime());
	    		if(request.isAccepted() == accepted) {
	    			System.out.println( "\t added");
	    			finals.add(request);
	    		}
	    	}
	    }
		
		return finals;
	}
	
	
	
	/**
	 * @param time
	 * @return Time in twenty four hour format.
	 */
	public static int convertTimeToInt(String time) {
		
		System.out.print("Converting time : "  + time);
    	int twentyFourHourTime = 0;
    	int earliestPossibleClass = 7;
    	//System.out.println(time);
    	//System.out.println("HI "+ Integer.parseInt(time.substring(0, time.indexOf(":"))));
    	if(Integer.parseInt(time.substring(0, time.indexOf(":")).toString()) < earliestPossibleClass) {
    		//System.out.println(Integer.parseInt(time.substring(0, time.indexOf(":"))));
    		twentyFourHourTime = Integer.parseInt(time.substring(0, time.indexOf(":"))) + 12;
    		//System.out.println(twentyFourHourTime);
    	}
    	else twentyFourHourTime = Integer.parseInt(time.substring(0, time.indexOf(":")));
    	twentyFourHourTime *=100;
    	
    	twentyFourHourTime += Integer.parseInt(time.substring(time.indexOf(":")+1));
    	System.out.println("\t"+ twentyFourHourTime);
    	return twentyFourHourTime;
		
	}




	/**
	 * @param room
	 * @param time
	 * @param date
	 * @return Room is available on a date at a given time.
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 */
	static boolean viewRoomAvailability( String room , String time , LocalDate date ) throws IOException, FileNotFoundException, ClassNotFoundException
    {
    	/*String asliday = "Thursday";
    	String room = "C21";
    	String time = "11:30-12:00";*/
		
    	System.out.println("Time argument is : " + time);
    	System.out.println("Time is :  " + time);
    	String startTime = time.substring(0, time.indexOf("-")).trim();
    	String endTime = time.substring(time.indexOf("-")+1).trim();
    	//System.out.print(startTime);
    	//System.out.println(endTime);
    	
    	
    	int twentyFourHourRequestStartTime = convertTimeToInt(startTime);
    	int twentyFourHourRequestEndTime = convertTimeToInt(endTime);
    	//System.out.print(twentyFourHourRequestStartTime + "\t");
    	//System.out.println(twentyFourHourRequestEndTime);
    	
    	
    	HashMap<String,Integer> mapbook = new HashMap<String,Integer>();
    	HashMap<String,String> secondmap = new HashMap<String,String>();
        mapbook.put("MONDAY",3);		secondmap.put("MONDAY","Monday");
        mapbook.put("TUESDAY",4);		secondmap.put("TUESDAY","Tuesday");
        mapbook.put("WEDNESDAY",5);		secondmap.put("WEDNESDAY","Wednesday");
        mapbook.put("THURSDAY",6);		secondmap.put("THURSDAY","Thursday");
        mapbook.put("FRIDAY",7);		secondmap.put("FRIDAY","Friday");
    	String csvFile = "C:\\Users\\HP\\Downloads\\Submission\\Submission\\Data\\project.csv";
        String FieldDelimiter = ",";
        BufferedReader bufferedReader;
        bufferedReader = new BufferedReader(new FileReader(csvFile));
        
        //ArrayList<String[]> possibleSubjectClashes = new ArrayList<String[]>();
        String line;
        String[] fields;

        int MAX_COURSES = 17;
        String[][] subjects = new String[2*MAX_COURSES+1][10];
        int lineCount = 0;
        String classDetails;
        while (( line = bufferedReader.readLine()) != null)
        {
        		System.out.println(line);
                fields = line.split(FieldDelimiter, 10);
                for ( int i = 0 ; i < fields.length ; i++ )
                {
                	subjects[lineCount][i] = fields[i];
                }
                lineCount++;//System.out.println(lineCount++);
        }
        bufferedReader.close();

        //boolean available = false;
        /*int twentyFourHourClassStart=0;
		int twentyFourHourClassEnd=0;*/
        int columnNumberCorrespondingToDay = mapbook.get(date.getDayOfWeek().toString().toUpperCase());
        //String allottedtime = "";
        int tuteColumn = 8, labColumn = 9;
        System.out.println("\n\n\nFIRST LOOP OVER");
        for ( int rowNumber = 1 ; rowNumber < 2*MAX_COURSES+1 ; rowNumber++ )
        {
        	//System.out.println(rowNumber);
        	System.out.println(subjects[rowNumber][0]);
        	if(subjects[rowNumber][0] == null) {
        		continue;
        	}
        	classDetails = subjects[rowNumber][columnNumberCorrespondingToDay];
        	String tuteDetails = subjects[rowNumber][tuteColumn];
        	String labDetails = subjects[rowNumber][labColumn];
        	
        	
        	
        	if (classDetails!=null && (classDetails.contains(room))) {
        		//classDetails.replaceAll("\"", "");
    			System.out.println("Class " + subjects[rowNumber][0] + classDetails);
    			String classTiming = classDetails.substring(1, classDetails.indexOf("$"));
    			System.out.println("Class timing is : " + classTiming);
    			if(classDetails.contains(room) && timeClash(classTiming, time)){
    				System.out.println(subjects[rowNumber][0] + classDetails);
    	    		return false;
    			}
    			/*twentyFourHourClassStart=0;
    			twentyFourHourClassEnd=0;
    			String classStartTime = classTiming.substring(0, classTiming.indexOf("-")).trim();
    	    	String classEndTime = classTiming.substring(classTiming.indexOf("-")+1).trim();
    	    	//System.out.println(classTiming);
    	    	System.out.println("We here " + subjects[rowNumber][0]);
    	    	int twentyFourHourClassStartTime = convertTimeToInt(classStartTime);
    	    	int twentyFourHourClassEndTime = convertTimeToInt(classEndTime);
    	    	
    	    	System.out.println(subjects[rowNumber][0] + classDetails + classStartTime + " " + classEndTime + " " + twentyFourHourClassStartTime + " " + twentyFourHourClassEndTime);
    	    	if(
    	    				(	(twentyFourHourRequestStartTime < twentyFourHourClassEndTime) 	&& (twentyFourHourRequestStartTime >= twentyFourHourClassStartTime )	)
    	    			|| 	(	(twentyFourHourRequestEndTime > twentyFourHourClassStartTime) 	&& (twentyFourHourRequestEndTime <= twentyFourHourClassEndTime)		)
    	    			||  (	(twentyFourHourRequestStartTime < twentyFourHourClassStartTime) && (twentyFourHourRequestEndTime > twentyFourHourClassEndTime)		)
    	    		) {
    	    		System.out.println(subjects[rowNumber][0] + classDetails);
    	    		return false;
    	    	}*/
        	    	
        	}
        		
    		if (tuteDetails!=null && tuteDetails.contains(room)) {
    			String[] deets = tuteDetails.split("_");
    			for(String eachTute : deets) {
    				System.out.println(eachTute);
    			}
    			for(String eachTute : deets) {
    				//System.out.println(subjects[rowNumber][0]);
    				System.out.println(eachTute);
        			String classTiming = eachTute.substring(eachTute.indexOf(" ") + 1, eachTute.indexOf("$"));
        			if(eachTute.contains(room) && timeClash(classTiming, time)){
        				System.out.println(subjects[rowNumber][0] + tuteDetails);
        	    		return false;
        			}
        			/*twentyFourHourClassStart=0;
        			twentyFourHourClassEnd=0;
        			String classStartTime = classTiming.substring(0, classTiming.indexOf("-")).trim();
        	    	String classEndTime = classTiming.substring(classTiming.indexOf("-")+1).trim();
        	    	
        	    	int twentyFourHourClassStartTime = convertTimeToInt(classStartTime);
        	    	int twentyFourHourClassEndTime = convertTimeToInt(classEndTime);
        	    	if(
        	    				(	(twentyFourHourRequestStartTime < twentyFourHourClassEndTime) 	&& (twentyFourHourRequestStartTime >= twentyFourHourClassStartTime )	)
        	    			|| 	(	(twentyFourHourRequestEndTime > twentyFourHourClassStartTime) 	&& (twentyFourHourRequestEndTime <= twentyFourHourClassEndTime)		)
        	    			||  (	(twentyFourHourRequestStartTime < twentyFourHourClassStartTime) && (twentyFourHourRequestEndTime > twentyFourHourClassEndTime)		)
        	    		) {
        	    		return false;
        	    	}*/
    			}
    	    	
    		}
        		
    		if (labDetails!=null && labDetails.contains(room)) {
    			String[] deets = labDetails.split("_");
    			
    			for(String eachLab : deets) {
    				System.out.println(eachLab);
        			String classTiming = labDetails.substring(labDetails.indexOf(" ") + 1, labDetails.indexOf("$"));
        			//System.out.println(classTiming);
        			if(eachLab.contains(room) && timeClash(classTiming, time)){
        				System.out.println(subjects[rowNumber][0] + labDetails);
        	    		return false;
        			}
        			/*twentyFourHourClassStart=0;
        			twentyFourHourClassEnd=0;
        			String classStartTime = classTiming.substring(0, classTiming.indexOf("-")).trim();
        	    	String classEndTime = classTiming.substring(classTiming.indexOf("-")+1).trim();
        	    	
        	    	int twentyFourHourClassStartTime = convertTimeToInt(classStartTime);
        	    	int twentyFourHourClassEndTime = convertTimeToInt(classEndTime);
        	    	if(
        	    				(	(twentyFourHourRequestStartTime < twentyFourHourClassEndTime) 	&& (twentyFourHourRequestStartTime >= twentyFourHourClassStartTime )	)
        	    			|| 	(	(twentyFourHourRequestEndTime > twentyFourHourClassStartTime) 	&& (twentyFourHourRequestEndTime <= twentyFourHourClassEndTime)		)
        	    			||  (	(twentyFourHourRequestStartTime < twentyFourHourClassStartTime) && (twentyFourHourRequestEndTime > twentyFourHourClassEndTime)		)
        	    		) {
        	    		System.out.println(subjects[rowNumber][0] + labDetails);
        	    		return false;
        	    	}*/
    			}
        	    	
        	
        	}
    		
    		
        	
        	
        	
        }
        return !clashWithRequests(room, date, time);
        //return true;
       
    }
	
	
	
	/**
	 * @param room
	 * @param date
	 * @param time
	 * @return Clash exists or not in a room at a time.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static boolean clashWithRequests(String room, LocalDate date, String time) throws IOException, ClassNotFoundException {
		ArrayList<Request> requests = new ArrayList<Request>();
		FileInputStream fileIn = new FileInputStream("C:\\Users\\HP\\Downloads\\Submission\\Submission\\Data\\Requests.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
	    try{
	        requests = (ArrayList<Request>) in.readObject();
	    }
	    catch (IOException e)
	    {
	        System.out.println("Could not deserialize requests");
	        e.printStackTrace();
	    }
	    for(Request request : requests) {
	    	System.out.println(request.getDATE() + request.getStartTime() + request.getEndTime());
	    	if(request.getDATE().getDayOfMonth() == (date.getDayOfMonth()) && request.getDATE().getMonth().toString().equals(date.getMonth().toString()) && request.getDATE().getYear() == date.getYear())
		    	if(request.getRoom().equalsIgnoreCase(room) && timeClash(request.getStartTime() + "-" + request.getEndTime(), time))
		    		return true;
	    }
	    System.out.println("No clashes with existing requests.");
		return false;
	}

	/*public void launchUserController(String usertype, User user) {
		//setRoot(FXMLLoader.load(getClass().getResource(usertype  + "View.fxml")));
		Parent root = FXMLLoader.load(getClass().getResource(usertype  + "View.fxml"));
		Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}*/
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	static boolean requestAvailableRoom(String time , LocalDate date, int size ) throws IOException, FileNotFoundException, ClassNotFoundException
    {
    	/*String asliday = "Thursday";
    	String room = "C21";
    	String time = "11:30-12:00";*/
    	System.out.println("Time argument is : " + time);
    	System.out.println("Time is :  " + time);
    	String startTime = time.substring(0, time.indexOf("-")).trim();
    	String endTime = time.substring(time.indexOf("-")+1).trim();
    	System.out.print(startTime);
    	System.out.println(endTime);
    	
    	
    	int twentyFourHourRequestStartTime = convertTimeToInt(startTime);
    	int twentyFourHourRequestEndTime = convertTimeToInt(endTime);
    	System.out.print(twentyFourHourRequestStartTime);
    	System.out.println(twentyFourHourRequestEndTime);
    	
    	
    	HashMap<String,Integer> mapbook = new HashMap<String,Integer>();
    	HashMap<String,String> secondmap = new HashMap<String,String>();
        mapbook.put("MONDAY",3);		
        mapbook.put("TUESDAY",4);		
        mapbook.put("WEDNESDAY",5);		
        mapbook.put("THURSDAY",6);		
        mapbook.put("FRIDAY",7);
        
        ArrayList<String> roomSize25 = new ArrayList<String>();
        roomSize25.add("LR1");roomSize25.add("LR2");roomSize25.add("LR3");
        
        ArrayList<String> roomSize60 = new ArrayList<String>();
        roomSize60.add("C02");roomSize60.add("C12");roomSize60.add("C22");roomSize60.add("C03");roomSize60.add("C13");roomSize60.add("C23");
        
        ArrayList<String> roomSize150 = new ArrayList<String>();
        roomSize150.add("C01");roomSize150.add("C02");roomSize150.add("C03");
        
        ArrayList<String> labs = new ArrayList<String>();
        labs.add("L21");labs.add("L22");labs.add("L23");
        
        HashMap<Integer, ArrayList<String>> sizeRoomHash = new HashMap<Integer, ArrayList<String>>();
        sizeRoomHash.put(25, roomSize25);
        sizeRoomHash.put(60, roomSize60);
        sizeRoomHash.put(150,roomSize150);
        sizeRoomHash.put(40, labs);
        
        int[] sizes = {25, 40, 60, 150};
        for(int iteratorSize : sizes) {
        	if(size> iteratorSize) continue;
        	ArrayList<String> rooms = sizeRoomHash.get(iteratorSize);
        	for(String room : rooms) {
        		if(viewRoomAvailability(room, time, date)) {
        			return true;
        		}
        	}
        }
        return false;
       
    }
	
	
	/**
	 * @param existingTime
	 * @param incomingTime
	 * @return Time Clashes between two time Strings.
	 */
	public static boolean timeClash(String existingTime, String incomingTime) {
		System.out.print("Checking clashes between : ");
		//int existingTimeStart = 0, existingTimeEnd=0, incomingTimeStart =0, incomingTimeEnd=0;
		System.out.println(existingTime + "\t" + incomingTime);
		String existingStartTimeString = existingTime.substring(0, existingTime.indexOf("-")).trim();
    	String existingEndTimeString = existingTime.substring(existingTime.indexOf("-")+1).trim();
    	String incomingStartTimeString = incomingTime.substring(0, incomingTime.indexOf("-")).trim();
    	String incomingEndTimeString = incomingTime.substring(incomingTime.indexOf("-")+1).trim();
    	
    	
    	
    	int twentyFourHourincomingStartTime = convertTimeToInt(incomingStartTimeString);
    	int twentyFourHourincomingEndTime = convertTimeToInt(incomingEndTimeString);
    	int twentyFourHourexistingStartTime = convertTimeToInt(existingStartTimeString);
    	int twentyFourHourexistingEndTime = convertTimeToInt(existingEndTimeString);
    	if(
    				(	(twentyFourHourincomingStartTime < twentyFourHourexistingEndTime) 	&& (twentyFourHourincomingStartTime >= twentyFourHourexistingStartTime )	)
    			|| 	(	(twentyFourHourincomingEndTime > twentyFourHourexistingStartTime) 	&& (twentyFourHourincomingEndTime <= twentyFourHourexistingEndTime)		)
    			||  (	(twentyFourHourincomingStartTime < twentyFourHourexistingStartTime) && (twentyFourHourincomingEndTime > twentyFourHourexistingEndTime)		)
    		) {
    		
    		return true;
    	}
		return false;
	}
}
