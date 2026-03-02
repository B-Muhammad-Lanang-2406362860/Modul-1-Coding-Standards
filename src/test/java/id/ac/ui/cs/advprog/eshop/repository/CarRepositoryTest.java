package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Iterator;
import static org.junit.jupiter.api.Assertions.*;

class CarRepositoryTest {
    CarRepository repository;

    @BeforeEach
    void setUp() {
        repository = new CarRepository();
    }

    @Test
    void testCreateAndFind() {
        Car car = new Car();
        car.setCarName("Tesla");
        repository.create(car);

        assertNotNull(car.getCarId());
        assertEquals(car, repository.findById(car.getCarId()));
    }

    @Test
    void testCreateWithExistingId() {
        Car car = new Car();
        car.setCarId("existing-id");
        repository.create(car);
        assertEquals("existing-id", car.getCarId());
    }

    @Test
    void testFindAll() {
        Car car1 = new Car();
        Car car2 = new Car();
        repository.create(car1);
        repository.create(car2);

        Iterator<Car> iterator = repository.findAll();
        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
    }

    @Test
    void testFindByIdNotFound() {
        assertNull(repository.findById("non-existent"));
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        car.setCarName("Old");
        repository.create(car);

        Car updatedCar = new Car();
        updatedCar.setCarName("New");
        updatedCar.setCarColor("Red");
        updatedCar.setCarQuantity(10);

        Car result = repository.update(car.getCarId(), updatedCar);
        assertNotNull(result);
        assertEquals("New", car.getCarName());
    }

    @Test
    void testUpdateNotFound() {
        Car updatedCar = new Car();
        assertNull(repository.update("non-existent", updatedCar));
    }

    @Test
    void testDelete() {
        Car car = new Car();
        repository.create(car);
        String id = car.getCarId();

        repository.delete(id);
        assertNull(repository.findById(id));
    }
}
