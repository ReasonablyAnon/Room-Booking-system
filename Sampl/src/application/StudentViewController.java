package application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


public class StudentViewController {

	
	
	private Parent parent;
    private Scene scene;
    private Stage stage = new Stage();
    private FXMLLoader fxmlLoader;
    Student currentUser;
    private String userName, password;
    
    
    //Requests
    @FXML 	private Label welcomeLabel;
	@FXML	private TextField requestRoom;
	@FXML	private TextField requestStartTime;
	@FXML	private TextField requestEndTime;
	@FXML	private TextArea requestPurpose;
	@FXML	private TextField requestSize;
	@FXML	private Button requestSubmit;
	@FXML	private Button logoutButton;
	@FXML	private DatePicker Date;
	@FXML 	private TableView<Request> tableRequests;
	@FXML 	private TableColumn<Request, String> roomColumn;
	@FXML 	private TableColumn<Request, String> startTimeColumn;
	@FXML 	private TableColumn<Request, String> endTimeColumn;
	@FXML 	private TableColumn<Request, String> dateColumn;
	@FXML 	private TableColumn<Request, String> sizeColumn;
	@FXML 	private TableColumn<Request, String> purposeColumn;
	@FXML 	private Button confirmedRequests;
	@FXML 	private Button pendingRequests;
	private ObservableList<Request> showingTheseRequests =
            FXCollections.observableArrayList();
	
	
	
	//Course Search
	@FXML private TitledPane coursePane;
	@FXML private TextField courseInput;
	@FXML private TableView<Course> table;
	@FXML private TableColumn<Course, String> codeColumn;
	@FXML private TableColumn<Course, String> nameColumn;
	@FXML private TableColumn<Course, String> instructorColumn;
	@FXML private TableColumn<Course, String> postConditions;
	@FXML private Button courseSearch;
	@FXML private Button courseRegister;
	private ObservableList<Course> showingTheseCourses =
            FXCollections.observableArrayList();
	private SampleController sampleController;
	private Stage prevStage;
		
		
		
	//Room Availablilty
	@FXML private TextField roomCheck;
	@FXML private DatePicker availableDate;
	@FXML private TextField availableStartTime;
	@FXML private TextField availableEndTime;
	@FXML private Button availableButton;
	@FXML private Label available;
	
	
	//View TimeTable
	@FXML private TitledPane viewTimetable;
	@FXML private TableView<TimeTable> timetable;
	@FXML private TableColumn<TimeTable, String> ttDays;
	@FXML private TableColumn<TimeTable, String> ttClass;
	@FXML private TableColumn<TimeTable, String> ttTime;
	@FXML private TableColumn<TimeTable, String> ttVenue;
	@FXML private TableColumn<TimeTable, String> ttType;
	@FXML private TextField courseRegister1;
	private ObservableList<TimeTable> ttData =
            FXCollections.observableArrayList();
	public HashMap<String,ArrayList<TimeTable>> daysBased = new HashMap<String,ArrayList<TimeTable>>();
	
	
	
	public void setLoginController(SampleController sampleController) {
		System.out.println("Setting controller");
        this.sampleController = sampleController;
    }
	
	public void launchThis(Stage primaryStage, Student user, SampleController sample) throws IOException {
		System.out.println(currentUser.getName());
        this.currentUser = user;
        this.userName = user.getName(); this.password = user.getPassword();
        stage = new Stage();
        System.out.println("HI");
        fxmlLoader.setController(sample);
        System.out.println("WHAT");
        parent = fxmlLoader.load();
        System.out.println("Loading");
        scene= new Scene(parent,900,600);
        loadStudentView(primaryStage);
        
        
        stage.setScene(scene);
        stage.setTitle(user.getName() + " Logged in");
        stage.show();
        primaryStage.close();
        System.out.println("Primary Closed");
        this.setStudent(user);
        System.out.println("FXML Loader is : " + fxmlLoader.toString());
        System.out.println(user.getName() + "is the name");
        
	}
	
