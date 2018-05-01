package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.net.malinowski.vehiclecatalog.model.Account;
import pl.net.malinowski.vehiclecatalog.model.Brand;
import pl.net.malinowski.vehiclecatalog.model.FuelType;
import pl.net.malinowski.vehiclecatalog.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository extends MongoRepository<Vehicle, String> {
    List<Vehicle> findByFuelType(FuelType fuelType);
    List<Vehicle> findByBrand(Brand brand);
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
    List<Vehicle> findByAccount(Account account);
}
