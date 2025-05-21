package com.skedlease;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import java.io.IOException;


public class EditDoctorController {
    @FXML
    private TextField emailAddress;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private ComboBox<String> specializations;
    public Alert registerSuccessAlert;
    


    @FXML
    public void initialize()
    {
        
    }

    public void fillDefaultValues(JSONArray allDoctors, String doctorName)
    {
        
        for(int i =  0; i < allDoctors.length(); i++)
        {
            JSONObject doctor = allDoctors.getJSONObject(i);
            JSONObject user = doctor.getJSONObject("user");
            if((user.getString("first_name") + " " + user.getString("last_name")).equals(doctorName))
            {
                emailAddress.setText(user.getString("email"));
                firstName.setText(user.getString("first_name"));
                lastName.setText(user.getString("last_name"));
                phoneNumber.setText(user.getString("phone_number"));          
            }
        }
    }

}
