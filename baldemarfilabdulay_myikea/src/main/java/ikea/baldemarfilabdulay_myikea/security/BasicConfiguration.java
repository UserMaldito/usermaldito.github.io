package ikea.baldemarfilabdulay_myikea.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ikea.baldemarfilabdulay_myikea.models.Role;
import ikea.baldemarfilabdulay_myikea.models.User;
import ikea.baldemarfilabdulay_myikea.services.RoleService_Rep;
import ikea.baldemarfilabdulay_myikea.services.UserService_Rep;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class BasicConfiguration {
    @Autowired
    private UserService_Rep UserService_Rep;
    
    @Autowired
    private RoleService_Rep RoleService_Rep;
    
    @Bean
    public SecurityFilterChain Basic_Configuration(HttpSecurity httpSec) throws Exception{
        httpSec.
        authorizeHttpRequests(auth -> 
        auth.requestMatchers("/**").permitAll()
        //
        // .requestMatchers("/**", "/register").permitAll()
        )
        .formLogin(log -> 
        log.loginPage("/login")
        .loginProcessingUrl("/login")
        .defaultSuccessUrl("/")
        .failureUrl("/login?error=true")
        .permitAll()
        )
        .logout(out -> 
        out.logoutUrl("/logout")
        .logoutSuccessUrl("/")
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        )
        .userDetailsService(UserService_Rep)
        .csrf(crf -> crf.disable())
        ;
        
        return httpSec.build();
    }
    
    @Bean 
    public PasswordEncoder passwordEncoder() { 
        return new BCryptPasswordEncoder(); 
    }
    
    @Bean
    public Boolean Seed(){
        boolean seedingCorrect = false;
        List<User> theUserList = UserService_Rep.ReadAll();
        List<Role> theRoleList = RoleService_Rep.ReadAll();
        
        if (theUserList.isEmpty()) {
            theUserList = this.TheUserList();
            seedingCorrect = true;
        }
        
        if (theRoleList.size() < 2) {
            theRoleList = this.TheRoleList();
            seedingCorrect = true;
        }

        if (seedingCorrect) {
            this.TheUserRoleList(theUserList, theRoleList);
        
            //All good? Let's Flash 'em out
            UserService_Rep.SaveAllEntity(theUserList);
            RoleService_Rep.SaveAllEntity(theRoleList);
        }
        
        
        return seedingCorrect;
    }
    
    private List<User> TheUserList(){
        List<User> userList = new ArrayList<>();
        
        String correo = "@maldito.com";
        String nombreUsuario = "admin";
        String defaultPassword = "Asdf1234!";
        
        User adminUser = new User(null, nombreUsuario, nombreUsuario, nombreUsuario + correo, 
        new BCryptPasswordEncoder().encode(defaultPassword), true, new ArrayList<>());
        
        nombreUsuario = "user";
        
        User basicUser = new User(null, nombreUsuario, nombreUsuario, nombreUsuario + correo, 
        new BCryptPasswordEncoder().encode(defaultPassword), true, new ArrayList<>());
        
        userList.add(adminUser);
        userList.add(basicUser);
        
        return userList;
    }
    
    private List<Role> TheRoleList(){
        List<Role> roleList = new ArrayList<>();
        
        String prefijo = "ROLE_";
        List<String> roleName = new ArrayList<>();
        //Add a Role in UpperCase
        roleName.add(prefijo + "ADMIN");
        roleName.add(prefijo + "USER");
        
        Role newRoles = null;
        for (int index = 0; index < roleName.size(); index++) {
            newRoles = new Role(null, roleName.get(index), new ArrayList<>());
            roleList.add(newRoles);
        }

        return roleList;
    }
    
    private void TheUserRoleList(List<User> userList, List<Role> roleList){
        //Users with Some Role(s)
        
        List<User> rolUsers = null;
        for (int index = 0; index < roleList.size(); index++) {
            rolUsers = new ArrayList<>();
            rolUsers.add(userList.get(index));
            roleList.get(index).setUserList(rolUsers);
        }
    }
    
}
