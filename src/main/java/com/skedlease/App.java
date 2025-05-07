package com.skedlease;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.io.IOException;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;


public class App extends Application {

    private static Scene scene;
    public static String sessionCookie;
    public static String csrfCookie;
    public static String token;
    public static SimpleCookieJar cookieJar = new SimpleCookieJar();
    public static OkHttpClient httpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                    .writeTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                    .readTimeout(20, java.util.concurrent.TimeUnit.SECONDS)
                    .cookieJar(cookieJar)
                    .build();

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



    public static String getCSRFToken() {
        OkHttpClient client = httpClient;
        Request request = new Request.Builder()
            .url("https://skedlease.onrender.com/user/get_csrf/")
            .build();
        try {
            Call call = client.newCall(request);
            Response response = call.execute();
            if (response.code() == 200) {
                String responseBody = response.body().string();
                JSONObject json = new JSONObject(responseBody);
                return json.getString("csrf_token"); // Adjust if your backend key is different
            } else {
                return Integer.toString(response.code());
            }
        } catch(IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}