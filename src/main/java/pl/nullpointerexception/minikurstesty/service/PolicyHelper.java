package pl.nullpointerexception.minikurstesty.service;

import pl.nullpointerexception.minikurstesty.model.CustomerPolicyConfiguration;
import pl.nullpointerexception.minikurstesty.model.Policy;
import pl.nullpointerexception.minikurstesty.model.PolicyConfiguration;
import pl.nullpointerexception.minikurstesty.model.PolicyStatus;
import pl.nullpointerexception.minikurstesty.model.User;

import java.time.Clock;
import java.time.LocalDateTime;

public class PolicyHelper {

    public static Policy copy(Policy oldPolicy, User user, PolicyConfiguration currentBaseConfig,
                              Clock clock) {
        Policy policy = new Policy();
        policy.setParentId(oldPolicy.getId());
        policy.setCreatorId(user.getId());
        policy.setCreated(LocalDateTime.now(clock));
        policy.setCustomer(oldPolicy.getCustomer());
        policy.setConfiguration(createNew(oldPolicy.getConfiguration(), currentBaseConfig));
        policy.setStatus(PolicyStatus.DRAFT);
        return policy;
    }

    private static CustomerPolicyConfiguration createNew(CustomerPolicyConfiguration currentConfig, PolicyConfiguration currentBaseConfig) {
        CustomerPolicyConfiguration customerPolicyConfiguration = new CustomerPolicyConfiguration();
        customerPolicyConfiguration.setAmountDiscount(currentConfig.getAmountDiscount());
        customerPolicyConfiguration.setPercentDiscount(currentConfig.getPercentDiscount());
        customerPolicyConfiguration.setPolicyConfiguration(currentBaseConfig);
        return customerPolicyConfiguration;
    }
}
