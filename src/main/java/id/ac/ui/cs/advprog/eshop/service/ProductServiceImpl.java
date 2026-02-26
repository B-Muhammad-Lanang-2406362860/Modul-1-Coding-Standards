package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void create(Product product){
        this.validateProduct(product);

        productRepository.create(product);
    }

    @Override
    public void edit(Product product){
        this.validateProduct(product);

        productRepository.edit(product);
    }

    @Override
    public void delete(String productId){
        Product product = this.findProductById(productId);
        nullValidation(product);

        productRepository.delete(product);
    }

    @Override
    public Product findById(String productId){
        Product product = productRepository.findById(productId);

        if (product == null) {
            throw new NoSuchElementException("Product Not Found");
        }

        return product;
    }

    public void validateProduct(Product product) {
        nullValidation(product);

        String productName = product.getProductName();
        int productQuantity = product.getProductQuantity();

        if (productName == null || productName.isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be empty!");
        }

        if (productQuantity < 0) {
            throw new IllegalArgumentException("Product quantity cannot be negative!");
        }
    }

    public void nullValidation(Product product){
        if (product == null) {
            throw new IllegalArgumentException("Product cannot be null!");
        }
    }

    @Override
    public List<Product> findAll(){
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProduct = new ArrayList<>();
        productIterator.forEachRemaining(allProduct::add);
        return allProduct;
    }
}
