package pl.nullpointerexception.minikurstesty.service;

import org.springframework.stereotype.Service;
import pl.nullpointerexception.minikurstesty.model.Policy;
import pl.nullpointerexception.minikurstesty.model.PolicyConfiguration;
import pl.nullpointerexception.minikurstesty.repository.PolicyConfigurationRepository;
import pl.nullpointerexception.minikurstesty.repository.PolicyRepository;

@Service
public class PolicyConfigurationService {

    private final PolicyConfigurationRepository policyConfigurationRepository;

    public PolicyConfigurationService(PolicyConfigurationRepository policyConfigurationRepository) {
        this.policyConfigurationRepository = policyConfigurationRepository;
    }

    public PolicyConfiguration getMostRecentConfiguration() {
        return policyConfigurationRepository.findFirstByOrderByIdDesc()
                .orElseThrow(() -> new IllegalArgumentException("Konfiguracja nie istnieje"));
    }
}
