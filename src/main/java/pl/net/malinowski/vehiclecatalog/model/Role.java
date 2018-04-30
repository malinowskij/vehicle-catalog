package pl.net.malinowski.vehiclecatalog.model;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "roles")
@Getter @Setter
public class Role {

    @Id
    private String roleId;

    private String name;

    private String description;

    @DBRef
    private Set<Privilege> privileges = new HashSet<>(0);

    public Role(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