	public void loadStudentView(Stage primaryStage) {
		try {
			
			if(currentUser!= null) {
				System.out.println(currentUser.toString());
			}
			else System.out.println("Current user is null");
        	fxmlLoader = new FXMLLoader(getClass().getResource("StudentView.fxml"));
        	fxmlLoader.setControllerFactory((Class<?> type) -> {
                try {
                    Object controller = type.newInstance();
                    if (controller instanceof SampleController) {
                        ((SampleController) controller).setPrimaryStage(primaryStage);
                    }
                    return controller ;
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            parent = fxmlLoader.load();
            //fxmlLoader.setController(this);
            scene = new Scene(parent, 900, 600);
            System.out.println("Scene loaded");
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public StudentViewController(Student user) throws IOException {
		
		System.out.println("Constructor called.");
		if(currentUser!=null) System.out.println(currentUser.getName() + " at the constructor");
		else System.out.println("No Current user at constructor");
		this.currentUser = user;
		if(currentUser!=null) System.out.println(currentUser.getName() + " at the constructor");
		else System.out.println("No Current user at constructor");
		/*currentUser = user;
		this.userName = user.getName(); this.password = user.getPassword();*/
	}

	public void initData(Student user) {
		this.currentUser = user;
	}
	public void initialize() throws IOException {
		//System.out.println("Initializing now " + currentUser.getName());
		//currentUser = user;
		codeColumn.setCellValueFactory(
                new PropertyValueFactory<Course, String>("code"));
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<Course, String>("name"));
        instructorColumn.setCellValueFactory(
                new PropertyValueFactory<Course, String>("instructor"));
        postConditions.setCellValueFactory(
                new PropertyValueFactory<Course, String>("postConditions"));
        
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
        
        
        
        available.setText("");
        table.setEditable(false);
        table.setItems(showingTheseCourses);
        /*requestPurpose.setEditable(false);
        requestSize.setEditable(false);*/
        tableRequests.setEditable(false);
        tableRequests.setItems(showingTheseRequests);
        //prevStage.close();
        //System.out.println("FXML is : "+ fxmlLoader.toString());
        //this.currentUser = (Student)sampleController.getUser();
        
		/*requestStartTime.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	requestStartTime.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});

		requestEndTime.textProperty().addListener(new ChangeListener<String>() {
		    public void changed(ObservableValue<? extends String> observable, String oldValue, 
		        String newValue) {
		        if (!newValue.matches("\\d*")) {
		        	requestEndTime.setText(newValue.replaceAll("[^\\d]", ""));
		        }
		    }
		});*/

		/*coursePane.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	        if (isNowExpanded) {
	            courseInput.setPromptText("Enter hint to search");
	            //try {
	            	if(showingTheseCourses!= null)
	            		//showingTheseCourses = (ObservableList<Course>) currentUser.showCourses("Programming");
	            	System.out.println(showingTheseCourses.toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
	    });*/
        
        ttDays.setCellValueFactory(
                new PropertyValueFactory<TimeTable, String>("days"));
        ttClass.setCellValueFactory(
                new PropertyValueFactory<TimeTable, String>("subject"));
        ttTime.setCellValueFactory(
                new PropertyValueFactory<TimeTable, String>("time"));
        ttVenue.setCellValueFactory(
                new PropertyValueFactory<TimeTable, String>("location"));
        ttType.setCellValueFactory(
                new PropertyValueFactory<TimeTable, String>("type"));
        timetable.setEditable(false);
        
        timetable.setItems(ttData);
        
        daysBased.put("Monday",new ArrayList<TimeTable>());
        daysBased.put("Tuesday",new ArrayList<TimeTable>());
        daysBased.put("Wednesday",new ArrayList<TimeTable>());
        daysBased.put("Thursday",new ArrayList<TimeTable>());
        daysBased.put("Friday",new ArrayList<TimeTable>());
        viewTimetable.expandedProperty().addListener((obs, wasExpanded, isNowExpanded) -> {
	        if (isNowExpanded) {
	        	ttData.clear();
	            ArrayList<TimeTable> studentTimetable = currentUser.getTimeTable();
	            ArrayList<TimeTable> mon = new ArrayList<TimeTable>();
	            ArrayList<TimeTable> tues = new ArrayList<TimeTable>();
	            ArrayList<TimeTable> wed = new ArrayList<TimeTable>();
	            ArrayList<TimeTable> thurs = new ArrayList<TimeTable>();
	            ArrayList<TimeTable> fri = new ArrayList<TimeTable>();
	            ArrayList<TimeTable> res = currentUser.showTT();
	            for ( TimeTable z1 : res )
	            {
	            	if ( z1.getDays().equals("Monday") )
	            	{
	            		mon.add(z1);
	            		if ( z1.getSubject().equals("Number Theory") )
	            		{
	            	        mon.add(new TimeTable("Monday","Number Theory","2:00-3:00","C23","Tutorial"));
	            	        mon.add(new TimeTable("Monday","Number Theory","1:30-3:00","LR-1","Tutorial"));
	            		}
	            	}
	            }
	        	Collections.sort(mon, new Comparator<TimeTable>() 
	        	{
	        	    public int compare( TimeTable one, TimeTable two ) 
	        	    {
	        			String[] timeslot1 = one.getTime().split("-");
	        			String[] timeslot2 = two.getTime().split("-");
	        			int initialTT = convertTimeToInt(timeslot1[0]);
	        			int initialCS = convertTimeToInt(timeslot2[0]);
	        	        return initialTT-initialCS;
	        	    }
	        	}); 
	            for ( TimeTable z1 : res )
	            {
	            	if ( z1.getDays().equals("Tuesday") )
	            	{
	            		tues.add(z1);
	            		if ( z1.getSubject().equals("Signal & Systems") )
	            		{
	               		 	mon.add(new TimeTable("Monday","Signal & Systems","2:00-3:00","LR-3","Tutorial"));
	            		}
	            		if( z1.getSubject().equals("Math-III") )
	            		{
	            	        mon.add(new TimeTable("Monday","Math-III","8:30-10:00","LR-1","Tutorial"));
	            	        mon.add(new TimeTable("Monday","Math-III","1:30-3:00","LR-1","Tutorial"));
	            		}
	            	}
	            }
	    	    	Collections.sort(mon, new Comparator<TimeTable>() 
	    	    	{
	    	    	    public int compare( TimeTable one, TimeTable two ) 
	    	    	    {
	    	    			String[] timeslot1 = one.getTime().split("-");
	    	    			String[] timeslot2 = two.getTime().split("-");
	    	    			int initialTT = convertTimeToInt(timeslot1[0]);
	    	    			int initialCS = convertTimeToInt(timeslot2[0]);
	    	    	        return initialTT-initialCS;
	    	    	    }
	    	    	});
	        	Collections.sort(tues, new Comparator<TimeTable>() 
	        	{
	        	    public int compare( TimeTable one, TimeTable two ) 
	        	    {
	        			String[] timeslot1 = one.getTime().split("-");
	        			String[] timeslot2 = two.getTime().split("-");
	        			int initialTT = convertTimeToInt(timeslot1[0]);
	        			int initialCS = convertTimeToInt(timeslot2[0]);
	        	        return initialTT-initialCS;
	        	    }
	        	}); 
	            for ( TimeTable z1 : res )
	            {
	            	if ( z1.getDays().equals("Wednesday") )
	            	{
	            		wed.add(z1);
	            		if( z1.getSubject().equals("Computer Organization") )
	            		{
	            	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","C22","Tutorial"));
	            	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","C23","Tutorial"));
	            	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","LR-2","Tutorial"));
	            	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","LR-3","Tutorial"));
	            		}
	            		else if ( z1.getSubject().equals("Advanced Programming") )
	            		{
	            	        wed.add(new TimeTable("Wednesday","Advanced Programming","4:00-5:30","S-01","Tutorial"));
	            		}
	            	}
	            }  
	        	Collections.sort(wed, new Comparator<TimeTable>() 
	        	{
	        	    public int compare( TimeTable one, TimeTable two ) 
	        	    {
	        			String[] timeslot1 = one.getTime().split("-");
	        			String[] timeslot2 = two.getTime().split("-");
	        			int initialTT = convertTimeToInt(timeslot1[0]);
	        			int initialCS = convertTimeToInt(timeslot2[0]);
	        	        return initialTT-initialCS;
	        	    }
	        	}); 
	            for ( TimeTable z1 : res )
	            {
	            	if ( z1.getDays().equals("Thursday") )
	            	{
	            		thurs.add(z1);
	            		if( z1.getSubject().equals("Advanced Programming") )
	            		{
	            	        thurs.add(new TimeTable("Thursday","Advanced Programming","12:00-1:00","L21","Lab"));
	            	        thurs.add(new TimeTable("Thursday","Advanced Programming","12:00-1:00","L22","Lab"));
	            	        thurs.add(new TimeTable("Thursday","Advanced Programming","12:00-1:00","L23","Lab"));
	            		}
	            	}
	            }
	        	Collections.sort(thurs, new Comparator<TimeTable>() 
	        	{
	        	    public int compare( TimeTable one, TimeTable two ) 
	        	    {
	        			String[] timeslot1 = one.getTime().split("-");
	        			String[] timeslot2 = two.getTime().split("-");
	        			int initialTT = convertTimeToInt(timeslot1[0]);
	        			int initialCS = convertTimeToInt(timeslot2[0]);
	        	        return initialTT-initialCS;
	        	    }
	        	}); 
	            for ( TimeTable z1 : res )
	            {
	            	if ( z1.getDays().equals("Friday") )
	            	{
	            		fri.add(z1);
	            		if( z1.getSubject().equals("Number Theory") )
	            		{
	            	        fri.add(new TimeTable("Friday","Number Theory","10:30-11:30","LR-1","Tutorial"));
	            		}
	            		else if ( z1.getSubject().equals("Advanced Programming") )
	            		{
	            	        fri.add(new TimeTable("Friday","Advanced Programming","4:00-5:30","LR-1","Tutorial"));
	            	        fri.add(new TimeTable("Friday","Advanced Programming","4:00-5:30","LR-2","Tutorial"));
	            	        fri.add(new TimeTable("Friday","Advanced Programming","4:00-5:30","LR-3","Tutorial"));
	            		}
	            	}
	            }
	        	Collections.sort(fri, new Comparator<TimeTable>() 
	        	{
	        	    public int compare( TimeTable one, TimeTable two ) 
	        	    {
	        			String[] timeslot1 = one.getTime().split("-");
	        			String[] timeslot2 = two.getTime().split("-");
	        			int initialTT = convertTimeToInt(timeslot1[0]);
	        			int initialCS = convertTimeToInt(timeslot2[0]);
	        	        return initialTT-initialCS;
	        	    }
	        	}); 
	        	ttData.addAll(mon);ttData.addAll(tues);ttData.addAll(wed);ttData.addAll(thurs);ttData.addAll(fri);
				//for(TimeTable timetable: studentTimetable) ttData.add(timetable);
				
	        }
		});
        requestPurpose.setEditable(true);
		requestSize.setEditable(true);
	}
	
	@FXML
	public void submitRequest() throws ClassNotFoundException, IOException {
		String room = requestRoom.getText().toString();
		String startTime = requestStartTime.getText().toString();
		String endTime = requestEndTime.getText().toString();
		String purpose = requestPurpose.getText().toString();
		
		String size = requestSize.getText().toString();
		LocalDate date = Date.getValue();
		if(User.viewRoomAvailability(room, startTime + "-" + endTime, date)) {
			requestPurpose.setEditable(true);
			requestSize.setEditable(true);
		}
		Request newRequest = new Request(purpose, room, size, date, startTime, endTime, currentUser.getEmail(), false);
		
		currentUser.addRequest(newRequest);
		for(Request request : Request.getRequests()) {
			System.out.println(request.getPurpose() +" " + request.getRoom() + "\t"+ request.getDATE());
		}
		
		
	}
	
	
	@FXML
	public void confirmedRequests() throws ClassNotFoundException, IOException {
		showingTheseRequests.clear();
		ArrayList<Request> requests = currentUser.checkMyRequests(true);
		for(Request request: requests) {
			showingTheseRequests.add(request);
		}
		
	}
	
	@FXML	
	public void pendingRequests() throws ClassNotFoundException, IOException {
		showingTheseRequests.clear();
		ArrayList<Request> requests = currentUser.checkMyRequests(false);
		for(Request request: requests) {
			showingTheseRequests.add(request);
		}
		
	}
	
	@FXML
	public void viewRoomAvailability() throws ClassNotFoundException {
		System.out.println(roomCheck.getText());
		String room = roomCheck.getText();
		LocalDate date = availableDate.getValue();
		String day = date.getDayOfWeek().toString();
		String time = availableStartTime.getText() + "-" + availableEndTime.getText();
		System.out.println("Room checking details are : ");
		System.out.println(day + "\t" + room + "\t" + time);
		try {
			if(User.viewRoomAvailability(room, time, date)) {
				System.out.println("Is the room " + room + " available at " + time + " on the day + " + date + " : true");
				available.setText("Available");
			}
			else available.setText("Not Available");
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
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
	 * Courses to be shown in TableView after user has input some keywords , while ensuring clashes
	 * @throws IOException
	 */
	@FXML
	public void showCourses() throws IOException
    {
    	String searchString = courseInput.getText();
    	//currentUser.showCourses(searchString);
    	System.out.println("Showing courses now.");
        ArrayList<Course> result =  currentUser.showCourses(searchString);
        showingTheseCourses.clear();
        for(Course course: result) 
        {
        	System.out.println(course.getName()+ " FINAL SEARCH NAMES ");
        	showingTheseCourses.add(course);
        }
        table.setItems(showingTheseCourses);
    }
	
	/**
	 * * Courses to be shown in TableView after user has input registered respective course , while ensuring clashes
	 * @throws IOException
	 */
	@FXML
	public void addCourse() throws IOException
	{
		Queue<String> daysSort = new LinkedList<String>();
		daysSort.add("Monday");daysSort.add("Tuesday");daysSort.add("Wednesday");daysSort.add("Thursday");daysSort.add("Friday");
		String course = courseRegister1.getText();
		currentUser.AddCourse(course,false);
        ArrayList<TimeTable> res = currentUser.showTT();
        for(TimeTable tt : res)
        {
        	System.out.println(tt.getDays()+"  "+tt.getSubject()+"  "+tt.getTime()+" SORTED MAYBE ");
        }
        ttData.clear();
        //ttData.addAll(res);     
        
        ArrayList<TimeTable> mon = new ArrayList<TimeTable>();
        ArrayList<TimeTable> tues = new ArrayList<TimeTable>();
        ArrayList<TimeTable> wed = new ArrayList<TimeTable>();
        ArrayList<TimeTable> thurs = new ArrayList<TimeTable>();
        ArrayList<TimeTable> fri = new ArrayList<TimeTable>();
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Monday") )
        	{
        		mon.add(z1);
        		if ( z1.getSubject().equals("Number Theory") )
        		{
        	        mon.add(new TimeTable("Monday","Number Theory","2:00-3:00","C23","Tutorial"));
        	        mon.add(new TimeTable("Monday","Number Theory","1:30-3:00","LR-1","Tutorial"));
        		}
        	}
        }
    	Collections.sort(mon, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Tuesday") )
        	{
        		tues.add(z1);
        		if ( z1.getSubject().equals("Signal & Systems") )
        		{
           		 	mon.add(new TimeTable("Monday","Signal & Systems","2:00-3:00","LR-3","Tutorial"));
        		}
        		if( z1.getSubject().equals("Math-III") )
        		{
        	        mon.add(new TimeTable("Monday","Math-III","8:30-10:00","LR-1","Tutorial"));
        	        mon.add(new TimeTable("Monday","Math-III","1:30-3:00","LR-1","Tutorial"));
        		}
        	}
        }
	    	Collections.sort(mon, new Comparator<TimeTable>() 
	    	{
	    	    public int compare( TimeTable one, TimeTable two ) 
	    	    {
	    			String[] timeslot1 = one.getTime().split("-");
	    			String[] timeslot2 = two.getTime().split("-");
	    			int initialTT = convertTimeToInt(timeslot1[0]);
	    			int initialCS = convertTimeToInt(timeslot2[0]);
	    	        return initialTT-initialCS;
	    	    }
	    	});
    	Collections.sort(tues, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Wednesday") )
        	{
        		wed.add(z1);
        		if( z1.getSubject().equals("Computer Organization") )
        		{
        	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","C22","Tutorial"));
        	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","C23","Tutorial"));
        	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","LR-2","Tutorial"));
        	        wed.add(new TimeTable("Wednesday","Computer Organization","1:30-2:30","LR-3","Tutorial"));
        		}
        		else if ( z1.getSubject().equals("Advanced Programming") )
        		{
        	        wed.add(new TimeTable("Wednesday","Advanced Programming","4:00-5:30","S-01","Tutorial"));
        		}
        	}
        }  
    	Collections.sort(wed, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Thursday") )
        	{
        		thurs.add(z1);
        		if( z1.getSubject().equals("Advanced Programming") )
        		{
        	        thurs.add(new TimeTable("Thursday","Advanced Programming","12:00-1:00","L21","Lab"));
        	        thurs.add(new TimeTable("Thursday","Advanced Programming","12:00-1:00","L22","Lab"));
        	        thurs.add(new TimeTable("Thursday","Advanced Programming","12:00-1:00","L23","Lab"));
        		}
        	}
        }
    	Collections.sort(thurs, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Friday") )
        	{
        		fri.add(z1);
        		if( z1.getSubject().equals("Number Theory") )
        		{
        	        fri.add(new TimeTable("Friday","Number Theory","10:30-11:30","LR-1","Tutorial"));
        		}
        		else if ( z1.getSubject().equals("Advanced Programming") )
        		{
        	        fri.add(new TimeTable("Friday","Advanced Programming","4:00-5:30","LR-1","Tutorial"));
        	        fri.add(new TimeTable("Friday","Advanced Programming","4:00-5:30","LR-2","Tutorial"));
        	        fri.add(new TimeTable("Friday","Advanced Programming","4:00-5:30","LR-3","Tutorial"));
        		}
        	}
        }
    	Collections.sort(fri, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
    	ttData.addAll(mon);ttData.addAll(tues);ttData.addAll(wed);ttData.addAll(thurs);ttData.addAll(fri);
        timetable.setItems(ttData);
	}

	/*@FXML
	public void addCourse() throws IOException
	{
		Queue<String> daysSort = new LinkedList<String>();
		daysSort.add("Monday");daysSort.add("Tuesday");daysSort.add("Wednesday");daysSort.add("Thursday");daysSort.add("Friday");
		String course = courseRegister1.getText();
		currentUser.AddCourse(course,false);
        ArrayList<TimeTable> res = currentUser.showTT();
        for(TimeTable tt : res)
        {
        	System.out.println(tt.getDays()+"  "+tt.getSubject()+"  "+tt.getTime()+" SORTED MAYBE ");
        }
        ttData.clear();
        System.out.println("Adding course : " + course);
        //ttData.addAll(res);     
        
        ArrayList<TimeTable> mon = new ArrayList<TimeTable>();
        ArrayList<TimeTable> tues = new ArrayList<TimeTable>();
        ArrayList<TimeTable> wed = new ArrayList<TimeTable>();
        ArrayList<TimeTable> thurs = new ArrayList<TimeTable>();
        ArrayList<TimeTable> fri = new ArrayList<TimeTable>();
        
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Monday") )
        	{
        		mon.add(z1);
        	}
        }
    	Collections.sort(mon, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Tuesday") )
        	{
        		tues.add(z1);
        	}
        }
    	Collections.sort(tues, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Wednesday") )
        	{
        		wed.add(z1);
        	}
        }
    	Collections.sort(wed, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Thursday") )
        	{
        		thurs.add(z1);
        	}
        }
    	Collections.sort(thurs, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
        for ( TimeTable z1 : res )
        {
        	if ( z1.getDays().equals("Friday") )
        	{
        		fri.add(z1);
        	}
        }
    	Collections.sort(fri, new Comparator<TimeTable>() 
    	{
    	    public int compare( TimeTable one, TimeTable two ) 
    	    {
    			String[] timeslot1 = one.getTime().split("-");
    			String[] timeslot2 = two.getTime().split("-");
    			int initialTT = convertTimeToInt(timeslot1[0]);
    			int initialCS = convertTimeToInt(timeslot2[0]);
    	        return initialTT-initialCS;
    	    }
    	}); 
    	ttData.addAll(mon);ttData.addAll(tues);ttData.addAll(wed);ttData.addAll(thurs);ttData.addAll(fri);
        timetable.setItems(ttData);
       for ( String t2 : daysBased.keySet() )
       {
    	   ArrayList<TimeTable> mapwala = daysBased.get(t2);
	       Set<TimeTable> hs = new HashSet<>();
	       hs.addAll(mapwala);
	       mapwala.clear();
	       mapwala.addAll(hs);
       }
       
        Collection<TimeTable> resultant = new ArrayList<TimeTable>();
        while ( !daysSort.isEmpty() )
        {
        	String day = daysSort.remove();
        	System.out.println(day);
	        for ( String key : daysBased.keySet() )
	        {
	     	   ArrayList<TimeTable> dates = daysBased.get(key);
		       Set<TimeTable> hs = new HashSet<>();
		       hs.addAll(dates);
		       dates.clear();
		       dates.addAll(hs);
		       if ( day.equals(key) )
	        	{
		        	Collections.sort(dates, new Comparator<TimeTable>() 
		        	{
		        	    public int compare( TimeTable one, TimeTable two ) 
		        	    {
		        			String[] timeslot1 = one.getTime().split("-");
		        			String[] timeslot2 = two.getTime().split("-");
		        			int initialTT = convertTimeToInt(timeslot1[0]);
		        			int initialCS = convertTimeToInt(timeslot2[0]);
		        	        return initialTT-initialCS;
		        	    }
		        	}); 
		        	resultant.addAll(dates);
		        	break;
	        	}
	        }
	        ttData.addAll(resultant);
        }
        for ( TimeTable z : ttData )
        {
        	System.out.println(z.getDays()+"  "+z.getSubject()+"  "+z.getTime()+" SORTED MAYBE ");
        }
        
	}*/
	
	public static int convertTimeToInt(String time) 
	{
    	int twentyFourHourTime = 0;
    	int earliestPossibleClass = 7;
    	if( Integer.parseInt(time.substring(0, time.indexOf(":")).toString()) < earliestPossibleClass ) 
    	{
    		twentyFourHourTime = Integer.parseInt(time.substring(0, time.indexOf(":"))) + 12;
    	}
    	else twentyFourHourTime = Integer.parseInt(time.substring(0, time.indexOf(":")));
    	twentyFourHourTime *=100;
    	twentyFourHourTime += Integer.parseInt
    			(
    					(
    							time.substring(time.indexOf(":")+1)
    					)
    			);
    	return twentyFourHourTime;
	}
	
	public void setStudent(Student student) {
		System.out.println("SET");
		this.currentUser = student;
	}

	public void setPrevStage(Stage stage2) {
		
		this.prevStage= stage2;
	}

}
