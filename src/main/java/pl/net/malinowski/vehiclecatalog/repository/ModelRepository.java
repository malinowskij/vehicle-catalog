package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.net.malinowski.vehiclecatalog.model.Model;

public interface ModelRepository extends MongoRepository<Model, String> {

}
