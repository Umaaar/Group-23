// package org.example.kingsmen;

// import org.example.model.Product;
// import org.example.service.CategoryService;
// import org.example.service.ProductService;
// import org.junit.jupiter.api.AfterEach;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.test.web.servlet.MvcResult;
// import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
// import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class AdminIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private ProductService productService;

//     @Autowired
//     private CategoryService categoryService;

//     private String baseUrl = "localhost:8080/admin/products";

//     private Product product;

//     @BeforeEach
//     void setUp() {
//         product = new Product();
//         product.setName("Product 1");
//         product.setCategory(categoryService.getCategoryById(1).get());
//         product.setDescription("Product 1 description");
//         product.setPrice(100.00);
//         product.setStock(10);
//         product.setSize("M");
//         product.setImageName(null);
//         productService.addProduct(product);
//     }

//     @AfterEach
//     void tearDown() {
//         productService.deteteAllProducts();
//     }

//     @Test
//     void testGetAllProducts() throws Exception {
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andReturn();
//     }

//     @Test
//     void testGetProductById() throws Exception {
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(baseUrl + "/" + product.getId()))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andReturn();
//     }

//     @Test
//     void testAddProduct() throws Exception {
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
//                 .param("name", "Product 2")
//                 .param("category", "1")
//                 .param("description", "Product 2 description")
//                 .param("price", "200.00")
//                 .param("stock", "20")
//                 .param("size", "L")
//                 .param("imageName", null))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andReturn();
//     }

//     @Test
//     void testUpdateProduct() throws Exception {
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/" + product.getId())
//                 .param("name", "Product 1")
//                 .param("category", "1")
//                 .param("description", "Product 1 description")
//                 .param("price", "100.00")
//                 .param("stock", "10")
//                 .param("size", "M")
//                 .param("imageName", null))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andReturn();
//     }

//     @Test
//     void testDeleteProduct() throws Exception {
//         MvcResult result = mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/" + product.getId()))
//                 .andExpect(MockMvcResultMatchers.status().isOk())
//                 .andReturn();
//     }



// }






    

    

