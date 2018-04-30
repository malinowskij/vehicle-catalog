package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.net.malinowski.vehiclecatalog.model.Brand;
import pl.net.malinowski.vehiclecatalog.model.FuelType;
import pl.net.malinowski.vehiclecatalog.model.Vehicle;

import java.util.List;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    List<Vehicle> findByFuelType(FuelType fuelType);
    List<Vehicle> findByBrand(Brand brand);
}
