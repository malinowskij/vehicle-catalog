package pl.net.malinowski.vehiclecatalog.util;

import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.net.malinowski.vehiclecatalog.model.*;
import pl.net.malinowski.vehiclecatalog.repository.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class StartupRunner implements ApplicationRunner {

    private final PrivilegeRepository privilegeRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final BrandRepository brandRepository;
    private final ModelRepository modelRepository;
    private final ResourceLoader resourceLoader;
    private final VehicleRepository vehicleRepository;
    private final StateRepository stateRepository;

    public StartupRunner(PrivilegeRepository privilegeRepository, AccountRepository accountRepository,
                         RoleRepository roleRepository, PasswordEncoder passwordEncoder, BrandRepository brandRepository,
                         ModelRepository modelRepository, ResourceLoader resourceLoader, VehicleRepository vehicleRepository,
                         StateRepository stateRepository) {
        this.privilegeRepository = privilegeRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.resourceLoader = resourceLoader;
        this.vehicleRepository = vehicleRepository;
        this.stateRepository = stateRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (stateRepository.findAll().size() == 0)
            initializeStates();

        if (accountRepository.findAll().size() < 2)
            initializeAccounts();

        if (brandRepository.findAll().size() == 0)
            initializeVehicleTypes();
    }

    private void initializeStates() {
        log.info("Start initializing states");
        stateRepository.saveAll(new ArrayList<State>() {{
            add(new State("mazowieckie"));
            add(new State("podlaskie"));
            add(new State("warmińsko-mazurskie"));
            add(new State("pomorskie"));
            add(new State("zachodnio-pomorskie"));
            add(new State("lubuskie"));
            add(new State("dolnośląskie"));
            add(new State("opolskie"));
            add(new State("śląskie"));
            add(new State("małopolskie"));
            add(new State("podkarpackie"));
            add(new State("lubelskie"));
            add(new State("świętokrzystkie"));
            add(new State("łódzkie"));
            add(new State("kujawsko-pomorskie"));
        }});
        log.info("End initializing states");
    }

    private void initializeVehicleTypes() {
        log.info("Start initializing brands and models");
        JSONParser parser = new JSONParser();
        Resource resource = resourceLoader.getResource("classpath:cars.json");
        List<Model> modelList = new ArrayList<>();

        try {
            JSONArray array = (JSONArray) parser.parse(new FileReader(resource.getFile()));
            for (Object o : array) {
                JSONObject b = (JSONObject) o;
                Brand brand = new Brand((String) b.get("title"));
                brandRepository.save(brand);

                JSONArray models = (JSONArray) b.get("models");
                for (Object ou : models) {
                    JSONObject m = (JSONObject) ou;
                    modelList.add(new Model((String) m.get("title"), brand));
                }

                modelRepository.saveAll(modelList);
                brand.getModels().addAll(modelList);
                brandRepository.save(brand);
                modelList = new ArrayList<>();
            }
        } catch (IOException | ParseException e) {
            log.error("I/O Error, or json file parse exception");
        }
        log.info("End initializing brands and marks");
        initializeVehicle();
    }

    private void initializeVehicle() {
        log.info("Start initializing vehicle");
        Brand peugeot = brandRepository.findByName("Peugeot");
        Model model = modelRepository.findByBrandAndName(peugeot, "405");
        Account user = accountRepository.findByUsername("user");

        Vehicle vehicle = new Vehicle(user, peugeot, model, "#ffffff", FuelType.DIESEL,
                1500, 5, 120);
        vehicleRepository.save(vehicle);
        log.info("End initializing vehicle");
    }

    private void initializeAccounts() {
        log.info("Start initializing accounts, role and privileges");
        Privilege privilegeAdmin = new Privilege("PRIVILEGE_ADMIN_READ", "privilege for admin");
        Privilege privilegeUser = new Privilege("PRIVILEGE_USER_READ", "privilege for user");

        privilegeRepository.save(privilegeAdmin);
        privilegeRepository.save(privilegeUser);

        Role roleAdmin = new Role("ROLE_ADMIN", "role for admin");
        roleAdmin.getPrivileges().add(privilegeAdmin);

        Role roleUser = new Role("ROLE_USER", "role for user");
        roleUser.getPrivileges().add(privilegeUser);

        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        Account admin = new Account("admin", "admin");
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.getRoles().add(roleAdmin);
        admin.getRoles().add(roleUser);
        admin.setState(stateRepository.findByName("mazowieckie"));
        admin.setCity("Warszawa");

        Account user = new Account("user", "user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleUser);
        user.setState(stateRepository.findByName("podlaskie"));
        user.setCity("Białystok");

        accountRepository.save(admin);
        accountRepository.save(user);
        log.info("End initializing accounts, roles and privileges");
    }
}
