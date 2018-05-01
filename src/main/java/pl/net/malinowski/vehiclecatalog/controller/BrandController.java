package pl.net.malinowski.vehiclecatalog.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.net.malinowski.vehiclecatalog.model.Brand;
import pl.net.malinowski.vehiclecatalog.services.BrandService;

import java.util.List;

@RestController
@RequestMapping("/brand")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @GetMapping
    public List<Brand> findAll() {
        return brandService.findAll();
    }
}
