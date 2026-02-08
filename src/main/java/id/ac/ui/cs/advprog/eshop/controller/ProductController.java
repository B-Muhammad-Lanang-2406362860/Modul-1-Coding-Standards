package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/create")
    public String createProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model){
        try {
            service.create(product);
        } catch (IllegalArgumentException exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "CreateProduct";
        }
        return "redirect:list";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable String productId, RedirectAttributes redirectAttributes, Model model){
        Product product;
        try {
            product = service.findProductById(productId);
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/product/list";
        }
        model.addAttribute("product", product);
        return "EditProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, RedirectAttributes redirectAttributes, Model model){
        try {
            service.edit(product);
        } catch (Exception exception) {
            model.addAttribute("errorMessage", exception.getMessage());
            return "EditProduct";
        }
        return "redirect:list";
    }

    @PostMapping("/delete/{productId}")
    public String deleteProductPost(@PathVariable String productId, RedirectAttributes redirectAttributes, Model model){
        try {
            service.delete(productId);
        } catch (Exception exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
        }
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = service.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }

}
