package myidz.controller;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.collections.ObservableList;

import javafx.collections.FXCollections;
import java.sql.*;


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
     TextArea debagtext;
    @FXML
    public void initialize(){
        contentPanel.setVisible(false);
        loginPanel.setVisible(true);
        LoginText.setText("denis");
        PassText.setText("pass");

    }
    Connection connecttion;
    public void btlloginEvent(){
        LoginText.setText("denis");
        PassText.setText("pass");



        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connecttion = DriverManager.getConnection("jdbc:mysql://localhost:3306/myticket?autoReconnect=true&useSSL=false",LoginText.getText(), PassText.getText());
            if (!connecttion.isClosed()){
                System.out.print("Все ок");

                contentPanel.setVisible(true);
                loginPanel.setVisible(false);
            }
           // connecttion.close();
        }
        catch (SQLException e)
        {
            btnlogin.setText("Не удалось");
            System.out.print("Не удаломь загрузить драйвер");
        }
        ttt();
    }


    @FXML
    TableView<Sessions> tabls;
    @FXML
    TableColumn<Sessions,Integer> IDcol;
    @FXML
    TableColumn<Sessions,String> nameCol;
    @FXML
    TableColumn<Sessions,String> timestart;
    @FXML
    TableColumn<Sessions,String> datastart;
    @FXML
    TableColumn<Sessions,String> hallname;
    public void ttt(){
        ObservableList<Sessions> sesionList = FXCollections.observableArrayList();
        IDcol.setCellValueFactory(new PropertyValueFactory<Sessions, Integer>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Sessions, String>("Name"));
        timestart.setCellValueFactory(new PropertyValueFactory<Sessions, String>("Data_start"));
        datastart.setCellValueFactory(new PropertyValueFactory<Sessions, String>("Time_start"));
        hallname.setCellValueFactory(new PropertyValueFactory<Sessions, String>("Hallname"));
        try {
            Statement statement = connecttion.createStatement();
        ResultSet res=  statement.executeQuery("SELECT `session`.id, spectacle.`name`,Data_start,time_start,Hall.name  FROM session INNER JOIN spectacle on session.id_spectacle = spectacle.id inner JOIN Hall on `session`.Hall_id = Hall.id ORDER BY Data_start,time_start;");
        while (res.next())
        {
            debagtext.setText(debagtext.getText()+res.getString(2)+"\n");
            sesionList.add(new Sessions(res.getInt(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5)));
        }
        }catch (SQLException e){
            e.printStackTrace();
        }
        tabls.setItems(sesionList);
    }

    public class Sessions{
        int ID;
        String Name;
        String Data_start;
        String Time_start;
        String Hallname;
      //  String Hall_name;
      //  String Scectacle;
        public Sessions(int id, String name, String time_start, String data_start,String hall)
        {
            this.ID = id;
            this.Name = name;
            this.Data_start = data_start;
            this.Time_start = time_start;
            this.Hallname = hall;
        }
        public  Sessions(){}

        public int getID()
        {return ID;}

        public void setID( int id)
        {this.ID = id;}

        public  String getName()
        {return Name;}

        public void setName(String name)
        {this.Name=name;}

        public  String getTime_start()
        {return Time_start;}

        public void setTime_start(String time)
        {this.Time_start=time;}

        public  String getData_start()
        {return Data_start;}

        public void setData_start(String data)
        {this.Data_start=data;}

        public  String getHallname()
        {return Hallname;}

        public void setHallname(String hall)
        {this.Hallname=hall;}
    }
}