package services;
import java.util.List;
import java.util.Map;

//Displaying employees in a well formatted structure
public class EmployeeDisplay<T> {
    //Display all employees in the database using a for-each loop

    public void displayAllEmployees(Map<T, model.Employee<T>> employees){
        System.out.println("=====================================================================");
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-15s\n",
                "ID", "Name", "Department", "Salary", "Rating", "Experience");
        System.out.println("=====================================================================");

        for (model.Employee<T> employee : employees.values()){
            System.out.printf("%-10s %-20s %-15s %-10.2f %-10.2f %-15d\n",
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getSalary(),
                    employee.getPerformanceRating(),
                    employee.getYearsOfExperience()
            );
        }
        System.out.println("=====================================================================");
    }

    //Report of selected employees using a stream
    public void displaySelectedEmployees(List<model.Employee<T>> employees){
        System.out.println("=====================================================================");
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-15s\n",
                "ID", "Name", "Department", "Salary", "Rating", "Experience");
        System.out.println("=====================================================================");

        employees.stream().forEach(employee -> {
            System.out.printf("%-10s %-20s %-15s %-10.2f %-10.2f %-15d\n",
                    employee.getEmployeeId(),
                    employee.getName(),
                    employee.getDepartment(),
                    employee.getSalary(),
                    employee.getPerformanceRating(),
                    employee.getYearsOfExperience()
            );
        });
        System.out.println("=====================================================================");
    }
}
