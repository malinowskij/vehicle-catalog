package pl.net.malinowski.vehiclecatalog.services.implementations;

import org.springframework.stereotype.Service;
import pl.net.malinowski.vehiclecatalog.model.State;
import pl.net.malinowski.vehiclecatalog.repository.StateRepository;
import pl.net.malinowski.vehiclecatalog.services.StatesService;

import java.util.List;

@Service
public class StatesServiceImpl implements StatesService {

    private final StateRepository stateRepository;

    public StatesServiceImpl(StateRepository stateRepository) {
        this.stateRepository = stateRepository;
    }

    @Override
    public List<State> findAll() {
        return stateRepository.findAll();
    }
}
