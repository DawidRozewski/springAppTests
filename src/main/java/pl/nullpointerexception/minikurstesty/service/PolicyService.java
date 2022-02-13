package pl.nullpointerexception.minikurstesty.service;

import org.springframework.stereotype.Service;
import pl.nullpointerexception.minikurstesty.model.Policy;
import pl.nullpointerexception.minikurstesty.repository.PolicyRepository;

@Service
public class PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyService(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    public Policy getPolicy(Long policyId) {
        return policyRepository.findById(policyId)
                .orElseThrow(() -> new IllegalArgumentException("Polityka nie istniej"));
    }

    public Policy addNewPolicy(Policy policy) {
        return policyRepository.save(policy);
    }

    public Policy getMostRecentPolicyForCustomer(Long customerId) {
        return policyRepository.findFirstByCustomerIdOrderByIdDesc(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Polityka nie istnieje"));
    }

}
