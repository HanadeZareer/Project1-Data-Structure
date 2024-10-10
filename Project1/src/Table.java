import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.CharacterStringConverter;
import javafx.util.converter.IntegerStringConverter;

public class Table<T> extends TableView<T>{
	
	// The columns of the TableView
	private TableColumn<T, String> columnEmployeeId;
	private TableColumn<T, String> columnName;
	private TableColumn<T, Integer> columnAge;
	private TableColumn<T, String> columnDep;
	private TableColumn<T, String> columnDateOJ;
	private TableColumn<T, Character> columnGender;
	
	// No-arg constructor calling the body of the TableView
	public Table() {
		buildTable();
	}

	// Creat the body of the TableView 
	private void buildTable() {
		
		// To resize the column of the TableView
		this.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		this.setEditable(true);
		
		columnEmployeeId = new TableColumn<>("employee ID");
		columnEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeID"));
		columnEmployeeId.setCellFactory(TextFieldTableCell.forTableColumn());
		
		columnName = new TableColumn<>("name");
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnName.setCellFactory(TextFieldTableCell.forTableColumn());
		
		columnAge = new TableColumn<>("age");
		columnAge.setCellValueFactory(new PropertyValueFactory<>("age"));
		columnAge.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
		
		columnDep = new TableColumn<>("department");
		columnDep.setCellValueFactory(new PropertyValueFactory<>("department"));
		columnDep.setCellFactory(TextFieldTableCell.forTableColumn());
		
		columnDateOJ = new TableColumn<>("date of joining");
		columnDateOJ.setCellValueFactory(new PropertyValueFactory<>("dateOfJoining"));
		columnDateOJ.setCellFactory(TextFieldTableCell.forTableColumn());
		
		columnGender = new TableColumn<>("gender");
		columnGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
		columnGender.setCellFactory(TextFieldTableCell.forTableColumn(new CharacterStringConverter()));
		
		// Add the columns to the TableView
		getColumns().addAll(columnEmployeeId, columnName, columnAge, columnDep, columnDateOJ, columnGender);
		
		// For styling 
		this.setStyle("-fx-size: 30");
	}
}