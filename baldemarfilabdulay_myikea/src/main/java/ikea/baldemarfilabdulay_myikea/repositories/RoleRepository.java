package ikea.baldemarfilabdulay_myikea.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ikea.baldemarfilabdulay_myikea.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
    Optional<Role> findByNombre(String nombreRole);
    Boolean existsByNombre(String nombre);
}
