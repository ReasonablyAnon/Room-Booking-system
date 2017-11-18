package application;
	
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application implements Serializable {
	
	public Parent parent;
	public Stage stage;
	public Scene scene;
	public User loggedUser;
	
	/*public Main() {
		instance = this;
	}*/
	@Override
	public void start(Stage primaryStage) throws IOException {
		SampleController sampleController = null;
		try {
			sampleController = new SampleController();
			//System.out.println("Back at the MAIN Function");
			stage = primaryStage;
			//sampleController.loadFXML();
			sampleController.launchController(primaryStage);
			//parent = loadFXML("Sample.fxml");
			//primaryStage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
        

		/*try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public Parent loadFXML(String fxml) {
		try {
			FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(fxml));
			parent = fxmlloader.load();
			//scene = stage.getScene();
			if(null == stage.getScene()) 
				scene = new Scene(parent,600,400);
			
			else 
				stage.getScene().setRoot(parent);
			
			fxmlloader.setController(this);
			stage.sizeToScene();
			//stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("Loaded " + fxml);
		return parent;
		
	}
	
	
	
	
	public static void main(String[] args) throws IOException, ClassNotFoundException,FileNotFoundException {
		
		ArrayList<Request> requests = new ArrayList<Request>();
		String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Requests.ser";
		//System.out.println(path);
		FileOutputStream fileOut =  new FileOutputStream(path);
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		try {
			out.writeObject(requests);}
		catch(Exception e) {
			e.printStackTrace();
		}
		out.close();
		
		requests.add(new Request("My Purpose", "C21", "50", LocalDate.now(), "11:00", "1:30", "iiitd", false));
		requests.add(new Request("My Purpose", "C21", "50", LocalDate.now(), "1:00", "1:30", "iiitd", false));
		fileOut =  new FileOutputStream(path);
		out =  new ObjectOutputStream(fileOut);
		try {
			out.writeObject(requests);}
		catch(Exception e) {
			e.printStackTrace();
		}
		out.close();
		 
		
		
		
		//System.out.println(Main.class.getResource("Sample.fxml").getPath());
		ArrayList<Student> students = new ArrayList<Student>();
		 
			Student a = new Student("iiitd","Sahil", "password1");
			Student b = new Student("gmail","Aamir", "password2");
			Student c = new Student("hotmail","Ashwat", "password3");
			Student d = new Student("yahoo","Sharan", "password4");
			students.add(a); students.add(b); students.add(c); students.add(d);
		
		ArrayList<Faculty> faculty = new ArrayList<Faculty>();
			Faculty fa = new Faculty("iiitd","Akshay", "password1");
			Faculty fb = new Faculty("gmail","Rajiv", "password2");
			Faculty fc = new Faculty("hotmail","Samresh", "password3");
			Faculty fd = new Faculty("yahoo","Vivek", "password4");
			faculty.add(fa); faculty.add(fb); faculty.add(fc); faculty.add(fd);
		
		ArrayList<Admin> admins = new ArrayList<Admin>();
			Admin aa = new Admin("iiitd","Sheetu", "password1");
			Admin ab = new Admin("gmail","Anshu", "password2");
			Admin ac = new Admin("hotmail","Ravi", "password3");
			Admin ad = new Admin("yahoo","Pankaj", "password4");
			admins.add(aa); admins.add(ab); admins.add(ac);admins. add(ad);
		
		
		
		
		
		String path1 = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Student.ser";
		FileOutputStream fileOut1 =  new FileOutputStream(path1);
		ObjectOutputStream out1 =  new ObjectOutputStream(fileOut1);
		try {
			out1.writeObject(students);
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}
		
		out1.close();
		
		
		String path2 = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Faculty.ser";
		FileOutputStream fileOut2 =  new FileOutputStream(path2);
		ObjectOutputStream out2 =  new ObjectOutputStream(fileOut2);
		try {
			out2.writeObject(faculty);
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}
		
		out2.close();
		
		String path3 = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\Admin.ser";
		FileOutputStream fileOut3 =  new FileOutputStream(path3);
		ObjectOutputStream out3 =  new ObjectOutputStream(fileOut3);
		try {
			out3.writeObject(admins);
		}
		catch (Exception e) {
			e.printStackTrace();
			e.getCause();
		}
		
		out3.close();
		
		////CHECK CLASHES WITH REQUEST. CHANGE REQUESTS THAT ARE SHOWN AS THEY ARE ACCEPTED.
		
		try {
			launch(args);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
}
