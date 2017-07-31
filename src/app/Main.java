package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {

    public static final int loginScreenWidth = 300;
    public static final int loginScreenHeight = 275;

    private static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("View/loginScreen.fxml"));
        primaryStage.setTitle("Examination System");
        primaryStage.setScene(new Scene(root, loginScreenWidth, loginScreenHeight));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

    public static void changeScene(String sceneName, int width, int height) throws IOException{
        try{
            Parent root = FXMLLoader.load(Main.class.getResource(sceneName));
            stage.setScene(new Scene(root, width, height));
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static Stage getStage(){
        return stage;
    }
}
