import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.channels.IllegalBlockingModeException;
import java.util.Optional;
import java.util.Scanner;

import javax.swing.event.SwingPropertyChangeSupport;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TestMyList extends Application {

	private static File selectedFile;
	private static MyList myList = new MyList(10);
	private static Table table = new Table(); // A TableView to display the employee records
	private TextField tfEmployeeID = new TextField();
	private TextField tfName = new TextField();
	private TextField tfAge = new TextField();
	private TextField tfDepartment = new TextField();
	private TextField tfDateOJ = new TextField();
	private TextField tfGender = new TextField();

	// To alert about errors not in taken
	private static Alert alertError = new Alert(AlertType.ERROR);
	private Alert alertNote = new Alert(AlertType.WARNING);

	private static Label lblNumOfEmp = new Label("WELCOME All");

	private static boolean found = false;

	public static void main(String[] args) {
		Application.launch(args);

	}

	@Override
	public void start(Stage stage) throws Exception {
		alertError.setTitle("Error!");
		alertError.setHeaderText(null);

		// Create the main Scene
		Button btOpen = new Button("Open File"); // to open the file
		Button btSave = new Button("Save"); // to save updated changes to the file
		Button btInsert = new Button("Insert"); // to add a new employee

		ImageView imageSearch = new ImageView("search.png");
		imageSearch.setFitHeight(20);
		imageSearch.setFitWidth(20);

		ImageView imageDelete = new ImageView("delete.png");
		imageDelete.setFitHeight(20);
		imageDelete.setFitWidth(20);

		Button btDelete = new Button("  Delete", imageDelete); // to delete the certain employee
		Button btSearch = new Button("  Search", imageSearch); // to search for a certain employee

		TextField tfSearch = new TextField(); // for enter the employeeID
		tfSearch.setPrefColumnCount(11);
		tfSearch.setTooltip(new Tooltip("search"));
		tfSearch.setPromptText("Enter employeeID..");

		HBox hBox1 = new HBox(20);
		hBox1.setPadding(new Insets(10, 10, 5, 10));
		hBox1.setAlignment(Pos.CENTER);
		hBox1.getChildren().addAll(tfSearch, btSearch, btDelete);

		HBox hBox2 = new HBox(30);
		hBox2.setPadding(new Insets(10, 10, 5, 10));
		hBox2.setAlignment(Pos.BOTTOM_CENTER);
		hBox2.getChildren().addAll(btInsert, btOpen, btSave);

		// Text Fields to enter the attributes of the employee
		tfEmployeeID.setPrefColumnCount(8);
		tfEmployeeID.setPromptText("Enter employee ID..");

		tfName.setPrefColumnCount(8);
		tfName.setPromptText("Enter name..");

		tfAge.setPrefColumnCount(8);
		tfAge.setPromptText("Enter age..");

		tfDepartment.setPrefColumnCount(8);
		tfDepartment.setPromptText("Enter department..");

		tfDateOJ.setPrefColumnCount(8);
		tfDateOJ.setPromptText("DOJ: yyyy-mm-dd");

		tfGender.setPrefColumnCount(8);
		tfGender.setPromptText("Enter F/M");

		HBox hBox3 = new HBox(0);
		hBox3.setAlignment(Pos.CENTER);
		hBox3.setPadding(new Insets(0, 10, 10, 10));

		// Add style to the label
		lblNumOfEmp.setStyle("-fx-text-fill: darkred; -fx-font-weight: bold; -fx-font-size: 20px");
		lblNumOfEmp.setAlignment(Pos.CENTER);
		lblNumOfEmp.setPadding(new Insets(10, 5, 5, 5));

		Label lblNote = new Label("Count by:");

		TextField tfEnter = new TextField();
		tfEnter.setPrefColumnCount(10);
		tfEnter.setPromptText("Enter age.. name.. gender, department..");

		ComboBox<String> cbo = new ComboBox<>();
		cbo.getItems().addAll("age", "name", "gender", "department");
		cbo.setValue("age");

		GridPane gridPane = new GridPane();
		gridPane.setAlignment(Pos.CENTER);
		gridPane.add(lblNote, 1, 0);
		gridPane.add(tfEnter, 0, 1);
		gridPane.add(cbo, 1, 1);

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.getChildren().addAll(table, hBox3, gridPane, lblNumOfEmp, hBox2);

		BorderPane borderPane = new BorderPane();

		borderPane.setAlignment(hBox1, Pos.CENTER);
		borderPane.setAlignment(vBox, Pos.BOTTOM_CENTER);

		borderPane.setCenter(vBox);
		borderPane.setTop(hBox1);

		// To select the employee file and fill myList with its data.
		btOpen.setOnAction(e -> {
			// A file chooser to select the employee file
			FileChooser fileChooser = new FileChooser();
			selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				if (selectedFile.getName().equals("employee.txt"))
					loadFile();
				else {
					alertError.setContentText("Can't upload this file :(");
					alertError.showAndWait();
				}
			}
		});

		// To save the updated data to the updatedEmployee.txt file
		btSave.setOnAction(e -> {
			lblNumOfEmp.setText("");
			WriteData();
			lblNumOfEmp.setText("saved successfully :)");
		});

		hBox3.getChildren().addAll(tfEmployeeID, tfName, tfAge, tfDepartment, tfDateOJ, tfGender);

		// To insert a new employee record into the myList
		btInsert.setOnAction(e -> {
			lblNumOfEmp.setText("");

			if (!tfEmployeeID.getText().trim().isEmpty() && !tfName.getText().trim().isEmpty()
					&& !tfAge.getText().trim().isEmpty() && !tfDepartment.getText().trim().isEmpty()
					&& !tfDateOJ.getText().trim().isEmpty() && !tfGender.getText().trim().isEmpty()) {
				try {
					int age = Integer.parseInt(tfAge.getText().trim());
					Employee employee = new Employee(tfEmployeeID.getText().trim(), tfName.getText().trim(), age,
							tfDepartment.getText().trim(), tfDateOJ.getText().trim(),
							tfGender.getText().trim().charAt(0));
					if (check()) {
						if (age != 0) {
							table.getItems().add(employee);
							myList.insert(employee);
							lblNumOfEmp.setText("The employee added successfully! :)");
							tfEmployeeID.clear();
							tfName.clear();
							tfAge.clear();
							tfDateOJ.clear();
							tfDepartment.clear();
							tfGender.clear();
						}
					}
				} catch (NumberFormatException ex) {
					alertError.setContentText(ex.toString());
					alertError.showAndWait();
				}

			} else {
				alertNote.setTitle("Missing");
				alertNote.setHeaderText(null);
				alertNote.setContentText("You forgot to enter some informatiom, Check it out!");
				alertNote.showAndWait();
			}
		});

		// To delete an employee record from myList using the EmployeeID.
		btDelete.setOnAction(e -> {
			
			Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
			alertConfirm.setHeaderText(null);
			alertConfirm.setTitle(null);
			alertConfirm.setContentText("Are you sure you want to delete it?");
			Optional<ButtonType> result;

			lblNumOfEmp.setText("");
			
			// Delete by using the selection model
			int row = table.getSelectionModel().getSelectedIndex();
			if (row >= 0) {
				tfSearch.setText(((Employee)myList.getAt(row)).getEmployeeID());
				result = alertConfirm.showAndWait();
				if (result.get() == ButtonType.OK) {
					table.getItems().remove(row);
					table.getSelectionModel().clearSelection();
					myList.delete(myList.getAt(row));
				}
				return;
			}
			
			found = false;
			Employee emp = new Employee();
			if (!tfSearch.getText().isEmpty()) {
				for (int i = 0; i < myList.size(); i++) {
					if (tfSearch.getText().trim().equals(((Employee) myList.getAt(i)).getEmployeeID())) {
						emp = (Employee) myList.getAt(i);
						found = true;
					}
				}
				if (!found)
					lblNumOfEmp.setText(tfSearch.getText().trim() + " Not Found :(");
				else {
					// Alert to confirm deletion
					result = alertConfirm.showAndWait();
					if (result.get() == ButtonType.OK) {
						myList.delete(emp);
						table.getItems().remove(emp);
						lblNumOfEmp.setText(emp.getEmployeeID() + " deleted successfully! :)");
					}
				}
			}
		});

		// To search for a specific employee record by EmployeeID.
		btSearch.setOnAction(e -> {
			found = false;
			String employeeID = tfSearch.getText().trim();
			if (!employeeID.isEmpty()) {
				for (int i = 0; i < myList.size(); i++) {
					if (employeeID.compareTo(((Employee) myList.getAt(i)).getEmployeeID()) == 0) {
						lblNumOfEmp.setText((Employee) myList.getAt(i) + "");
						found = true;
						tfSearch.clear();
						return;
					}
				}
				if (!found)
					lblNumOfEmp.setText(employeeID + " not Found :(");
				tfSearch.clear();
			}
		});

		// To display the number of employees grouped by age, gender, and department.
		tfEnter.setOnAction(e -> {
			if (!tfEnter.getText().trim().isEmpty() && !cbo.getValue().isEmpty()) {
				int count = 0;

				switch (cbo.getValue()) {
				case "age":
					int age = isCorrectAge(tfEnter.getText().trim());
					for (int i = 0; i < myList.size(); i++) {
						if (((Employee) myList.getAt(i)).getAge() == age)
							count++;
					}
					lblNumOfEmp.setText(count + " employee(s) their age " + age);
					tfEnter.clear();

					break;
				case "name":
					if (isCorrectName(tfEnter.getText().trim())) {
						for (int i = 0; i < myList.size(); i++) {
							if (((Employee) myList.getAt(i)).getName().equals(tfEnter.getText().trim()))
								count++;
						}
						lblNumOfEmp.setText(count + " employee(s) their name " + tfEnter.getText().trim());
						tfEnter.clear();
					}

					break;
				case "department":
					for (int i = 0; i < myList.size(); i++) {
						if (((Employee) myList.getAt(i)).getDepartment().equals(tfEnter.getText().trim()))
							count++;
					}
					lblNumOfEmp.setText(count + " employee(s) at " + tfEnter.getText().trim());
					tfEnter.clear();
					break;
				case "gender":
					if (isCorrectGender(tfEnter.getText().trim())) {
						for (int i = 0; i < myList.size(); i++) {
							if (((Employee) myList.getAt(i)).getGender() == tfEnter.getText().trim().charAt(0))
								count++;
						}
						lblNumOfEmp.setText(count + " employee(s) > " + tfEnter.getText().trim());
						tfEnter.clear();
					}
					break;
				}
			}
		});

		Scene scene = new Scene(borderPane, 820, 600);
		scene.getStylesheets().add("stylesheet.css"); // for the style of the GUI
		stage.setScene(scene);
		stage.setTitle("Employee Memo");
		stage.show();
	}

	// To check if the gender M/F and nothing else
	private boolean isCorrectGender(String gender) {
		if (gender.length() == 1) {
			if (gender.charAt(0) == 'F' || gender.charAt(0) == 'M')
				return true;
		} else {
			alertError.setContentText("Gender must be (F/M)\n");
			alertError.showAndWait();
		}
		return false;
	}

	// To check if the name alphabetical
	private boolean isCorrectName(String name) {
		if (name.matches("[A-Za-z ]+")) {
			return true;
		} else {
			alertError.setContentText("Error mismatch !!\nThe name must be alphabetical\n");
			alertError.showAndWait();
		}
		return false;
	}

	// To check if the entered age in range
	private int isCorrectAge(String trim) {
		try {
			int age = Integer.parseInt(trim);
			if (age > 0 && age < 120)
				return age;
			else {
				alertError.setContentText("Age out of range :(\n");
				alertError.showAndWait();
			}
		} catch (NumberFormatException ex) {
			alertError.setContentText(ex.toString());
			alertError.showAndWait();
		}
		return -1;
	}

	// Add a new employee record to the table
