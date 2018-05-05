package hibernate;

import java.sql.SQLException;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.Residents;

/**
 * hiberante
 *
 */
public class App extends javafx.application.Application {
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Student House Database");
		primaryStage.setMinWidth(600);
		primaryStage.setMinHeight(600);
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
		Button residents = new Button();
		residents.setText("Residents");
		residents.setOnAction(event -> {
			try {
				new Residents().startApp();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		StackPane root = new StackPane();
		root.getChildren().addAll(residents);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.hide();
		primaryStage.show();
	}

}