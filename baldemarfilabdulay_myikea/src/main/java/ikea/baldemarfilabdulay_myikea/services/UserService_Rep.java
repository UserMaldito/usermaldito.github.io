package ikea.baldemarfilabdulay_myikea.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import ikea.baldemarfilabdulay_myikea.models.Role;
import ikea.baldemarfilabdulay_myikea.models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ikea.baldemarfilabdulay_myikea.repositories.RoleRepository;
import ikea.baldemarfilabdulay_myikea.repositories.UserRepository;

//CRRUD + EG
@Service
public class UserService_Rep implements UserDetailsService {
    @Autowired
    private UserRepository ur;
    
    @Autowired
    private RoleService_Rep rsr;
    
    @Autowired
    private RoleRepository rr;
    
    public void Create(User createUser){
        //Encoding the User Password
        String userPsw = new BCryptPasswordEncoder().encode(createUser.getPasswordHashed());
        createUser.setPasswordHashed(userPsw);
        
        createUser.setNombre(createUser.getEmail());
        createUser.setApellidos("default");
        createUser.setActivo(true);
        
        //Creating and Setting the New Role to the User
        List<Role> newRoleList = new ArrayList<>();
        Optional<Role> userRole = rr.findByNombre("ROLE_USER");
        if (!userRole.isPresent()) {
            Role newRole = new Role(null, "ROLE_USER", null);
            rsr.Create(newRole);
        }
        
        newRoleList.add(userRole.get());
        createUser.setRoleList(newRoleList);
        
        ur.saveAndFlush(createUser);
    }
    
    public List<User> ReadAll(){
        Iterable<User> iterUser = ur.findAll();
        
        List<User> listUser = StreamSupport
        .stream(iterUser.spliterator(), false)
        .toList();
        
        return listUser;
    }
    
    public User ReadSingle(Integer idUser){
        return GetUser(idUser);
    }
    
    public void Update(User UserUpdate){
        User oldUser = GetUser(UserUpdate.getId());
        
        // oldUser.setExtinto(animalUpdate.getExtinto());
        
        ur.saveAndFlush(oldUser);
    }
    
    public void Delete(Integer idUser){
        ur.deleteById(idUser);
        ur.flush();
    }
    
    
    public Boolean Exist(Integer idUser){
        return ur.existsById(idUser);
    }
    
    public Boolean Exist(String email){
        return ur.existsUserByEmail(email);
    }
    
    public Boolean ExistByName(String username){
        return ur.existsUserByNombre(username);
    }
    
    public User GetUser(Integer idUser){
        User UserRead = null;
        Optional<User> UserFound = ur.findById(idUser);
        
        if (UserFound.isPresent()) {
            UserRead = UserFound.get();
        }
        
        return UserRead;
    }
    
    public User GetUser(String email) {
        return ur.findUserByEmail(email).get();    
    }
    
    public User GetUserByName(String username) {
        return ur.findUserByNombre(username).get();    
    }
    
    public void SaveEntity(User user) {
        ur.saveAndFlush(user);
    }
    
    public void SaveAllEntity(List<User> userList) {
        ur.saveAllAndFlush(userList);
    }
    
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        boolean exist = this.ExistByName(username);
        if (exist) {
            User loadUser = GetUserByName(username);
            
            String authorities = "";
            List<Role> roleUserList = loadUser.getRoleList();
            if (!roleUserList.isEmpty()) {
                for (Role role : roleUserList) {
                    authorities += role.getNombre() + ",";
                }
                authorities = authorities
                .substring(0, authorities.length() - 1);
            }
            
            UserDetails userDetails =
            org.springframework.security.core.userdetails.User
            .withUsername(loadUser.getNombre())
            .password(loadUser.getPasswordHashed())
            .authorities(authorities)
            .build();
            
            return userDetails;
        }

        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
    
}
