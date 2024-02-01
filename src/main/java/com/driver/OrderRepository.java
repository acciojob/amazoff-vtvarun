package com.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class OrderRepository {

    Logger logger = LoggerFactory.getLogger(OrderRepository.class);

    HashMap<String,Order> orderHashMap = new HashMap<>();

    HashMap<String, List<Order>> ordersOfDeliveryPartners = new HashMap<>();

    public HashMap<String,Order> getAllOrdersFromDataBase(){
        return orderHashMap;
    }

    public Integer numberOfAllOrder(){
        return orderHashMap.size();
    }

    public Integer numberOfAssignedOrders(){
        Integer count = 0;
        for(String order : ordersOfDeliveryPartners.keySet()){
            int currSize = ordersOfDeliveryPartners.get(order).size();
            count += currSize;
        }
        return count;
    }


    public void addOrder(Order order){
        logger.info("Object order Entered Repository");
        String id = order.getId();
        orderHashMap.put(id,order);
        logger.info("Object order Settled in Repository");
    }

    public void addDeliveryPartner(String deliveryPartnerId){
        logger.info("PartnerId Entered Repository");
        ordersOfDeliveryPartners.put(deliveryPartnerId,new ArrayList<>());
        logger.info("Partner Id Settled In Repository");
    }


    public boolean isOrderIdValid(String orderId){
        return orderHashMap.containsKey(orderId);
    }

    public boolean isPartnerIdValid(String partnerId){
        return ordersOfDeliveryPartners.containsKey(partnerId);
    }

    // get order by order id
    public Order getOrderById(String orderId){
        return orderHashMap.get(orderId);
    }

    // get partner by partner id
    public DeliveryPartner getPartnerById(String partnerId){
        DeliveryPartner partner = new DeliveryPartner(partnerId);
        int numberOfOrders = ordersOfDeliveryPartners.get(partnerId).size();
        partner.setNumberOfOrders(numberOfOrders);
        return partner;
    }

    //get number of orders by partnerId
    public Integer getNumberOfOrders(String partnerId){
        return ordersOfDeliveryPartners.get(partnerId).size();
    }

    // get allOrder by partner Id
    public List<Order> getAllOrderByPartnerId(String partnerId){
        return ordersOfDeliveryPartners.get(partnerId);
    }



    // return all orders assigned to a partnerId
    public List<Order> getAllOrders(String partnerId){
        return ordersOfDeliveryPartners.get(partnerId);
    }

    public void deletePartner(String partnerId){
        ordersOfDeliveryPartners.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        orderHashMap.remove(orderId);
    }
}
