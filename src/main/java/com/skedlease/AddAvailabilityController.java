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

public class AddAvailabilityController 
{
    public String date;
    public List<String> doctorsList = new ArrayList<>();
    public JSONObject selectedDoctor;
    @FXML
    private TextField startTime;
    @FXML
    private TextField endTime;
    @FXML
    private ComboBox<String> doctorsBox;
    @FXML
    private Label headerLabel;


    public void setDate(String date) 
    {
        this.date = date;
        headerLabel.setText("Add Availability for " + date);
        fillComboBox(App.allDoctors);
    }

    public void fillComboBox(JSONArray doctors)
    {
        for (int i = 0; i < doctors.length(); i++) 
        {
            JSONObject doctor = doctors.getJSONObject(i);
            String name = doctor.getJSONObject("user").getString("first_name") 
                + " " + doctor.getJSONObject("user").getString("last_name");
            doctorsList.add(name);
        }
        doctorsBox.getItems().addAll(doctorsList);
    }

    @FXML
    public void pickDoctor()
    {
        String selectedName = doctorsBox.getValue();
        for (int i = 0; i < App.allDoctors.length(); i++) 
        {
            JSONObject doctor = App.allDoctors.getJSONObject(i);
            String name = doctor.getJSONObject("user").getString("first_name") 
                + " " + doctor.getJSONObject("user").getString("last_name");
            if (name.equals(selectedName)) 
            {
                selectedDoctor = doctor;
                break;
            }
        }
    }

    public void addAvailability() 
    {
        try
        {
            String token = App.getCSRFToken();
            OkHttpClient client = App.httpClient;

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("doctor_id", selectedDoctor.getInt("id"));
            jsonRequest.put("date", date);
            jsonRequest.put("start_time", startTime.getText());
            jsonRequest.put("end_time", endTime.getText());

            MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
            RequestBody requestBody = RequestBody.create(jsonRequest.toString(), JSON_TYPE);

            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/add-slot/")
                .addHeader("Content-Type", "application/json")
                .addHeader("X-CSRFToken", token)
                .addHeader("Referer", "https://skedlease.onrender.com/")
                .post(requestBody)
                .build();

            Call call = client.newCall(request);
            Response response = call.execute();

            if (response.isSuccessful()) 
            {
                back();
            }else 
            {
                System.out.println("Status: " + response.code());
                String responseText = response.body().string();
                System.out.println("Response: " + responseText);
            }

        }catch(IOException | org.json.JSONException e)
        {
            e.printStackTrace();
        }
    }   

    @FXML
    public void back()
    {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Availability View.fxml"));
            Parent root = loader.load();
            doctorsBox.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}