package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CarTest {

    Car car;

    @BeforeEach
    void setUp(){
        this.car = new Car();
        this.car.setCarId("f2df549f-e42a-4442-8349-f1ed4566c8d3");
        this.car.setCarName("Sampo Cap Bambang");
        this.car.setCarColor("Biru");
        this.car.setCarQuantity(100);
    }

    @Test
    void testGetCarId(){
        assertEquals("f2df549f-e42a-4442-8349-f1ed4566c8d3", this.car.getCarId());
    }

    @Test
    void testGetCarName(){
        assertEquals("Sampo Cap Bambang", this.car.getCarName());
    }

    @Test
    void testGetCarQuantity() {
        assertEquals(100, this.car.getCarQuantity());
    }

    @Test
    void testGetCarColor() {
        assertEquals("Biru", this.car.getCarColor());
    }
}
