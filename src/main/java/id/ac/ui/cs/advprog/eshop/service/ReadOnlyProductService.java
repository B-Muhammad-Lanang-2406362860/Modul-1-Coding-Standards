package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface ReadOnlyProductService {
    public Product findById(String queryUUID);
    public List<Product> findAll();
}
