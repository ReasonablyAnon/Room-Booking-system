

import javafx.scene.layout.AnchorPane;

import java.awt.Button;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class StudentView2Controller implements Initializable
{
    private final ObservableList<courses> data =
            FXCollections.observableArrayList
            (
            );

    //Search Room Components
    @FXML private AnchorPane root;
    @FXML private Label label;
    @FXML private Accordion accordion;
    @FXML private TitledPane title;
	@FXML private AnchorPane searchanchor;
	@FXML private TextField searchfield;
	@FXML private Button submit;
	@FXML private TextField courseInput;
	@FXML private TableView<courses> table;
	@FXML private Button register;
	@FXML private TableColumn<courses, String> CodeCol;
	@FXML private TableColumn<courses, String> NameCol;
	@FXML private TableColumn<courses, String> InstructorCol;
	@FXML private TableColumn<courses, String> PC1;

	//View Room components
	@FXML private TextField roomInput;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private TextField startTime;
	@FXML private TextField endTime;
	@FXML private Button roomSubmit;

	public StudentView2Controller()
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

        CodeCol.setCellValueFactory(
                new PropertyValueFactory<courses, String>("Code"));
        NameCol.setCellValueFactory(
                new PropertyValueFactory<courses, String>("Name"));
        InstructorCol.setCellValueFactory(
                new PropertyValueFactory<courses, String>("Instructor"));
        PC1.setCellValueFactory(
                new PropertyValueFactory<courses, String>("PC1"));
        table.setEditable(true);
        table.setItems(data);
    }

    @FXML
    public void showCourses()
    {
        try {
			readCSV();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



    private void readCSV() throws IOException, FileNotFoundException
    {
    	String CsvFile = "/home/ashwat/Downloads/TestingPc.csv";
        String FieldDelimiter = ",";
        BufferedReader br;
        br = new BufferedReader(new FileReader(CsvFile));
        String line;
        while (( line = br.readLine()) != null )
        {
                //String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                String[] fields = line.split(FieldDelimiter, -1);
                if(fields.length <6)
                	continue;
                String aline = "";
                for(int i=0; i< fields.length; i++)
                {
                	aline += " " + fields[i];
                }
                String postCondition = "";
                postCondition = fields[3] + "\n"  + fields[4] + "\n" + fields[5] + "\n"  + fields[6] + "\n"  + fields[7];
                courses record = new courses( fields[0].replaceAll("\"", "").replace("/", "\n"), fields[1], fields[2].replaceAll("\"", ""),
                		postCondition);
                data.add(record);
        }
    }
}
