package myidz.controller;

import com.mysql.fabric.jdbc.FabricMySQLDriver;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import javafx.collections.ObservableList;

import javafx.collections.FXCollections;
import javafx.scene.text.Text;
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
  //   @FXML
  //   TextArea debagtext;
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
        LoadSpectacle();
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

    @FXML
    TableView<Spectacle> SpectacleTable;
    @FXML
    TableColumn<Spectacle,Integer> SpectacleID;
    @FXML
    TableColumn<Spectacle,String> SpectacleName;
    @FXML
    TableColumn<Spectacle,String> SpectacleGenre;
    @FXML
    TableColumn<Spectacle,String> SpectacleLength;



    public void LoadSpectacle()
    {
        ObservableList<Spectacle> spectacleList = FXCollections.observableArrayList();
        SpectacleID.setCellValueFactory(new PropertyValueFactory<Spectacle, Integer>("ID"));
        SpectacleName.setCellValueFactory(new PropertyValueFactory<Spectacle, String>("Name"));
        SpectacleGenre.setCellValueFactory(new PropertyValueFactory<Spectacle, String>("Genre"));
        SpectacleLength.setCellValueFactory(new PropertyValueFactory<Spectacle, String>("Length"));
        try {
            Statement statement = connecttion.createStatement();
            ResultSet res=  statement.executeQuery("SELECT *  FROM spectacle");
            while (res.next())
            {
                // debagtext.setText(debagtext.getText()+res.getString(2)+"\n");
                spectacleList.add(new Spectacle(res.getInt(1),res.getString(2),res.getString(3),res.getString(4)));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        SpectacleTable.setItems(spectacleList);
    }
    public class Spectacle{
        int ID;
        String Name;
        String Genre;
        String Length;

        public Spectacle(int id, String name, String genre, String length)
        {
            this.ID = id;
            this.Name = name;
            this.Genre = genre;
            this.Length = length;

        }
        public Spectacle()
        {}
        public int getID(){
            return ID;
        }
        public void setID(int id)
        {
            this.ID= id;
        }

        public String getName(){
            return Name;
        }
        public void setName(String name)
        {
            this.Name= name;
        }
        public String getGenre(){
            return Genre;
        }
        public void setGenre(String genre)
        {
            this.Genre= genre;
        }
        public String getLength(){
            return Length;
        }
        public void setLength(String length)
        {
            this.Length= length;
        }

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
    TextField SpectacleAddName;
    @FXML
    TextField SpectacleAddGenre;
    @FXML
    TextField SpectacleAddLength;

    public void AddSpectacle(){
        if (SpectacleAddName.getText().equals("") |  SpectacleAddGenre.getText().equals("")| SpectacleAddLength.getText().equals(""))
        {
            //debagtext.setText("Пусто");
            System.out.print("Пусто");
            return;
        }
        try {

            Statement statement = connecttion.createStatement();
            int done = statement.executeUpdate("INSERT INTO spectacle(name, genre, time_length) VALUE ('"+SpectacleAddName.getText() +"','"+SpectacleAddGenre.getText() +"','"+SpectacleAddLength.getText() + "');");
            System.out.print(done);
        }catch (SQLException e){
            e.printStackTrace();
        }
        loadsessionTable();
        loadsession();
        LoadSpectacle();
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
           // debagtext.setText("Пусто");
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
        loadsession();
        LoadSpectacle();

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
          //  debagtext.setText("Пусто");
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
    @FXML
    ChoiceBox<KeyValuePair>   ChoiseSes;
    public void loadTicket(){

        try {
            ChoiseSes.getItems().clear();

            Statement statement = connecttion.createStatement();
            ResultSet res=  statement.executeQuery("SELECT session.id, spectacle.name , session.Data_start, session.time_start, Hall.name from session INNER JOIN Hall on session.Hall_id = Hall.id inner join spectacle on session.id_spectacle=spectacle.id  ORDER BY Data_start,time_start;");
            while (res.next())
            {
                ChoiseSes.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)+" | "+res.getString(3)+" | "+res.getString(4)+" | "+res.getString(5)));
          //      ChoiseSes.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
                //     ChoiseHall1.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        row.getItems().clear();
        place.getItems().clear();
    }
    @FXML
    Text ticketStatus;
    public boolean getticketStatus(String place )
    {
        try {
            Statement statement = connecttion.createStatement();
            ResultSet res=  statement.executeQuery("SELECT tickets.id from tickets inner JOIN  HallandSpace on tickets.id_place = HallandSpace.id WHERE tickets.id_session="+ChoiseSes.getValue().getKey()  +" and HallandSpace.row = "+row.getValue().getKey()  +" and HallandSpace.space = "+place+"");
    //       System.out.print(res.getString(1));
            if (!res.next()) {

                //ticketStatus.setVisible(true);
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


       // ticketStatus.setVisible(false);
        return false;
    }

    public boolean getticketStatus()
    {
        try {
            Statement statement = connecttion.createStatement();
            ResultSet res=  statement.executeQuery("SELECT tickets.id from tickets inner JOIN  HallandSpace on tickets.id_place = HallandSpace.id WHERE tickets.id_session="+ChoiseSes.getValue().getKey()  +" and HallandSpace.row = "+row.getValue().getKey()  +" and HallandSpace.space = "+place.getValue().getKey()+"");
            //       System.out.print(res.getString(1));
            if (res.next()) {

                ticketStatus.setVisible(true);
                return true;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }


         ticketStatus.setVisible(false);
        return false;
    }
 @FXML
 TextField price;
    public void BuyTicket()
    {
        if (getticketStatus())
            return;
        try {
            Statement statement = connecttion.createStatement();
            int done = statement.executeUpdate("INSERT INto tickets(id_session, id_place,sale,price) VALUE('"+ChoiseSes.getValue().getKey()+"',(select HallandSpace.id from HallandSpace INNER JOIN session on HallandSpace.Hall = session.Hall_id where session.id="+ChoiseSes.getValue().getKey()+" and HallandSpace.row = "+row.getValue().getKey()+" and HallandSpace.space = "+place.getValue().getKey()+" ),'1','"+price.getText()+"')" );
            System.out.print(done);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
    @FXML
    ChoiceBox<KeyValuePair>   row;
    public void loadRow(){

        try {
            row.getItems().clear();

            Statement statement = connecttion.createStatement();
            ResultSet res=  statement.executeQuery("SELECT HallandSpace.row  from HallandSpace INNER JOIN session on session.Hall_id = HallandSpace.Hall where session.id ='"+ChoiseSes.getValue().getKey() + "' GROUP BY HallandSpace.row;");
            while (res.next())
            {
                row.getItems().add(new KeyValuePair(res.getString(1),res.getString(1)));
                //
                //
                //   ChoiseSes.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
                //     ChoiseHall1.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        place.getItems().clear();
    }

    @FXML
    ChoiceBox<KeyValuePair>   place;
    public void loadPlace(){

        try {

            place.getItems().clear();
            Statement statement = connecttion.createStatement();
            ResultSet res=  statement.executeQuery("SELECT HallandSpace.space  from HallandSpace INNER JOIN session on session.Hall_id = HallandSpace.Hall where session.id ='"+ChoiseSes.getValue().getKey() + "'  and HallandSpace.row = '"+row.getValue().getKey() + "';");
            while (res.next())
            {
                if (getticketStatus(res.getString(1))) {
                    place.getItems().add(new KeyValuePair(res.getString(1), res.getString(1)));
                }else
                {
                    place.getItems().add(new KeyValuePair(res.getString(1), res.getString(1)+" Занято"));
                }
                      //    ChoiseSes.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
                //     ChoiseHall1.getItems().add(new KeyValuePair(res.getString(1),res.getString(2)));
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
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