package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id = id;
        // the format of time in String should be (HH:MM) : is also a character, which needs to be ignored
        int hour1 = (deliveryTime.charAt(0)-'0') * 10;
        int hour2 = (deliveryTime.charAt(1)-'0');
        int minute1 = (deliveryTime.charAt(3)-'0') * 10;
        int minute2 = (deliveryTime.charAt(4)-'0');
        this.deliveryTime = ((hour1 + hour2) * 60) + (minute1+minute2);
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
