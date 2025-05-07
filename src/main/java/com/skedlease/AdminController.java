package com.skedlease;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import org.json.JSONObject;

import java.io.IOException;

public class AdminController {
    @FXML
    private VBox doctorListContainer;

    public String cookie;
    public String csrfCookie;

    @FXML
    public void initialize() 
    {
        loadDoctorCard();
        loadDoctors();
    }

    public void loadDoctors()
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent root = loader.load();
            LoginController loginController = loader.getController();

            String token = App.getCSRFToken();
            OkHttpClient client = App.httpClient;

            JSONObject requestDoctors = new JSONObject(); 

            MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
            //RequestBody requestBody = RequestBody.create(requestDoctors.toString(), JSON_TYPE);

            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/doctors/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://skedlease.onrender.com/")
                // .addHeader("Cookie", "csrftoken=" + csrfCookie + "; " + cookie)
                .addHeader("X-CSRFToken", token)
                .get()
                .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            String responseBody = response.body().string();

            if (response.isSuccessful()) {
                
                //JSONObject responseJSON = new JSONObject(responseBody);
                   
                System.out.println("Response: " + responseBody);

            } else {
                System.out.println("Response: " + responseBody);
            }

        } catch (IOException | org.json.JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void loadDoctorCard()
    {
        try
        {
            for (int i = 0; i < 3; i++) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Doctor Card.fxml"));
                Node card = loader.load();
                DoctorCardController doctorCardController = loader.getController();
            
                switch (i) {
                    case 0:
                        doctorCardController.setDoctorData("John Medicine", "dentistry");
                        break;
                    case 1:
                        doctorCardController.setDoctorData("Yuri Lowenthal", "pediatrics");
                        break;
                    case 2:
                        doctorCardController.setDoctorData("Dave Nduka", "surgery");
                        break;
                }
            
                doctorListContainer.getChildren().add(card);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
