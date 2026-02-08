package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    @Test
    void testCreate_WhenProductIsInvalid_thenThrowError() {
        Product product = new Product();
        product.setProductName("");
        product.setProductQuantity(20);

        assertThrows(IllegalArgumentException.class, () -> {
           productService.create(product);
        });
    }

    @Test
    void testCreate_WhenProductIsValid_thenDoNothing() {
        Product product = new Product();
        product.setProductName("sabun");
        product.setProductQuantity(20);

        assertDoesNotThrow(() -> {
            productService.create(product);
        });
    }

    @Test
    void testEdit_WhenProductIsInvalid_thenThrowError() {
        Product product = new Product();
        product.setProductName("sabun");
        product.setProductQuantity(20);
        productService.create(product);

        assertThrows(IllegalArgumentException.class, () -> {
            product.setProductName("");
            productService.edit(product);
        });
    }

    @Test
    void testEdit_WhenProductIsValid_thenDoNothing() {
        Product product = new Product();
        product.setProductName("sabun");
        product.setProductQuantity(20);
        productService.create(product);

        assertDoesNotThrow(() -> {
            product.setProductName("saboon");
            productService.edit(product);
        });
    }

    @Test
    void testDelete_WhenProductIsInvalid_thenThrowError() {
        assertThrows(NoSuchElementException.class, () -> {
            productService.delete("idBodong");
        });
    }

    @Test
    void testDelete_WhenProductIsValid_thenDoNothing() {
        List<Product> fakeProductRepository = new ArrayList<Product>();

        // mock productRepository.create() method
        doAnswer(productRepository -> {
            Product product = productRepository.getArgument(0);
            fakeProductRepository.add(product);
            return null;
        }).when(productRepository).create(any());

        // mock productRepository.findById() method
        doAnswer(productRepository -> {
            String productId = productRepository.getArgument(0);
            for(Product product: fakeProductRepository){
                if (product.getProductId().equals(productId)) return product;
            }
            return null;
        }).when(productRepository).findById(any());

        // mock productRepository.delete() method
        doNothing().when(productRepository).delete(any());

        // mock productRepostitory.findAll() method
        when(productRepository.findAll())
                .thenAnswer(productRepository -> fakeProductRepository.iterator());

        Product product = new Product();
        product.setProductName("sabun");
        product.setProductQuantity(20);
        product.setProductId("ee1a82ab-b109-43a3-ad74-dc52448a7c46");
        productService.create(product);

        Product productInRepository = productService.findAll().get(0);
        System.out.println(productInRepository.getProductId());

        assertDoesNotThrow(() -> {
            productService.delete("ee1a82ab-b109-43a3-ad74-dc52448a7c46");
        });
    }

    @Test
    void testValidateProduct_WhenProductIsNull_thenThrowError() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.create(null);
        });
    }

    @Test
    void testValidateProduct_WhenProductNameIsEmpty_thenThrowError() {
        Product product = new Product();
        product.setProductName("");

        assertThrows(IllegalArgumentException.class, () -> {
            productService.create(product);
        });
    }

    @Test
    void testValidateProduct_WhenProductNameIsNull_thenThrowError() {
        Product product = new Product();

        assertThrows(IllegalArgumentException.class, () -> {
            productService.create(product);
        });
    }

    @Test
    void testValidateProduct_WhenProductQuantityIsMinus_thenThrowError() {
        Product product = new Product();
        product.setProductName("sabun");
        product.setProductQuantity(-5);

        assertThrows(IllegalArgumentException.class, () -> {
            productService.create(product);
        });
    }

    @Test
    void testNullValidation_WhenProductNameIsEmpty_thenThrowError() {
        assertThrows(IllegalArgumentException.class, () -> {
            productService.create(null);
        });
    }

    @Test
    void testFindAll_WhenTheresTwoProducts_ThenReturnListOfTwoProducts() {
        List<Product> fakeProductRepository = new ArrayList<Product>();

        // mock productRepository.create() method
        doAnswer(productRepository -> {
            Product product = productRepository.getArgument(0);
            fakeProductRepository.add(product);
            return null;
        }).when(productRepository).create(any());

        // mock productRepostitory.findAll() method
        when(productRepository.findAll())
                .thenAnswer(productRepository -> fakeProductRepository.iterator());

        Product product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);

        Product product2 = new Product();
        product2.setProductName("Sampo Cap Livegoy");
        product2.setProductQuantity(10);

        productService.create(product1);
        productService.create(product2);

        List<Product> productList = productService.findAll();

        assertEquals(productList.get(0).getProductName(), product1.getProductName());
        assertEquals(productList.get(1).getProductName(), product2.getProductName());
    }

}
