package id.ac.ui.cs.advprog.eshop.controller;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService service;

    // Test GET /product/create
    @Test
    void createProductPageShouldReturnCreateProductView() throws Exception {
        mockMvc.perform(get("/product/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("product"));
    }

    // Test POST /product/create success
    @Test
    void createProductPostShouldRedirectWhenSuccess() throws Exception {
        mockMvc.perform(post("/product/create")
                        .param("name", "Laptop")
                        .param("price", "1500"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service).create(org.mockito.ArgumentMatchers.any(Product.class));
    }

    // Test POST /product/create fail (IllegalArgumentException)
    @Test
    void createProductPostShouldReturnCreateProductViewWhenExceptionThrown() throws Exception {

        doThrow(new IllegalArgumentException("Invalid product"))
                .when(service)
                .create(org.mockito.ArgumentMatchers.any(Product.class));

        mockMvc.perform(post("/product/create")
                        .param("name", "BadProduct")
                        .param("price", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("CreateProduct"))
                .andExpect(model().attributeExists("errorMessage"));
    }


    // Test GET /product/edit/{productId} success
    @Test
    void editProductPageReturnsEditProductViewWhenProductExists() throws Exception {

        Product product = new Product();
        product.setProductId("1");

        when(service.findById("1")).thenReturn(product);

        mockMvc.perform(get("/product/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists("product"));
    }

    // Test GET /product/edit/{productId} fail
    @Test
    void editProductPageRedirectsWhenProductNotFound() throws Exception {

        when(service.findById("99"))
                .thenThrow(new RuntimeException("Not found"));

        mockMvc.perform(get("/product/edit/99"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    // Test POST /product/edit success
    @Test
    void editProductPostRedirectsWhenSuccessful() throws Exception {

        mockMvc.perform(post("/product/edit")
                        .param("id", "1")
                        .param("name", "UpdatedProduct")
                        .param("price", "200"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("list"));

        verify(service).edit(any(Product.class));
    }

    // Test POST /product/edit fail
    @Test
    void editProductPostReturnsEditProductViewWhenExceptionThrown() throws Exception {

        doThrow(new RuntimeException("Update failed"))
                .when(service)
                .edit(any(Product.class));

        mockMvc.perform(post("/product/edit")
                        .param("id", "1")
                        .param("name", "BadProduct")
                        .param("price", "-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("EditProduct"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    // Test POST /product/delete/{productId} success
    @Test
    void deleteProductPostRedirectsWhenSuccessful() throws Exception {

        mockMvc.perform(post("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"));

        verify(service).delete("1");
    }

    // Test POST /product/delete/{productId} fail
    @Test
    void deleteProductPostStillRedirectsWhenExceptionThrown() throws Exception {

        doThrow(new RuntimeException("Delete failed"))
                .when(service)
                .delete("1");

        mockMvc.perform(post("/product/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/product/list"))
                .andExpect(flash().attributeExists("errorMessage"));
    }

    // Test GET /list
    @Test
    void productListPageReturnsProductListViewWithProducts() throws Exception {

        List<Product> products = List.of(new Product(), new Product());

        when(service.findAll()).thenReturn(products);

        mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("ProductList"))
                .andExpect(model().attributeExists("products"));
    }




}

