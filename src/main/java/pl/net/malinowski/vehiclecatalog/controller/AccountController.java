package pl.net.malinowski.vehiclecatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.vehiclecatalog.model.Account;
import pl.net.malinowski.vehiclecatalog.services.CustomUserDetailsService;
import pl.net.malinowski.vehiclecatalog.util.AuthenticationUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/account")
public class AccountController {

    private final CustomUserDetailsService userDetailsService;

    public AccountController(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Account account, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getAllErrors());
        return ResponseEntity.ok(userDetailsService.save(account));
    }

    @GetMapping("/details")
    public ResponseEntity<Account> accountDetails() {
        return ResponseEntity.ok(userDetailsService.getLoggedInAccount());
    }
}
