package myidz.controller;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.collections.ObservableList;

import javafx.collections.FXCollections;
import javafx.util.StringConverter;

import java.sql.*;
import java.time.format.DateTimeFormatter;
//import ;


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
        //LoginText.setText("denis");
     //   PassText.setText("pass");



        try{
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
            connecttion = DriverManager.getConnection("jdbc:mysql://localhost:3306/myticket?autoReconnect=true&useSSL=false&database?useUnicode=true&characterEncoding=utf8",LoginText.getText(), PassText.getText());
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
        loadsessionTable();
        loadsession();
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
    public void loadsessionTable(){
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
           // debagtext.setText(debagtext.getText()+res.getString(2)+"\n");
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
    @FXML
    ChoiceBox<KeyValuePair>   ChoiseHall;

    @FXML
    ChoiceBox<KeyValuePair>   ChoiseName;
    @FXML
    DatePicker sessionaddDate;
    @FXML
    TextField sessionaddTime;
    public void add_session(){
        if (ChoiseHall.getValue()== null |  ChoiseName.getValue()== null | sessionaddDate.getValue() == null | sessionaddTime.getText().length()==0)
        {
            debagtext.setText("Пусто");
            return;
        }
        try {

            Statement statement = connecttion.createStatement();
            String str = ChoiseHall.getValue().getKey();
            String pattern = "yyyy-MM-dd";

                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);


            int done = statement.executeUpdate("INSERT INTO session(Data_start,time_start,Hall_id,id_spectacle) VALUE ('"+dateFormatter.format(sessionaddDate.getValue())+"','"+sessionaddTime.getText()+"','"+ChoiseHall.getValue().getKey()+"','"+ChoiseName.getValue().getKey()+"');");
       System.out.print(done);
        }catch (SQLException e){
            e.printStackTrace();
        }
        loadsessionTable();

    }


    @FXML
    TextField DeleHall;
    public void DellHallAction()
    {


            try {
                Statement statement = connecttion.createStatement();
                if(DeleHall.getText().length()!=0 ) {
                    statement.executeUpdate("Delete from Hall where id = '" + DeleHall.getText() + "' ");
                }else
                    if (ChoiceDellHall.getValue()!= null)
                    {
                        statement.executeUpdate("Delete from Hall where id = '" + ChoiceDellHall.getValue().getKey() + "' ");

                    }
                    else {return;}
                System.out.print("OK");
            }catch (SQLException e){
                e.printStackTrace();
            }
        loadsessionTable();
        loadsession();
    }




    public  void ttt(){

    }
    @FXML
    ChoiceBox<KeyValuePair> ChoiceDellHall;
    public void loadsession()
    {

        try {
            ChoiseHall.getItems().clear();
            ChoiceDellHall.getItems().clear();

            ChoiseName.getItems().clear();
            Statement statement = connecttion.createStatement();
            ResultSet res=  statement.executeQuery("SELECT * from  Hall");
            while (res.next())
            {
                ChoiseHall.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
           //     ChoiseHall1.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
            }
            Statement statement1 = connecttion.createStatement();
            //connecttion.close();
            ResultSet res1=  statement1.executeQuery("SELECT id,name from  spectacle");
            while (res1.next())
            {
                ChoiseName.getItems().add(new KeyValuePair(res1.getString(1),res1.getString(2)));
            }
            Statement statement2 = connecttion.createStatement();
            ResultSet res2=  statement2.executeQuery("SELECT * from  Hall");
            while (res2.next())
            {
                ChoiceDellHall.getItems().add(new KeyValuePair(res2.getString(1),res2.getString(2)));
                //     ChoiseHall1.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    @FXML
    TextField AddHallName;
    @FXML
    TextField HallROW;
    @FXML
    TextField HallSpace;
    public void AddHallandSpace()
    {
        if (AddHallName.getText().length()==0|HallROW.getText().length()==0|HallSpace.getText().length()==0)
        {
            debagtext.setText("Пусто");
            return;
        }
        try {

            Statement statement = connecttion.createStatement();
            int done = statement.executeUpdate("INSERT INTO Hall(name) VALUE ('"+AddHallName.getText()+"');");
            System.out.print(done);
        }catch (SQLException e){
            e.printStackTrace();
        }

        for(int i = 1;i<=Integer.parseInt(HallROW.getText());i++){
            for(int j = 1;j<=Integer.parseInt(HallSpace.getText());j++){
                try {

                    Statement statement = connecttion.createStatement();
                    ResultSet res1=  statement.executeQuery("SELECT id,name from  Hall where name like'"+ AddHallName.getText()+"'");
                    res1.next();
                    String idHall = res1.getString(1);
                    int done = statement.executeUpdate("INSERT INTO HallandSpace(Hall, row, space) VALUE ('"+idHall+"','"+i+"','"+j+"');");
                    System.out.print(done);
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
        loadsessionTable();
        loadsession();
    }





    public class KeyValuePair {
        private final String key;
        private final String name;
        public KeyValuePair(String key, String name) {
            this.key = key;
            this.name = name;
        }

        public String getKey()   {    return key;    }

        public String toString() {    return name;  }
    }
}