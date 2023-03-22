package org.example.controller;


import org.example.dto.OrderDTO;
import org.example.dto.ProductDTO;
import org.example.dto.ProductSizeDTO;
import org.example.model.*;

import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    ProductSizeService productSizeService;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

   

   @GetMapping("/admin")
   public String adminHome(@AuthenticationPrincipal CustomUserDetail authentication ,Model model){
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
   public String getCat(Model model){ 
    model.addAttribute("categories", categoryService.getAllCategory());
       System.out.println(categoryService.getAllCategory());
    return "/backend-views/categories" ;
   }

    @GetMapping("/admin/categories/create")
    public String postCreateCat(Model model){
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
public String productspage(Model model){
   model.addAttribute("products", productService.getAllProduct());
   model.addAttribute("sizes", sizeService.getAllSizes());

   return "/backend-views/products";
}


@GetMapping("/admin/products/create")
public String createProducts( Model model){
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
model.addAttribute("sizes", sizeService.getAllSizes());
Product product = productService.getProductById(id).get();
ProductDTO productDTO = new ProductDTO();
productDTO.setId(product.getId());
productDTO.setName(product.getName());
productDTO.setCategoryId(product.getCategory().getId());
productDTO.setDescription(product.getDescription());
productDTO.setPrice(product.getPrice());
productDTO.setStock(product.getStock());

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





@GetMapping("/admin/products/productSize/{id}")
public String productSize(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    try {
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + id));
        List<ProductSize> productSizes = productSizeService.getProductSizesByProductId(id);
        model.addAttribute("product", product);
        model.addAttribute("sizes", sizeService.getAllSizes());
        model.addAttribute("productSizes", productSizes);
        return "/backend-views/products-size";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("error", "Could not load product sizes.");
        return "redirect:/admin/ims";
    }
}

@GetMapping("/admin/products/productSize/create")
public String createProductSizeGet(Model model) {
    model.addAttribute("productSizeDTO", new ProductSizeDTO());
    model.addAttribute("products", productService.getAllProduct());
    model.addAttribute("sizes", sizeService.getAllSizes());
    return "/backend-views/product-size-create";
}


@PostMapping("/admin/products/productSize/create")
public String createProductSizePost(@ModelAttribute("productSizeDTO") ProductSizeDTO productSizeDTO, RedirectAttributes redirectAttributes) {
    try {
        ProductSize productSize = new ProductSize();
        Long id = productSizeDTO.getId();
        if (id != null) {
            productSize.setId(id);
        }
        Product product = productService.getProductById(productSizeDTO.getProductId()).orElseThrow(() -> new IllegalArgumentException("Invalid product ID: " + productSizeDTO.getProductId()));
        productSize.setProduct(product);
        Size size = sizeService.getSizeById(productSizeDTO.getSizeId()).orElseThrow(() -> new IllegalArgumentException("Invalid size ID: " + productSizeDTO.getSizeId()));
        productSize.setSize(size);
        productSize.setQuantity(productSizeDTO.getQuantity());
        productSizeService.saveProductSize(productSize);
        redirectAttributes.addFlashAttribute("successMessage", "Product size added successfully.");
        return "redirect:/admin/ims";
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Could not add product size.");
        return "redirect:/admin/products/productSize/create";
    }
}

@GetMapping("/admin/products/productSize/delete/{id}")
    public String deleteProductSize(@PathVariable Long id, RedirectAttributes redirectAttributes) {
    try {
    productSizeService.deleteProductSizeById(id);
    redirectAttributes.addFlashAttribute("successMessage", "Product size deleted successfully");
    } catch (Exception e) {
    redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the product size");
    }
    return "redirect:/admin/ims";
}

@GetMapping("/admin/products/productSize/update/{id}")
    public String updateProductSizeGet(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
    try {
    ProductSize productSize = productSizeService.getProductSizeById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product size ID: " + id));
    ProductSizeDTO productSizeDTO = new ProductSizeDTO();
    productSizeDTO.setId(productSize.getId());
    productSizeDTO.setProductId(productSize.getProduct().getId());
    productSizeDTO.setSizeId(productSize.getSize().getId());
    productSizeDTO.setQuantity(productSize.getQuantity());
    model.addAttribute("productSizeDTO", productSizeDTO);
    model.addAttribute("products", productService.getAllProduct());
    model.addAttribute("sizes", sizeService.getAllSizes());
    return "/backend-views/product-size-create";
    } catch (Exception e) {
    redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating the product size");
    return "redirect:/admin/ims";
    }
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
public String deleteSize(@PathVariable Long id, RedirectAttributes redirectAttributes){
    try {
        sizeService.deleteSize(id);
        redirectAttributes.addFlashAttribute("successMessage", "Size deleted successfully.");
    } catch (DataIntegrityViolationException e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete this size as it is associated with one or more products.");
    }
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



    public String adminOrdersPage(@AuthenticationPrincipal CustomUserDetail authentication,Model model){ 
        model.addAttribute("adminname",authentication.getFirstname());
        return "/backend-views/admin-orders";
}
    @GetMapping("/admin/orders/delete/{id}")
    public String removeOrder(@PathVariable int id){

        orderService.removeProductById(id);
        return "redirect:/admin/orders";
    }
    @GetMapping("/admin/orders/update/{id}")
    public String updateOrderGet(@PathVariable Integer id,  Model model) {
        System.out.println("it worked");
        OrderDetails order = orderService.getOrderByorder_id(id).get();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
//        orderDTO.setOrder_products(order.getOrder_products());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setEmail(order.getEmail());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setName(order.getName());

        model.addAttribute("orderDTO", order);
        List<OrderItem> orderItemList = orderItemService.getAllOrderItemsByID(order.getId());
        model.addAttribute("productsOrdered", orderItemList);

        System.out.println("this is the size of the list of the order item" +orderItemList.size());



        return "/backend-views/orders-update";
    }
    @PostMapping("/admin/orders/update/{id}")
    public String updateOrderPost(@PathVariable Integer id, Model model) {


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
    model.addAttribute("customers",customUserDetailService.getAllUsers());
    return "/backend-views/customers";
}

@GetMapping("/admin/queries")
public String adminQueries(@AuthenticationPrincipal CustomUserDetail authentication, Model model){ 
    model.addAttribute("adminname",authentication.getFirstname());
    return "/backend-views/customer-queries";
}

@GetMapping("/admin/ims")
public String adminIMS(@AuthenticationPrincipal CustomUserDetail authentication, Model model){
    model.addAttribute("adminname",authentication.getFirstname());
    model.addAttribute("productSizes", productSizeService.getAllProductSizes());

    return "backend-views/products-size";
}
}

