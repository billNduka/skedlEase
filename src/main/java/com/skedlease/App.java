package com.skedlease;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static String getCSRFToken()
    {
        OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            .writeTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            .readTimeout(15, java.util.concurrent.TimeUnit.SECONDS)
            .build();

        Request request = new Request.Builder()
            .url("https://skedlease.onrender.com/user/get_csrf/")
            .build();

        try
        {
            Call call = client.newCall(request);
            Response response = call.execute();

            if(response.code() == 200)
            {
                return response.body().string();
            } else
            {
                return Integer.toString(response.code());
            }

        } catch(IOException e)
        {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        } 
    }

    public static void main(String[] args) {
        launch();
    }

}