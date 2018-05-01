package pl.net.malinowski.vehiclecatalog.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "accounts")
public class Account {

    @Id
    private String id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @Indexed(unique = true)
    @NotNull
    @Email
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6)
    @NotNull
    private String password;

    @NotNull
    @DBRef
    private State state;

    @NotNull
    private String city;

    @DBRef
    private Set<Role> roles = new HashSet<>();

    private boolean enabled = true;

    public Account(@NotNull String username, @Size(min = 6) @NotNull String password) {
        this.username = username;
        this.password = password;
    }

    public Account(@NotNull String firstName, @NotNull String lastName, @NotNull @Email String username,
                   @Size(min = 6) @NotNull String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }
}
