package pl.net.malinowski.vehiclecatalog.services;

import pl.net.malinowski.vehiclecatalog.model.Brand;
import pl.net.malinowski.vehiclecatalog.model.Model;

import java.util.List;

public interface ModelService {
    List<Model> findByBrand(Brand brand);
}
