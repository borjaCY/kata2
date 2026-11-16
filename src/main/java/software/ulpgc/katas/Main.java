package software.ulpgc.katas;

import software.ulpgc.katas.io.EmployeeReader;
import software.ulpgc.katas.io.UrlEmployeeReader;
import software.ulpgc.katas.model.Employee;
import software.ulpgc.katas.model.MaritalStatus;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*
Entrada desde Recurso y salida en Fichero
 */
public class Main {
    static void main() throws IOException {
        URL url = Main.class.getResource("/employees.tsv");
        EmployeeReader reader = new UrlEmployeeReader(url);
        List<Employee>  employees = reader.readEmployees();
        Map<MaritalStatus, Long> result_map = employees.stream().filter(e -> e.maritalStatus() != null)
                .collect(Collectors.groupingBy(Employee::maritalStatus, Collectors.counting()));
        try(OutputStream out = new FileOutputStream("resultado.txt")) {
            String resultado = "Total empleados: " + employees.size() + "\n" +
            "Por estado civil: " + result_map + "\n";
            out.write(resultado.getBytes(StandardCharsets.UTF_8));
        }


    }
}
