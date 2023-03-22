package org.example.controller;

import org.example.model.*;
import org.example.dto.ProductDTO;
import org.example.global.GlobalData;
import org.example.service.OrderItemService;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.ProductSizeService;
import org.example.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.example.dto.OrderDTO;

@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;
    @Autowired
    ProductSizeService productSizeService;
    @Autowired
    OrderItemService orderItemService;

    @Autowired
    SizeService sizeService;

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id) {
        GlobalData.cart.add(productService.getProductById(id).get());
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartGet(@AuthenticationPrincipal CustomUserDetail authentication, Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart", GlobalData.cart);
        model.addAttribute("name", authentication.getFirstname());
        model.addAttribute("email", authentication.getEmail());
        return "/frontend-views/cart-page";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index, Model model) {
        GlobalData.cart.remove(index);
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getQuantityTimesPrice).sum());
        return "redirect:/cart";
    }

    @PostMapping("/addToCart/{id}")
    public String dropdown(@PathVariable Long id, ProductDTO dropdown, RedirectAttributes redirectAttributes) {
        Product item = productService.getProductById(id).get();
        Product newItem = new Product();
        newItem.setId(item.getId());
        System.out.println(item.getId());
        newItem.setName(item.getName());
        Category category = item.getCategory();
        newItem.setCategory(category);
        newItem.setDescription(item.getDescription());
        newItem.setPrice(item.getPrice());
        newItem.setStock(item.getStock());
        newItem.setImageName(item.getImageName());
        if (item.getStock() <= 0) {
            redirectAttributes.addFlashAttribute("errorMessage", "Sorry, Item Out Of Stock");
        } else if (dropdown.getStock() > item.getStock()) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Sorry, Max Quantity For This Item Is " + item.getStock());
        } else {
            System.out.println(dropdown.getProductSizeIds());
            for (Long sizeids : dropdown.getProductSizeIds()) {
                System.out.println(sizeids);
                System.out.println(sizeService.getSizeById(sizeids).get().getName());
                newItem.setSize(sizeService.getSizeById(sizeids).get().getName());
            }
            item.setQuantity(dropdown.getStock());
            item.setPrice(item.getPrice() * item.getQuantity());
            newItem.setQuantity(dropdown.getStock());
            newItem.setPrice(item.getPrice());
            GlobalData.cart.add(newItem);
            redirectAttributes.addFlashAttribute("successMessage", "Item Added To Cart!");
        }
        return "redirect:/product/product-detail/{id}";
    }

    @GetMapping("/checkout")
    public String checkout() {
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String createOrder(OrderDTO orderDTO, RedirectAttributes redirectAttributes) throws IOException {
        if (GlobalData.cart == null || GlobalData.cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cart is Empty");
        } else {
            OrderDetails newOrder = new OrderDetails();
            newOrder.setEmail(orderDTO.getEmail());
            newOrder.setName(orderDTO.getName());
            newOrder.setTotal(GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
            newOrder.setStatus(1);
            orderService.addOrder(newOrder);
            List<OrderItem> orderItems = new ArrayList<>();
            for (Product item : GlobalData.cart) {
                OrderItem newOrderItem = new OrderItem();
                newOrderItem.setOrderID(newOrder.getId());
                newOrderItem.setOrderDetails(newOrder);
                newOrderItem.setProduct(item);
                newOrderItem.setQuantity(item.getQuantity());
                newOrderItem.setPrice(item.getPrice());
                newOrderItem.setSize(item.getSize());
                orderItemService.createOrderItem(newOrderItem);
                orderItemService.decreasingStock(newOrderItem.getProduct().getId(), newOrderItem.getQuantity()); //decreasing stock
                orderItems.add(newOrderItem);
            }
            GlobalData.cart.removeAll(GlobalData.cart);
            redirectAttributes.addFlashAttribute("successMessage", "Order Placed Successfully!");
        }
        return "redirect:/cart";
    }
}
