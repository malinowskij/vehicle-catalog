package pl.net.malinowski.vehiclecatalog.services.implementations;

import org.springframework.stereotype.Service;
import pl.net.malinowski.vehiclecatalog.model.Brand;
import pl.net.malinowski.vehiclecatalog.model.Model;
import pl.net.malinowski.vehiclecatalog.repository.ModelRepository;
import pl.net.malinowski.vehiclecatalog.services.ModelService;

import java.util.List;

@Service
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;

    public ModelServiceImpl(ModelRepository modelRepository) {
        this.modelRepository = modelRepository;
    }

    @Override
    public List<Model> findByBrand(Brand brand) {
        return modelRepository.findByBrand(brand);
    }
}
