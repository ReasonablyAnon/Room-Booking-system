package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author Sahil Hassan
 *
 */
public class Request implements Serializable{
	
	

	private String purpose;
	private String room;
	private String size;
	private LocalDate DATE;
	private String startTime;
	private String endTime;
	private boolean accepted;
	private String userEmail;
	


	private static ArrayList<Request> requests = new ArrayList<Request>();
	

	/**
	 * @param purpose
	 * @param room
	 * @param size
	 * @param DATE
	 * @param startTime
	 * @param endTime
	 * @param email
	 * @param accepted
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Request(String purpose, String room, String size, LocalDate DATE, String startTime, String endTime, String email, boolean accepted) throws IOException, ClassNotFoundException {
		super();
		clear();
		System.out.println("Student request with room.");
		this.purpose = purpose;
		this.room = room;
		this.size = size;
		this.DATE = DATE;
		this.startTime = startTime;
		this.endTime = endTime;
		this.accepted = accepted;
		this.userEmail = email;
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		//System.out.println(path);
		clear();
		FileInputStream fileIn = new FileInputStream(path + "Requests.ser");
		/*if(fileIn == null) {
			System.out.println("File not found");
		}*/
		
		ObjectInputStream in = new ObjectInputStream(fileIn);
	    try{
	        requests = (ArrayList<Request>) in.readObject();
	    }
	    catch (IOException e)
	    {
	        System.out.println("Could not deserialize requests");
	        e.printStackTrace();
	    }
	    in.close();
	    /*for(Request request: requests) {
	    	long diff = request.DATE.compareTo(LocalDate.now());
	    	if(diff<-5)	requests.remove(request);
	    }*/
	    requests.add(this);
	    for(Request request: requests) {
	    	System.out.println("Serializing request in constructor : " + request.getUserEmail() + "\t" + request.getDATE() + "\t" + request.getStartTime());
	    }
	    FileOutputStream fileOut =  new FileOutputStream(path + "Requests.ser");
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		out.writeObject(requests);
		out.close();
		
	}
	
	/**
	 * @param purpose
	 * @param size
	 * @param DATE
	 * @param startTime
	 * @param endTime
	 * @param email
	 * @param accepted
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public Request(String purpose, String size, LocalDate DATE, String startTime, String endTime, String email, boolean accepted) throws IOException, ClassNotFoundException {
		super();
		clear();
		
		this.purpose = purpose;
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
        	if(Integer.parseInt(size)> iteratorSize) continue;
        	ArrayList<String> rooms = sizeRoomHash.get(iteratorSize);
        	for(String room : rooms) {
        		if(User.viewRoomAvailability(room, startTime + "-" + endTime , DATE)) {
        			this.room = room;
        		}
        	}
        }
		this.size = size;
		this.DATE = DATE;
		this.startTime = startTime;
		this.endTime = endTime;
		this.accepted = accepted;
		this.userEmail = email;
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		//System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path + "Requests.ser");
		/*if(fileIn == null) {
			System.out.println("File not found");
		}*/
		ObjectInputStream in = new ObjectInputStream(fileIn);
	    try{
	        requests = (ArrayList<Request>) in.readObject();
	    }
	    catch (IOException e)
	    {
	        System.out.println("Could not deserialize requests");
	        e.printStackTrace();
	    }
	    in.close();
	    for(Request request: requests) {
	    	long diff = request.DATE.compareTo(LocalDate.now());
	    	if(diff<-5)	requests.remove(request);
	    }
	    requests.add(this);
	    FileOutputStream fileOut =  new FileOutputStream(path + "Requests.ser");
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		out.writeObject(requests);
		out.close();
		
	}
	
	/**
	 * @param room
	 * @param date
	 * @param startTime
	 * @param endTime
	 * @param email
	 * @param accepted
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * Create new booking for Admin or Faculty member and clear old requests.
	 */
	public Request(String room, LocalDate date, String startTime, String endTime, String email, boolean accepted) throws IOException, ClassNotFoundException {
		clear();
		System.out.println("Booking constructor." + room + date + startTime + endTime + email);
		this.purpose = "";
		this.room = room;
		this.size = "0";
		this.DATE = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.accepted = accepted;
		this.userEmail = email;
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		//System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path + "Requests.ser");
		/*if(fileIn == null) {
			System.out.println("File not found");
		}*/
		ObjectInputStream in = new ObjectInputStream(fileIn);
	    try{
	        requests = (ArrayList<Request>) in.readObject();
	    }
	    catch (IOException e)
	    {
	        System.out.println("Could not deserialize requests");
	        e.printStackTrace();
	    }
	    in.close();
	    System.out.println("Clearing....");
	    ArrayList<Request> tempRequests = new ArrayList<Request>();
	    for(Request request : requests) {
	    	tempRequests.add(request);
	    }
	    for(Request request: requests) {
	    	System.out.println(request.DATE + "\t" + request.getStartTime() + "\t" + LocalDate.now() + request.DATE.compareTo(LocalDate.now()) + "\t " + LocalDate.now().compareTo(request.DATE));
	    	long diff = request.DATE.compareTo(LocalDate.now());
	    	if(diff<-5) {
	    		tempRequests.remove(request);
	    		//requests.remove(request);
	    	}
	    }
	    System.out.println("Cleared old requests.");
	    requests=tempRequests;
	    requests.add(this);
	    for(Request request : requests) {
	    	System.out.println(request.getUserEmail() + "\t" + request.getRoom() + "\t"+ request.getDATE() + request.getStartTime());
	    }
	    System.out.println("Construcotr over for booking.");
	    FileOutputStream fileOut =  new FileOutputStream(path + "Requests.ser");
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		out.writeObject(requests);
		out.close();
		
	}

	/**
	 * Clears all requests that are older than 5 days.
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void clear() throws IOException, ClassNotFoundException {
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
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
	    /*for(Request request: requests) {
	    	long diff = request.DATE.compareTo(LocalDate.now());
	    	if(diff<-5)	requests.remove(request);
	    }*/
	    ArrayList<Request> tempRequests = new ArrayList<Request>();
	    for(Request request : requests) {
	    	tempRequests.add(request);
	    }
	    for(Request request: requests) {
	    	System.out.println(request.DATE + "\t" + request.getStartTime() + "\t" + LocalDate.now() + request.DATE.compareTo(LocalDate.now()) + "\t " + LocalDate.now().compareTo(request.DATE));
	    	long diff = request.DATE.compareTo(LocalDate.now());
	    	if(diff < -5) {
	    		System.out.println("Removing : " + request.getPurpose());
	    		tempRequests.remove(request);
	    		//requests.remove(request);
	    	}
	    }
	    System.out.println("Constructor over for clear.");
	    FileOutputStream fileOut =  new FileOutputStream(path + "Requests.ser");
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		out.writeObject(tempRequests);
		out.close();
	}
	
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public String getPurpose() {
		return purpose;
	}


	/**
	 * @param purpose
	 */
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}


	/**
	 * @return
	 */
	public String getRoom() {
		return room;
	}


	/**
	 * @param room
	 */
	public void setRoom(String room) {
		this.room = room;
	}


	/**
	 * @return
	 */
	public String getSize() {
		return size;
	}


	/**
	 * @param size
	 */
	public void setSize(String size) {
		this.size = size;
	}


	/**
	 * @return
	 */
	public LocalDate getDATE() {
		return DATE;
	}


	/**
	 * @param dATE
	 */
	public void setDATE(LocalDate dATE) {
		DATE = dATE;
	}


	/**
	 * @return
	 */
	public String getStartTime() {
		return startTime;
	}


	/**
	 * @param startTime
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	/**
	 * @return
	 */
	public String getEndTime() {
		return endTime;
	}


	/**
	 * @param endTime
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}


	/**
	 * @return
	 */
	public boolean isAccepted() {
		return accepted;
	}


	
	/**
	 * @param accepted
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * Change accepted value of this request and remove all clashing requests if setting to true.
	 */
	public void setAccepted(boolean accepted) throws IOException, ClassNotFoundException {
		clear();
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path + "Requests.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		try
	    { 
	        requests = (ArrayList<Request>) in.readObject();
	    }
	    catch (IOException e)
	    {
	        System.out.println("Could not deserialize requests in getAcceptedNoClashRequest()");
	        e.printStackTrace();
	    }
	    in.close();
	    Request temp = null;
	    for(Request request : requests) {
	    	if(request.getUserEmail().equals(this.getUserEmail()) && request.getDATE().equals(this.getDATE()) && request.getStartTime().equals(this.getStartTime())) {
	    		System.out.println("Found the request.");
	    		request.accepted = accepted;
	    		System.out.println(request.getPurpose() + request.getDATE() + request.getSize() + request.getStartTime());
	    		temp = request;
	    		break;
	    		
	    	}
	    }
	    //Remove clashing requests.
	    ArrayList<Request> serializeThese = new ArrayList<Request>();
	    serializeThese.add(temp);
	    for(Request request : requests) {
//	    	if(!temp.getDATE().equals(request))
	    		/*User.timeClash(temp.getStartTime() + "-"+ temp.getEndTime(), request.getStartTime() + "-" + request.getEndTime());*/
	    		if(!temp.getDATE().toString().equals(request.getDATE().toString()) || !User.timeClash(temp.getStartTime() + "-"+ temp.getEndTime(), request.getStartTime() + "-" + request.getEndTime()) || !temp.getRoom().equals(request.getRoom()))
	    		{	
	    			serializeThese.add(request);
	    			System.out.println(request.getPurpose());
	    		}
	    }

	    System.out.println("Serializing these requests:");
	    for(Request request : serializeThese) {
	    	System.out.println(request.getPurpose() + request.getDATE() + request.getStartTime());
	    }
	    FileOutputStream fileOut =  new FileOutputStream(path + "Requests.ser");
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		out.writeObject(serializeThese);
		out.close();
	}


	public static ArrayList<Request> getRequests() {
		return requests;
	}


	public static void setRequests(ArrayList<Request> requests) {
		Request.requests = requests;
	}
	/**
	 * @return
	 */
	public String getUserEmail() {
		return userEmail;
	}

	/**
	 * @param userEmail
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
	/**
	 * @return ArrayList<Request> that have not been accepted
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static ArrayList<Request> getUnacceptedNoClashRequests() throws IOException, ClassNotFoundException{
		clear();
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path + "Requests.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		try
	    { 
	        requests = (ArrayList<Request>) in.readObject();
	    }
	    catch (IOException e)
	    {
	        System.out.println("Could not deserialize requests in getUnacceptedNoClashRequest()");
	        e.printStackTrace();
	    }
	    in.close();
	    ArrayList<Request> acceptedRequests = new ArrayList<Request>();
	    for(Request request : requests) {
	    	if(request.accepted)
	    		acceptedRequests.add(request);
	    }
	    ArrayList<Request> toShowRequests = new ArrayList<Request>();
	    for(Request acceptedRequest : acceptedRequests) {
	    	for(Request request : requests)
	    	if(!acceptedRequest.getDATE().toString().equals(request.getDATE().toString()) || !acceptedRequest.getStartTime().equals(request.getStartTime()))
	    		toShowRequests.add(request);
	    }
	    
	    FileOutputStream fileOut =  new FileOutputStream(path + "Requests.ser");
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		out.writeObject(toShowRequests);
		out.close();
		
		return toShowRequests;
		
	}
}
