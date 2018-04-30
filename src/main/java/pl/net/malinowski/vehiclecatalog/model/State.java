package pl.net.malinowski.vehiclecatalog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter @Setter
@Document(collection = "states")
public class State {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    public State(String name) {
        this.name = name;
    }
}
