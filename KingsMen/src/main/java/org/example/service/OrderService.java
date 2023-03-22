package org.example.service;

import org.example.model.OrderDetails;
import org.example.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public void saveOrder(OrderDetails orderDetails){
        orderRepository.save(orderDetails);
    }
    public List<OrderDetails> getAllOrders() {
        return orderRepository.findAll();
    }
    public void removeProductById(Integer id) {
        orderRepository.deleteById(id);
    }

    public Optional<OrderDetails> getOrderByorder_id(int id){
        return  orderRepository.findById(id);
    }
    public Integer getOrderCount() {
        return Math.toIntExact(orderRepository.count());
    }

    public List<OrderDetails> findByKeyword(String keyword){
        return orderRepository.findByKeyword(keyword);
    }

    public void addOrder(OrderDetails order) {
        orderRepository.save(order);
    }

}
