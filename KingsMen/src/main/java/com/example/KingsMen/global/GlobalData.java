// package com.example.KingsMen.global;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// import com.example.KingsMen.service.ProductService;
// import com.example.KingsMen.repository.CatagoryRepository;
// import com.example.KingsMen.model.Category;
// import com.example.KingsMen.model.Product;

//     @Component
//     public class GlobalData implements CommandLineRunner{
//         private final ProductService productService;
//         private final CatagoryRepository categoryRepository;   
//         private static final Logger logger = LoggerFactory.getLogger(GlobalData.class);

//     @Autowired
//     public GlobalData(ProductService productService, CatagoryRepository categoryRepository){
//         this.productService = productService;
//         this.categoryRepository = categoryRepository;

//     }

//     @Override
//     public void run(String... args){
//         exampleProducts();
//         category();
//     }

//     private void category(){
//         Category category1 = new Category();
//         Category category2 = new Category();
//         Category category3 = new Category();
        
//         category1.setCategoryName("Blazer");

//         category1.setCategoryName("Shirts");

//         category1.setCategoryName("Shoes");

//         categoryRepository.save(category1);
//         categoryRepository.save(category2);
//         categoryRepository.save(category3);


//     }


//     private void exampleProducts(){

//         Product product1 = new Product();
//         Product product2 = new Product();
//         Product product3 = new Product();
//         Product product4 = new Product();
//         Product product5 = new Product();

//         product1.setName("Blue Blazer");
//         product1.setCategory(categoryRepository.findByCategoryName("Blazer"));
//         product1.setPrice(99.9);
//         product1.setImageName("IMG_URL");
//         product1.setStock(10);
//         product1.setSize("M");
       

//         product2.setName("Black Blazer");
//         product2.setCategory(categoryRepository.findByCategoryName("Blazer"));
//         product2.setPrice(99.9);
//         product2.setImageName("IMG_URL");
//         product2.setStock(10);
//         product2.setSize("M");


//         product3.setName("Red Blazer");
//         product3.setCategory(categoryRepository.findByCategoryName("Blazer"));
//         product3.setPrice(99.9);
//         product3.setImageName("IMG_URL");
//         product3.setStock(10);
//         product3.setSize("M");

//         product4.setName("White Blazer");
//         product4.setCategory(categoryRepository.findByCategoryName("Blazer"));
//         product4.setPrice(99.9);
//         product4.setImageName("IMG_URL");
//         product4.setStock(10);
//         product4.setSize("M");

//         product5.setName("Grey Blazer");
//         product5.setCategory(categoryRepository.findByCategoryName("Blazer"));
//         product5.setPrice(99.9);
//         product5.setImageName("IMG_URL");
//         product5.setStock(10);
//         product5.setSize("M");

//         productService.save(product1);
//         productService.save(product2);
//         productService.save(product3);
//         productService.save(product4);
//         productService.save(product5);

//     }   
// }