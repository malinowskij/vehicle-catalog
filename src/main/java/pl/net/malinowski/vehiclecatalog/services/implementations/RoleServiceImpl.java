package pl.net.malinowski.vehiclecatalog.services.implementations;

import org.springframework.stereotype.Service;
import pl.net.malinowski.vehiclecatalog.model.Role;
import pl.net.malinowski.vehiclecatalog.repository.RoleRepository;
import pl.net.malinowski.vehiclecatalog.services.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
