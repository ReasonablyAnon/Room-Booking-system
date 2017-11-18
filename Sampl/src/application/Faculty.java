package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Sahil Hassan
 *
 */
/**
 * @author Sahil Hassan
 *
 */
/**
 * @author Sahil Hassan
 *
 */
public class Faculty extends User  implements Serializable{

	/**
	 * @param name
	 * @param email
	 * @param password
	 */
	public Faculty(String email, String name, String password) {
		super(email, name, password);
		// TODO Auto-generated constructor stub
	}

	protected void addRequest(Request request) {
		myRequests.add(request);
	}
	
	/**
	 * Logout of current session
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public void logout() throws IOException, ClassNotFoundException {
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Faculty.ser";
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Faculty> readUsers = new ArrayList<Faculty>();
		try
	    {
	        readUsers = (ArrayList<Faculty>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		for(int i=0; i<readUsers.size(); i++) {
			System.out.println("Faculty for this iteration is : " + readUsers.get(i).getEmail());
			if(this.getEmail().equals(readUsers.get(i).getEmail())){
				readUsers.set(i, this);
				break;
				
			}
		}
		String Path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Faculty.ser";
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
	
	
	/**
	 * 
	 * @param name
	 * @param password
	 * @param usertype
	 * @return Faculty object if credentials match, else null.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public static Faculty validate(String name, String password, String usertype) throws ClassNotFoundException, IOException {
		
		System.out.println("Name : " + name);
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\";
		FileInputStream fileIn = new FileInputStream(path + usertype + ".ser");
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Faculty> readUsers = new ArrayList<Faculty>();
		try
	    { 
	        readUsers = (ArrayList<Faculty>) in.readObject();
	        in.close();
	    }
	    catch (IOException e)
	    {
	        System.out.println("didnt work");
	        e.printStackTrace();
	    }
		for(Faculty faculty: readUsers) {
			System.out.println("Faculty for this iteration is : " + faculty.getName());
	    	if(faculty.getName().equals(name)) {
	    		System.out.print("username is right : \t");
	    		if(faculty.getPassword().equals(password)) {
	    			System.out.println("Logged in "); return faculty;
	    		}
	    		else return null;
	    	}
		}
		return null;
	}
	
	
	/**
	 * @param newFaculty
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static boolean addUser(Faculty newFaculty) throws IOException, ClassNotFoundException {
		
    	String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Faculty.ser";
    	System.out.println(path);
		FileInputStream fileIn = new FileInputStream(path);
		ObjectInputStream in = new ObjectInputStream(fileIn);
		ArrayList<Faculty> readUsers = new ArrayList<Faculty>();
		try
	    { 
			
				readUsers= (ArrayList<Faculty>) in.readObject();
    		
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
			if(user.getEmail().equalsIgnoreCase(newFaculty.getEmail())){
				System.out.println("Email registered");
				emailRegistered = true;
				return false;
			}
		}
		readUsers.add(newFaculty);
    		
		String Path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Faculty.ser";
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
	
}
