package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import DAO.IDao;
import hibernate.Factory;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Resident;

public class DebtorView {

	private Stage primaryStage = new Stage();
	private IDao<Resident> mainDao = Factory.getInstance().getResidentDAO();
	private IDao[] utilDao = { Factory.getInstance().getResidentTypeDAO(),
			Factory.getInstance().getFormOfEducationDAO(), Factory.getInstance().getFacultyDAO(),
			Factory.getInstance().getRoomDAO() };
	private String order = "id";
	private String property = null;
	private String value = null;
	private HashMap<String, Object> critMap = new HashMap<>();
	Label[] labels = { new Label("id"), new Label("Имя"), new Label("Пол"), new Label("Телефон"), new Label("Возраст"),
			new Label("Тип проживающего"), new Label("Форма обучения"), new Label("Факультет"),
			new Label("Адрес общежития"), new Label("Комната"), new Label("Счет") };
	List<Resident> data = read();
	VBox vbox = getTable();

	public void startApp() {
		for (Label l : labels) {
			l.setOnMouseClicked(event -> {
				if (l.getText().equals("id"))
					order = l.getText();
				else if (l.getText().equals("Имя"))
					order = "name";
				else if (l.getText().equals("Пол"))
					order = "sex";
				else if (l.getText().equals("Возраст"))
					order = "age";
				else if (l.getText().equals("Тип проживающего")) {
					property = "residentType";
					value = "residentType";
					order = null;
				} else if (l.getText().equals("Форма обучения")) {
					property = "formOfEducation";
					value = "foe";
					order = null;
				} else if (l.getText().equals("Факультет")) {
					property = "faculty";
					value = "faculty";
					order = null;
				} else if (l.getText().equals("Адрес общежития")) {
					property = "room.studentHouse";
					value = "address";
					order = null;
				}

				else if (l.getText().equals("Комната")) {
					property = "room";
					value = "roomNumber";
					order = null;
				} else if (l.getText().equals("Счет"))
					order = "balance";

				data = read();
				vbox = getTable();
				StackPane root = new StackPane();
				root.getChildren().addAll(vbox);
				Scene scene = new Scene(root);
				scene.getStylesheets().add("view.css");
				primaryStage.setScene(scene);
			});
		}

		StackPane root = new StackPane();
		root.getChildren().addAll(vbox);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");
		primaryStage.setTitle("Проживающие");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public int itemsPerPage() {
		return 5;
	}

	public VBox getTable() {
		int count = (data.size() % itemsPerPage() == 0) ? data.size() / itemsPerPage()
				: data.size() / itemsPerPage() + 1;

		Pagination pagination = new Pagination(count, 0);
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				return createPage(pageIndex);
			}
		});

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(pagination);
		return vbox;
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
		gridPane.add(separator, 0, 1, 14, 1);
		int k = 2;
		for (int i = page; i < page + itemsPerPage(); i++) {
			Resident item;
			if (i != data.size())
				item = data.get(i);
			else
				break;
			if (item.getBalance() >= 0)
				continue;
			Label[] localLabels = { new Label(String.valueOf(item.getId())), new Label(item.getName()),
					new Label(item.getSex()), new Label(item.getPhone()), new Label(String.valueOf(item.getAge())),
					new Label(item.getResType().getName()), new Label(item.getFoe().getName()),
					new Label(item.getFaculty().getName()), new Label(item.getRoom().getStudentHouse().getAddress()),
					new Label(String.valueOf(item.getRoom().getRoomNumber())),
					new Label(String.valueOf(item.getBalance())) };

			for (int j = 0; j < localLabels.length; j++)
				gridPane.add(localLabels[j], j, k, 1, 1);
			k++;
		}
		box.setPadding(new Insets(20));
		box.getChildren().addAll(gridPane);

		return box;
	}

	public List<Resident> read() {
		List<Resident> data;
		List<Resident> debtors = new ArrayList<>();
		data = (List<Resident>) Factory.getInstance().getResidentDAO().getAllItems(order, property, value, null);
		data.forEach(x -> {
			if (x.getBalance() <= 0)
				debtors.add(x);
		});

		return debtors;
	}

}
