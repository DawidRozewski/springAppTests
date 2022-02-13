package pl.nullpointerexception.minikurstesty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nullpointerexception.minikurstesty.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
