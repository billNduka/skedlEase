package com.skedlease;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.*;

public class AppointmentCardController 
{
    @FXML
    public Label doctorLabel;
    @FXML
    public Label patientLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Label timeLabel;
    @FXML
    public Label statusLabel;
    @FXML
    public Text note;

    public void setCardData(String doctor, String patient, String date, String startTime, String endTime, String status, String noteText)
    {
        doctorLabel.setText(doctor);
        patientLabel.setText(patient);
        dateLabel.setText(date);
        timeLabel.setText(startTime + " - " + endTime);
        statusLabel.setText(status);
        note.setText(noteText);
    }



    public void edit()
    {

    }

}