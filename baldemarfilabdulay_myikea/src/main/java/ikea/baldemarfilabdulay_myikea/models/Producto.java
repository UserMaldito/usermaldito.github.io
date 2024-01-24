package ikea.baldemarfilabdulay_myikea.models;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
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

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "productoffer")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id;

    @NotBlank(message = "Campo Obligatorio")
    private String product_name;

    @NotNull(message = "Campo Obligatorio")
    private Float product_price;

    private String product_picture;

    @NotNull(message = "Campo Obligatorio")
    private Integer id_municipio;

    @NotNull(message = "Campo Obligatorio")
    @Min(0)
    @Max(600)
    @Digits(integer = 600, fraction = 0, message = "Error: El rango debe ser entre 0 y 600 ...")
    private Integer product_stock;

    //Relations
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_municipio",insertable = false, updatable = false)
    private Municipio municipio;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
                name = "producto_carrito",
                joinColumns = { @JoinColumn(name = "product_id")},
                inverseJoinColumns = { @JoinColumn(name = "id_carrito")}
                )
    private List<Carrito> carritos;

}