package hibernate;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import view.PaymentView;
import view.PersonalView;
import view.ResidentView;

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
		residents.setLayoutX(200);
		residents.setLayoutY(200);

		residents.setText("Residents");
		residents.setOnAction(event -> {
			new ResidentView().startApp();
		});

		Button payments = new Button();
		payments.setLayoutX(400);
		payments.setLayoutY(400);
		payments.setText("Payments");
		payments.setOnAction(event -> {
			new PaymentView().startApp();
		});

		Button personal = new Button();
		personal.setLayoutX(100);
		personal.setLayoutY(100);
		personal.setText("Personal");
		personal.setOnAction(event -> {
			new PersonalView().startApp();
		});

		Pane root = new Pane();
		root.getChildren().add(residents);
		root.getChildren().add(payments);
		root.getChildren().add(personal);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.hide();
		primaryStage.show();
	}

}