package ikea.baldemarfilabdulay_myikea.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ikea.baldemarfilabdulay_myikea.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findUserByEmail(String email);
    Boolean existsUserByEmail(String email);
    
    Optional<User> findUserByNombre(String nombre);
    Boolean existsUserByNombre(String nombre);

}
