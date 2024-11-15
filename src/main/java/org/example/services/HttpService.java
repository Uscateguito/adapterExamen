package org.example.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.models.Employee;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class HttpService implements EmployeeService {
    private static final String API_URL = System.getenv("API_URL");
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper;

    public HttpService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }


    private String fetchEmployee(String endpoint) throws java.io.IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Failed to get employee: " + response.statusCode());
        }
    }
    
    @Override
    public List<Employee> getEmployees() throws Exception {
        System.out.println("API_URL: " + API_URL);
        String endpoint = API_URL + "/Employees";
        String response = fetchEmployee(endpoint);
        return  objectMapper.readValue(response, objectMapper.getTypeFactory().constructCollectionType(List.class, Employee.class));
    }
    
    @Override
    public Employee getEmployeeById(int id) throws Exception {
        String endpoint = API_URL + "/Employees/" + id;
        String response = fetchEmployee(endpoint);
        return objectMapper.readValue(response, Employee.class);
    }
}


