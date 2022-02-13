package pl.nullpointerexception.minikurstesty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nullpointerexception.minikurstesty.model.Policy;

import java.util.Optional;

@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    Optional<Policy> findFirstByCustomerIdOrderByIdDesc(Long customerId);
}
