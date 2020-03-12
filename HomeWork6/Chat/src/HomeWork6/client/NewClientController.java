package HomeWork6.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.DataOutputStream;
import java.io.IOException;

public class NewClientController {
    @FXML
    Button btn;

    @FXML
    TextArea textLogin;
    @FXML
    TextArea textPass;
    @FXML
    TextArea textNick;

    public void btnClick() {
        DataOutputStream out = ((SignUpStage)btn.getScene().getWindow()).out;
        try {
            out.writeUTF("/signUp " + textLogin.getText() + " " + textPass.getText() + " " +textNick.getText());
            ((SignUpStage)btn.getScene().getWindow()).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
