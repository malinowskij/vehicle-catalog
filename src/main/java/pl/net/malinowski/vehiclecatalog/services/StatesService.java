package pl.net.malinowski.vehiclecatalog.services;

import pl.net.malinowski.vehiclecatalog.model.State;

import java.util.List;

public interface StatesService {
    List<State> findAll();
}
