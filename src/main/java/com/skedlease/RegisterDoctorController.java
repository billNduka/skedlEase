package com.skedlease;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import java.io.IOException;


public class RegisterDoctorController {
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private ComboBox<String> specializations;
    @FXML
    private Label specialization1;
    @FXML
    private Label specialization2;
    public Alert registerSuccessAlert;
    public Alert specializationSuccessAlert;
    public String[] selectedSpecializations = new String[2];
    public List<String> specialtiesList;


    @FXML
    public void initialize()
    {
        specialtiesList = getSpecialities();
        specializations.getItems().addAll(specialtiesList);
        createAlerts();
    }

    public List<String> getSpecialities()
    {
        List<String> specializations = new ArrayList<>(); // Declare outside the try block
        try
        {
            String token = App.getCSRFToken();
            OkHttpClient client = App.httpClient;

            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/specialities/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://skedlease.onrender.com/")
                .addHeader("X-CSRFToken", token)
                .get()
                .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            if(response.isSuccessful())
            {
                String responseBody = response.body().string();
                JSONArray specialities = new JSONArray(responseBody);
                
                for (int i = 0; i < specialities.length(); i++) 
                {
                    JSONObject specialization = specialities.getJSONObject(i);
                    String name = specialization.getString("name");
                    specializations.add(name);
                }
            } 
            else
            {
                System.out.println(response.code() + " " + response.body().string());
            }
        }
        catch (IOException | org.json.JSONException ex) 
        {
            ex.printStackTrace();
        }
        return specializations; 
    }

    public void register()
    {
        String token = App.getCSRFToken();
        OkHttpClient client = App.httpClient;

        selectedSpecializations[0] = Integer.toString(specialtiesList.indexOf(specialization1.getText()));
        selectedSpecializations[1] = Integer.toString(specialtiesList.indexOf(specialization2.getText()));

        try {
            JSONObject json = new JSONObject();
            json.put("email", emailAddress.getText()); 
            json.put("first_name", firstName.getText());
            json.put("last_name", lastName.getText());
            json.put("phone_number", phoneNumber.getText());
            json.put("user_role", "doctor");
            json.put("specialities", selectedSpecializations);

            MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(json.toString(), JSON_TYPE);

            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/user/signup/")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-CSRFToken", token)
                .addHeader("Referer", "https://skedlease.onrender.com/")
                .post(body)
                .build();

            Call call = client.newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) 
            {
                registerSuccessAlert.show();
            }else 
            {
                System.out.println("Status: " + response.code());
                String responseText = response.body().string();
                System.out.println("Response: " + responseText);
            }

        } catch (IOException | org.json.JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void registerSpecialities()
    {
        
    }

    public void createAlerts()
    {
        registerSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
        registerSuccessAlert.setTitle("Registration status");
        registerSuccessAlert.setHeaderText("Registration status");
        registerSuccessAlert.setContentText("Registration successful");

        specializationSuccessAlert = new Alert(Alert.AlertType.INFORMATION);
        specializationSuccessAlert.setTitle("Specialty registration status");
        specializationSuccessAlert.setHeaderText("Specialty registration status");
        specializationSuccessAlert.setContentText("Specialty registration successful");

    }

    public void getSelectedSpecialization()
    {
        if(specialization1.getText().isEmpty())
        {
            specialization1.setText(specializations.getValue());

        }
        else
        {
            specialization2.setText(specializations.getValue());
        }
    }

}