//	private static void addRecord() {
//		for (int i = 0; i < myList.size(); i++)
//			table.getItems().add((Employee) myList.getAt(i));
//	}

	// To check if the entered values as required
	private boolean check() {
		String employeeID = tfEmployeeID.getText().trim();
		String dateOfJoining = tfDateOJ.getText().trim();

		if (tfEmployeeID.getText().trim().matches("E[0-9][0-9][0-9]")) {
			if (!isExist(employeeID)) {
				if (isCorrectName(tfName.getText().trim())) {
					isCorrectAge(tfAge.getText().trim());
					if (isCorrectDate(dateOfJoining)) {
						isCorrectGender(tfGender.getText().trim());
						return true;
					}
				}
			} else {
				alertError.setContentText(employeeID + " is already exist!\n");
				alertError.showAndWait();
			}
		} else {
			alertError.setContentText(employeeID + " mismatches with E[0-1][0-9][0-9] :( \n");
			alertError.showAndWait();
		}
		return false;
	}

	// To check if the date as in correct format
	private boolean isCorrectDate(String dateOfJoining) {
		String[] date = dateOfJoining.split("-");

		if (date.length == 3) {
			try {
				int year = Integer.parseInt(date[0]);
				int month = Integer.parseInt(date[1]);
				int day = Integer.parseInt(date[2]);

				// needed since some months has 29 | 30 | 31
				if (year >= 1990 && month > 0 && month <= 12 && day > 0 && day <= 31)
					return true;
				else {
					alertError.setContentText("Date Out of Range! :(\n");
					alertError.showAndWait();
				}

			} catch (NumberFormatException ex) {
				System.out.println(ex.getMessage());
			}
		} else {
			alertError.setContentText("Date missmatch format -> yyyy-mm-dd\n");
			alertError.showAndWait();
		}
		return false;
	}

	// To check if the exact employeeID exist since it is a primary key
	private static boolean isExist(String employeeID) {
		for (int i = 0; i < myList.size(); i++) {
			if (employeeID.compareTo(((Employee) myList.getAt(i)).getEmployeeID()) == 0)
				return true;
		}
		return false;
	}

	// To write employee's data on the file
	public static void WriteData() {
		try (PrintWriter output = new PrintWriter(new File("updatedEmployee.txt"))) {
			for (int i = 0; i < myList.size(); i++) {
				Employee employee = (Employee) myList.getAt(i);
				output.println(employee.getEmployeeID() + "," + employee.getName() + "," + employee.getAge() + ","
						+ employee.getDepartment() + "," + employee.getDepartment() + "," + employee.getDateOfJoining()
						+ "," + employee.getGender());
			}
			System.out.println("Data uploaded Successfully! :)");
		} catch (IOException ex) {
			ex.toString();
		}

	}

	// To read employee's data from the selected file (employee.txt)
	public static void loadFile() {
		// Check if the file exist
		if (selectedFile == null) {
			System.out.println("no such file selected :(");
			return;
		}

		if (selectedFile.exists()) {
			try (Scanner input = new Scanner(selectedFile)) {
				while (input.hasNext()) {
					Employee employee = new Employee(input.nextLine());
					if (!isExist(employee.getEmployeeID())) {
						myList.insert(employee);
						table.getItems().add(employee);
					}
				}
				lblNumOfEmp.setText("Data downloaded Successfully! :)");

			} catch (IOException ex) {
				alertError.setContentText(ex.toString());
			}
		} else
			lblNumOfEmp.setText("Oopps! file doesn't exist :(");
	}

}