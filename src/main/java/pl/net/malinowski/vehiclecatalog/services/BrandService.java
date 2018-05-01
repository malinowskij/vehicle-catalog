package pl.net.malinowski.vehiclecatalog.services;

import pl.net.malinowski.vehiclecatalog.model.Brand;

import java.util.List;
import java.util.Optional;

public interface BrandService {
    List<Brand> findAll();
    Optional<Brand> findById(String id);
}
