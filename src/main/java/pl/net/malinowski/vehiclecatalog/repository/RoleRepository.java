package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.vehiclecatalog.model.Role;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
}
