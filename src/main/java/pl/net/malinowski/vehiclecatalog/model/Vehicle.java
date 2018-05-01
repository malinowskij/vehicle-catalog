package pl.net.malinowski.vehiclecatalog.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
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

    private String color;

    private FuelType fuelType;

    @Indexed(unique = true)
    private String registrationNumber;

    @Min(100)
    @Max(10000)
    private int weight;

    @Min(1)
    @Max(64)
    private int peopleCapacity;

    @Min(2)
    @Max(5000)
    private int horsepower;

    @Min(16)
    @Max(18)
    @Indexed(unique = true)
    private String VIN;

    private Date registrationDate;

    public Vehicle(Account account, Brand brand, Model model, String color,
                   FuelType fuelType, @Min(100) @Max(10000) int weight,
                   @Min(1) @Max(64) int peopleCapacity, @Min(2) @Max(5000) int horsepower) {
        this.account = account;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.fuelType = fuelType;
        this.weight = weight;
        this.peopleCapacity = peopleCapacity;
        this.horsepower = horsepower;
        this.registrationDate = new Date();
    }

    public Vehicle(Account account, Brand brand, Model model, String color, FuelType fuelType, @Min(100) @Max(10000) int weight,
                   @Min(1) @Max(64) int peopleCapacity, @Min(2) @Max(5000) int horsepower, @Min(16) @Max(18) String VIN) {
        this.account = account;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.fuelType = fuelType;
        this.weight = weight;
        this.peopleCapacity = peopleCapacity;
        this.horsepower = horsepower;
        this.VIN = VIN;
        this.registrationDate = new Date();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", account=" + account.getUsername() +
                ", brand=" + brand.getName() +
                ", model=" + model.getName() +
                ", color='" + color + '\'' +
                ", fuelType=" + fuelType.toString() +
                ", weight=" + weight +
                ", peopleCapacity=" + peopleCapacity +
                ", horsepower=" + horsepower +
                '}';
    }
}
