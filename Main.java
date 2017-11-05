

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.application.Application;

public class Main extends Application
{
	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException
	{
		AnchorPane root = FXMLLoader.load(getClass().getResource("StudentView2.fxml"));
		//BorderPane root = getClass().getClassLoader().getResource("StudentView2.fxml")
		Scene scene = new Scene(root,1000,1000);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
