package HomeWork6.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class SignUpStage extends Stage {
    String nickTo;
    DataOutputStream out;
    List<TextArea> parentList;

    public SignUpStage(DataOutputStream out) {
        this.out = out;
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("NewClient.fxml"));
            setTitle("new client");
            Scene scene = new Scene(root, 200, 180);
            setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
