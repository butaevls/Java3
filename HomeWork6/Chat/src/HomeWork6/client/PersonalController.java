package HomeWork6.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.DataOutputStream;
import java.io.IOException;

public class PersonalController {
    @FXML
    Button btn;

    @FXML
    TextArea textArea;

    public void btnClick() {
        DataOutputStream out = ((MiniStage)btn.getScene().getWindow()).out;
        String nickTo = ((MiniStage)btn.getScene().getWindow()).nickTo;

        if(!((MiniStage)btn.getScene().getWindow()).parentList.contains(textArea)) {
            ((MiniStage)btn.getScene().getWindow()).parentList.add(textArea);
            System.out.println("1");
        }

        try {
            out.writeUTF("/w " + nickTo + " " + textArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
