package pl.net.malinowski.vehiclecatalog.services.implementations;

import org.springframework.stereotype.Service;
import pl.net.malinowski.vehiclecatalog.model.Account;
import pl.net.malinowski.vehiclecatalog.model.Vehicle;
import pl.net.malinowski.vehiclecatalog.repository.VehicleRepository;
import pl.net.malinowski.vehiclecatalog.services.CustomUserDetailsService;
import pl.net.malinowski.vehiclecatalog.services.VehicleService;
import pl.net.malinowski.vehiclecatalog.util.AuthenticationUtil;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final CustomUserDetailsService userDetailsService;
    private Random random = new Random();

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
        Account account = userDetailsService.getLoggedInAccount();
        vehicle.setAccount(account);
        vehicle.setRegistrationNumber(giveRegistrationNumber(account));

        return vehicleRepository.save(vehicle);
    }

    @Override
    public boolean delete(String id) {
        Optional<Vehicle> vehicle = vehicleRepository.findById(id);
        if (!vehicle.isPresent())
            return false;
        vehicleRepository.delete(vehicle.get());
        return true;
    }

    @Override
    public Optional<Vehicle> update(Vehicle vehicle) {
        Account account = userDetailsService.getLoggedInAccount();
        if (AuthenticationUtil.isAdmin() || account.getId().equals(vehicle.getAccount().getId()))
            return Optional.of(vehicleRepository.save(vehicle));
        return Optional.empty();
    }

    private String giveRegistrationNumber(Account account) {
        String registrationNumber;
        do {
            registrationNumber = createRegistrationNumber(account);
        } while (vehicleRepository.findByRegistrationNumber(registrationNumber).isPresent());
        return registrationNumber;
    }

    private String createRegistrationNumber(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append(account.getState().getName().substring(0, 1).toUpperCase())
                .append(account.getCity().substring(0, 2).toUpperCase()).append(" ")
                .append(generateSecondPartOfRegistrationNumber());
        return sb.toString();
    }

    private String generateSecondPartOfRegistrationNumber() {
        int secondPart = random.nextInt(9999);
        StringBuilder sb = new StringBuilder();

        if (secondPart < 10)
            sb.append("000");
        else if (secondPart < 100)
            sb.append("00");
        else if (secondPart < 1000)
            sb.append("0");
        sb.append(secondPart);
        return sb.toString();
    }
}
