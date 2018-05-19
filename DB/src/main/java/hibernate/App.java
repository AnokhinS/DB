package hibernate;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
		residents.setText("Residents");
		residents.setOnAction(event -> {
			new ResidentView().startApp();
		});
		residents.setPrefSize(200, 50);
		residents.setId("dark");
		Button payments = new Button();
		payments.setText("Payments");
		payments.setOnAction(event -> {
			new PaymentView().startApp();
		});
		payments.setPrefSize(200, 50);
		Button personal = new Button();
		personal.setText("Personal");
		personal.setOnAction(event -> {
			new PersonalView().startApp();
		});
		personal.setPrefSize(200, 50);
		GridPane root = new GridPane();
		root.setAlignment(Pos.CENTER);
		VBox vbox = new VBox(50);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(residents, payments, personal);
		root.getChildren().add(vbox);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");
		primaryStage.setScene(scene);
		primaryStage.hide();
		primaryStage.show();
	}

}