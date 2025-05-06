package com.skedlease;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DoctorCardController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label specializationLabel;

    public void setDoctorData(String name, String specialization)
    {
        nameLabel.setText(name);
        specializationLabel.setText(specialization != null ? specialization : "General");
    }

    public void delete()
    {
        
    }
}
