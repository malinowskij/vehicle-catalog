package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.net.malinowski.vehiclecatalog.model.Brand;
import pl.net.malinowski.vehiclecatalog.model.Model;

import java.util.List;

public interface ModelRepository extends MongoRepository<Model, String> {
    List<Model> findByBrand(Brand brand);
    Model findByBrandAndName(Brand brand, String name);
}
