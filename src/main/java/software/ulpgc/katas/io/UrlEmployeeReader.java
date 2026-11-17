package software.ulpgc.katas.io;

import software.ulpgc.katas.model.Employee;
import software.ulpgc.katas.model.MaritalStatus;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class UrlEmployeeReader implements EmployeeReader {
    private final URL url;
    public UrlEmployeeReader(URL url) {
        this.url = url;
    }
    @Override
    public List<Employee> readEmployees() {
        try{
            return loadFrom(url.openStream());
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    private List<Employee> loadFrom(InputStream inputStream) throws IOException {
        try(Reader reader = new InputStreamReader(inputStream)){
            return loadFrom(reader);
        }
    }

    private List<Employee> loadFrom(Reader reader) {
        return loadFrom(new BufferedReader(reader).lines().toList());
    }

    private List<Employee> loadFrom(List<String> lines) {
        List<Employee> employees = new ArrayList<>();
        for(int i=1; i<lines.size(); i++){
            employees.add(loadFrom(lines.get(i)));
        }
        return employees;
    }

    private Employee loadFrom(String line) {
        return loadFrom(line.split("\t"));
    }

    private Employee loadFrom(String[] fields) {
        return new Employee(fields[0], statusOf(fields[6]));
    }

    private MaritalStatus statusOf(String field) { return field.isEmpty() ? null : MaritalStatus.valueOf(field);}
}

