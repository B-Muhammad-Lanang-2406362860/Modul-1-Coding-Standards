package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product){
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        product.setProductId(uuidString);
        productData.add(product);
        return product;
    }

    public Product edit(Product product){
        String productUUID = product.getProductId();
        Product productFromRepo = this.findById(productUUID);
        if (productFromRepo != null) {
            productFromRepo.setProductName(product.getProductName());
            productFromRepo.setProductQuantity(product.getProductQuantity());
        }
        return product;
    }

    public Product findById(String queryUUID){
        for(Product product: productData){
            String currentProductUUID = product.getProductId();
            if (currentProductUUID.equals(queryUUID)){
                return product;
            }
        }
        return null;
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}
