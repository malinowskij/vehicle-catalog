package pl.net.malinowski.vehiclecatalog.services.implementations;

import org.springframework.stereotype.Service;
import pl.net.malinowski.vehiclecatalog.model.Brand;
import pl.net.malinowski.vehiclecatalog.repository.BrandRepository;
import pl.net.malinowski.vehiclecatalog.services.BrandService;

import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(String id) {
        return brandRepository.findById(id);
    }
}
