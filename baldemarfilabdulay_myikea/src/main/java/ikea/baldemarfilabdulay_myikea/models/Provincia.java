package ikea.baldemarfilabdulay_myikea.models;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "provincias")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_provincia;

    @NotBlank(message = "Campo Obligatorio")
    private String nombre;

    @OneToMany(
        mappedBy = "provincia",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch =  FetchType.EAGER
    )
    private List<Municipio> municipios;



}