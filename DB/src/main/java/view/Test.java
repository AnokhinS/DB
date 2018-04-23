package view;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import hibernate.Factory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Faculty;

public class Test extends Application {

	private final Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
	// private final SessionFactory sf = cfg.buildSessionFactory();
	private final StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
			.applySettings(cfg.getProperties()).build();
	private final SessionFactory sf = cfg.configure().buildSessionFactory(serviceRegistry);
	private Stage primaryStage;

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

		// create.setOnAction((ActionEvent event) -> {
		// create();
		// });

		List<Faculty> data = read();
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(10));
		Label snHeader = new Label("id");
		Label firstnameHeader = new Label("Faculty");

		snHeader.setFont(new Font("Arial", 15));
		firstnameHeader.setFont(new Font("Arial", 15));

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		gridPane.add(snHeader, 0, 0);
		gridPane.add(firstnameHeader, 1, 0);

		Separator separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);

		gridPane.add(separator, 0, 1, 7, 1);

		int i = 2;
		for (Faculty item : data) {

			Label sn = new Label(Long.toString(item.getId()));
			Label firstname = new Label(item.getName());
			// Button edit = new Button("Edit");
			// edit.setOnAction((ActionEvent event) -> {
			// update(item.getId());
			// });
			// Button delete = new Button("Delete");
			// delete.setOnAction((ActionEvent event) -> {
			// delete(item.getId());
			// });
			gridPane.add(sn, 0, i, 1, 1);
			gridPane.add(firstname, 1, i, 1, 1);
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

	// public void create() {
	// Stage createStage = new Stage();
	// StackPane root = new StackPane();
	// Scene scene = new Scene(root);
	//
	// TextField FirstName, LastName, Email, Phone;
	// FirstName = new TextField();
	// FirstName.setTooltip(new Tooltip("Enter First Name"));
	// FirstName.setPromptText("First Name");
	// FirstName.setMaxWidth(200);
	//
	// LastName = new TextField();
	// LastName.setTooltip(new Tooltip("Enter Last Name"));
	// LastName.setPromptText("Last Name");
	// LastName.setMaxWidth(200);
	//
	// Email = new TextField();
	// Email.setTooltip(new Tooltip("Enter Email"));
	// Email.setPromptText("Email");
	// Email.setMaxWidth(200);
	//
	// Phone = new TextField();
	// Phone.setTooltip(new Tooltip("Enter Phone"));
	// Phone.setPromptText("Phone");
	// Phone.setMaxWidth(200);
	//
	// Button savebtn = new Button("Save");
	// savebtn.setTooltip(new Tooltip("Save"));
	//
	// savebtn.setOnAction(event -> {
	// Session session = sf.openSession();
	// session.beginTransaction();
	// Customer customer = new Customer(FirstName.getText(), LastName.getText(),
	// Email.getText(), Phone.getText());
	// session.save(customer);
	//
	// session.getTransaction().commit();
	// session.close();
	// startApp();
	//
	// ((Node) (event.getSource())).getScene().getWindow().hide();
	// });
	//
	// VBox vbox = new VBox(10);
	// vbox.getChildren().addAll(FirstName, LastName, Email, Phone, savebtn);
	// vbox.setPadding(new Insets(10));
	// root.getChildren().add(vbox);
	//
	// createStage.setTitle("New Customer");
	// createStage.setScene(scene);
	// createStage.show();
	// }

	public List<Faculty> read() throws SQLException {
		List<Faculty> data = (List<Faculty>) Factory.getInstance().getFacultyDAO().getAllItems();
		return data;
	}
	//
	// public void update(int id) {
	// Session session = sf.openSession();
	// Customer customer = (Customer) session.get(Customer.class, id);
	//
	// Stage updateStage = new Stage();
	// StackPane root = new StackPane();
	// Scene scene = new Scene(root);
	//
	// TextField FirstName, LastName, Email, Phone;
	// FirstName = new TextField(customer.getFirstName());
	// FirstName.setTooltip(new Tooltip("Enter First Name"));
	// FirstName.setPromptText("First Name");
	// FirstName.setMaxWidth(200);
	//
	// LastName = new TextField(customer.getLastName());
	// LastName.setTooltip(new Tooltip("Enter Last Name"));
	// LastName.setPromptText("Last Name");
	// LastName.setMaxWidth(200);
	//
	// Email = new TextField(customer.getEmail());
	// Email.setTooltip(new Tooltip("Enter Email"));
	// Email.setPromptText("Email");
	// Email.setMaxWidth(200);
	//
	// Phone = new TextField(customer.getPhone());
	// Phone.setTooltip(new Tooltip("Enter Mobile Number"));
	// Phone.setPromptText("Phone");
	// Phone.setMaxWidth(200);
	//
	// Button savebtn = new Button("Save");
	// savebtn.setTooltip(new Tooltip("Save"));
	//
	// savebtn.setOnAction(event -> {
	//
	// session.beginTransaction();
	//
	// customer.setFirstName(FirstName.getText());
	// customer.setLastName(LastName.getText());
	// customer.setEmail(Email.getText());
	// customer.setPhone(Phone.getText());
	// session.update(customer);
	//
	// session.getTransaction().commit();
	// session.close();
	//
	// startApp();
	//
	// ((Node) (event.getSource())).getScene().getWindow().hide();
	// });
	//
	// VBox vbox = new VBox(10);
	// vbox.getChildren().addAll(FirstName, LastName, Email, Phone, savebtn);
	// vbox.setPadding(new Insets(10));
	// root.getChildren().add(vbox);
	//
	// updateStage.setTitle("Edit Customer");
	// updateStage.setScene(scene);
	// updateStage.show();
	// }
	//
	// public void delete(int id) {
	// Session session = sf.openSession();
	// session.beginTransaction();
	// Customer customer = (Customer) session.get(Customer.class, id);
	//
	// Alert alert = new Alert(Alert.AlertType.INFORMATION);
	// alert.setTitle("Deleting " + customer.getFirstName() + " " +
	// customer.getLastName());
	// alert.setHeaderText(
	// "Are you Sure, You want to delete " + customer.getFirstName() + " " +
	// customer.getLastName());
	// alert.setContentText("This action can't be undone!");
	// Optional result = alert.showAndWait();
	//
	// if (result.get() == ButtonType.OK) {
	// session.delete(customer);
	// session.getTransaction().commit();
	// session.close();
	// startApp();
	// }
	// }

	public static void main(String[] args) {
		launch(args);
	}

}
