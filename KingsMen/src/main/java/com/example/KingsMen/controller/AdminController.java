package com.example.KingsMen.controller;

import com.example.KingsMen.dto.ProductDTO;
import com.example.KingsMen.model.Category;
import com.example.KingsMen.model.Product;
import com.example.KingsMen.service.CategoryService;
import com.example.KingsMen.service.ProductService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/productImage";
    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;


   @GetMapping("/admin")
   public String adminHome(){
       return "/backend-views/admin-index";
   }
   
/* ---------------------------------------------------Category CRUD Mapping ------------------------------------------------- */
   @GetMapping("/admin/categories")
   public String getCat(Model model){ 
    model.addAttribute("categories", categoryService.getAllCategory());
       System.out.println(categoryService.getAllCategory());
    return "/backend-views/categories";
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
public String products(Model model){
   model.addAttribute("products", productService.getAllProduct());
   return "/backend-views/products";
}

@GetMapping("/admin/products/create")
public String createProducts(Model model){
   model.addAttribute("productDTO", new ProductDTO());
   model.addAttribute("categories", categoryService.getAllCategory());
   return "/backend-views/products-create";

}
@PostMapping("/admin/products/create")
public String createProductsPost(@ModelAttribute("productDTO") ProductDTO productDTO,
                                @RequestParam("productImage") MultipartFile file,
                                @RequestParam("imgName") String imgName) throws IOException{
 Product product = new Product();
  product.setId(productDTO.getId());
  product.setName(productDTO.getName());
  product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
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

@GetMapping("/admin/products/delete/{id}")
public String deleteProduct(@PathVariable long id){
    productService.removeProductById(id);
    return "redirect:/admin/products";
}

@GetMapping("/admin/products/update/{id}")
public String editProduct(@PathVariable long id, Model model){
    Optional<Product> product = productService.getProductById(id);
    if(product.isPresent()){
        model.addAttribute("productDTO", product.get());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "/backend-views/products-create";
    }else{
        return "404";
    }
}








/* --------------------------------------------------- End of Product CRUD Mapping ------------------------------------------------- */

/* --------------------------------------------------- Size CRUD Mapping --------------------------------------------------------*/


/*@GetMapping("/admin/size")
public String sizes(Model model){
   model.addAttribute("size", sizeService.getAllSizes());
   return "/backend-views/size";
}

@GetMapping("/admin/size/create")
public String createSizeGet(Model model){
   model.addAttribute("sizeDTO", new SizesDTO());
   model.addAttribute("categories", categoryService.getAllCategory());
   return "/backend-views/size-create";
}
@PostMapping("/admin/size/create")
public String createSizePost(@ModelAttribute("sizeDTO") SizesDTO sizeDTO){
   Sizes size = new Sizes();
    size.setCategory(categoryService.getCategoryById(sizeDTO.getCategoryId()).get());
    size.setSize(sizeDTO.getSize());
    sizeService.addSize(size);
    return "redirect:/admin/size";
}

@GetMapping("/admin/size/delete/{id}")
public String deleteSize(@PathVariable int id){
    sizeService.removeSizeById(id);
    return "redirect:/admin/size";

}

@GetMapping("/admin/size/update/{id}")
public String updateSizeGet(@PathVariable Long id, Model model){
Sizes size = sizeService.getSizeById(id);
SizesDTO sizeDTO = new SizesDTO();
sizeDTO.setId(size.getId());
sizeDTO.setCategoryId(size.getCategory().getId());
sizeDTO.setSize(size.getSize());
model.addAttribute("categories", categoryService.getAllCategory());
model.addAttribute("sizeDTO", sizeDTO);
return "/backend-views/size-create";

    
}
*/


/* --------------------------------------------------- End of Size CRUD Mapping --------------------------------------------------------*/







   


   

    @GetMapping("/admin/orders")
    public String adminOrders(){ return "/backend-views/admin-orders";
}

    @GetMapping("/admin/accounts")
    public String adminAccounts(){ return "/backend-views/admin-accounts";
}

    @GetMapping("/admin/accounts/add")
    public String adminAddProduct(){ return "/backend-views/admin-addProduct";
}




}
