
package myidz;

      //  import com.mysql.jdbc.Driver;

        import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.stage.Stage;

        import com.mysql.fabric.jdbc.FabricMySQLDriver;
        import java.sql.Connection;
        import java.sql.Driver;
        import java.sql.DriverManager;
        import java.sql.SQLException;

public class MainApp extends Application {

    public static void main(String[] args) throws Exception {
        launch(args);


    }

    @Override
    public void start(Stage stage) throws Exception {

        String fxmlFile = "/fxml/form.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
        stage.setTitle("Tickets");
        stage.setScene(new Scene(root));
        stage.show();
    }
}