package myidz.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Created by denis on 28.11.16.
 */
public class MainController {

    @FXML
    TextField LoginText;
    @FXML
    PasswordField PassText;
    @FXML
    Button btnlogin;
    @FXML
    AnchorPane  loginPanel;
    @FXML
    AnchorPane  contentPanel;
    @FXML
    public void initialize(){
        contentPanel.setVisible(false);
        loginPanel.setVisible(true);

    }

    public void btlloginEvent(){
        //LoginText.setText("root");
        if(LoginText.getText().equals("root") && PassText.getText().equals("pass")) {
            contentPanel.setVisible(true);
            loginPanel.setVisible(false);
        }
        System.out.print(LoginText.getText());
    }
}