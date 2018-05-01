package pl.net.malinowski.vehiclecatalog.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.net.malinowski.vehiclecatalog.model.Account;
import pl.net.malinowski.vehiclecatalog.model.Privilege;
import pl.net.malinowski.vehiclecatalog.model.Role;
import pl.net.malinowski.vehiclecatalog.repository.AccountRepository;
import pl.net.malinowski.vehiclecatalog.util.AuthenticationUtil;

import java.util.*;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public CustomUserDetailsService(final AccountRepository accountRepository,
                                    PasswordEncoder passwordEncoder, RoleService roleService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final Account account = accountRepository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("User " + username + " not found.");
        }
        if (account.getRoles() == null || account.getRoles().isEmpty()) {
            throw new UsernameNotFoundException("User not authorized.");
        }
        return new User(account.getUsername(), account.getPassword(), account.isEnabled(), true, true, true,
                getAuthorities(account.getRoles()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(final Collection<Role> roles) {
        final List<String> privileges = new ArrayList<String>();
        final List<Privilege> collection = new ArrayList<Privilege>();
        for (final Role role : roles) {
            collection.addAll(role.getPrivileges());
        }
        for (final Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(final List<String> privileges) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (final String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    public Account save(Account account) {
        account.setRoles(new HashSet<>());
        account.getRoles().add(roleService.findByName("ROLE_USER"));
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        return accountRepository.save(account);
    }

    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Account getLoggedInAccount() {
        return findByUsername(AuthenticationUtil.getAuthUsername());
    }

}