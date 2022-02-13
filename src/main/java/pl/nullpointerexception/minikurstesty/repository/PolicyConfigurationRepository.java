package pl.nullpointerexception.minikurstesty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.nullpointerexception.minikurstesty.model.Policy;
import pl.nullpointerexception.minikurstesty.model.PolicyConfiguration;

import java.util.Optional;

@Repository
public interface PolicyConfigurationRepository extends JpaRepository<PolicyConfiguration, Long> {
    Optional<PolicyConfiguration> findFirstByOrderByIdDesc();
}
