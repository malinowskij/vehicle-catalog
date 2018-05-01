package pl.net.malinowski.vehiclecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter @Setter
@Document(collection = "brands")
public class Brand {

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;

    @DBRef
    @JsonManagedReference
    private Set<Model> models = new HashSet<>();

    public Brand(String name) {
        this.name = name;
    }
}
