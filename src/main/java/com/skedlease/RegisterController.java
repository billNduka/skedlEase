package com.skedlease;

import java.io.IOException;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.Response;


public class RegisterController
{
    @FXML
    private TextField email;
    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField matricNumber;
    @FXML
    private PasswordField password1;
    @FXML
    private PasswordField password2;

    private Alert successAlert;
    private Alert existsAlert;
    private Alert failureAlert;

    public void createAlerts()
    {
        successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Registration status");
        successAlert.setHeaderText("Registration status");
        successAlert.setContentText("Registration was successful");

        existsAlert = new Alert(Alert.AlertType.INFORMATION);
        existsAlert.setTitle("Registration status");
        existsAlert.setHeaderText("Registration status");
        existsAlert.setContentText("The user with this email address already exists");

        failureAlert = new Alert(Alert.AlertType.ERROR);
        failureAlert.setTitle("Registration status");
        failureAlert.setHeaderText("Registration status");
        failureAlert.setContentText("Registration was unsuccessful");
    }
    public void submit(ActionEvent e) throws IOException
    {
        System.out.println("submitting...");
        createAlerts();

        String token = App.getCSRFToken();

        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("email", email.getText());
        json.put("first_name", firstName.getText());
        json.put("last_name", lastName.getText());
        json.put("phone_number", phoneNumber.getText());
        json.put("user_role", "patient");
        json.put("matric_number", matricNumber.getText());
        json.put("password", password1.getText());
        json.put("password2", password2.getText());

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody formBody = RequestBody.create(json.toString(), JSON);

        try
        {
            Request request = new Request.Builder()
                .url("https://skedlease.onrender.com/user/signup/")
                .addHeader("Content-type", "application/json")
                .addHeader("X-CSRFToken", token)
                .post(formBody)
                .build();
            Call call = client.newCall(request);
            Response response = call.execute();

            if(response.code() == 201)
            {
                successAlert.show();
                App.setRoot("primary");
            } else if(response.code() == 400) 
            {
                existsAlert.showAndWait();
            } else
            {
                failureAlert.showAndWait();
                System.out.println(response.code());
            }

        } catch(IOException e1)
        {
            e1.printStackTrace();
        }
    } 
}