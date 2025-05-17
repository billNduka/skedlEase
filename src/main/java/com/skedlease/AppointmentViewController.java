package com.skedlease;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppointmentViewController 
{
    private JSONArray allAppointments;
    private Integer[] noOfAppointments = new Integer[30];
    @FXML private Label[] dateLabels = new Label[30];
    @FXML private Label[] countLabels = new Label[30];
    @FXML private GridPane root;

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = today.format(formatter);



    @FXML 
    public void initialize() {
        for (int i = 0; i < 30; i++) 
        {
            dateLabels[i] = (Label) root.lookup("#date" + (i + 1));
            countLabels[i] = (Label) root.lookup("#count" + (i + 1));
            noOfAppointments[i] = 0; 
        }

        loadAppointments();
        if (allAppointments != null) 
        {
            fillAppointmentView(allAppointments);
        }
    }

    public void loadAppointments()
    {
        try
        {
            String token = App.getCSRFToken();
            OkHttpClient client = App.httpClient;

            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/appointments/")
                .addHeader("Content-Type", "application/json")
                .addHeader("Referer", "https://skedlease.onrender.com/")
                .addHeader("X-CSRFToken", token)
                .get()
                .build();

            Call call = client.newCall(request);
            Response response = call.execute();
            String responseBody = response.body().string();
            if(response.isSuccessful())
            {
                allAppointments = new JSONArray(responseBody);
                App.allAppointments = allAppointments;
            }

        } 
        catch (IOException | org.json.JSONException ex) 
        {
            ex.printStackTrace();
        }
    }

    public void fillAppointmentView(JSONArray appointments)
    {

        for (int i = 0; i < 30; i++) 
        {
            LocalDate date = today.plusDays(i);
            dateLabels[i].setText(date.format(formatter));
        }
        
        for (int i = 0; i < appointments.length(); i++) 
        {
            JSONObject appointment = appointments.getJSONObject(i);
            String appointmentDateStr = appointment.getJSONObject("availability_slot").getString("date");
            for (int j = 0; j < 30; j++) 
            {
                LocalDate date = today.plusDays(j);
                String targetDateStr = date.format(formatter);

                if (appointmentDateStr.equals(targetDateStr)) 
                {
                    noOfAppointments[j]++;
                    break;
                }
            }
        }

        for (int i = 0; i < 30; i++) 
        {
            countLabels[i].setText(noOfAppointments[i].toString());
        }

    }


    @FXML
    private void handleDayClick(MouseEvent event) {
        VBox clickedBox = (VBox) event.getSource();
        Label dateLabel = (Label) clickedBox.getChildren().get(0); 
        String date = dateLabel.getText();

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Day Appointments.fxml"));
            Parent root = loader.load();
            DayAppointmentsController controller = loader.getController();
            controller.setDate(date);

            Stage stage = (Stage) clickedBox.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}