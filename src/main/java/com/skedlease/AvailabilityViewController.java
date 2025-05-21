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


public class AvailabilityViewController 
{
    private JSONArray allAvailable;
    private Integer[] noAvailable = new Integer[30];
    @FXML private Label[] dateLabels = new Label[30];
    @FXML private Label[] countLabels = new Label[30];
    @FXML private GridPane root;

    LocalDate today = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String formattedDate = today.format(formatter);


    @FXML 
    public void initialize() 
    {
        for (int i = 0; i < 30; i++) 
        {
            dateLabels[i] = (Label) root.lookup("#date" + (i + 1));
            countLabels[i] = (Label) root.lookup("#count" + (i + 1));
            noAvailable[i] = 0; 
        }    
        loadAvailable();
        if (allAvailable != null) 
        {
            fillAvailabilityView(allAvailable);
        }
    }

    public void loadAvailable()
    {
        try
        {
            String token = App.getCSRFToken();
            OkHttpClient client = App.httpClient;

            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/slots")
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
                allAvailable = new JSONArray(responseBody);
                App.allAvailable = allAvailable;
            }
            else
            {
                System.out.println("Error: " + response.code());
            }
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    public void fillAvailabilityView(JSONArray allAvailable) 
    {
        for (int i = 0; i < 30; i++) 
        {
            LocalDate date = today.plusDays(i);
            dateLabels[i].setText(date.format(formatter));
        }

        for (int i = 0; i < allAvailable.length(); i++) 
        {
            JSONObject availability = allAvailable.getJSONObject(i);
            String availableDateStr = availability.getString("date");
            for (int j = 0; j < 30; j++) 
            {
                LocalDate date = today.plusDays(j);
                String targetDateStr = date.format(formatter);

                if (availableDateStr.equals(targetDateStr)) 
                {
                    noAvailable[j]++;
                    break;
                }
            }
        }

        for (int i = 0; i < 30; i++) 
        {
            countLabels[i].setText(noAvailable[i].toString());
        }

    }
    public void handleDayClick(MouseEvent event)
    {
        VBox clickedBox = (VBox) event.getSource();
        Label dateLabel = (Label) clickedBox.getChildren().get(0); 
        String date = dateLabel.getText();

        try
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Day Availabilities.fxml"));
            Parent root = loader.load();
            DayAvailabilityController controller = loader.getController();
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
