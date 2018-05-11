package view;

import java.util.List;
import java.util.Optional;

import org.hibernate.exception.GenericJDBCException;
import org.postgresql.util.PSQLException;

import DAO.IDao;
import hibernate.Factory;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Personal;
import model.Profession;
import model.StudentHouse;

public class PersonalView {

	private Stage primaryStage;
	private IDao<Personal> mainDao = Factory.getInstance().getPersonalDAO();
	private IDao[] utilDao = { Factory.getInstance().getProfessionDAO(), Factory.getInstance().getStudentHouseDAO() };
	private String order = "id";

	public void startApp() {
		primaryStage = new Stage();

		List<Personal> data = read();

		Label[] labels = { new Label("id"), new Label("name"), new Label("profession"), new Label("studentHouse") };
		for (Label l : labels) {
			l.setOnMouseClicked(event -> {
				order = l.getText();
				primaryStage.hide();
				startApp();

			});
		}

		GridPane gridPane = new GridPane();
		gridPane.setHgap(30);
		gridPane.setVgap(10);
		for (int i = 0; i < labels.length; i++) {
			labels[i].setFont(new Font("Arial", 15));
			gridPane.add(labels[i], i, 0);
		}

		Separator separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);

		gridPane.add(separator, 0, 1, 7, 1);

		int i = 2;
		for (Personal item : data) {
			Label[] localLabels = { new Label(String.valueOf(item.getId())), new Label(item.getName()),
					new Label(item.getProfession().getName()), new Label(item.getStudentHouse().getAddress()) };
			Button edit = new Button("Edit");
			edit.setOnAction(event -> {

				update(item.getId());
			});
			Button delete = new Button("Delete");
			delete.setOnAction(event -> {
				delete(item.getId());
			});
			for (int j = 0; j < localLabels.length; j++)
				gridPane.add(localLabels[j], j, i, 1, 1);
			gridPane.add(edit, localLabels.length + 1, i, 1, 1);
			gridPane.add(delete, localLabels.length + 2, i, 1, 1);
			i++;
		}

		Button create = new Button();
		create.setText("Add personal");
		create.setOnAction(event -> {
			create();
		});

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(create, gridPane);

		StackPane root = new StackPane();
		root.getChildren().addAll(vbox);

		Scene scene = new Scene(root);

		primaryStage.setTitle("Personal");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void create() {
		String[] attr = { "Enter personal's name", "Select profession", "Select student house" };
		Stage createStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		Text[] text = new Text[attr.length];
		TextArea area = new TextArea();
		for (int i = 0; i < text.length; i++) {
			text[i] = new Text(attr[i]);
		}
		TextField field = new TextField();

		field.setPromptText(attr[0]);

		ComboBox[] comboBoxes = new ComboBox[2];
		for (int i = 0; i < comboBoxes.length; i++) {
			comboBoxes[i] = new ComboBox<>();
			comboBoxes[i].setPromptText(attr[i + 1]);
			comboBoxes[i].getItems().addAll(utilDao[i].items());
		}

		VBox vbox = new VBox(10);
		for (int i = 0; i < text.length; i++) {
			vbox.getChildren().add(text[i]);
			if (i < 1)
				vbox.getChildren().add(field);
			else
				vbox.getChildren().add(comboBoxes[i - 1]);
		}

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));

		savebtn.setOnAction(event -> {

			int[] id = new int[comboBoxes.length];
			for (int i = 0; i < id.length; i++) {
				String s = (String) comboBoxes[i].getValue();
				id[i] = Integer.valueOf(s.split("-")[0]);
			}
			try {
				mainDao.addItem(new Personal(field.getText(), new Profession(id[0]), new StudentHouse(id[1])));
				createStage.hide();
				startApp();

			} catch (PSQLException | GenericJDBCException e1) {
				area.appendText(e1.getCause().getMessage() + "\n");
				e1.printStackTrace();
			}

		});

		vbox.getChildren().addAll(savebtn, area);

		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		createStage.setTitle("New Resident");
		createStage.setScene(scene);
		createStage.show();
	}

	public List<Personal> read() {
		List<Personal> data = null;
		data = (List<Personal>) Factory.getInstance().getPersonalDAO().getAllItems(order);

		return data;
	}

	public void update(long l) {
		String[] attr = { "New personal's name", "New personal's profession", "New personal's student house" };

		Stage updateStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		TextArea area = new TextArea();
		Text[] text = new Text[attr.length];
		for (int i = 0; i < text.length; i++) {
			text[i] = new Text(attr[i]);
		}
		TextField field = new TextField();

		ComboBox[] comboBoxes = new ComboBox[2];
		for (int i = 0; i < comboBoxes.length; i++) {
			comboBoxes[i] = new ComboBox<>();
			comboBoxes[i].setPromptText(attr[i + 1]);
			comboBoxes[i].getItems().addAll(utilDao[i].items());
		}

		VBox vbox = new VBox(10);
		vbox.getChildren().add(text[0]);
		vbox.getChildren().add(field);
		for (int i = 1; i < text.length; i++) {
			vbox.getChildren().add(text[i]);
			vbox.getChildren().add(comboBoxes[i - 1]);
		}

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));

		savebtn.setOnAction(event -> {
			int[] id = new int[comboBoxes.length];
			for (int i = 0; i < id.length; i++) {
				String s = (String) comboBoxes[i].getValue();
				id[i] = Integer.valueOf(s.split("-")[0]);
			}
			try {
				Personal item = mainDao.getItemById(l);
				item.setName(field.getText());
				item.setProfession(new Profession(id[0]));
				item.setStudentHouse(new StudentHouse(id[1]));
				mainDao.updateItem(item);

			} catch (Exception e1) {
				area.appendText(e1.getCause().getCause().getMessage() + "\n");
				e1.printStackTrace();
			}
			primaryStage.hide();
			updateStage.hide();
			startApp();
		});

		vbox.getChildren().addAll(savebtn, area);
		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		updateStage.setTitle("Edit Personal");
		updateStage.setScene(scene);
		updateStage.show();
	}

	public void delete(long l) {
		Personal item = null;
		try {
			item = mainDao.getItemById(l);
		} catch (PSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Deleting " + item.getName());
		alert.setHeaderText("Are you Sure, You want to delete " + item.getName());
		alert.setContentText("This action can't be undone!");
		Optional result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			primaryStage.hide();
			try {
				mainDao.deleteItem(item);
			} catch (PSQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startApp();
		}
	}

}
