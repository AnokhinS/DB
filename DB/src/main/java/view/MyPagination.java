package view;

import java.util.List;

import DAO.IDao;
import hibernate.Factory;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

public class MyPagination extends Application {
	private Pagination pagination;
	List<Resident> data = read();
	private IDao<Resident> mainDao = Factory.getInstance().getResidentDAO();
	private IDao[] utilDao = { Factory.getInstance().getResidentTypeDAO(),
			Factory.getInstance().getFormOfEducationDAO(), Factory.getInstance().getFacultyDAO(),
			Factory.getInstance().getRoomDAO() };
	Label[] labels = { new Label("id"), new Label("Имя"), new Label("Пол"), new Label("Телефон"), new Label("Возраст"),
			new Label("Тип проживающего"), new Label("Форма обучения"), new Label("Факультет"),
			new Label("Адрес общежития"), new Label("Комната"), new Label("Счет") };

	public static void main(String[] args) throws Exception {
		launch(args);
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
		gridPane.add(separator, 0, 1, 14, 1);
		int k = 2;
		for (int i = page; i < page + itemsPerPage(); i++) {
			Resident item;
			if (i != data.size())
				item = data.get(i);
			else
				break;
			Label[] localLabels = { new Label(String.valueOf(item.getId())), new Label(item.getName()),
					new Label(item.getSex()), new Label(item.getPhone()), new Label(String.valueOf(item.getAge())),
					new Label(item.getResType().getName()), new Label(item.getFoe().getName()),
					new Label(item.getFaculty().getName()), new Label(item.getRoom().getStudentHouse().getAddress()),
					new Label(String.valueOf(item.getRoom().getRoomNumber())),
					new Label(String.valueOf(item.getBalance())) };
			Button edit = new Button("Изменить");
			Button delete = new Button("Выселить");
			for (int j = 0; j < localLabels.length; j++)
				gridPane.add(localLabels[j], j, k, 1, 1);
			gridPane.add(edit, localLabels.length + 1, k, 1, 1);
			gridPane.add(delete, localLabels.length + 2, k, 1, 1);
			k++;
			System.out.println(k);
		}
		box.setPadding(new Insets(20));
		box.getChildren().addAll(gridPane);

		return box;
	}

	@Override
	public void start(final Stage stage) throws Exception {
		int count = (data.size() / itemsPerPage() == 0) ? data.size() / itemsPerPage()
				: data.size() / itemsPerPage() + 1;
		pagination = new Pagination(count, 0);
		pagination.setPageFactory(new Callback<Integer, Node>() {
			@Override
			public Node call(Integer pageIndex) {
				return createPage(pageIndex);
			}
		});
		Button delete = new Button("Выселить");

		StackPane root = new StackPane();
		Scene scene = new Scene(root);
		VBox box = new VBox(10);
		box.getChildren().addAll(delete, pagination);
		root.getChildren().addAll(box);
		stage.setScene(scene);
		stage.setTitle("PaginationSample");
		stage.show();
	}

	public List<Resident> read() {
		List<Resident> data;
		data = (List<Resident>) Factory.getInstance().getResidentDAO().getAllItems("id");
		return data;
	}
}