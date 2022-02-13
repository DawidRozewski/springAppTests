package pl.nullpointerexception.minikurstesty.service;

import org.springframework.stereotype.Service;
import pl.nullpointerexception.minikurstesty.model.Policy;
import pl.nullpointerexception.minikurstesty.model.PolicyConfiguration;
import pl.nullpointerexception.minikurstesty.model.PolicyStatus;
import pl.nullpointerexception.minikurstesty.model.Privileges;
import pl.nullpointerexception.minikurstesty.model.User;
import pl.nullpointerexception.minikurstesty.service.exception.ValidationException;

import javax.transaction.Transactional;
import java.time.Clock;
import java.util.Arrays;
import java.util.List;

@Service
public class PolicyCopyService {

    private PolicyService policyService;
    private UserService userService;
    private PolicyConfigurationService policyConfigurationService;
    private Clock clock;

    public PolicyCopyService(PolicyService policyService, UserService userService,
                             PolicyConfigurationService policyConfigurationService, Clock clock) {
        this.policyService = policyService;
        this.userService = userService;
        this.policyConfigurationService = policyConfigurationService;
        this.clock = clock;
    }

    @Transactional
    public Policy createNewPolicyFromExistingOne(Long policyId, Long userId) {
        Policy existingPolicy = policyService.getPolicy(policyId);
        validatePolicy(existingPolicy);
        User user = userService.getUserById(userId);
        validateUserCanCreatePolicy(user.getPrivileges());
        PolicyConfiguration currentBaseConfig = policyConfigurationService.getMostRecentConfiguration();
        return createNewPolicy(existingPolicy, currentBaseConfig, user);
    }

    /**
     * Walidujemy politykę
     */
    private void validatePolicy(Policy policy) {
        validateMostRecentPolicyForCustomer(policy, policy.getCustomer().getId());
        validatePolicyStatus(policy, Arrays.asList(PolicyStatus.ACTIVE));
        validateCustomerIsActive(policy);
    }

    /**
     * Sprawdzamy czy użytkowanik chce utworzyć kopię z najnowszej polityki klienta
     */
    private void validateMostRecentPolicyForCustomer(Policy existingPolicy, Long customerId) {
        Policy policy = policyService.getMostRecentPolicyForCustomer(customerId);
        if (!policy.getId().equals(existingPolicy.getId())) {
            throw new ValidationException("To nie jest najnowsza polityka dla tego klienta");
        }
    }

    /**
     * Sprawdzamy Czy polityka ma odpowiedni status
     */
    public void validatePolicyStatus(Policy policy, List<PolicyStatus> validStatuses) {
        if (!validStatuses.contains(policy.getStatus())) {
            throw new ValidationException("Niepoprawny status polityki");
        }
    }

    /**
     * Sprawdzamy czy klient jest aktywny
     */
    public void validateCustomerIsActive(Policy policy) {
        if (policy.getCustomer() != null && !policy.getCustomer().isActive()) {
            throw new ValidationException("Klient id: " + policy.getCustomer().getId() + " jest nieaktywny");
        }
    }

    /**
     * Sprawdzamy czy użytkownik ma uprawnienia do twoprzenia polityk
     */
    private void validateUserCanCreatePolicy(List<Privileges> privileges) {
        privileges.stream()
                .filter(p -> p.getName().equals("createPolicy"))
                .filter(Privileges::isActive)
                .findFirst()
                .orElseThrow(() -> new ValidationException("Użytkownik nie może tworzyć polityk"));
    }

    /**
     * Tworzymy nową politykę
     */
    private Policy createNewPolicy(Policy oldPolicy, PolicyConfiguration currentBaseConfig, User user) {
        Policy newPolicy = PolicyHelper.copy(oldPolicy, user, currentBaseConfig, clock);
        return policyService.addNewPolicy(newPolicy);
    }
}
