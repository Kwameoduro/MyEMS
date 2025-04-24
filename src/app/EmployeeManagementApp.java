package ui;

import database.EmployeeDatabase;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Employee;
import model.EmployeeComparators;
import model.EmployeeSalaryManager;
import model.EmployeeSearch;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;


import java.util.ArrayList;
import java.util.List;


//Main JavaFX Application for managing employees
public class EmployeeManagementApp extends Application {

    private EmployeeDatabase<String> database = new EmployeeDatabase<>();
    private EmployeeSearch<String> searchManager;
    private TableView<Employee<String>> employeeTable = new TableView<>();
    private EmployeeSalaryManager<String> salaryManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("My Employee Management System");

        searchManager = new EmployeeSearch<>(database);
        salaryManager = new EmployeeSalaryManager<>(database);

        //Layout Setup
        BorderPane root = new BorderPane();
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(10));

        //Input Fields
        // Input Fields with Validation
        HBox inputFields = new HBox(10);

// Name - Letters and space only
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[a-zA-Z ]*") ? change : null
        ));

// Department - Letters and space only
        TextField departmentField = new TextField();
        departmentField.setPromptText("Department");
        departmentField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("[a-zA-Z ]*") ? change : null
        ));

// Salary - Numbers and optional decimal
        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary GHC");
        salaryField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d*(\\.\\d{0,2})?") ? change : null
        ));

// Rating - Numbers and optional decimal
        TextField ratingField = new TextField();
        ratingField.setPromptText("Performance Rating");
        ratingField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d*(\\.\\d{0,2})?") ? change : null
        ));

