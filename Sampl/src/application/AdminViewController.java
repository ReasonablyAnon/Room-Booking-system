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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class AdminViewController {

	Admin currentUser;
	
	private Parent parent;
	private Stage stage;
	private Scene scene;
	
	@FXML	private Button logoutButton;
	@FXML	private Label available;
	@FXML	private Label booked;
	
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
	
	
	//Student Requests
	@FXML	private TitledPane studentRequests;
	@FXML 	private TableView<Request> tableRequests;
	@FXML 	private TableColumn<Request, String> roomColumn;
	@FXML 	private TableColumn<Request, String> startTimeColumn;
	@FXML 	private TableColumn<Request, String> endTimeColumn;
	@FXML 	private TableColumn<Request, String> dateColumn;
	@FXML 	private TableColumn<Request, String> sizeColumn;
	@FXML 	private TableColumn<Request, String> purposeColumn;
	@FXML 	private TableColumn<Request, String> emailColumn;
	@FXML 	private TableColumn<Request, String> nameColumn;
	@FXML 	private TableColumn<Request, String> acceptedColumn;
	@FXML private Button confirmRequestButton;
	
	private ObservableList<Request> showingTheseRequests =
            FXCollections.observableArrayList();
	private ObservableList<Request> showingAcceptedStudentRequests =
            FXCollections.observableArrayList();
	
	@FXML 	private TitledPane checkMyRequests;
	@FXML 	private TableView<Request> myRequests;
	@FXML 	private TableColumn<Request, String> myRoomColumn;
	@FXML 	private TableColumn<Request, String> myStartTimeColumn;
	@FXML 	private TableColumn<Request, String> myEndTimeColumn;
	@FXML 	private TableColumn<Request, String> myDateColumn;
	@FXML	private Button cancelRequest;
	private ObservableList<Request> showingMyRequests =
            FXCollections.observableArrayList();
	
	//@FXML	private TitledPane acceptedStudentRequests;
	
	
	
	
	
	public AdminViewController(Admin user) {
		this.currentUser = user;
		// TODO Auto-generated constructor stub
	}


	public void initialize() throws IOException{
		roomColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("room"));
		dateColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("DATE"));
        startTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("startTime"));
        endTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("endTime"));
        sizeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("size"));
        purposeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("purpose"));
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("userEmail"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("name"));
		/*acceptedColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("accepted"));*/
		tableRequests.setEditable(false);
		tableRequests.setItems(showingTheseRequests);
		studentRequests.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	        if (isNowExpanded) {
	            try {
					pendingStudentRequests();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
		});
		tableRequests.getSelectionModel().setSelectionMode(
			    SelectionMode.SINGLE
			);
		/*tableRequests.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	if(confirmRequestButton.onMouseClickedProperty())
		    	try {
	            	System.out.println("Selected a row and hit submit");
					tableRequests.getSelectionModel().getSelectedItem().setAccepted(true);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
		    }
		});*/
		tableRequests.setOnMousePressed((MouseEvent event) -> {
	        if(event.getButton().equals(MouseButton.PRIMARY)){
	            System.out.println(tableRequests.getSelectionModel().getSelectedItem());
	        }
	    });
		
		myRoomColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("room"));
		myDateColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("DATE"));
        myStartTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("startTime"));
        myEndTimeColumn.setCellValueFactory(
                new PropertyValueFactory<Request, String>("endTime"));
        myRequests.setEditable(false);
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
	public void launchThis(Stage primaryStage, Admin user, SampleController sampleController) {
		currentUser = user;
        stage = new Stage();
        loadAdminView();
        stage.setScene(scene);
        stage.setTitle(user.getName() + " Logged in");
        stage.show();
        primaryStage.close();
		
	}


	private void loadAdminView() {
		try {
        	FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminView.fxml"));
            parent = fxmlLoader.load();
            fxmlLoader.setController(this);
            scene = new Scene(parent, 950, 600);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
	}
	
	/**
	 * Log out of current Session
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void logoutClicked() throws IOException, ClassNotFoundException {
		currentUser.logout();
		currentUser = null;
		Stage stage = (Stage)logoutButton.getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("Sample.fxml"));
		Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.setTitle("User Login");
	    stage.show();
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
		}
		else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Slot not available in this room");
            alert.showAndWait();
			bookRoom.setText("");
			bookStartTime.setText("");
			bookEndTime.setText("");
		}
		
		
	}
	
	
	/**
	 * Show requests that are pending accepting
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void pendingStudentRequests() throws ClassNotFoundException, IOException {
		showingTheseRequests.clear();
		ArrayList<Request> requests = currentUser.getUnAcceptedRequests();
		for(Request request: requests) {
			showingTheseRequests.add(request);
		}
		
	}
	
	/**
	 * Show requests that are accepted
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void acceptedStudentRequests() throws ClassNotFoundException, IOException {
		showingAcceptedStudentRequests.clear();
		ArrayList<Request> requests = currentUser.getAcceptedRequests();
		for(Request request: requests) {
			showingAcceptedStudentRequests.add(request);
		}
		
	}
	
	/**
	 * Confirm selected request
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	@FXML
	public void confirmingRequest() throws ClassNotFoundException, IOException {
		//Request confirming = null;
		/*tableRequests.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	try {
	            	System.out.println("Selected a row and hit submit");
					tableRequests.getSelectionModel().getSelectedItem().setAccepted(true);
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
		    }
		});*/
		
		
		System.out.println("Selected a row and hit submit");
		tableRequests.getSelectionModel().getSelectedItem().setAccepted(true);
		
		
		ArrayList<Request> requests = currentUser.getUnAcceptedRequests();
		System.out.println("Clearing requests");
		showingTheseRequests.clear();
		for(Request request: requests) {
			showingTheseRequests.add(request);
		}
		
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
	        System.out.println("Could not deserialize requests in cancelMyRequest");
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

	public void setAdmin(Admin user) {
		this.currentUser = user;
		
	}


	public void initData(Admin user) {
		this.currentUser = user;
		
	}

}
