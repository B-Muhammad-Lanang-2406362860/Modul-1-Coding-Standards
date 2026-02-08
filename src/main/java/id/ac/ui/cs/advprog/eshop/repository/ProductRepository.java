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

    public void create(Product product){
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();

        product.setProductId(uuidString);
        productData.add(product);
    }

    public void edit(Product product){
        String productId = product.getProductId();
        Product productFromRepo = this.findById(productId);

        if (productFromRepo != null) {
            productFromRepo.setProductName(product.getProductName());
            productFromRepo.setProductQuantity(product.getProductQuantity());
        }
    }

    public void delete(Product product){
        if (product != null) {
            this.productData.remove(product);
        }
    }

    public Product findById(String productId){
        for(Product product: productData){
            String currentProductId = product.getProductId();
            if (currentProductId.equals(productId)){
                return product;
            }
        }
        return null;
    }

    public Iterator<Product> findAll(){
        return productData.iterator();
    }
}
