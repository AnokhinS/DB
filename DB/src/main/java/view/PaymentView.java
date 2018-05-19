package view;

import java.util.List;
import java.util.Optional;

import DAO.IDao;
import hibernate.Factory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
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
import model.Payment;
import model.Resident;

public class PaymentView {

	private Stage primaryStage;
	private IDao<Payment> mainDao = Factory.getInstance().getPaymentDAO();
	private IDao utilDao = Factory.getInstance().getResidentDAO();
	private String order = "id";

	public void startApp() {
		primaryStage = new Stage();

		List<Payment> data = read(order);

		Label[] labels = { new Label("id"), new Label("resident"), new Label("sum"), new Label("date") };

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
		for (Payment item : data) {
			Label[] localLabels = { new Label(String.valueOf(item.getId())), new Label(item.getResident().getName()),
					new Label(String.valueOf(item.getSum())), new Label(String.valueOf(item.getDate())) };

			Button delete = new Button("Delete");
			delete.setOnAction(event -> {
				delete(item.getId());
			});
			for (int j = 0; j < localLabels.length; j++)
				gridPane.add(localLabels[j], j, i, 1, 1);
			gridPane.add(delete, localLabels.length + 1, i, 1, 1);
			i++;
		}

		Button create = new Button();
		create.setText("Add payment");
		create.setOnAction(event -> {
			create();
		});

		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(20));
		vbox.getChildren().addAll(create, gridPane);

		StackPane root = new StackPane();
		root.getChildren().addAll(vbox);

		Scene scene = new Scene(root);

		primaryStage.setTitle("Payments");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void create() {
		String[] attr = { "Select resident", "Enter sum", "Select date" };
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
		Button savebtn = new Button("Save");
		savebtn.setTooltip(new Tooltip("Save"));

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

		createStage.setTitle("New Resident");
		createStage.setScene(scene);
		createStage.show();
	}

	public List<Payment> read(String s) {
		List<Payment> data = null;
		data = (List<Payment>) Factory.getInstance().getPaymentDAO().getAllItems(s);
		return data;
	}

	public void delete(long l) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Deleting...");
		alert.setHeaderText("Are you Sure?");
		alert.setContentText("This action can't be undone!");
		Optional result = alert.showAndWait();

		if (result.get() == ButtonType.OK) {
			primaryStage.hide();
			Payment item = mainDao.getItemById(l);
			mainDao.deleteItem(item);
			startApp();
		}
	}

}
