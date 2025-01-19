package com.med;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.med.model.Employee;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class RestClient {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final String BASE_URL = "http://backend:8080/api/v1/employee";

    // Fetch all employees
    public List<Employee> getAllEmployees() throws IOException {
        Request request = new Request.Builder().url(BASE_URL).build();
        try (Response response = client.newCall(request).execute()) {
            String jsonResponse = response.body().string();
            Type employeeListType = new TypeToken<List<Employee>>() {}.getType();
            return gson.fromJson(jsonResponse, employeeListType);
        }
    }

    // Add a new employee
    public Employee addEmployee(Employee employee) throws IOException {
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        String json = gson.toJson(employee);

        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String jsonResponse = response.body().string();
            return gson.fromJson(jsonResponse, Employee.class);
        }
    }
}