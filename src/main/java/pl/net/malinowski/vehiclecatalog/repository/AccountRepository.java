package pl.net.malinowski.vehiclecatalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import pl.net.malinowski.vehiclecatalog.model.Account;

import java.util.List;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    Account findByUsername(String username);
}
