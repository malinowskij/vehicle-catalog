package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.vehiclecatalog.model.Privilege;

@Repository
public interface PrivilegeRepository extends MongoRepository<Privilege, String> {
}
