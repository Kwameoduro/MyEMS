package utils;

import model.Employee;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class EmployeeSorter<T> {

    public void sortEmployees(List<Employee<T>> employees) {
        employees.sort(Comparator.comparing((Employee<T> e) -> e.getDepartment())
                .thenComparing(Employee::getSalary));
    }

    public static <T> void mergeSort(List<Employee<T>> employees, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(employees, left, mid);
            mergeSort(employees, mid + 1, right);
            merge(employees, left, mid, right);
        }
    }

    private static <T> void merge(List<Employee<T>> employees, int left, int mid, int right) {
        List<Employee<T>> leftList = new ArrayList<>(employees.subList(left, mid + 1));
        List<Employee<T>> rightList = new ArrayList<>(employees.subList(mid + 1, right + 1));

        int i = 0, j = 0, k = left;
        while (i < leftList.size() && j < rightList.size()) {
            if (leftList.get(i).getSalary() <= rightList.get(j).getSalary()) {
                employees.set(k++, leftList.get(i++));
            } else {
                employees.set(k++, rightList.get(j++));
            }
        }
        while (i < leftList.size()) employees.set(k++, leftList.get(i++));
        while (j < rightList.size()) employees.set(k++, rightList.get(j++));
    }
}