// Experience - Integer only
        TextField experienceField = new TextField();
        experienceField.setPromptText("Experience (In Years)");
        experienceField.setTextFormatter(new TextFormatter<>(change ->
                change.getControlNewText().matches("\\d*") ? change : null
        ));

        inputFields.getChildren().addAll(nameField, departmentField, salaryField, ratingField, experienceField);

        //Dropdown menu and run button
        ComboBox<String> actionDropdown = new ComboBox<>();
        actionDropdown.getItems().addAll(
                "Add Employee",
                "Remove Employee",
                "Search by Department",
                "Search by Name",
                "Sort by Salary",
                "Sort by Performance Rating",
                "Sort by Years of Experience",
                "Give Salary Raise",
                "Top 5 Highest-Paid",
                "Average Salary by Department"
        );
        actionDropdown.setPromptText("Select Action");

        Button runButton = new Button("Run");
        runButton.setStyle("-fx-background-color: #13cfa9; -fx-text-fill: white; -fx-font-weight: bold;");



        //Event Handlers
        runButton.setOnAction(e -> {
            String selectedAction = actionDropdown.getValue();
            if(selectedAction == null){
                showAlert("Error", "Kindly select an action from the dropdown");
                return;
            }
            switch (selectedAction){
                case "Add Employee":
                    try{
                        String name = nameField.getText();
                        String department = departmentField.getText();
                        double performanceRating = Double.parseDouble(ratingField.getText());
                        double salary = Double.parseDouble(salaryField.getText());
                        int yearsOfExperience = Integer.parseInt(experienceField.getText());
                        //System.out.println("Years of Experience: " + yearsOfExperience);


                        if(performanceRating < 0 || performanceRating > 5){
                            showAlert("Try Again", "Performance rating is between 0.0 and 5.0");
                            return;
                        }

                        if(salary < 0){
                            showAlert("Try again", "Salary must be a positive number.");
                            return;
                        }

                        String newEmployeeId = String.format("%03d",database.getAllEmployees().size() +1);
                        //String formattedId = String.format("%03d", newEmployeeId);
                        Employee<String> employee = new Employee<>(newEmployeeId, name, department, salary, performanceRating, yearsOfExperience, true);
                        //System.out.println("Employee Data: " + employee);
                        database.addEmployee(employee);
                        updateEmployeeTable();
                        showAlert("Success", "Employee added successfully");
                    } catch (NumberFormatException ex){
                        showAlert("Error", "Please enter valid numeric details");
                    }
                    break;

                case "Search by Name":
                    if (database.getAllEmployees().isEmpty()) {
                        showAlert("Error", "The database is empty. Please add employees before searching.");
                        return;
                    }
                    String name = nameField.getText().trim(); // Use nameField to input the name
                    List<Employee<String>> employeesByName = (List<Employee<String>>) searchManager.searchByName(name);
                    employeeTable.getItems().clear();
                    employeeTable.getItems().addAll(employeesByName);
                    showAlert("Success", "Employees successfully filtered by name.");
                    break;


                case "Remove Employee":
                    if(!database.getAllEmployees().isEmpty()){
                        database.getAllEmployees().keySet().stream().findFirst().ifPresent(database::removeEmployee);
                        updateEmployeeTable();
                        showAlert("Success", "Employee removed!");
                    } else {
                        showAlert("Error", "No employees to remove.");
                    }
                    break;

                case "Search by Department":
                    if (database.getAllEmployees().isEmpty()) {
                        showAlert("Error", "The database is empty. Please add employees before searching.");
                        return;
                    }
                    String department = departmentField.getText().trim();
                    if(department.isEmpty()){
                        showAlert("Error", "Please enter a valid department name");
                        return;
                    }
                    List<Employee<String>> employeesInDepartment = searchManager.searchByDepartment(department);
                    employeeTable.getItems().clear();
                    employeeTable.getItems().addAll(employeesInDepartment);
                    showAlert("Success", "Employees filtered by department");
                    break;

                case "Sort by Performance Rating":
                    if (database.getAllEmployees().isEmpty()) {
                        showAlert("Error", "The database is empty. Please add employees before sorting.");
                        return;
                    }
                    employeeTable.getItems().clear();
                    database.getAllEmployees().values().stream().sorted(new EmployeeComparators.EmployeePerformanceComparator<>()).forEach(employeeTable.getItems()::add);
                    showAlert("Success", "Employees sorted by performance rating!");
                    break;

                case "Sort by Years of Experience":
                    if (database.getAllEmployees().isEmpty()) {
                        showAlert("Error", "The database is empty. Please add employees before sorting.");
                        return;
                    }
                    employeeTable.getItems().clear();
                    database.getAllEmployees().values().stream().sorted(new EmployeeComparators.EmployeeExperienceComparator<>()).forEach(employeeTable.getItems()::add);
                    showAlert("Success", "Employees sorted by years of experience!");
                    break;

                case "Sort by Salary":
                    if (database.getAllEmployees().isEmpty()) {
                        showAlert("Error", "The database is empty. Please add employees before sorting.");
                        return;
                    }
                    List<Employee<String>> employeesBySalary = new ArrayList<>(database.getAllEmployees().values());
                    employeesBySalary.sort(new EmployeeComparators.EmployeeSalaryComparator<>());
                    employeeTable.getItems().clear();
                    employeeTable.getItems().addAll(employeesBySalary);
                    showAlert("Success", "Highly Paid Employees First!");
                    break;


                case "Give Salary Raise":
                    try {
                        double raisePercentage = Double.parseDouble(ratingField.getText().trim()); // Input raise percentage
                        salaryManager.giveSalaryRaise(4.5, raisePercentage); // Apply raise to high performers (threshold 4.5)
                        updateEmployeeTable(); // Refresh TableView
                        showAlert("Success", "Salary raise applied, Enjoy!");
                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Please enter a valid percentage for the salary raise.");
                    }
                    break;


                case "Top 5 Highest-Paid":
                    if (database.getAllEmployees().isEmpty()) {
                        showAlert("Error", "The database is empty. Please add employees before retrieving the top 5.");
                        return;
                    }
                    List<Employee<String>> topPaidEmployees = salaryManager.getTopPaidEmployees(5);
                    employeeTable.getItems().clear();
                    employeeTable.getItems().addAll(topPaidEmployees);
                    showAlert("Success", "Displayed top 5 highest-paid employees!");
                    break;



                case "Average Salary by Department":
                    department = departmentField.getText().trim();
                    if (department.isEmpty()) {
                        showAlert("Error", "Please enter a valid department name.");
                        return;
                    }
                    double averageSalary = salaryManager.calculateAverageSalary(department);
                    if (averageSalary == 0.0) {
                        showAlert("Info", "No employees found in the specified department.");
                    } else {
                        showAlert("Success", "Average salary in " + department + ": GHC" + averageSalary);
                    }
                    break;


                default:
                    showAlert("Error", "Invalid action selected");
                    break;
            }
        });


        //Align dropdown and button horizontally
        HBox actionBar = new HBox(10);
        actionBar.getChildren().addAll(actionDropdown, runButton);

        // Update Section (UI for Updating Employee Details)
        ComboBox<String> updateFieldDropdown = new ComboBox<>();
        updateFieldDropdown.getItems().addAll("Name", "Department", "Salary", "Performance Rating", "Years of Experience", "Active");
        updateFieldDropdown.setPromptText("Select Field to Update");

        TextField updateValueField = new TextField();
        updateValueField.setPromptText("Enter New Value");

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-background-color: #13cfa9; -fx-text-fill: white; -fx-font-weight: bold;");

        HBox updateSection = new HBox(10);
        updateSection.getChildren().addAll(updateFieldDropdown, updateValueField, updateButton);
        //Add components to main layout
        mainLayout.getChildren().addAll(actionBar, inputFields, employeeTable, updateSection);

        //Setup TableView for employee data
        setupEmployeeTable();

        // Event Handler for the Update Button
        updateButton.setOnAction(e -> {
            Employee<String> selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee == null) {
                showAlert("Error", "Please select an employee to update.");
                return;
            }

            String field = updateFieldDropdown.getValue();
            if (field == null) {
                showAlert("Error", "Please select a field to update.");
                return;
            }

            String newValue = updateValueField.getText().trim();
            if (newValue.isEmpty()) {
                showAlert("Error", "Please enter a new value.");
                return;
            }

            try {
                switch (field) {
                    case "Name":
                        database.updateEmployeeDetails(selectedEmployee.getEmployeeId(), "name", newValue);
                        break;
                    case "Department":
                        database.updateEmployeeDetails(selectedEmployee.getEmployeeId(), "department", newValue);
                        break;
                    case "Salary":
                        database.updateEmployeeDetails(selectedEmployee.getEmployeeId(), "salary", Double.parseDouble(newValue));
                        break;
                    case "Performance Rating":
                        database.updateEmployeeDetails(selectedEmployee.getEmployeeId(), "performanceRating", Double.parseDouble(newValue));
                        break;
                    case "Years of Experience":
                        database.updateEmployeeDetails(selectedEmployee.getEmployeeId(), "yearsOfExperience", Integer.parseInt(newValue));
                        break;
                    case "Active":
                        database.updateEmployeeDetails(selectedEmployee.getEmployeeId(), "isActive", Boolean.parseBoolean(newValue));
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid field selected.");
                }

                updateEmployeeTable();
                showAlert("Success", "Employee details updated successfully!");

            } catch (NumberFormatException ex) {
                showAlert("Error", "Invalid data format. Please try again.");
            } catch (IllegalArgumentException ex) {
                showAlert("Error", ex.getMessage());
            }
        });


        //set main layout in the center of the root pane
        root.setCenter(mainLayout);

        //Set Scene
        primaryStage.setScene(new Scene(root, 760, 510));
        primaryStage.show();

    }

    //Set up the TableView with columns for employee attributes
    private void setupEmployeeTable() {
        TableColumn<Employee<String>, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getEmployeeId()));
        idColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee<String>, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getName()));

        TableColumn<Employee<String>, String> departmentColumn = new TableColumn<>("Department");
        departmentColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getDepartment()));

        TableColumn<Employee<String>, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getSalary()));
        salaryColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee<String>, Double> performanceColumn = new TableColumn<>("Performance Rating");
        performanceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getPerformanceRating()));
        performanceColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Employee<String>, Integer> experienceColumn = new TableColumn<>("Years of Experience");
        experienceColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getYearsOfExperience()));
        experienceColumn.setStyle("-fx-alignment: CENTER;");

        // Customize column widths
        idColumn.setPrefWidth(80);
        nameColumn.setPrefWidth(170);
        departmentColumn.setPrefWidth(100);
        salaryColumn.setPrefWidth(100);
        performanceColumn.setPrefWidth(140);
        experienceColumn.setPrefWidth(150);

        employeeTable.getColumns().addAll(idColumn, nameColumn, departmentColumn, salaryColumn, performanceColumn, experienceColumn);
    }

    //Update the employee List in the UI
    private void updateEmployeeTable(){
        employeeTable.getItems().clear();
        employeeTable.getItems().addAll(database.getAllEmployees().values());
    }


    //Display Alert dialog
    private void showAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
