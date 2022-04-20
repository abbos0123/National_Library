package uz.library.national_library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.library.national_library.models.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);


    List<User> findAllByUserNameContainingIgnoreCase(@Param("keyword") String keyword);

    User findUserByUserName(String username);


    boolean existsByUserName(String userName);

    boolean existsUserById(Long id);
}
