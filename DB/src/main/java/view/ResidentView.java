package view;

import java.util.List;
import java.util.Optional;

import DAO.IDao;
import hibernate.Factory;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import model.Faculty;
import model.FormOfEducation;
import model.Resident;
import model.ResidentType;
import model.Room;

public class ResidentView {

	private Stage primaryStage;
	private IDao<Resident> mainDao = Factory.getInstance().getResidentDAO();
	private IDao[] utilDao = { Factory.getInstance().getResidentTypeDAO(),
			Factory.getInstance().getFormOfEducationDAO(), Factory.getInstance().getFacultyDAO(),
			Factory.getInstance().getRoomDAO() };
	private String order = "id";

	public void startApp() {
		primaryStage = new Stage();
		List<Resident> data = read();
		Label[] labels = { new Label("id"), new Label("name"), new Label("sex"), new Label("phone"), new Label("age"),
				new Label("residentType"), new Label("formOfEducation"), new Label("faculty"), new Label("Address"),
				new Label("room"), new Label("balance") };
		for (Label l : labels) {
			if (!(l.getText().equals("Address") || l.getText().equals("residentType")
					|| l.getText().equals("formOfEducation") || l.getText().equals("faculty")))
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

		gridPane.add(separator, 0, 1, 14, 1);

		int i = 2;
		for (Resident item : data) {
			Label[] localLabels = { new Label(String.valueOf(item.getId())), new Label(item.getName()),
					new Label(item.getSex()), new Label(item.getPhone()), new Label(String.valueOf(item.getAge())),
					new Label(item.getResType().getName()), new Label(item.getFoe().getName()),
					new Label(item.getFaculty().getName()), new Label(item.getRoom().getStudentHouse().getAddress()),
					new Label(String.valueOf(item.getRoom().getRoomNumber())),
					new Label(String.valueOf(item.getBalance())) };
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
		create.setText("Add resident");
		create.setOnAction(event -> {
			create();
		});

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(create, gridPane);

		StackPane root = new StackPane();
		root.getChildren().addAll(vbox);
		Scene scene = new Scene(root);

		primaryStage.setTitle("Residents");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void create() {
		String[] attr = { "Enter resident's name", "Enter resident's age", "Enter resident's phone",
				"Select resident's sex", "Select resident's type", "Select resident's form of education",
				"Select resident's faculty", "Select resident's room" };

		Text[] text = new Text[attr.length];
		TextArea area = new TextArea();
		for (int i = 0; i < text.length; i++) {
			text[i] = new Text(attr[i]);
		}
		TextField[] fields = new TextField[3];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new TextField();
			fields[i].setPromptText(attr[i]);
		}
		fields[1].textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,2}")) {
					Platform.runLater(() -> {
						fields[1].setText(newValue.replace(newValue, oldValue));

					});
				}
			}
		});
		fields[2].setText("+7");
		fields[2].textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[\\+0-9]{0,12}")) {
					Platform.runLater(() -> {
						fields[2].setText(newValue.replace(newValue, oldValue));
					});
				}
			}
		});

		String[] options = { "М", "Ж" };
		ComboBox[] comboBoxes = new ComboBox[5];
		for (int i = 0; i < comboBoxes.length; i++) {
			comboBoxes[i] = new ComboBox<>();
			comboBoxes[i].setPromptText(attr[i + 3]);
			if (i > 0) {
				comboBoxes[i].getItems().addAll(utilDao[i - 1].items());
			}
			comboBoxes[i].getSelectionModel().selectFirst();
		}
		comboBoxes[0].getItems().addAll(options);
		comboBoxes[0].getSelectionModel().selectFirst();

		VBox vbox = new VBox(10);
		for (int i = 0; i < text.length; i++) {
			vbox.getChildren().add(text[i]);
			if (i < fields.length)
				vbox.getChildren().add(fields[i]);
			if (i > 2)
				vbox.getChildren().add(comboBoxes[i - 3]);
		}

		Stage createStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));
		savebtn.setOnAction(event -> {
			String name = fields[0].getText();
			String nameRegex = "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}|" + "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}|"
					+ "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}";
			String age = fields[1].getText();
			String ageRegex = "[1-6][0-9]";
			String phone = fields[2].getText();
			String phoneRegex = "\\+7[0-9]{10}";

			int[] id = new int[comboBoxes.length - 1];
			for (int i = 0; i < id.length; i++) {
				String s = (String) comboBoxes[i + 1].getValue();
				id[i] = Integer.valueOf(s.split("-")[0]);
			}

			boolean checker = true;
			if (!name.matches(nameRegex)) {
				area.appendText("Неверный формат имени\n");
				fields[0].getStyleClass().add("error");
				checker = false;
			} else {
				fields[0].getStyleClass().remove("error");
			}
			if (!age.matches(ageRegex)) {
				area.appendText("Некорректный возраст\n");
				fields[1].getStyleClass().add("error");
				checker = false;
			} else {
				fields[1].getStyleClass().remove("error");
			}
			if (!phone.matches(phoneRegex)) {
				area.appendText("Неверный формат номера телефона\n");
				fields[2].getStyleClass().add("error");
				checker = false;
			} else {
				fields[2].getStyleClass().remove("error");
			}

			if (checker) {
				for (TextField tf : fields)
					tf.getStyleClass().remove("error");
				try {

					Resident item = new Resident(name, (int) Integer.valueOf(age), phone,
							(String) comboBoxes[0].getValue(), new ResidentType(id[0]), new FormOfEducation(id[1]),
							new Faculty(id[2]), new Room(id[3]));
					mainDao.addItem(item);
					primaryStage.hide();
					createStage.hide();
					startApp();
				} catch (Exception e1) {
					while (e1.getCause() != null)
						e1 = (Exception) e1.getCause();
					area.appendText(e1.getMessage().split("Где")[0] + "\n");
				}
			}
		});

		vbox.getChildren().addAll(savebtn, area);

		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		createStage.setTitle("New Resident");
		createStage.setScene(scene);
		createStage.show();
	}

	public List<Resident> read() {
		List<Resident> data = (List<Resident>) Factory.getInstance().getResidentDAO().getAllItems(order);
		return data;
	}

	public void update(long l) {
		String[] attr = { "New resident's name", "New resident's age", "New resident's phone", "New resident's sex",
				"New resident's type", "New resident's form of education", "New resident's faculty",
				"New resident's room" };
		Resident item = mainDao.getItemById(l);
		TextArea area = new TextArea();
		Text[] text = new Text[8];
		for (int i = 0; i < text.length; i++) {
			text[i] = new Text(attr[i]);
		}
		TextField[] fields = new TextField[3];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = new TextField();
			fields[i].setPromptText(attr[i]);
		}
		fields[1].textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d{0,2}")) {
					Platform.runLater(() -> {
						fields[1].setText(newValue.replace(newValue, oldValue));
					});
				}
			}
		});
		fields[2].textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("[\\+0-9]{0,12}")) {
					Platform.runLater(() -> {
						fields[2].setText(newValue.replace(newValue, oldValue));
					});
				}
			}
		});
		fields[0].setText(item.getName());
		fields[1].setText(String.valueOf(item.getAge()));
		fields[2].setText(item.getPhone());

		String[] options = { "М", "Ж" };
		ComboBox[] comboBoxes = new ComboBox[5];
		for (int i = 0; i < comboBoxes.length; i++) {
			comboBoxes[i] = new ComboBox<>();
			comboBoxes[i].setPromptText(attr[i + 3]);
			if (i > 0) {
				comboBoxes[i].getItems().addAll(utilDao[i - 1].items());
			}
		}
		comboBoxes[0].getItems().addAll(options);

		comboBoxes[0].getSelectionModel().select(item.getSex());
		comboBoxes[1].getSelectionModel().select(item.getResType().getId() + "-" + item.getResType().getName());
		comboBoxes[2].getSelectionModel().select(item.getFoe().getId() + "-" + item.getFoe().getName());
		comboBoxes[3].getSelectionModel().select(item.getFaculty().getId() + "-" + item.getFaculty().getName());
		comboBoxes[4].getSelectionModel().select(item.getRoom().getId() + "-" + item.getRoom().getName());

		VBox vbox = new VBox(10);
		for (int i = 0; i < text.length; i++) {
			vbox.getChildren().add(text[i]);
			if (i < fields.length)
				vbox.getChildren().add(fields[i]);
			if (i > 2)
				vbox.getChildren().add(comboBoxes[i - 3]);
		}

		Stage updateStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");

		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));
		savebtn.setOnAction(event -> {
			String name = fields[0].getText();
			String nameRegex = "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}|" + "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}|"
					+ "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}";
			String age = fields[1].getText();
			String ageRegex = "[1-6][0-9]";
			String phone = fields[2].getText();
			String phoneRegex = "\\+7[0-9]{10}";

			int[] id = new int[comboBoxes.length - 1];
			for (int i = 0; i < id.length; i++) {
				String s = (String) comboBoxes[i + 1].getValue();
				id[i] = Integer.valueOf(s.split("-")[0]);
			}

			boolean checker = true;
			if (!name.matches(nameRegex)) {
				area.appendText("Неверный формат имени\n");
				fields[0].getStyleClass().add("error");
				checker = false;
			} else {
				fields[0].getStyleClass().remove("error");
			}
			if (!age.matches(ageRegex)) {
				area.appendText("Некорректный возраст\n");
				fields[1].getStyleClass().add("error");
				checker = false;
			} else {
				fields[1].getStyleClass().remove("error");
			}
			if (!phone.matches(phoneRegex)) {
				area.appendText("Неверный формат номера телефона\n");
				fields[2].getStyleClass().add("error");
				checker = false;
			} else {
				fields[2].getStyleClass().remove("error");
			}
			if (checker) {
				for (TextField tf : fields) {
					tf.getStyleClass().remove("error");
				}
				try {
					item.setName(name);
					item.setAge((int) Integer.valueOf(age));
					item.setPhone(phone);
					item.setSex((String) comboBoxes[0].getValue());
					item.setResType(new ResidentType(id[0]));
					item.setFoe(new FormOfEducation(id[1]));
					item.setFaculty(new Faculty(id[2]));
					item.setRoom(new Room(id[3]));
					mainDao.updateItem(item);
					primaryStage.hide();
					updateStage.hide();
					startApp();
				} catch (Exception e) {
					while (e.getCause() != null)
						e = (Exception) e.getCause();
					area.appendText(e.getMessage().split("Где")[0] + "\n");
				}
			}
		});

		vbox.getChildren().addAll(savebtn, area);
		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		updateStage.setTitle("Edit Resident");
		updateStage.setScene(scene);
		updateStage.show();
	}

	public void delete(long l) {
		Resident item = mainDao.getItemById(l);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Deleting " + item.getName());
		alert.setHeaderText("Are you Sure, You want to delete " + item.getName());
		alert.setContentText("This action can't be undone!");
		Optional result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			primaryStage.hide();
			mainDao.deleteItem(item);
			startApp();
		}
	}

}
