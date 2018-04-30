package pl.net.malinowski.vehiclecatalog.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "privileges")
@Getter @Setter
public class Privilege {

    @Id
    private String privilegeId;

    private String name;

    private String description;

    public Privilege(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
