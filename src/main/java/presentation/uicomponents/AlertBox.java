package presentation.uicomponents;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * Pop-Up Fenster um Fehlermeldungen für den Nutzer sichtbar zu machen
 */

public class AlertBox {

    public static void display(String title, String message){

        /*
         * Setzt neue Stage mit einem Titel, Beschreibung der Eingabe und Eingabefeld
         */

        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close the window");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene (layout);
        window.setScene(scene);
        window.showAndWait();

    }
}
