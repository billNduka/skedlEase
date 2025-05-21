package com.skedlease;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.scene.Parent;

public class DayAvailabilityController 
{
    public String date;

    @FXML
    private VBox availabilityListContainer;

    @FXML
    private Label headerLabel;

    public void setDate(String date) 
    {
        this.date = date;
        displayAvailablity(App.allAvailable);
    }

    public void displayAvailablity(JSONArray allAvailable)
    {
        headerLabel.setText("All Available for " + date);
        availabilityListContainer.getChildren().clear();

        for (int i = 0; i < allAvailable.length(); i++) 
        {
            JSONObject availability = allAvailable.getJSONObject(i);
            String availabilityDate = availability.getString("date");
            String doctorName = availability.getJSONObject("doctor").getJSONObject("user").getString("first_name") 
                + " " + availability.getJSONObject("doctor").getJSONObject("user").getString("last_name");

            if (availabilityDate.equals(date)) 
            {
                try 
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Availability Card.fxml"));
                    Node cardNode = loader.load();

                    AvailabilityCardController controller = loader.getController();
                    controller.setCardData(
                        doctorName,
                        availabilityDate,
                        availability.getString("start_time"),
                        availability.getString("end_time")
                    );

                    availabilityListContainer.getChildren().add(cardNode);
                } catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
        }

    }

    @FXML
    public void back() 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Availability View.fxml"));
            Parent root = loader.load();
            javafx.stage.Stage stage = (javafx.stage.Stage) availabilityListContainer.getScene().getWindow();
            stage.setScene(new javafx.scene.Scene(root));
            stage.show();
        } catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

}
