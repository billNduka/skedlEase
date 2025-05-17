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

public class DayAppointmentsController {

    public String date;

    @FXML
    private VBox appointmentListContainer;

    @FXML
    private Label headerLabel;

    public void setDate(String date) 
    {
        this.date = date;
        displayAppointments(App.allAppointments);
    }

    public void displayAppointments(JSONArray allAppointments) {
        // Set the header label to show the date
        headerLabel.setText("Appointments for " + date);

        appointmentListContainer.getChildren().clear();

        for (int i = 0; i < allAppointments.length(); i++) {
            JSONObject appointment = allAppointments.getJSONObject(i);
            System.out.println(appointment);
            JSONObject slot = appointment.getJSONObject("availability_slot");
            String appointmentDate = slot.getString("date");
            String doctorName = slot.getJSONObject("doctor").getJSONObject("user").getString("first_name") 
                + " " + slot.getJSONObject("doctor").getJSONObject("user").getString("last_name");

            if (appointmentDate.equals(date)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Appointment Card.fxml"));
                    Node cardNode = loader.load();

                    AppointmentCardController controller = loader.getController();
                    controller.setCardData(
                        doctorName,
                        "ID = " + String.valueOf(appointment.getJSONObject("patient").get("id")),
                        appointmentDate,
                        slot.getString("start_time"),
                        slot.getString("end_time"),
                        appointment.getString("status"),
                        appointment.getString("note")
                    );

                    appointmentListContainer.getChildren().add(cardNode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void back(ActionEvent event) 
    {
        // Handle back button navigation if needed
    }
}