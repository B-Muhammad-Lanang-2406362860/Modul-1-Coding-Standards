package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface ProductManagementService {
    public void create(Product product);
    public void edit(Product product);
    public void delete(String productId);
}
