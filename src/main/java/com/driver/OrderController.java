package com.driver;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("orders")
public class OrderController {

    Logger logger = LoggerFactory.getLogger(OrderController.class);


    OrderService orderService = new OrderService();

    // checked and logged
    @PostMapping("/add-order")
    public ResponseEntity<String> addOrder(@RequestBody Order order){
        logger.info("Order Object Entered Controller");
        orderService.addOrder(order);
        return new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);
    }

    // checked and logged
    @PostMapping("/add-partner/{partnerId}")
    public ResponseEntity<String> addPartner(@PathVariable String partnerId){
        logger.info("Partner Id Entered Controller");
        orderService.addDeliveryPartner(partnerId);
        return new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);
    }

    // checked and logged
    @PutMapping("/add-order-partner-pair")
    public ResponseEntity<String> addOrderPartnerPair(@RequestParam String orderId, @RequestParam String partnerId){
        logger.info("Pair[orderId,partnerId] Entered Controller");
        boolean status = orderService.addOrderPartnerPair(orderId,partnerId);
        return new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);
        //return null; // check the logging's to find why the null was returned
    }

    // checked and logged
    @GetMapping("/get-order-by-id/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable String orderId){
        logger.info("Order Id Entered Controller");
        Order order = orderService.getOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    // checked and logged
    @GetMapping("/get-partner-by-id/{partnerId}")
    public ResponseEntity<DeliveryPartner> getPartnerById(@PathVariable String partnerId){
        logger.info("Partner Id Entered Controller");
        DeliveryPartner deliveryPartner = orderService.getPartnerById(partnerId);
        return new ResponseEntity<>(deliveryPartner, HttpStatus.CREATED);
    }

    // checked and logged
    @GetMapping("/get-order-count-by-partner-id/{partnerId}")
    public ResponseEntity<Integer> getOrderCountByPartnerId(@PathVariable String partnerId){
        logger.info("Partner Id Entered Controller");
        Integer orderCount = orderService.getNumberOfOrders(partnerId);
        return new ResponseEntity<>(orderCount, HttpStatus.CREATED);
    }

    // checked and logged
    @GetMapping("/get-orders-by-partner-id/{partnerId}")
    public ResponseEntity<List<String>> getOrdersByPartnerId(@PathVariable String partnerId){
        logger.info("Partner Id Entered Controller : GETMAPPING: get-orders-by-partner");
        List<String> orders = orderService.getAllOrders(partnerId);
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    // checked and logged
    @GetMapping("/get-all-orders")
    public ResponseEntity<List<String>> getAllOrders(){
        logger.info("GET MAPPING : get-all-orders : CONTROLLER");
        List<String> orders = orderService.allOrders();
        return new ResponseEntity<>(orders, HttpStatus.CREATED);
    }

    // checked and logged
    @GetMapping("/get-count-of-unassigned-orders")
    public ResponseEntity<Integer> getCountOfUnassignedOrders(){
        logger.info("GET MAPPING : get-count-of-unassigned-orders");
        Integer countOfOrders = orderService.countUnassignedOrders();
        logger.info("Returned all the unassigned order count");
        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }


    // checked and logged
    @GetMapping("/get-count-of-orders-left-after-given-time/{partnerId}/{time}")
    public ResponseEntity<Integer> getOrdersLeftAfterGivenTimeByPartnerId(@PathVariable String time, @PathVariable String partnerId){
        logger.info("GETMAPPING : get-count-of-orders-left-after-given-time : Controller");
        Integer countOfOrders = orderService.getOrderLeftAfterTime(time,partnerId);

        return new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);
    }

    // checked and logged
    @GetMapping("/get-last-delivery-time/{partnerId}")
    public ResponseEntity<String> getLastDeliveryTimeByPartnerId(@PathVariable String partnerId){
        logger.info("GET MAPPING : get-last-delivery-time : CONTROLLER");
        String time = orderService.getLastTime(partnerId);
        return new ResponseEntity<>(time, HttpStatus.CREATED);
    }

    // checked and logged
    @DeleteMapping("/delete-partner-by-id/{partnerId}")
    public ResponseEntity<String> deletePartnerById(@PathVariable String partnerId){
        logger.info("DELETE MAPPING : delete-partner-by-partner-id : CONTROLLER");
        orderService.deletePartner(partnerId);
        return new ResponseEntity<>(partnerId + " removed successfully", HttpStatus.CREATED);
    }

    //checked and logged
    @DeleteMapping("/delete-order-by-id/{orderId}")
    public ResponseEntity<String> deleteOrderById(@PathVariable String orderId){
        logger.info("DELETE ORDER BY ID : CONTROLLER");
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>(orderId + " removed successfully", HttpStatus.CREATED);
    }
}
