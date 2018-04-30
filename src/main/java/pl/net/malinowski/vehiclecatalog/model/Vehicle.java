package pl.net.malinowski.vehiclecatalog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Getter @Setter
@Document(collection = "vehicles")
public class Vehicle {

    @Id
    private String id;

    @DBRef
    private Account account;

    @DBRef
    private Brand brand;

    @DBRef
    private Model model;

    public Vehicle(Brand brand, Model model) {
        this.brand = brand;
        this.model = model;
    }
}
