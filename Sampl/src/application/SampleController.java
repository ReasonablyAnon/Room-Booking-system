package application;


import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/*import javafx.event.ActionEvent;
import javafx.event.EventHandler;*/
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
/*import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;*/
import javafx.stage.Stage;

public class SampleController {
	
	ObservableList<String> userTypeList = FXCollections.observableArrayList("Student" , "Faculty", "Admin");
	
	private Parent parent;
    private Scene scene;
    private Stage stage;
    private FXMLLoader fxmlLoader;
    
    
    private StudentViewController StudentController;
    private FacultyViewController FacultyController;
    private AdminViewController AdminController;
    
    
    
    private User user;
    private Student newStudent;
    private Admin newAdmin;
    private Faculty newFaculty;
    private ArrayList<Student> readStudent = new ArrayList<Student>();
    private ArrayList<Faculty> readFaculty = new ArrayList<Faculty>();
    private ArrayList<Admin> readAdmin = new ArrayList<Admin>();
    
    
	@FXML	private ChoiceBox<String> userType;
	
	@FXML	private TextField email;
	@FXML	private TextField userName;
	@FXML	private TextField password;
	
	
	
	@FXML	ToggleGroup group = new ToggleGroup();
	@FXML	RadioButton loginButton = new RadioButton("Login");
	@FXML	RadioButton signupButton = new RadioButton("Sign Up");
	
	@FXML	private Button submitButton;
	@FXML	private Label label;
	
	public void launchController(Stage primaryStage) {
		loadSample();
        this.stage = primaryStage;
        stage.setTitle("User Login");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.hide();
        stage.show();

	}
	public void closeStage(Stage stage) {
		stage.close();
	}
	
