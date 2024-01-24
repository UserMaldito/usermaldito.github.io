package ikea.baldemarfilabdulay_myikea.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ikea.baldemarfilabdulay_myikea.models.Producto;

@Repository
public interface ProductosRepository extends JpaRepository<Producto, Integer>{
    
}
