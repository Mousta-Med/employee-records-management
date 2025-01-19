package com.med;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.med.model.Employee;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class RestClient {
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final String BASE_URL = "http://localhost:8080/api/v1/employee";

    // Fetch all employees
    public List<Employee> getAllEmployees() throws IOException {
        Request request = new Request.Builder().url(BASE_URL).build();
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String jsonResponse = response.body().string();
            Type employeeListType = new TypeToken<List<Employee>>() {}.getType();
            return gson.fromJson(jsonResponse, employeeListType);
        }
    }

    public Employee addEmployee(Employee employee) throws IOException, ParseException {
        // Convert the employee object to JSON
        String json = gson.toJson(employee);

        // Parse the JSON string into a JsonObject
        JsonObject jsonObject = gson.fromJson(json, JsonObject.class);

        // Check and modify the hireDate field
        if (jsonObject.has("hireDate")) {
            String originalHireDate = jsonObject.get("hireDate").getAsString();

            // Format the hireDate to ISO 8601
            SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a");
            SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            String formattedHireDate = isoDateFormat.format(inputFormat.parse(originalHireDate));

            // Update the hireDate in the JSON object
            jsonObject.addProperty("hireDate", formattedHireDate);
        }

        // Convert the updated JsonObject back to a JSON string
        String updatedJson = gson.toJson(jsonObject);

        // Prepare the HTTP request
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(updatedJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        // Execute the request and handle the response
        try (Response response = client.newCall(request).execute()) {
            assert response.body() != null;
            String jsonResponse = response.body().string();
            return gson.fromJson(jsonResponse, Employee.class);
        }
    }

}