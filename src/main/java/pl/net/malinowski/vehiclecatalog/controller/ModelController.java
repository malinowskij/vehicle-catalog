package pl.net.malinowski.vehiclecatalog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.net.malinowski.vehiclecatalog.services.BrandService;
import pl.net.malinowski.vehiclecatalog.services.ModelService;

@RestController
@RequestMapping("/model")
public class ModelController {

    private final ModelService modelService;
    private final BrandService brandService;

    public ModelController(ModelService modelService, BrandService brandService) {
        this.modelService = modelService;
        this.brandService = brandService;
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping("/brand/{id}")
    public ResponseEntity<?> findByBrandId(@PathVariable String id) {
        return brandService.findById(id).<ResponseEntity<?>>map(brand1 -> ResponseEntity
                .ok(modelService.findByBrand(brand1)))
                .orElseGet(() -> ResponseEntity.badRequest().body(false));
    }
}
