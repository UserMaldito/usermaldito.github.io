package ikea.baldemarfilabdulay_myikea.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ikea.baldemarfilabdulay_myikea.models.Role;
import ikea.baldemarfilabdulay_myikea.models.User;
import ikea.baldemarfilabdulay_myikea.repositories.RoleRepository;

//CRRUD + EG
@Service
public class RoleService_Rep {
    @Autowired
    private RoleRepository rr;

    public void Create(Role createRole){
        rr.saveAndFlush(createRole);
    }
    
    public List<Role> ReadAll(){
        Iterable<Role> iterRole = rr.findAll();

        List<Role> listRole = StreamSupport
        .stream(iterRole.spliterator(), false)
        .toList();

        return listRole;
    }

    public Role ReadSingle(Integer idRole){
        return GetRole(idRole);
    }

    public void Update(Role RoleUpdate){
        Role oldRole = GetRole(RoleUpdate.getId());

        oldRole.setNombre(RoleUpdate.getNombre());

        rr.saveAndFlush(oldRole);
    }

    public void Delete(Integer idRole){
        rr.deleteById(idRole);
        rr.flush();
    }


    public Boolean Exist(Integer idRole){
        return rr.existsById(idRole);
    }

    public Role GetRole(Integer idRole){
        Role RoleRead = null;
        Optional<Role> RoleFound = rr.findById(idRole);

        if (RoleFound.isPresent()) {
            RoleRead = RoleFound.get();
        }
        
        return RoleRead;
    }


    public void SaveEntity(Role role) {
        rr.saveAndFlush(role);
    }

    public void SaveAllEntity(List<Role> roleList) {
        rr.saveAllAndFlush(roleList);
    }

}
