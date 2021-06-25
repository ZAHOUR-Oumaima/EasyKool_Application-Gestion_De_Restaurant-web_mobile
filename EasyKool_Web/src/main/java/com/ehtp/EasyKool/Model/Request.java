package com.ehtp.EasyKool.Model;

import java.util.ArrayList;

public class Request {

    private String username;
    private ArrayList<Order> breakfast;
    private ArrayList<Order> lunch;
    private ArrayList<Order> dinner;

    public Request(String username, ArrayList<Order> breakfast, ArrayList<Order> lunch, ArrayList<Order> dinner) {
        this.username = username;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    public Request() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Order> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<Order> breakfast) {
        this.breakfast = breakfast;
    }

    public ArrayList<Order> getLunch() {
        return lunch;
    }

    public void setLunch(ArrayList<Order> lunch) {
        this.lunch = lunch;
    }

    public ArrayList<Order> getDinner() {
        return dinner;
    }

    public void setDinner(ArrayList<Order> dinner) {
        this.dinner = dinner;
    }

    /*public String getNameQteBreakfast(){
        String result = "";
        for(Order order: breakfast){
            result += order.getArticlename()+"["+order.getQuantity()+"]"+"  ";
        }
        return result;
    }

    public String getNameQteLunch(){
        String result = "";
        for(Order order: lunch){
            result += order.getArticlename()+"["+order.getQuantity()+"]"+"  ";
        }
        return result;
    }

    public String getNameQteDinner(){
        String result = "";
        for(Order order: dinner){
            result += order.getArticlename()+"["+order.getQuantity()+"]"+"  ";
        }
        return result;
    }*/


}
