
import acsse.gui.HomePage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		HomePage root = new HomePage();		
        ScrollPane scrollPane = new ScrollPane(root);
        scrollPane.setFitToWidth(true); // to make the width of the scroll pane same as the container
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS); // to always show vertical scroll bar

        scrollPane.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0), transparent;");
        // add the scroll pane to the container
       
		
		Scene scene = new Scene(scrollPane,800,600,Paint.valueOf("#c9cdd4"));
		
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