	protected void loadSample() {
		try {
			fxmlLoader = new FXMLLoader(getClass().getResource("Sample.fxml"));
			parent = fxmlLoader.load();
			fxmlLoader.setController(this);
			scene = new Scene(parent,400,400);
			//parent = (Parent)(fxmlloader);
			//parent = (Parent)fxmlloader.load();
			//parent = (Parent)(AnchorPane)fxmlloader;
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public SampleController() throws IOException,LoadException {
		
		
			/*System.out.println(SampleController.class);
			System.out.println(getClass().getResource("Sample.fxml"));
			System.out.println(getClass().getPackage().getClass().getResource("\\C:\\Users\\Sahil Hassan\\Eclipse Workspace\\Sampl\\src\\application\\Sample.fxml"));
			System.out.println(getClass().getClassLoader());
			System.out.println(SampleController.class.getClassLoader().getResources("Sample.fxml"));
			System.out.println("\n\n\n");
			//loadFXML();
		
		System.out.println("WHATEVER");*/
		
		//parent = root.getParent();
		
		/*FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Sample.fxml"));
        fxmlLoader.setController(this);
        try {
            parent = (Parent) fxmlLoader.load();
            scene = new Scene(parent, 600, 400);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

	}
	
	
	
	
	@FXML
	private void initialize() throws ClassNotFoundException, IOException {
		userType.setItems(userTypeList);
		label.setVisible(false);
		loginButton.setUserData("Login");
		loginButton.setToggleGroup(group);
		loginButton.setSelected(true);
		email.setVisible(false);
		signupButton.setUserData("Sign Up");
		signupButton.setToggleGroup(group);
		userType.setValue("Student");
		//Request.clear();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>()
	    {
		    @Override
		    public void changed(ObservableValue<? extends Toggle> ov, Toggle t, Toggle t1)
		        {
		        RadioButton chk = (RadioButton)t1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
		        System.out.println("Selected Radio Button - "+chk.getText());
		        if(chk.getText().equals("Sign Up"))	email.setVisible(true);
		        else email.setVisible(false);
		        }
	    });
		
	}
	
	@FXML
	private void submit() throws IOException, ClassNotFoundException {

	            String username = userName.getText();
	            String Password = password.getText();
	            String Email = "";
	            if(signupButton.isSelected()) {
	            	System.out.println("Signing Up");
	            	Email = email.getText();
	            	if(Email.contains("@iiitd.ac.in") || Email.contains("@iiitd.edu.in")) {
	            		System.out.println(userType.getValue());
	            		String usertype = userType.getValue();
	            		System.out.println(userType);
	            		if(usertype.equals("Student")) {
	            			System.out.println("Creating new Student.");
	            			newStudent = new Student(Email, username, Password);
	            			if(!Student.addUser(newStudent)) {
	            				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        	            alert.setContentText("Email already registered");
		        	            alert.showAndWait();
		        				userName.setText("");
		        				password.setText("");
		        				email.setText("");
	            			}
	            		}
	            			
	            		else if(usertype.equals("Faculty")) {
	            			System.out.println("Creating new Student.");
	            			newFaculty= new Faculty(Email, username, Password);
	            			if(!Faculty.addUser(newFaculty)) {
	            				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        	            alert.setContentText("Email already registered");
		        	            alert.showAndWait();
		        				userName.setText("");
		        				password.setText("");
		        				email.setText("");
	            			}
	            		}
	            			
	            		else {
	            			System.out.println("Creating new Admin.");
	            			newAdmin = new Admin(Email, username, Password);
	            			if(!Admin.addUser(newAdmin)) {
	            				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        	            alert.setContentText("Email already registered");
		        	            alert.showAndWait();
		        				userName.setText("");
		        				password.setText("");
		        				email.setText("");
	            			}
	            		}
	            	}
		            	
		            	/*String path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\" + usertype + ".ser";
		            	System.out.println(path);
		        		FileInputStream fileIn = new FileInputStream(path);
		        		ObjectInputStream in = new ObjectInputStream(fileIn);
		        		ArrayList<User> readUsers = new ArrayList<User>();
		        		try
		        	    { 
		        			if(usertype.equals("Student")) {
		        				readStudent= (ArrayList<Student>) in.readObject();
		            		}
		            			
		            		else if(usertype.equals("Faculty")) {
		            			readFaculty= (ArrayList<Faculty>) in.readObject();
		            		}
		            			
		            		else {
		            			readAdmin= (ArrayList<Admin>) in.readObject();
		            		}
		        			
		        			
		        			readUsers = (ArrayList<User>) in.readObject();
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
		        			if(user.getEmail().equalsIgnoreCase(Email)){
		        				System.out.println("Email registered");
		        				emailRegistered = true;
		        				Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        	            alert.setContentText("Email already registered");
		        	            alert.showAndWait();
		        				userName.setText("");
		        				password.setText("");
		        				email.setText("");
		        			}
		        		}
		        		if(!emailRegistered){
		        			if(userType.equals("Student"))
		        				readUsers.add(newStudent);
		            		else if(userType.equals("Faculty"))
		            			readUsers.add(newFaculty);
		            		else
		            			readUsers.add(newAdmin);
			        		//readUsers.add(newUser);
			        		String Path = "C:\\Users\\Sahil Hassan\\Documents\\Coursework\\CSE201 Advanced Programming\\AP Project 1\\Data\\" + usertype + ".ser";
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
		        		fileIn = new FileInputStream(path);
		        		in = new ObjectInputStream(fileIn);
		        		readUsers = new ArrayList<User>();
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
		        		System.out.println(readUsers.size());
		        		for(User iterUser: readUsers) {
		        			System.out.println("Email : " + iterUser.getEmail() + "\tName : " + iterUser.getName() + "\tPassword : " + iterUser.getPassword());
		        		}
		        		
	            	}
	            	else {
	            		Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	            alert.setContentText("Email is invalid");
        	            alert.showAndWait();
        				userName.setText("");
        				password.setText("");
        				email.setText("");
	            		
	            	}*/
	            }
	            else {
	            	try {
	            	System.out.println(username + " " + Password);
	            	String usertype = userType.getValue().toString();
	            	System.out.println(usertype);
	            	//User user = (User)User.validate(username, Password, usertype);
	            	if(usertype.equals("Student")) {
	            		Student user = Student.validate(username, Password, usertype);
	            		if(user!=null) {
	            			System.out.println("User is student");
							this.user = user;
							fxmlLoader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
							StudentController = new StudentViewController(user);
							System.out.println("Student Controller constructor done.");
							StudentController.setStudent(user);
							StudentController.initData(user);
							
							//
				            fxmlLoader.setController(StudentController);
				            System.out.println(StudentController.currentUser.getName());
				            //StudentController.launchThis((Stage)loginButton.getScene().getWindow(), (Student)this.user,this);
							parent = fxmlLoader.load();
							//StudentViewController controller = fxmlLoader.<StudentViewController>getController();
				            //controller.setPrevStage(stage);
							//StudentController.setStudent((Student)user);
				            
				            scene = new Scene(parent, 900, 600);
					        stage = new Stage();
					        System.out.println("HI");
					        stage.setScene(scene);
					        stage.setTitle(user.getName() + " Logged in");
					        stage.show();
					        Stage old = (Stage) loginButton.getScene().getWindow();
					        old.close();
					        System.out.println("Primary Closed");
	            		}
	            		else {
	            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        	            alert.setContentText("Invalid details.");
	        	            alert.showAndWait();
	        				userName.setText("");
	        				password.setText("");
	        				email.setText("");
	            		}
	            	}
					       
					else if(usertype.equals("Faculty")) {
						Faculty user = Faculty.validate(username, Password, usertype);
	            		if(user!=null) {
	            			System.out.println("User is faculty");
							this.user = user;
							fxmlLoader = new FXMLLoader(getClass().getResource("FacultyView.fxml"));
							FacultyController = new FacultyViewController(user);
							System.out.println("Faculty Controller constructor done.");
							FacultyController.setFaculty(user);
							FacultyController.initData(user);
							
							
				            fxmlLoader.setController(FacultyController);
				            System.out.println(FacultyController.currentUser.getName());
							parent = fxmlLoader.load();
				            
				            scene = new Scene(parent, 900, 600);
					        stage = new Stage();
					        System.out.println("HI");
					        stage.setScene(scene);
					        stage.setTitle(user.getName() + " Logged in");
					        stage.show();
					        Stage old = (Stage) loginButton.getScene().getWindow();
					        old.close();
					        System.out.println("Primary Closed");
	            		}
	            		else {
	            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        	            alert.setContentText("Invalid details.");
	        	            alert.showAndWait();
	        				userName.setText("");
	        				password.setText("");
	        				email.setText("");
	            		}
							/*FacultyController = new FacultyViewController();
							System.out.println("Faculty Controller constructor done.");
							FacultyController.launchThis((Stage)loginButton.getScene().getWindow(), (Faculty)user,this);*/
					}
					else {
							
							Admin user = Admin.validate(username, Password, usertype);
		            		if(user!=null) {
		            			System.out.println("User is admin");
								this.user = user;
								fxmlLoader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
								AdminController = new AdminViewController(user);
								System.out.println("Admin Controller constructor done.");
								AdminController.setAdmin(user);
								AdminController.initData(user);
								System.out.print(user.getEmail() + " ");
								
								
					            fxmlLoader.setController(AdminController);
					            System.out.println(AdminController.currentUser.getName());
								parent = fxmlLoader.load();
					            
					            scene = new Scene(parent, 900, 600);
						        stage = new Stage();
						        //System.out.println("HI");
						        stage.setScene(scene);
						        stage.setTitle(user.getName() + " Logged in");
						        stage.show();
						        Stage old = (Stage) loginButton.getScene().getWindow();
						        old.close();
						        System.out.println("Primary Closed");
		            		//}
		            		
		            		
							/*AdminController = new AdminViewController();
							System.out.println("Admin Controller constructor done.");
							AdminController.launchThis((Stage)loginButton.getScene().getWindow(), (Admin)user,this);*/
						}
		            		else {
		            			Alert alert = new Alert(Alert.AlertType.INFORMATION);
		        	            alert.setContentText("Invalid details.");
		        	            alert.showAndWait();
		        				userName.setText("");
		        				password.setText("");
		        				email.setText("");
		            		}
						/*Stage stage = (Stage)submitButton.getScene().getWindow();
						setRoot(FXMLLoader.load(getClass().getResource(usertype  + "View.fxml")));
						Parent root = FXMLLoader.load(getClass().getResource(usertype  + "View.fxml"));
						Scene scene = new Scene(root);
					    stage.setScene(scene);
					    stage.show();*/

					}
					/*else {
						Alert alert = new Alert(Alert.AlertType.INFORMATION);
        	            alert.setContentText("User id and Password do no match.");
        	            alert.showAndWait();
					}*/
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	       }
	            

	}
	public void setPrimaryStage(Stage primaryStage) {
		// TODO Auto-generated method stub
		this.stage = primaryStage;
		
	}
	public User getUser() {
		return this.user;
	}
	
}







