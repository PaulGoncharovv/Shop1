package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.models.Category;
import com.example.springsecurityapplication.models.Image;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import com.example.springsecurityapplication.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class AdminController {

    private final ProductService productService;

    //Путь для фотографий
    @Value("${upload.path}")
    private String uploadPath;

    private final CategoryRepository categoryRepository;

    public AdminController(ProductService productService, CategoryRepository categoryRepository) {
        this.productService = productService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/admin")
    public String admin(Model model)
    {
        model.addAttribute("products", productService.getAllProduct());
    return "admin";
    }
    @GetMapping("admin/product/add")
    public String addProduct(Model model){
        model.addAttribute("product", new Product());
        model.addAttribute("category", categoryRepository.findAll());
        return "product/addProduct";
    }
    @PostMapping("/admin/product/add")
    public String addProduct(@ModelAttribute("product")
                             @Valid Product product, BindingResult bindingResult, @RequestParam("file_one")MultipartFile file_one,
                             @RequestParam("file_two")MultipartFile file_two, @RequestParam("file_three")MultipartFile file_three,
                             @RequestParam("file_four")MultipartFile file_four, @RequestParam("file_five")MultipartFile file_five, @RequestParam("category") int category, Model model) throws IOException {
        Category category_db = (Category) categoryRepository.findById(category).orElseThrow();
        System.out.println(category_db.getName());
        if (bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/addProduct";
        }
        if(file_one !=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_one.getOriginalFilename();
            file_one.transferTo(new File(uploadPath + "/" + resultFileName));
            //привязка фото к файлу 1:18
            Image image = new Image();
            image.setProduct(product);
            image.setFilename(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_two !=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_two.getOriginalFilename();
            file_two.transferTo(new File(uploadPath + "/" + resultFileName));
            //привязка фото к файлу 1:18
            Image image = new Image();
            image.setProduct(product);
            image.setFilename(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_three !=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_three.getOriginalFilename();
            file_three.transferTo(new File(uploadPath + "/" + resultFileName));
            //привязка фото к файлу 1:18
            Image image = new Image();
            image.setProduct(product);
            image.setFilename(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_four !=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_four.getOriginalFilename();
            file_four.transferTo(new File(uploadPath + "/" + resultFileName));
            //привязка фото к файлу 1:18
            Image image = new Image();
            image.setProduct(product);
            image.setFilename(resultFileName);
            product.addImageToProduct(image);
        }
        if(file_five !=null){
            File uploadDir = new File(uploadPath);
            if(!uploadDir.exists()){
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file_five.getOriginalFilename();
            file_five.transferTo(new File(uploadPath + "/" + resultFileName));
            //привязка фото к файлу 1:18
            Image image = new Image();
            image.setProduct(product);
            image.setFilename(resultFileName);
            product.addImageToProduct(image);
        }
        productService.saveProduct(product, category_db);
        return "redirect:/admin";
    }
    //УДАЛЕНИЕ
    @GetMapping("admin/product/delete/{id}")
    public String deleteProduct(@PathVariable("id")int id){
        productService.deleteProduct(id);
        return "redirect:/admin";
    }

    @GetMapping("admin/product/edit/{id}")
    public String editProduct(Model model, @PathVariable("id") int id){
        model.addAttribute("product", productService.getProductId(id));
        model.addAttribute("category", categoryRepository.findAll());
        return "product/editProduct";
    }

    //Обработка формы
    @PostMapping("admin/product/edit/{id}")
    public String editProduct(@ModelAttribute("product")@Valid Product product, BindingResult bindingResult, @PathVariable("id") int id, Model model){
        if(bindingResult.hasErrors()){
            model.addAttribute("category", categoryRepository.findAll());
            return "product/editProduct";
        }
        productService.updateProduct(id, product);
        return "redirect:/admin";
    }


}
