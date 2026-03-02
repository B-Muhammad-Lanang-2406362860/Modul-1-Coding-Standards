package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductManagementService;
import id.ac.ui.cs.advprog.eshop.service.ReadOnlyProductService;
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
    private ReadOnlyProductService readService;

    @Autowired
    private ProductManagementService writeService;

    @GetMapping("/create")
    public String createProductPage(Model model){
        Product product = new Product();
        model.addAttribute("product", product);
        return "CreateProduct";
    }

    @PostMapping("/create")
    public String createProductPost(@ModelAttribute Product product, Model model){
        writeService.create(product);
        return "redirect:list";
    }

    @GetMapping("/edit/{productId}")
    public String editProductPage(@PathVariable String productId, RedirectAttributes redirectAttributes, Model model){
        Product product = readService.findById(productId);
        model.addAttribute("product", product);
        return "EditProduct";
    }

    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, RedirectAttributes redirectAttributes, Model model){
        System.out.println(product.getProductId());
        writeService.edit(product);
        return "redirect:list";
    }

    @PostMapping("/delete/{productId}")
    public String deleteProductPost(@PathVariable String productId, RedirectAttributes redirectAttributes, Model model){
        writeService.delete(productId);
        return "redirect:/product/list";
    }

    @GetMapping("/list")
    public String productListPage(Model model){
        List<Product> allProducts = readService.findAll();
        model.addAttribute("products", allProducts);
        return "ProductList";
    }
}

