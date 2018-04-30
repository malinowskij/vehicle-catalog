package pl.net.malinowski.vehiclecatalog.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter @Setter
@Document(collection = "models")
public class Model {

    @Id
    private String id;

    @Indexed
    private String name;

    @DBRef
    @JsonBackReference
    private Brand brand;

    public Model(String name, Brand brand) {
         this.name = name;
         this.brand = brand;
    }
}
