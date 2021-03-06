package view;

import java.util.List;
import java.util.Optional;

import DAO.IDao;
import hibernate.MyDaoFactory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import model.Payment;
import model.Resident;

public class PaymentView {

	private Stage primaryStage;
	private IDao<Payment> mainDao = MyDaoFactory.getInstance().getPaymentDAO();
	private IDao utilDao = MyDaoFactory.getInstance().getResidentDAO();
	private String order = "id";
	List<Payment> data = read();
	VBox vbox = getTable();
	Label[] labels = { new Label("id"), new Label("Проживающий"), new Label("Сумма"), new Label("Дата") };

	public void startApp() {
		for (Label l : labels) {
			l.setOnMouseClicked(event -> {
				if (l.getText().equals("Проживающий")) {
					order = "resident";
				} else if (l.getText().equals("Сумма"))
					order = "sum";
				else if (l.getText().equals("Дата")) {
					order = "date";
				} else
					order = l.getText();
				data = read();
				vbox = getTable();
				StackPane root = new StackPane();
				root.getChildren().addAll(vbox);
				Scene scene = new Scene(root);
				scene.getStylesheets().add("view.css");
				primaryStage.setScene(scene);
			});
		}

		primaryStage = new Stage();
		StackPane root = new StackPane();
		root.getChildren().addAll(vbox);
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");
		primaryStage.setScene(scene);
		primaryStage.setTitle("Платежи");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public VBox getTable() {
		int count = (data.size() % itemsPerPage() == 0) ? data.size() / itemsPerPage()
				: data.size() / itemsPerPage() + 1;
		Button create = new Button();
		create.setText("Добавить платеж");
		create.setOnAction(event -> {
			create();
		});
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		if (count != 0) {
			Pagination pagination = new Pagination(count, 0);
			pagination.setPageFactory(new Callback<Integer, Node>() {
				@Override
				public Node call(Integer pageIndex) {
					return createPage(pageIndex);
				}
			});
			vbox.getChildren().addAll(create, pagination);
		} else
			vbox.getChildren().addAll(create);

		return vbox;
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
			Payment item;
			if (i != data.size())
				item = data.get(i);
			else
				break;
			Label[] localLabels = { new Label(String.valueOf(item.getId())), new Label(item.getResident().getName()),
					new Label(String.valueOf(item.getSum())), new Label(String.valueOf(item.getDate())) };
			Button delete = new Button("Удалить");
			delete.setOnAction(event -> {
				delete(item.getId());
			});
			for (int j = 0; j < localLabels.length; j++)
				gridPane.add(localLabels[j], j, k, 1, 1);
			gridPane.add(delete, localLabels.length + 1, k, 1, 1);
			k++;
		}
		box.setPadding(new Insets(20));
		box.getChildren().addAll(gridPane);

		return box;
	}

	public void create() {
		String[] attr = { "Выберите проживающего", "Введите сумму платежа", "Введите дату" };
		Text[] text = new Text[attr.length];
		for (int i = 0; i < text.length; i++) {
			text[i] = new Text(attr[i]);
		}
		TextField sum = new TextField();
		TextArea area = new TextArea();
		sum.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					sum.setText(newValue.replaceAll(newValue, oldValue));
				}
			}
		});

		String[] options = null;
		options = utilDao.items();
		ComboBox comboBox = new ComboBox();
		comboBox.getItems().addAll(options);

		DatePicker datePicker = new DatePicker();

		Stage createStage = new Stage();
		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		scene.getStylesheets().add("view.css");
		Button savebtn = new Button("Сохранить");
		savebtn.setTooltip(new Tooltip("Сохранить"));

		savebtn.setOnAction(event -> {
			String s = (String) comboBox.getValue();
			long id = 0;
			boolean checker = true;
			if (s == null) {
				checker = false;
				comboBox.getStyleClass().add("error");
				area.appendText("Выберите проживающего\n");
			} else {
				id = Long.valueOf(s.split("-")[0]);
				comboBox.getStyleClass().remove("error");
			}
			if (sum.getText().equals("")) {
				checker = false;
				sum.getStyleClass().add("error");
				area.appendText("Введите сумму платежа\n");
			} else {
				sum.getStyleClass().remove("error");
			}

			if (datePicker.getValue() == null) {
				checker = false;
				datePicker.getStyleClass().add("error");
				area.appendText("Выберите дату\n");
			} else {
				datePicker.getStyleClass().remove("error");
			}

			try {
				mainDao.addItem(
						new Payment(new Resident(id), (double) Double.valueOf(sum.getText()), datePicker.getValue()));
				createStage.hide();
				primaryStage.hide();
				startApp();

			} catch (Exception e) {

			}

		});

		VBox vbox = new VBox(10);

		vbox.getChildren().add(text[0]);
		vbox.getChildren().add(comboBox);
		vbox.getChildren().add(text[1]);
		vbox.getChildren().add(sum);
		vbox.getChildren().add(text[2]);
		vbox.getChildren().add(datePicker);

		vbox.getChildren().addAll(savebtn, area);

		vbox.setPadding(new Insets(10));
		root.getChildren().add(vbox);

		createStage.setTitle("Добавление платежа");
		createStage.setScene(scene);
		createStage.show();
	}

	public List<Payment> read() {
		List<Payment> data = null;
		data = (List<Payment>) MyDaoFactory.getInstance().getPaymentDAO().getAllItems(order, null, null, null);
		return data;
	}

	public void delete(long l) {
		Payment item = mainDao.getItemById(l);
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Удаляем платеж");
		alert.setHeaderText("Вы уверены?");
		alert.setContentText("Это действие не может быть отменено");
		try {
			Optional result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				primaryStage.hide();
				mainDao.deleteItem(item);
				startApp();
			}
		} catch (Exception e) {
		}
	}

}
