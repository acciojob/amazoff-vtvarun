package com.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    OrderRepository orderRepository;

    OrderService(){
        orderRepository = new OrderRepository();
    }
    public void addOrder(Order order){
        logger.info("Order Object Entered Service");
        orderRepository.addOrder(order);
    }

    public void addDeliveryPartner(String deliveryPartnerId){
        logger.info("Partner id Entered Service");
        orderRepository.addDeliveryPartner(deliveryPartnerId);
    }

    public boolean addOrderPartnerPair(String orderId, String partnerId){
        logger.info("Pair[orderId,PartnerId] Entered Service");

        boolean validPartnerId = orderRepository.isPartnerIdValid(partnerId);
        boolean validOrderId =  orderRepository.isOrderIdValid(orderId);

        HashMap<String,List<Order>> ordersOfDeliveryPartners = orderRepository.ordersOfDeliveryPartners;
        HashMap<String,Order> orderHashMap = orderRepository.orderHashMap;

        if(validPartnerId && validOrderId) {
            ordersOfDeliveryPartners.get(partnerId).add(orderHashMap.get(orderId));
            logger.info("Pair[OrderId,Partner Id] Settled In Repository");
            return true;
        } else if(!validPartnerId && !validOrderId){
            logger.info("Both partnerId and OrderID were invalid");
            return false;
        } else if(validOrderId){
            logger.info("Invalid Partner Id Was Entered");
            return false;
        }

        logger.info("Invalid orderId Was Entered");
        return false;

    }

    public Order getOrderById(String orderId){
        logger.info("Order Id Entered Service");
        boolean isOrderValid = orderRepository.isOrderIdValid((orderId));
        if(isOrderValid){
            return orderRepository.getOrderById(orderId);
        }
        return null;
    }

    public DeliveryPartner getPartnerById(String partnerId){
        logger.info("Partner Id Entered Service");

        boolean isPartnerIdValid = orderRepository.isPartnerIdValid(partnerId);

        if(isPartnerIdValid){
            logger.info("Got Partner Object Successfully");
            return orderRepository.getPartnerById(partnerId);
        }

        logger.info("Invalid Partner Id Was Found");
        return null;
    }

    public Integer getNumberOfOrders(String partnerId){
        logger.info("Partner Id Entered Service");
        if(orderRepository.isPartnerIdValid(partnerId)){
            logger.info("Got the number of Orders given to Delivery Partner");
            return orderRepository.getNumberOfOrders(partnerId);
        }

        logger.info("Partner Id Was not valid");
        return null;
    }

    public List<String> getAllOrders(String partnerId){
        logger.info("PartnerId Entered Service");
        if(!orderRepository.isPartnerIdValid(partnerId)){
            logger.info("PartnerId is Invalid");
            return null;
        }

        List<Order> orders =  orderRepository.getAllOrders(partnerId);
        // converting all the orders into a list of string
        List<String> allOrders = new ArrayList<>();
        for(Order orderId : orders){
            String currOrder = "OrderId :"+orderId.getId()+" Delivery Time: "+ orderId.getDeliveryTime();
            allOrders.add(currOrder);
        }
        logger.info("Returned all orders given to a delivery Partner");
        return allOrders;
    }

    public List<String> allOrders(){
        HashMap<String,Order> orderHashMap = orderRepository.getAllOrdersFromDataBase();
        List<String> allOrders = new ArrayList<>();
        for(String orders : orderHashMap.keySet()){
            String currOrder = "OrderId : "+orderHashMap.get(orders).getId()+" Delivery Time :"+orderHashMap.get(orders).getDeliveryTime();
            allOrders.add(currOrder);
        }
        logger.info("Returned all the orders");
        return allOrders;
    }

    public Integer countUnassignedOrders(){
        return orderRepository.numberOfAllOrder() - orderRepository.numberOfAssignedOrders();
    }

    public Integer getOrderLeftAfterTime(String time, String partnerid){
        logger.info("Entered Service");
        boolean isPartnerIdValid = orderRepository.isPartnerIdValid(partnerid);
        if(!isPartnerIdValid) {
            logger.info("Partner Id was invalid");
            return null;
        }

        List<Order> orders = orderRepository.getAllOrderByPartnerId(partnerid);
        int hour1 = (time.charAt(0)-'0') * 10;
        int hour2 = time.charAt(1)-'0';
        int hour = (hour1 + hour2) * 60;
        int minute1 = (time.charAt(3)-'0') * 10;
        int minute2 = time.charAt(4)-'0';
        int minute = minute1+minute2;
        int totalTime = hour + minute;
        Integer count = 0;
        for(Order o : orders){
            if(o.getDeliveryTime() > totalTime ){
                count++;
            }
        }
        logger.info("Returned counts of order left after time");
        return count;
    }

    public String getLastTime(String partnerId){
        logger.info("Entered Service Layer");
        if(!orderRepository.isPartnerIdValid(partnerId)) {
            logger.info("Partner ID Was invalid");
            return null;
        }

        List<Order> orders = orderRepository.getAllOrderByPartnerId(partnerId);
        int time  = 0;
        for(Order o : orders){
            if(o.getDeliveryTime() > time){
                time = o.getDeliveryTime();
            }
        }

        //converting the integer time format back to HH:MM
        String hour = (time / 60)+"";
        String min = (time % 60)+"";
        return hour+":"+min;

    }

    public void deletePartner(String partnerId){
        if(!orderRepository.isPartnerIdValid(partnerId)) return;
        orderRepository.deletePartner(partnerId);
    }

    public void deleteOrder(String orderId){
        if(!orderRepository.isOrderIdValid(orderId)) return;
        orderRepository.deleteOrder(orderId);
        HashMap<String, List<Order>> assinged = orderRepository.ordersOfDeliveryPartners;
        for(String s : assinged.keySet()){
            List<Order> orderList = assinged.get(s);
            for(int i=0; i < orderList.size() ; i++){
                if(orderList.get(i).getId().equals(orderId)){
                    orderList.remove(i);
                }
            }
        }

    }
}
