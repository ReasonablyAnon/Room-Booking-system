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

public class Admin extends User implements Serializable {

	
	
	/**
	 * @param email
	 * @param name
	 * @param password
	 * Constructor functioon calls constructor superclass
	 */
	public Admin(String email, String name, String password) {
		super(email, name, password);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return All requests that haven't been accepted
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Request> getUnAcceptedRequests() throws IOException, ClassNotFoundException{
		//Request.clear();
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		FileInputStream fileIn = new FileInputStream("C:\\\\Users\\\\Sahil Hassan\\\\Documents\\\\Coursework\\\\CSE201 Advanced Programming\\\\AP Project 1\\\\Data\\\\Requests.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Request> readRequests= new ArrayList<Request>();
		try
	    { 
			readRequests = (ArrayList<Request>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		ArrayList<Request> returnRequests = new ArrayList<Request>();
		for(Request request : readRequests) {
			if(!request.isAccepted() && !(request.getDATE().compareTo(LocalDate.now()) < -5))	{
				System.out.println(request.getPurpose());
				returnRequests.add(request);
			}
		}
		return returnRequests;
	}
	
	
	/**
	 * @return All requests that have been accepted
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public ArrayList<Request> getAcceptedRequests() throws IOException, ClassNotFoundException{
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		FileInputStream fileIn = new FileInputStream("C:\\\\Users\\\\Sahil Hassan\\\\Documents\\\\Coursework\\\\CSE201 Advanced Programming\\\\AP Project 1\\\\Data\\\\Requests.ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Request> readRequests= new ArrayList<Request>();
		try
	    { 
			readRequests = (ArrayList<Request>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		ArrayList<Request> returnRequests = new ArrayList<Request>();
		for(Request request : readRequests) {
			if(request.isAccepted() && !(request.getDATE().compareTo(LocalDate.now()) < -5))	returnRequests.add(request);
		}
		return returnRequests;
	}
	
	
	
	/**
	 * @param name
	 * @param password
	 * @param usertype
	 * @return Admin who is logging in, null if credentials are wrong
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Admin validate(String name, String password, String usertype) throws ClassNotFoundException, IOException {
		
		System.out.println("Name : " + name);
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		FileInputStream fileIn = new FileInputStream(path + usertype + ".ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Admin> readUsers = new ArrayList<Admin>();
		try
	    { 
	        readUsers = (ArrayList<Admin>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		for(Admin admin: readUsers) {
			System.out.println("Admin for this iteration is : " + admin.getName());
	    	if(admin.getName().equals(name)) {
	    		System.out.print("username is right : \t");
	    		if(admin.getPassword().equals(password)) {
	    			System.out.println("Logged in "); return admin;
	    		}
	    		else return null;
	    	}
		}
		return null;
	}

	/**
	 * @param newAdmin
	 * @return Successful addition of new Admin
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static boolean addUser(Admin newAdmin) throws IOException, ClassNotFoundException {
		
    	String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Admin.ser";
    	System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Admin> readUsers = new ArrayList<Admin>();
		try
	    { 
			
				readUsers= (ArrayList<Admin>) in.readObject();
    		
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		boolean emailRegistered = false;
		for(User user: readUsers) {
			System.out.println(user.getEmail());
			if(user.getEmail().equalsIgnoreCase(newAdmin.getEmail())){
				System.out.println("Email registered");
				emailRegistered = true;
				return false;
			}
		}
		readUsers.add(newAdmin);
    		
		String Path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Admin.ser";
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
		return true;

	}
	
	/**
	 * Logs current Admin out
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void logout() throws IOException, ClassNotFoundException {
		Request.clear();
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Admin.ser";
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Admin> readUsers = new ArrayList<Admin>();
		try
	    {
	        readUsers = (ArrayList<Admin>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		for(int i=0; i<readUsers.size(); i++) {
			System.out.println("Admin for this iteration is : " + readUsers.get(i).getEmail());
			if(this.getEmail().equals(readUsers.get(i).getEmail())){
				readUsers.set(i, this);
				break;
				
			}
		}
		String Path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Admin.ser";
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

}
