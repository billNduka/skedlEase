module com.skedlease {
    requires javafx.controls;
    requires javafx.fxml;
    requires okhttp3;
    requires org.json;

    opens com.skedlease to javafx.fxml;
    exports com.skedlease;
}
