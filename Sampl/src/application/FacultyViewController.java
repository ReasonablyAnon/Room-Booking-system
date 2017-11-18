package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FacultyViewController {

	
	
	
	Faculty currentUser;
	
	private Parent parent;
	private Stage stage;
	private Scene scene;
	
	
	@FXML	private Label booked;
	@FXML	private Label available;
	@FXML 	private Button logoutButton;
	@FXML	private Button cancelButton;
	//Book Room
	@FXML	private TextField bookRoom;
	@FXML	private TextField bookStartTime;
	@FXML	private TextField bookEndTime;
	@FXML	private Button		bookSubmit;
	@FXML	private DatePicker bookDate;
	
	//Room Availablilty
	@FXML private TextField roomCheck;
	@FXML private DatePicker availableDate;
	@FXML private TextField availableStartTime;
	@FXML private TextField availableEndTime;
	@FXML private Button availableButton;
	
	
	@FXML 	private TitledPane checkMyRequests;
	@FXML 	private TableView<Request> myRequests;
	@FXML 	private TableColumn<Request, String> myRoomColumn;
	@FXML 	private TableColumn<Request, String> myStartTimeColumn;
	@FXML 	private TableColumn<Request, String> myEndTimeColumn;
	@FXML 	private TableColumn<Request, String> myDateColumn;
	private ObservableList<Request> showingMyRequests =
            FXCollections.observableArrayList();
	
	
	
	
	public FacultyViewController(Faculty user) {
		this.currentUser = user;
	}

	public void launchThis(Stage primaryStage, Faculty user, SampleController sampleController) {
		currentUser = user;
        stage = new Stage();
        loadFacultyView();
        stage.setScene(scene);
        stage.setTitle(user.getName() + " Logged in");
        System.out.println("Stage set " + currentUser.getName());
        //welcomeLabel.setText("Welcome" + currentUser.getName());
        stage.show();
        primaryStage.close();
		
	}
	
	public void initialize() {
		myRoomColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("room"));
		myDateColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("DATE"));
        myStartTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("startTime"));
        myEndTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("endTime"));
        //myRequests.setEditable(false);
		myRequests.setItems(showingMyRequests);
		checkMyRequests.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	        if (isNowExpanded) {
	            try {
					checkMyRequests();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		});
	}
	/**
	 * View personal bookings
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void checkMyRequests() throws ClassNotFoundException, IOException {
		ArrayList<Request> myRequests = currentUser.checkMyRequests(true);
		showingMyRequests.clear();
		for(Request request : myRequests) {
			System.out.println(request.getRoom() + request.getDATE() + request.getStartTime());
			showingMyRequests.add(request);
		}
		
	}
	/**
	 * Cancel booking
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void cancelMyRequest() throws ClassNotFoundException, IOException {
		System.out.println("Selected a row and hit submit");
		myRequests.getSelectionModel().getSelectedItem().setAccepted(false);
		ArrayList<Request> requests = currentUser.checkMyRequests(true);
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
	        System.out.println("Could not deserialize requests in cancelMyRequest()");
	        e.printStackTrace();
	    }
	    in.close();
	    ArrayList<Request> tempRequests = new ArrayList<Request>();
	    for(Request request : requests) {
	    	if(!request.equals(myRequests.getSelectionModel().getSelectedItem())) tempRequests.add(request);
	    }
	    FileOutputStream fileOut =  new FileOutputStream(path + "Requests.ser");
		ObjectOutputStream out =  new ObjectOutputStream(fileOut);
		out.writeObject(tempRequests);
		out.close();
		System.out.println("Clearing requests");
		showingMyRequests.clear();
		for(Request request: requests) {
			showingMyRequests.add(request);
		}
	}


	
	/**
	 * View availability of room
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void viewRoomAvailability() throws ClassNotFoundException {
		System.out.println(roomCheck.getText());
		String room = roomCheck.getText();
		LocalDate date = availableDate.getValue();
		System.out.println(date);
		//String day = date.getDayOfWeek().toString();
		String time = availableStartTime.getText() + "-" + availableEndTime.getText();
		System.out.println("Room checking details are : ");
		System.out.println(date + "\t" + room + "\t" + time);
		try {
			if(User.viewRoomAvailability(room, time, date)) {
				System.out.println("Is the room " + room + " available at " + time + " on the day + " + date + " : true");
				available.setText("Available");
			}
			else available.setText("Not Available");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * Book a Room if it is available at a given time.
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void bookRoom() throws ClassNotFoundException, IOException {
		
		String room = bookRoom.getText().toString();
		String startTime = bookStartTime.getText().toString();
		String endTime = bookEndTime.getText().toString();
		LocalDate date = bookDate.getValue();
		Request newRequest = null;
		System.out.println(room + startTime + endTime + date);
		
		if(User.viewRoomAvailability(room, startTime + "-" + endTime, date)) {
			newRequest = new Request(room, date, startTime, endTime, currentUser.getEmail(), true);
			currentUser.addRequest(newRequest);
			for(Request request : Request.getRequests()) {
				System.out.println(request.getUserEmail() + "\t" + request.getPurpose() +" " + request.getRoom() + "\t"+ request.getDATE());
			}
			booked.setText("Booked.");
		}
		else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Slot not available in this room");
            alert.showAndWait();
			bookRoom.setText("");
			bookStartTime.setText("");
			bookEndTime.setText("");
			booked.setText("");
		}
		
		
	}

	private void loadFacultyView() {
		try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FacultyView.fxml"));
            parent = fxmlLoader.load();
            fxmlLoader.setController(this);
            scene = new Scene(parent, 950, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	@FXML
	public void logoutClicked() throws IOException {
		Stage stage = (Stage)logoutButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.setTitle("User Login");
	    stage.show();
	}
	
	
	@FXML
	public void submitClicked() {
		 
		
	}

	public void setFaculty(Faculty user) {
		this.currentUser = user;
		
	}

	public void initData(Faculty user) {
		this.currentUser = user;
		// TODO Auto-generated method stub
		
	}

}
