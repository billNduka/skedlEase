package com.skedlease;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;
import java.io.IOException;

public class AdminController {
    @FXML
    private VBox doctorListContainer;

    private JSONArray allDoctors;
    private List<String> fullNames = new ArrayList<>();
    private List<String> doctorSpecialties = new ArrayList<>();
    

    @FXML
    public void initialize() 
    {
        loadDoctors();
        loadDoctorCard(fullNames, doctorSpecialties);
    }

    public void loadDoctors()
    {
        try
        {
            String token = App.getCSRFToken();
            OkHttpClient client = App.httpClient;

            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/doctors/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://skedlease.onrender.com/")
                .addHeader("X-CSRFToken", token)
                .get()
                .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            String responseBody = response.body().string();

            if (response.isSuccessful()) 
            {   
                allDoctors = new JSONArray(responseBody);

                for(int i =  0; i < allDoctors.length(); i++)
                {
                    JSONObject doctor = allDoctors.getJSONObject(i);
                    JSONObject user = doctor.getJSONObject("user");

                    String fullName = user.getString("first_name") + " " + user.getString("last_name");
                    fullNames.add(fullName);


                    JSONArray specialitiesArray = doctor.getJSONArray("specialities");
                    StringBuilder specialities = new StringBuilder();
                    for (int j = 0; j < specialitiesArray.length(); j++) 
                    {
                        switch (specialitiesArray.getInt(j)) 
                        {
                            case 1:
                                specialities.append("cardiology");
                                break;
                            case 2:
                                specialities.append("General Medicine");
                                break;
                            case 3:
                                specialities.append("Dentistry");
                                break;
                            case 4:
                                specialities.append("Dermatology");
                                break;
                            case 5:
                                specialities.append("Oncology");
                                break;
                            case 6:
                                specialities.append("Obstetrician-Gynecologist");
                                break;
                            case 7:
                                specialities.append("Nephrology");
                                break;
                            case 8:
                                specialities.append("Internal Medicine");
                                break;
                            case 9:
                                specialities.append("Neurology");
                                break;
                            default:
                                specialities.append("");
                            break;
                        }

                        if (j < specialitiesArray.length() - 1) 
                        {
                            specialities.append(", ");
                        }
                    }
                    doctorSpecialties.add(specialities.toString());
                    

                }

                System.out.println("Response: " + responseBody);

            } else {
                System.out.println("Response: " + responseBody);
            }

        } catch (IOException | org.json.JSONException ex) {
            ex.printStackTrace();
        }
    }

    public void loadDoctorCard(List<String> fullNames, List<String> doctorSpecialities)
    {
        try
        {
            for (int i = 0; i < fullNames.size(); i++) 
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Doctor Card.fxml"));
                Node card = loader.load();
                DoctorCardController doctorCardController = loader.getController();
            
                doctorCardController.setDoctorData(fullNames.get(i), doctorSpecialities.get(i));            
                doctorListContainer.getChildren().add(card);
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public void addDoctor()
    {
        try
        {
            //App.setRoot("Register Doctor");
            App.setRoot("Appointment View");
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
