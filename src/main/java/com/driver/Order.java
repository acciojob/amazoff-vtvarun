package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        // the format of time in String should be (HH:MM) : is also a character, which needs to be ignored
        String[] timeArr=deliveryTime.split(":");
        int hour=Integer.parseInt(timeArr[0]);
        int min=Integer.parseInt(timeArr[1]);
        this.deliveryTime=(hour*60)+min;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
