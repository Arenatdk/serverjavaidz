package myidz.controller;

/**
 * Created by denis on 29.11.16.
 */
public class TableTickets {
    private int id;
    private int id_session;
    private int id_place;
    private boolean sale;
    private int price;

    public TableTickets(int id,int id_session,int id_place,boolean sale,int price){
        this.id = id;
        this.id_session = id_session;
        this.id_place=id_place;
        this.sale = sale;
        this.price = price;
    }
}
