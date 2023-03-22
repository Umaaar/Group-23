package org.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.example.model.OrderItem;
import org.example.repository.OrderItemRepository;
import org.example.repository.ProductRepository;
import org.example.repository.ProductSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductSizeRepository productSizeRepository;

    @Autowired
    ProductRepository productRepository;


    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public List<OrderItem> getAllOrderItemsByID( int Id){
           return orderItemRepository.findByOrderID(Id);
    }

    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public OrderItem updateOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

//     @Transactional
//     public void decreasingStock(Long productSizeId, int quantity) {
//     productSizeRepository.decreaseStock(productSizeId, quantity);
// }

    @Transactional
    public void decreasingStock(Long productId, int stock) {
        productRepository.decreaseStock(productId, stock);
    }
}
