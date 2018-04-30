package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import pl.net.malinowski.vehiclecatalog.model.State;

public interface StateRepository extends MongoRepository<State, String> {
    State findByName(String name);
}
