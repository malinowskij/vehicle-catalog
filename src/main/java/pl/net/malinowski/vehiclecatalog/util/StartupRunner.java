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

    public StartupRunner(PrivilegeRepository privilegeRepository, AccountRepository accountRepository,
                         RoleRepository roleRepository, PasswordEncoder passwordEncoder, BrandRepository brandRepository,
                         ModelRepository modelRepository, ResourceLoader resourceLoader) {
        this.privilegeRepository = privilegeRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.brandRepository = brandRepository;
        this.modelRepository = modelRepository;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (accountRepository.findAll().size() < 2)
            initializeAccounts();

        if (brandRepository.findAll().size() == 0)
            initializeVehicleTypes();
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

        Account user = new Account("user", "user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleUser);

        accountRepository.save(admin);
        accountRepository.save(user);
        log.info("End initializing accounts, roles and priviliges");
    }
}
