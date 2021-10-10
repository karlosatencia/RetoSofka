import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static void main(String[] args) throws Exception {
        launch(args);
                
    }
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent ventana = FXMLLoader.load(getClass().getResource("vistas/vistaPrincipal.fxml"));
        // MOstrar la escena que contiene el layout
        Scene scene = new Scene(ventana);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}