package pl.net.malinowski.vehiclecatalog.services;

import pl.net.malinowski.vehiclecatalog.model.Role;

public interface RoleService {
    Role findByName(String name);
}
