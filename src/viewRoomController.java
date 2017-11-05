
import java.awt.Button;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class viewRoomController implements Initializable
{

	@FXML private TextField roomInput;
	@FXML private DatePicker startDate;
	@FXML private DatePicker endDate;
	@FXML private TextField startTime;
	@FXML private TextField endTime;
	@FXML private Button roomSubmit;

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{


	}

}
