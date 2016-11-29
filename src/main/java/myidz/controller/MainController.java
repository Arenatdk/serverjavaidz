package myidz.controller;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


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
    TableView tabls;
    @FXML
    TableColumn IDcol;
     @FXML
    AnchorPane  contentPanel;
    @FXML
    public void initialize(){
        contentPanel.setVisible(false);
        loginPanel.setVisible(true);
        LoginText.setText("denis");
        PassText.setText("pass");

    }

    public void btlloginEvent(){
        LoginText.setText("denis");
        PassText.setText("pass");
        Connection connecttion;


        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connecttion = DriverManager.getConnection("jdbc:mysql://localhost:3306/myticket?autoReconnect=true&useSSL=false",LoginText.getText(), PassText.getText());
            if (!connecttion.isClosed()){
                System.out.print("Все ок");

                contentPanel.setVisible(true);
                loginPanel.setVisible(false);
            }
        }
        catch (SQLException e)
        {
            btnlogin.setText("Не удалось");
            System.out.print("Не удаломь загрузить драйвер");
        }

    }

    public void ttt(){
        //tabls.setItems();

    }
}