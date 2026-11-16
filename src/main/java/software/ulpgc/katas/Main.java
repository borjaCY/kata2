package software.ulpgc.katas;

import software.ulpgc.katas.io.EmployeeReader;
import software.ulpgc.katas.io.UrlEmployeeReader;
import software.ulpgc.katas.model.Employee;

import java.io.IOException;
import java.net.URL;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() throws IOException {
        URL url = new URL("https://raw.githubusercontent.com/sachin365123/CSV-files-for-Data-Science-and-Machine-Learning/refs/heads/main/Employee%20Dataset%20-%20TSV.txt");
        EmployeeReader reader = new UrlEmployeeReader(url);
        List<Employee> employees = reader.readEmployees();
        for (Employee employee : employees) {
            System.out.println(employee);
        }

    }
}
