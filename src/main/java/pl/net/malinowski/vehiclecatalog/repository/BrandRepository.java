package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.net.malinowski.vehiclecatalog.model.Brand;

public interface BrandRepository extends MongoRepository<Brand, String> {
    Brand findByName(String name);
}
