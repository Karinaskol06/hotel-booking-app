package practice.hotel_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import practice.hotel_system.entity.Users;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByUsername(String username);
    List<Users> findByUsernameAndPassword(String username, String password);
    Users getByUsername(String username);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO users_roles_set (users_set_id, roles_set_id) " +
            "VALUE (:userId, :roleId)", nativeQuery = true)
    void saveNewUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

}
