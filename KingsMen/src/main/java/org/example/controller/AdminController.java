package org.example.controller;


import org.example.dto.OrderDTO;
import org.example.dto.ProductDTO;
import org.example.model.Category;
import org.example.model.CustomUserDetail;
import org.example.model.OrderDetails;
import org.example.model.Product;
import org.example.model.Size;
import org.example.service.CategoryService;
import org.example.service.CustomUserDetailService;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import javax.websocket.Session;

@Controller
public class AdminController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/KingsMen/src/main/resources/static/images/productImage";
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SizeService sizeService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    OrderService orderService;
   @GetMapping("/admin")
   public String adminHome(@AuthenticationPrincipal CustomUserDetail authentication, HttpServletResponse response ,Model model){
       System.out.println(customUserDetailService.getUserCount());
       model.addAttribute("adminname",authentication.getFirstname());
       model.addAttribute("total_customers",String.valueOf(customUserDetailService.getUserCount()));
       model.addAttribute("total_catagories",String.valueOf(categoryService.getCatCount()));
       model.addAttribute("total_products",String.valueOf(productService.getProductCount()));
       model.addAttribute("total_in_stock_products",String.valueOf(productService.getInStockProducts()));
       model.addAttribute("total_in_out_of_stock_products",String.valueOf(productService.getOutOfStockProducts()));
       model.addAttribute("total_orders",String.valueOf(orderService.getOrderCount()));
       return "/backend-views/admin-index";
   }


   
/* ---------------------------------------------------Category CRUD Mapping ------------------------------------------------- */
   @GetMapping("/admin/categories")
   public String getCat(@AuthenticationPrincipal CustomUserDetail authentication, Model model){ 
    model.addAttribute("adminname",authentication.getFirstname());
    model.addAttribute("categories", categoryService.getAllCategory());
       System.out.println(categoryService.getAllCategory());
    return "/backend-views/categories" ;
   }

    @GetMapping("/admin/categories/create")
    public String postCreateCat(@AuthenticationPrincipal CustomUserDetail authentication, Model model){
        model.addAttribute("adminname",authentication.getFirstname());
        model.addAttribute("category", new Category());
     return "/backend-views/category-create";
    }

    @PostMapping("/admin/categories/create")
    public String adminCreateCat(@ModelAttribute("category") Category category){
        categoryService.addCategory(category);
        System.out.println("it works");
     return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id){
        categoryService.removeCategoryById(id);
        return "redirect:/admin/categories";

    }

    @GetMapping("/admin/categories/update/{id}")
    public String editCategory(@PathVariable int id, Model model){
        Optional<Category> category = categoryService.getCategoryById(id);
        if(category.isPresent()){
            model.addAttribute("category", category.get());
            return "/backend-views/category-create";
        }else{
            return "404";
        }
    }
/* --------------------------------------------------- End of Category CRUD Mapping ----------------------------------------------- */

/* --------------------------------------------------- Product CRUD Mapping --------------------------------------------------------*/

@GetMapping("/admin/products")
public String products(Model model ,String keyword) {

    if (keyword != null) {
        System.out.println(keyword);
        model.addAttribute("products", productService.findByKeyword(keyword));

//        System.out.println(productService.findByKeyword(keyword));
    } else {
        model.addAttribute("products", productService.getAllProduct());
        

    }
    return "/backend-views/products";
}
public String productspage(@AuthenticationPrincipal CustomUserDetail authentication, Model model){
    model.addAttribute("adminname",authentication.getFirstname());
   model.addAttribute("products", productService.getAllProduct());
   model.addAttribute("sizes", sizeService.getAllSizes());

   return "/backend-views/products";
}


