package ikea.baldemarfilabdulay_myikea.models;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "municipios")
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_municipio;

    @NotNull(message = "Campo Obligatorio")
    private Integer id_provincia;

    @NotNull(message = "Campo Obligatorio")
    private Integer cod_municipio;

    @NotNull(message = "Campo Obligatorio")
    private Integer DC;

    @NotBlank(message = "Campo Obligatorio")
    private String nombre;

    @OneToMany(
        mappedBy = "municipio",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch =  FetchType.EAGER
    )
    private List<Producto> productos;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_provincia", nullable = false, insertable = false, updatable = false)
    private Provincia provincia;

}