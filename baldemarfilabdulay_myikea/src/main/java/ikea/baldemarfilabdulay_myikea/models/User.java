package ikea.baldemarfilabdulay_myikea.models;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer Id;

    @NotBlank(message = "Campo Obligatorio")
    private String nombre;

    @NotBlank(message = "Campo Obligatorio")
    private String Apellidos;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Campo Obligatorio")
    private String email;

    @NotBlank(message = "Campo Obligatorio")
    private String PasswordHashed;

    @NotNull
    private Boolean Activo;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
                name = "Users_Roles",
                joinColumns = { @JoinColumn(name = "user_Id")},
                inverseJoinColumns = { @JoinColumn(name = "role_Id")}
                )
    private List<Role> roleList = new ArrayList<>();
}