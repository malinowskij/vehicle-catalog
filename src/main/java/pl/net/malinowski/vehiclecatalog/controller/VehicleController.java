package pl.net.malinowski.vehiclecatalog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.net.malinowski.vehiclecatalog.model.Vehicle;
import pl.net.malinowski.vehiclecatalog.services.VehicleService;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @GetMapping
    public List<Vehicle> findAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findById(@PathVariable String id) {
        return vehicleService.findById(id).map(v -> new ResponseEntity<>(v, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @PostMapping
    public ResponseEntity<?> save(@Valid @RequestBody Vehicle vehicle, BindingResult result) {
        if (result.hasErrors())
            return ResponseEntity.badRequest().body(result.getAllErrors());
        return ResponseEntity.ok(vehicleService.save(vehicle));
    }
}
