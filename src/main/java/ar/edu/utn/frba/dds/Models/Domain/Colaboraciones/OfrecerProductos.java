package ar.edu.utn.frba.dds.Models.Domain.Colaboraciones;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import ar.edu.utn.frba.dds.Models.Domain.Utils.Oferta;
import javax.persistence.*;
import lombok.*;

@Data
@Entity
@DiscriminatorValue("OfrecerProductos")
@AllArgsConstructor
@NoArgsConstructor
public class OfrecerProductos extends Colaboracion {

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "productos_ofrecidos",
        joinColumns = @JoinColumn(name = "id_colaboracion"),
        inverseJoinColumns = @JoinColumn(name = "id_oferta")
    )
    private List<Oferta> productosOfrecidos = new ArrayList<>();

    @Column(name = "fecha_oferta")
    private LocalDate fechaOferta;

    //
    @Transient
    private Long idOferta;
    //

    public void addProducto(Oferta producto){
        this.productosOfrecidos.add(producto);
    }
}