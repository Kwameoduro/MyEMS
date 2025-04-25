package services;

import java.util.Comparator;

public class EmployeeComparators {
    //Comparator to sort employees by salary in descending order
    public static class EmployeeSalaryComparator<T> implements Comparator<model.Employee<T>> {
        @Override
        public int compare(model.Employee<T> e1, model.Employee<T> e2){
            if(e1 == null || e2 == null){
                throw new IllegalArgumentException("Cannot compare null employees");
            }
            return Double.compare(e2.getSalary(), e1.getSalary());
        }
    }

    //Comparator to sort employees by performance rating in descending order
    public static class EmployeePerformanceComparator<T> implements Comparator<model.Employee<T>>{
        @Override
        public int compare(model.Employee<T> e1, model.Employee<T> e2){
            return Double.compare(e2.getPerformanceRating(), e1.getPerformanceRating());
        }
    }

    //Comparator to sort employees by years of experience
    public static class EmployeeExperienceComparator<T> implements Comparator<model.Employee<T>>{
        @Override
        public int compare(model.Employee<T> e1, model.Employee<T> e2){
            return Integer.compare(e2.getYearsOfExperience(), e1.getYearsOfExperience());
        }
    }
}

