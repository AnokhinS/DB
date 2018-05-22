package view;

import java.util.List;
import java.util.Optional;

import DAO.IDao;
import hibernate.Factory;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
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
import javafx.util.Callback;
import model.Personal;
import model.Profession;
import model.StudentHouse;

public class PersonalView {

	private Stage primaryStage;
	private IDao<Personal> mainDao = Factory.getInstance().getPersonalDAO();
	private IDao[] utilDao = { Factory.getInstance().getProfessionDAO(), Factory.getInstance().getStudentHouseDAO() };
	private String order = "id";
	private String property = null;
	private String value = null;
	List<Personal> data = read();
	Label[] labels = { new Label("id"), new Label("Имя"), new Label("Профессия"), new Label("Общежитие") };

	public void startApp() {
		primaryStage = new Stage();
		data = read();
		for (Label l : labels) {
			l.setOnMouseClicked(event -> {
				if (l.getText().equals("Имя"))
					order = "name";
				else if (l.getText().equals("Профессия"))
					order = "profession";
				else if (l.getText().equals("Общежитие")) {
					property = "studentHouse";
					value = "address";
					order = null;
				} else
					order = l.getText();
				primaryStage.hide();
				startApp();

			});
		}

		int count = (data.size() % itemsPerPage() == 0) ? data.size() / itemsPerPage()
				: data.size() / itemsPerPage() + 1;

		Pagination pagination = new Pagination(count, 0);
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				return createPage(pageIndex);
			}
		});

		Button create = new Button();
		create.setText("Добавить работника");
		create.setOnAction(event -> {
			create();
		});

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(create, pagination);

		StackPane root = new StackPane();
		root.getChildren().addAll(vbox);

		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");
		primaryStage.setTitle("Работники общежития");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public int itemsPerPage() {
		return 5;
	}

	public VBox createPage(int pageIndex) {

		VBox box = new VBox(10);
		int page = pageIndex * itemsPerPage();
		GridPane gridPane = new GridPane();
		gridPane.setHgap(30);
		gridPane.setVgap(10);
		for (int j = 0; j < labels.length; j++) {
			labels[j].setFont(new Font("Arial", 15));
			gridPane.add(labels[j], j, 0);
		}
		Separator separator = new Separator();
		separator.setOrientation(Orientation.HORIZONTAL);
		gridPane.add(separator, 0, 1, 7, 1);
		int k = 2;
		for (int i = page; i < page + itemsPerPage(); i++) {
			Personal item;
			if (i != data.size())
				item = data.get(i);
			else
				break;
			Label[] localLabels = { new Label(String.valueOf(item.getId())), new Label(item.getName()),
					new Label(item.getProfession().getName()), new Label(item.getStudentHouse().getAddress()) };
			Button edit = new Button("Изменить");
			edit.setOnAction(event -> {
				update(item.getId());
			});
			Button delete = new Button("Уволить");
			delete.setOnAction(event -> {
				delete(item.getId());
			});
			for (int j = 0; j < localLabels.length; j++)
				gridPane.add(localLabels[j], j, k, 1, 1);
			gridPane.add(edit, localLabels.length + 1, k, 1, 1);
			gridPane.add(delete, localLabels.length + 2, k, 1, 1);
			k++;
		}
		box.setPadding(new Insets(20));
		box.getChildren().addAll(gridPane);

		return box;
	}

	public void create() {
		String[] attr = { "Введите имя работника", "Выберите профессию", "Выберите общежитие" };
		Stage createStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");
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
			comboBoxes[i].getSelectionModel().selectFirst();
		}

		VBox vbox = new VBox(10);
		for (int i = 0; i < text.length; i++) {
			vbox.getChildren().add(text[i]);
			if (i < 1)
				vbox.getChildren().add(field);
			else
				vbox.getChildren().add(comboBoxes[i - 1]);
		}

		Button savebtn = new Button("Сохранить");
		savebtn.setTooltip(new Tooltip("Сохранить"));

		savebtn.setOnAction(event -> {
			String name = field.getText();
			String nameRegex = "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}|" + "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}|"
					+ "\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}[A-Za-zА-ЯА-я]+\\s{0,}";
			int[] id = new int[comboBoxes.length];

			boolean checker = true;
			if (!name.matches(nameRegex)) {
				area.appendText("Неверный формат имени\n");
				field.getStyleClass().add("error");
				checker = false;
			} else {
				field.getStyleClass().remove("error");
			}

			if (checker)
				try {
					mainDao.addItem(new Personal(name, new Profession(id[0]), new StudentHouse(id[1])));
					createStage.hide();
					startApp();

				} catch (Exception e) {
					while (e.getCause() != null)
						e = (Exception) e.getCause();
					area.appendText(e.getMessage() + "\n");
				}

		});

		vbox.getChildren().addAll(savebtn, area);

		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		createStage.setTitle("Добавление работника");
		createStage.setScene(scene);
		createStage.show();
	}

	public List<Personal> read() {
		List<Personal> data;
		if (order != null)
			data = (List<Personal>) Factory.getInstance().getPersonalDAO().getAllItems(order);
		else
			data = (List<Personal>) Factory.getInstance().getPersonalDAO().getAllItems(property, value);
		return data;
	}

	public void update(long l) {
		String[] attr = { "Имя работника", "Профессия работника", "Общежитие" };

		Stage updateStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");
		TextArea area = new TextArea();
		Text[] text = new Text[attr.length];
		for (int i = 0; i < text.length; i++) {
			text[i] = new Text(attr[i]);
		}
		Personal item = mainDao.getItemById(l);
		TextField field = new TextField(item.getName());

		ComboBox[] comboBoxes = new ComboBox[2];
		for (int i = 0; i < comboBoxes.length; i++) {
			comboBoxes[i] = new ComboBox<>();
			comboBoxes[i].setPromptText(attr[i + 1]);
			comboBoxes[i].getItems().addAll(utilDao[i].items());
		}
		comboBoxes[0].getSelectionModel().select(item.getProfession().getId() + "-" + item.getProfession().getName());
		comboBoxes[1].getSelectionModel()
				.select(item.getStudentHouse().getId() + "-" + item.getStudentHouse().getName());
		VBox vbox = new VBox(10);
		vbox.getChildren().add(text[0]);
		vbox.getChildren().add(field);
		for (int i = 1; i < text.length; i++) {
			vbox.getChildren().add(text[i]);
			vbox.getChildren().add(comboBoxes[i - 1]);
		}

		Button savebtn = new Button("Сохранить");
		savebtn.setTooltip(new Tooltip("Сохранить"));

		savebtn.setOnAction(event -> {
			int[] id = new int[comboBoxes.length];
			for (int i = 0; i < id.length; i++) {
				String s = (String) comboBoxes[i].getValue();
				id[i] = Integer.valueOf(s.split("-")[0]);
			}
			try {
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

		updateStage.setTitle("Изменение данных о работнике");
		updateStage.setScene(scene);
		updateStage.show();
	}

	public void delete(long l) {
		Personal item = mainDao.getItemById(l);

		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Удаляем " + item.getName());
		alert.setHeaderText("Вы уверены, что хотите удалить " + item.getName() + " ?");
		alert.setContentText("Это действие не может быть отменено");
		Optional result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			primaryStage.hide();
			mainDao.deleteItem(item);
			startApp();
		}
	}

}
