package com.skedlease;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.*;

public class AvailabilityCardController 
{
    @FXML
    public Label doctorLabel;
    @FXML
    public Label dateLabel;
    @FXML
    public Label timeLabel;

    public void setCardData(String doctor, String date, String startTime, String endTime)
    {
        doctorLabel.setText(doctor);
        dateLabel.setText(date);
        timeLabel.setText(startTime + " - " + endTime);
    }

    public void edit()
    {

    }

}
