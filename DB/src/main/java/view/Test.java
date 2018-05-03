package view;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import DAO.IDao;
import hibernate.Factory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Faculty;

public class Test extends Application {

	private Stage primaryStage;
	private IDao<Faculty> dao = Factory.getInstance().getFacultyDAO();

	@Override
	public void start(Stage stage) {
		primaryStage = stage;
		try {
			startApp();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		primaryStage.setOnCloseRequest(e -> {
			Platform.exit();
			System.exit(0);
		});
	}

	void startApp() throws SQLException {

		Button create = new Button();
		create.setText("Create New Customer");

		create.setOnAction(event -> {
			create();
		});

		List<Faculty> data = read();
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		Label id = new Label("id");
		Label colName = new Label("Faculty");

		id.setFont(new Font("Arial", 15));
		colName.setFont(new Font("Arial", 15));

		GridPane gridPane = new GridPane();
		gridPane.setHgap(30);
		gridPane.setVgap(10);

		gridPane.add(id, 0, 0);
		gridPane.add(colName, 1, 0);

		Separator separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);

		gridPane.add(separator, 0, 1, 7, 1);

		int i = 2;
		for (Faculty item : data) {
			Label localId = new Label(Long.toString(item.getId()));
			Label localColName = new Label(item.getName());
			Button edit = new Button("Edit");
			edit.setOnAction(event -> {
				try {
					update(item.getId());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			Button delete = new Button("Delete");
			delete.setOnAction(event -> {
				try {
					delete(item.getId());
				} catch (SQLException e) {
					e.printStackTrace();
				}
			});
			gridPane.add(localId, 0, i, 1, 1);
			gridPane.add(localColName, 1, i, 1, 1);
			gridPane.add(edit, 2, i, 1, 1);
			gridPane.add(delete, 3, i, 1, 1);
			i++;
		}

		vbox.getChildren().addAll(create, gridPane);

		StackPane root = new StackPane();
		root.getChildren().addAll(vbox);

		Scene scene = new Scene(root);

		primaryStage.setTitle("JavaFX CRUD");
		primaryStage.setScene(scene);
		primaryStage.hide();
		primaryStage.show();

	}

	public void create() {
		Stage createStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);

		TextField faculty;
		faculty = new TextField();
		faculty.setTooltip(new Tooltip("Enter Faculty Name"));
		faculty.setPromptText("First Name");
		faculty.setMaxWidth(200);

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));

		savebtn.setOnAction(event -> {
			try {
				dao.addItem(new Faculty(faculty.getText()));
				startApp();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			((Node) event.getSource()).getScene().getWindow().hide();
		});

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(faculty, savebtn);
		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		createStage.setTitle("New Customer");
		createStage.setScene(scene);
		createStage.show();
	}

	public List<Faculty> read() throws SQLException {
		List<Faculty> data = (List<Faculty>) Factory.getInstance().getFacultyDAO().getAllItems();
		return data;
	}

	public void update(long l) throws SQLException {

		Faculty item = dao.getItemById(l);
		Stage updateStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);

		TextField faculty;
		faculty = new TextField(item.getName());
		faculty.setTooltip(new Tooltip("Enter First Name"));
		faculty.setPromptText("First Name");
		faculty.setMaxWidth(200);

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));

		savebtn.setOnAction(event -> {
			item.setName(faculty.getText());
			try {
				dao.updateItem(item);
				startApp();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			((Node) (event.getSource())).getScene().getWindow().hide();
		});

		VBox vbox = new VBox(10);
		vbox.getChildren().addAll(faculty, savebtn);
		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		updateStage.setTitle("Edit Customer");
		updateStage.setScene(scene);
		updateStage.show();
	}

	public void delete(long l) throws SQLException {
		Faculty item = dao.getItemById(l);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Deleting " + item.getName());
		alert.setHeaderText("Are you Sure, You want to delete " + item.getName());
		alert.setContentText("This action can't be undone!");
		Optional result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			dao.deleteItem(item);
			startApp();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