@GetMapping("/admin/products/create")
public String createProducts(@AuthenticationPrincipal CustomUserDetail authentication, Model model){
    model.addAttribute("adminname",authentication.getFirstname());
   model.addAttribute("productDTO", new ProductDTO());
   model.addAttribute("categories", categoryService.getAllCategory());
    model.addAttribute("sizes", sizeService.getAllSizes());
    
   return "/backend-views/products-create";

}
@PostMapping("/admin/products/create")
public String createProductsPost(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException{
 Product product = new Product();
  product.setId(productDTO.getId());
    System.out.println(productDTO.getId());
  product.setName(productDTO.getName());
  Category category = categoryService.getCategoryById(productDTO.getCategoryId()).get();
  product.setCategory(category);
  product.setDescription(productDTO.getDescription());
  product.setPrice(productDTO.getPrice());
  product.setStock(productDTO.getStock());
 // product.setSize(category.getSizes().get(0));
//   List<Size> sizes = new ArrayList<>();
// for (Long sizeId : productDTO.getSizeIds()) {
//     Size size = category.getSizes().get(sizeId.intValue());
//     sizes.add(size);
// }
// product.setSizes(sizes);
List<Size> sizes = new ArrayList<>();
for (Long sizeId : productDTO.getSizeIds()) {
    Size size = sizeService.getSizeById(sizeId).get();
    sizes.add(size);
}
product.setSizes(sizes);

    String imageUUID;
    if(!file.isEmpty()){
        imageUUID = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get(uploadDirectory, imageUUID);
        Files.write(fileNameAndPath, file.getBytes());
    }else{
        imageUUID = imgName;
    }
    product.setImageName(imageUUID);
    productService.addProduct(product);
    
    return "redirect:/admin/products";
                                 
} 

@GetMapping("/admin/products/update/{id}")
public String updateProductGet(@PathVariable long id, Model model) {
Product product = productService.getProductById(id).get();
ProductDTO productDTO = new ProductDTO();
productDTO.setId(product.getId());
productDTO.setName(product.getName());
productDTO.setCategoryId(product.getCategory().getId());
productDTO.setDescription(product.getDescription());
productDTO.setPrice(product.getPrice());
productDTO.setStock(product.getStock());
//Category category = categoryService.getCategoryById(product.getCategory().getId()).get();
//productDTO.setSizeIds(category.getSizes().get(0).getId());
List<Long> sizeIds = new ArrayList<>();
for (Size size : product.getSizes()) {
    sizeIds.add(size.getId());
}
productDTO.setSizeIds(sizeIds);
productDTO.setImageName(product.getImageName());

model.addAttribute("productDTO", productDTO);
model.addAttribute("categories", categoryService.getAllCategory());

    return "/backend-views/products-create";
}

@GetMapping("/admin/products/delete/{id}")
public String deleteProduct(@PathVariable long id){
    productService.removeProductById(id);
    return "redirect:/admin/products";
}










/* --------------------------------------------------- End of Product CRUD Mapping ------------------------------------------------- */

/* --------------------------------------------------- Size CRUD Mapping --------------------------------------------------------*/


@GetMapping("/admin/size")
public String sizes(Model model){
   model.addAttribute("size", sizeService.getAllSizes());
   return "/backend-views/size";
}

@GetMapping("/admin/size/create")
public String createSizeGet(Model model){
    model.addAttribute("size", new Size());
   return "/backend-views/size-create";
}

@PostMapping("/admin/size/create")
public String createSizePost(@ModelAttribute("size") Size size){
    sizeService.saveSize(size);
   return "redirect:/admin/size";
}


@GetMapping("/admin/size/delete/{id}")
public String deleteSize(@PathVariable Long id){
    sizeService.deleteSize(id);
    return "redirect:/admin/size";

}

@GetMapping("/admin/size/update/{id}")
public String updateSizeGet(@PathVariable Long id, Model model){
    Optional<Size> size = sizeService.getSizeById(id);
    if(size.isPresent()){
        model.addAttribute("size", size.get());
        return "/backend-views/size-create";
    }else{
        return "404";
    }
}





/* --------------------------------------------------- End of Size CRUD Mapping --------------------------------------------------------*/


    @GetMapping("/admin/orders")
    public String adminOrders(Model model ,String keyword){

        if(keyword!=null){
            model.addAttribute("orders",orderService.findByKeyword(keyword) );

        }else{
            model.addAttribute("orders",orderService.getAllOrders() );

        }
        return "/backend-views/admin-orders";
    }



    public String adminOrdersPage(@AuthenticationPrincipal CustomUserDetail authentication, Model model){ 
        model.addAttribute("adminname",authentication.getFirstname());
        return "/backend-views/admin-orders";
}
    @GetMapping("/admin/orders/delete/{id}")
    public String removeOrder(@PathVariable int id){

        orderService.removeProductById(id);
        return "redirect:/admin/orders";
    }
    @GetMapping("/admin/orders/update/{id}")
    public String updateOrderGet(@PathVariable Integer id, Model model) {
        System.out.println("it worked");
        OrderDetails order = orderService.getOrderByorder_id(id).get();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
//        orderDTO.setOrder_products(order.getOrder_products());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setEmail(order.getEmail());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setName(order.getName());

        model.addAttribute("orderDTO", orderDTO);


        return "/backend-views/orders-update";
    }




    @GetMapping("/admin/accounts")
    public String adminAccounts(@AuthenticationPrincipal CustomUserDetail authentication, Model model){ 
        model.addAttribute("adminname",authentication.getFirstname());
        return "/backend-views/admin-accounts";
}

    @GetMapping("/admin/accounts/add")
    public String adminAddProduct(@AuthenticationPrincipal CustomUserDetail authentication, Model model){
        model.addAttribute("adminname",authentication.getFirstname());

         return "/backend-views/admin-addProduct";
}

@GetMapping("/admin/customers")
public String adminCustomers(@AuthenticationPrincipal CustomUserDetail authentication, Model model){ 
    model.addAttribute("adminname",authentication.getFirstname());

    return "/backend-views/customers";
}

@GetMapping("/admin/queries")
public String adminQueries(@AuthenticationPrincipal CustomUserDetail authentication, Model model){ 
    model.addAttribute("adminname",authentication.getFirstname());
    return "/backend-views/customer-queries";
}
}