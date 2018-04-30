package pl.net.malinowski.vehiclecatalog.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.net.malinowski.vehiclecatalog.model.Account;
import pl.net.malinowski.vehiclecatalog.model.Privilege;
import pl.net.malinowski.vehiclecatalog.model.Role;
import pl.net.malinowski.vehiclecatalog.repository.AccountRepository;
import pl.net.malinowski.vehiclecatalog.repository.PrivilegeRepository;
import pl.net.malinowski.vehiclecatalog.repository.RoleRepository;

@Component
public class StartupRunner implements ApplicationRunner {

    private final PrivilegeRepository privilegeRepository;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StartupRunner(PrivilegeRepository privilegeRepository, AccountRepository accountRepository,
                         RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.privilegeRepository = privilegeRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
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
    }
}
