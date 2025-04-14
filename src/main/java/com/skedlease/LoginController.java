package com.skedlease;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;
//import java.util.HexFormat;

import org.json.JSONObject;

public class LoginController {

    @FXML
    private TextField username;
    
    @FXML
    private PasswordField password;

    
public void submit(ActionEvent e) {
    String token = App.getCSRFToken();  // CSRF token from your App.java
    OkHttpClient client = new OkHttpClient();

    try {
        // Create JSON object for login
        JSONObject json = new JSONObject();
        json.put("username", username.getText());  // Note: the API expects "username" as key
        json.put("password", password.getText());  // spelling fixed: "password"

        MediaType JSON_TYPE = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(json.toString(), JSON_TYPE);

        Request request = new Request.Builder()
            .url("https://skedlease.onrender.com/user/login/")
            .addHeader("Content-Type", "application/json")
            .addHeader("X-CSRFToken", token)
            .post(body)
            .build();

        Call call = client.newCall(request);
        Response response = call.execute();

        System.out.println("Status: " + response.code());

        String responseText = response.body().string();
        System.out.println("Response: " + responseText);

        if (response.code() == 200) {
            // Successful login logic here
        } else {
            // Handle incorrect credentials or other issues
        }

    } catch (IOException | org.json.JSONException ex) {
        ex.printStackTrace();
    }
}

    public void register(ActionEvent e) throws IOException
    {
        App.setRoot("register");
    }

}
