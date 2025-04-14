package com.skedlease;

import java.io.IOException;

import org.json.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.FormBody;
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
    private PasswordField password1;
    @FXML
    private PasswordField password2;

    public void submit(ActionEvent e) throws IOException
    {
        String token = App.getCSRFToken();

        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        json.put("email", email.getText());
        json.put("first_name", firstName.getText());
        json.put("last_name", lastName.getText());
        json.put("phone_number", phoneNumber.getText());
        json.put("user_role", "patient");
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
                System.out.println(response.body().string());
            } else
            {
                System.out.println(response.code());
            }
            System.out.println(response.body().string());

        } catch(IOException e1)
        {
            e1.printStackTrace();
        }
    } 
}