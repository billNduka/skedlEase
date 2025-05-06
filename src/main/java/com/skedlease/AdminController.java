package com.skedlease;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import java.io.IOException;

public class AdminController {
    @FXML
    private VBox doctorListContainer;


    @FXML
    public void initialize() 
    {
        loadDoctorCard();
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
