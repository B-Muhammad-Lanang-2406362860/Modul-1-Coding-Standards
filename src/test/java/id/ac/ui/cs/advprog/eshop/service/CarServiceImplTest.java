package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.repository.CarRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {
    @Mock
    CarRepository carRepository;

    @InjectMocks
    CarServiceImpl carService;

    @Test
    void testCreate() {
        Car car = new Car();
        carService.create(car);
        verify(carRepository, times(1)).create(car);
    }

    @Test
    void testFindAll() {
        Car car = new Car();
        when(carRepository.findAll()).thenReturn(Arrays.asList(car).iterator());
        List<Car> result = carService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    void testFindById() {
        Car car = new Car();
        when(carRepository.findById("1")).thenReturn(car);
        assertEquals(car, carService.findById("1"));
    }

    @Test
    void testUpdate() {
        Car car = new Car();
        carService.update("1", car);
        verify(carRepository, times(1)).update("1", car);
    }

    @Test
    void testDeleteCarById() {
        carService.deleteCarById("1");
        verify(carRepository, times(1)).delete("1");
    }
}
