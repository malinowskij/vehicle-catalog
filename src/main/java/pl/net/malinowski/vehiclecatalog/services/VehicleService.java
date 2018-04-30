package pl.net.malinowski.vehiclecatalog.services;

import pl.net.malinowski.vehiclecatalog.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface VehicleService {
    List<Vehicle> findAll();
    Optional<Vehicle> findById(String id);
    Vehicle save(Vehicle vehicle);
}
