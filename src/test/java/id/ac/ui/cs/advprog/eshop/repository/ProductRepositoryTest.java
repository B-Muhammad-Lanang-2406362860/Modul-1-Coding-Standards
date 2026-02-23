package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    Product product1, product2;

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp(){
        product1 = new Product();
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);
        product1.setProductId("ee1a82ab-b109-43a3-ad74-dc52448a7c46");

        product2 = new Product();
        product2.setProductName("Sampo Cap Ucup");
        product2.setProductQuantity(20);
        productRepository.create(product2);
        product2.setProductId("a0fa82ab-b109-43a3-ad74-dc59328a7c47");

    }

    @Test
    void testCreateAndFind() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        assertEquals(product1.getProductName(), savedProduct.getProductName());
        assertEquals(product1.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        productRepository.delete(product1);
        productRepository.delete(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct(){
        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindById_whenValidId_thenProductIsReturned() {
        Product productReturned = productRepository.findById("ee1a82ab-b109-43a3-ad74-dc52448a7c46");
        assertEquals(product1, productReturned);
    }

    @Test
    void testFindById_whenProductNotFound_thenReturnNull() {
        Product productReturned = productRepository.findById("zz1a82ad-b109-43a3-ad74-dc52448a7c46");
        assertNull(productReturned);
    }

    @Test
    void testDelete_whenProductValid_thenRemoveProduct() {
        productRepository.delete(product1);
        Product deletedProduct1 = productRepository.findById("ee1a82ab-b109-43a3-ad74-dc52448a7c46");
        assertNull(deletedProduct1);
    }

    @Test
    void testDelete_whenProductIsNull_thenDoNothing() {
        productRepository.delete(null);
        Product undeletedProduct1 = productRepository.findById("ee1a82ab-b109-43a3-ad74-dc52448a7c46");
        assertEquals(product1, undeletedProduct1);

        Product undeletedProduct2 = productRepository.findById("a0fa82ab-b109-43a3-ad74-dc59328a7c47");
        assertEquals(product2, undeletedProduct2);
    }

    @Test
    void testEdit_whenProductValid_thenEditProduct() {
        product1.setProductQuantity(200);
        product1.setProductName("Sabun Liveboy");

        productRepository.edit(product1);

        Product editedProduct1 = productRepository.findById("ee1a82ab-b109-43a3-ad74-dc52448a7c46");

        assertEquals(editedProduct1.getProductQuantity(), product1.getProductQuantity());
        assertEquals(editedProduct1.getProductName(), product1.getProductName());
    }
}
