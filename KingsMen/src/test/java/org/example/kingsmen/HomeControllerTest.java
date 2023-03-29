 package org.example.kingsmen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.annotation.Before;
import org.example.controller.HomeController;
import org.example.model.Category;
import org.example.model.Contact;
import org.example.model.CustomUserDetail;
import org.example.model.OrderDetails;
import org.example.model.OrderItem;
import org.example.service.CategoryService;
import org.example.service.ContactService;
import org.example.service.CustomUserDetailService;
import org.example.service.OrderItemService;
import org.example.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
 public class HomeControllerTest {
 
     @Mock
     private ContactService contactService;
 
     @Mock
     private CategoryService categoryService;
 
     @Mock
     private OrderService orderService;
 
     @Mock
     private OrderItemService orderItemService;
 
     @Mock
     private CustomUserDetailService customUserDetailService;
 
     @InjectMocks
     private HomeController homeController;
 
     @Mock
     private Model model;
 
     @Mock
     private HttpServletResponse response;
 
     @Mock
     private RedirectAttributes redirectAttributes;
 
     @Mock
     private Contact contact;
 
     @Mock
     private CustomUserDetail customUserDetail;
 
     @Mock
     private OrderDetails orderDetails;
 
     @Mock
     private OrderItem orderItem;
 
        @Before(value = "")
        public void setUp() {
            MockitoAnnotations.initMocks(this);
        }
     @Test
     public void testHomePage() {
         List<Category> categories = new ArrayList<>();
         Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
         String result = homeController.homePage(model);
         assertEquals("/frontend-views/index", result);
         Mockito.verify(model).addAttribute("categories", categories);
     }
 
     @Test
     public void testDashboard() {
         List<Category> categories = new ArrayList<>();
         Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
         String result = homeController.dashboard(customUserDetail, response, model);
         assertEquals("/frontend-views/customer-dashboard", result);
         Mockito.verify(model).addAttribute("firstname", customUserDetail.getFirstname());
         Mockito.verify(model).addAttribute("lastname", customUserDetail.getLastname());
         Mockito.verify(model).addAttribute("email", customUserDetail.getEmail());
         Mockito.verify(model).addAttribute("categories", categories);
     }
 
     @Test
     public void testOrders() {
         List<Category> categories = new ArrayList<>();
         List<OrderDetails> orders = new ArrayList<>();
         Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
         Mockito.when(orderService.findByKeyword(customUserDetail.getFirstname())).thenReturn(orders);
         String result = homeController.Orders(orderDetails, customUserDetail, response, model);
         assertEquals("/frontend-views/orders", result);
         Mockito.verify(model).addAttribute("orders", orders);
         Mockito.verify(model).addAttribute("categories", categories);
     }
 
     @Test
     public void testGetOrderItems() {
         List<Category> categories = new ArrayList<>();
         List<OrderItem> orderItems = new ArrayList<>();
         Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
         Mockito.when(orderService.getOrderByorder_id(orderDetails.getId())).thenReturn(Optional.of(orderDetails));
         Mockito.when(orderItemService.getAllOrderItemsByID(orderDetails.getId())).thenReturn(orderItems);
         String result = homeController.getOrderItems(orderDetails.getId(), model, customUserDetail);
         assertEquals("/frontend-views/order-details", result);
         Mockito.verify(model).addAttribute("orderItems", orderItems);
         Mockito.verify(model).addAttribute("categories", categories);
     }
 
     @Test
     public void testContactPage() {
         List<Category> categories = new ArrayList<>();
         Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
         String result = homeController.ContactPage(model);
         assertEquals("/frontend-views/contact-details", result);
         Mockito.verify(model).addAttribute("contact", new Contact());
         Mockito.verify(model).addAttribute("categories", categories);
     }

     @Test
     public void testPostContact() {
         Mockito.when(contactService.save(contact)).thenReturn(contact);
         String result = homeController.postContact(contact, redirectAttributes);
         assertEquals("redirect:/contact-us", result);
         Mockito.verify(redirectAttributes).addFlashAttribute(
            "successMessage",
            "Message sent successfully."
        );
             }
     

    @Test
    public void testPostContact_withDataIntegrityViolationException() {
        Mockito.doThrow(DataIntegrityViolationException.class).when(contactService).save(contact);
        String result = homeController.postContact(contact, redirectAttributes);
        assertEquals("redirect:/contact-us", result);
        Mockito.verify(redirectAttributes, Mockito.never()).addFlashAttribute("successMessage", "Message sent successfully.");
        Mockito.verify(redirectAttributes).addFlashAttribute("errorMessage", "Please try again, message was not sent.");
    }

    @Test
    public void testAboutPage() {
        Model model = Mockito.mock(Model.class);
        List<Category> categories = new ArrayList<>();
        Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
        String result = homeController.AboutPage(model);
        assertEquals("/frontend-views/about-us", result);
        Mockito.verify(model).addAttribute("categories", categories);
    }

    @Test
    public void testFaq() {
        Model model = Mockito.mock(Model.class);
        List<Category> categories = new ArrayList<>();
        Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
        String result = homeController.Faq(model);
        assertEquals("/frontend-views/faq", result);
        Mockito.verify(model).addAttribute("categories", categories);
    }

    @Test
    public void testReturns() {
        Model model = Mockito.mock(Model.class);
        List<Category> categories = new ArrayList<>();
        Mockito.when(categoryService.getAllCategory()).thenReturn(categories);
        String result = homeController.Returns(model);
        assertEquals("/frontend-views/returns", result);
        Mockito.verify(model).addAttribute("categories", categories);
    }
    } 