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
import javafx.scene.control.ComboBox;
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
    public void initialize()
    {
        specializations.getItems().addAll(getSpecialities());
        
    }

    private List<String> getSpecialities()
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

    // private void register()
    // {
    //     String token = App.getCSRFToken();
    //     OkHttpClient client = App.httpClient;

    //     try {
    //         JSONObject json = new JSONObject();
    //         json.put("username", username.getText()); 
    //         json.put("password", password.getText());  

    //         MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
    //         RequestBody body = RequestBody.create(json.toString(), JSON_TYPE);

    //         Request request = new Request.Builder()
    //             .url("https://skedlease.onrender.com/user/login/")
    //             .addHeader("Content-Type", "application/json")
    //             .addHeader("X-CSRFToken", token)
    //             .addHeader("Referer", "https://skedlease.onrender.com/")
    //             .post(body)
    //             .build();

    //         Call call = client.newCall(request);
    //         Response response = call.execute();

    //         if (response.isSuccessful()) 
    //         {

    //         }    

    //     } catch (IOException | org.json.JSONException ex) {
    //         ex.printStackTrace();
    //     }
    // }
}
