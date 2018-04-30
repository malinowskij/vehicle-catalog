package pl.net.malinowski.vehiclecatalog.services.implementations;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.net.malinowski.vehiclecatalog.model.Vehicle;
import pl.net.malinowski.vehiclecatalog.repository.VehicleRepository;
import pl.net.malinowski.vehiclecatalog.services.CustomUserDetailsService;
import pl.net.malinowski.vehiclecatalog.services.VehicleService;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomUserDetailsService userDetailsService;

    public VehicleServiceImpl(VehicleRepository vehicleRepository, CustomUserDetailsService userDetailsService) {
        this.vehicleRepository = vehicleRepository;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    @Override
    public Optional<Vehicle> findById(String id) {
        return vehicleRepository.findById(id);
    }

    @Override
    public Vehicle save(Vehicle vehicle) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        vehicle.setAccount(userDetailsService.findByUsername(auth.getName()));

        return vehicleRepository.save(vehicle);
    }
}
