package practice.hotel_system.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import practice.hotel_system.entity.Users;
import practice.hotel_system.repository.UsersRepository;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    private final UsersRepository usersRepository;

    public UserService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public Users saveNewUser(Users user) {
        return usersRepository.save(user);
    }

    public boolean getUserByLogin(String username){
        return !usersRepository.findByUsername(username).isEmpty();
    }

    public boolean getUserByLoginAndPassword(String username, String password){
        return !usersRepository.findByUsernameAndPassword(username, password).isEmpty();
    }

    public Users getUserByUsername(String username){
        return usersRepository.getByUsername(username);
    }

    public Long getUserIdByUsername(String username) {
        Users user = usersRepository.getByUsername(username);
        if (user != null) {
            return user.getId();
        }
        return null;
    }

    public List<Users> findAllUsers(){
        return usersRepository.findAll();
    }

    public void saveNewUserRole(Long id, Long roleId){
        usersRepository.saveNewUserRole(id, roleId);
    }

    //for program to understand where is this object located in
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepository.getByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return user;
    }
}