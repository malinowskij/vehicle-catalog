package pl.net.malinowski.vehiclecatalog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.net.malinowski.vehiclecatalog.model.State;
import pl.net.malinowski.vehiclecatalog.services.StatesService;

import java.util.List;

@RestController
@RequestMapping("/states")
public class StatesController {

    private final StatesService statesService;

    public StatesController(StatesService statesService) {
        this.statesService = statesService;
    }

    @GetMapping
    public List<State> getStates() {
        return statesService.findAll();
    }
}
