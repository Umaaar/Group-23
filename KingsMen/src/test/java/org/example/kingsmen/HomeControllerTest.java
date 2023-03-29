// package org.example.kingsmen;


// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.mockito.ArgumentMatchers.anyList;
// import static org.mockito.ArgumentMatchers.eq;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.verify;
// import static org.mockito.Mockito.verifyNoMoreInteractions;
// import static org.mockito.Mockito.when;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import javax.servlet.http.HttpServletResponse;

// import org.example.controller.HomeController;
// import org.example.model.Category;
// import org.example.model.CustomUserDetail;
// import org.example.model.Role;
// import org.example.service.CategoryService;
// import org.example.service.ContactService;
// import org.example.service.CustomUserDetailService;
// import org.example.service.OrderItemService;
// import org.example.service.OrderService;
// import org.junit.Test;
// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.junit.runner.RunWith;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.MockitoAnnotations;
// import org.mockito.junit.MockitoJUnitRunner;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.setup.MockMvcBuilders;
// import org.springframework.ui.Model;
// import org.springframework.ui.ModelMap;


// @RunWith(MockitoJUnitRunner.class)
// public class  HomeControllerTest {
   
//     @InjectMocks
//     private HomeController homeController;

//     @Mock
//     private CategoryService categoryService;

//     @Mock
//     private ContactService contactService;

//     @Mock
//     private OrderService orderService;

//     @Mock
//     private OrderItemService orderItemService;

//     @Mock
//     private CustomUserDetailService customUserDetailService;

//     @Mock
//     private CustomUserDetail customUserDetail;

//     @Mock 
//     private HttpServletResponse response;
    
//     @Mock
//     private Model model;

//     private MockMvc mockMvc;

//     @BeforeAll
//     public void setUp() {
//         mockMvc = MockMvcBuilders.standaloneSetup(homeController).build();
//     }

//     @Test
//     public void testHomePage() throws Exception {
//         // Mock the service call
//         List<Category> categories = Arrays.asList(new Category(), new Category());
//         when(categoryService.getAllCategory()).thenReturn(categories);

//         // Call the controller method and verify the result
//         String result = homeController.homePage(mock(Model.class));
//         assertEquals("/frontend-views/index", result);
//     }




   
// }
