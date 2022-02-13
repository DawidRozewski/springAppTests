package pl.nullpointerexception.minikurstesty.service;

import pl.nullpointerexception.minikurstesty.model.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static pl.nullpointerexception.minikurstesty.service.PolicyCopyServiceTest.*;

class PolicyCopyServiceTestHelper {

    static User createUser(List<Privileges> list) {
        User user = new User();
        user.setId(USER_ID);
        user.setUserName("User");
        user.setPrivileges(list);
        return user;
    }

    static List<Privileges> createPrivilages() {
        Privileges privileges = new Privileges();
        privileges.setName("createPolicy");
        privileges.setActive(true);
        return Arrays.asList(privileges);
    }

    static Policy createMostRecentPolicy(long policyId) {
        Policy policy = new Policy();
        policy.setId(policyId);
        policy.setCreated(LocalDateTime.now(FIXED));
        return policy;
    }

    static Customer createCustomer(boolean active) {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setName("Customer1");
        customer.setActive(active);
        return customer;
    }

    static Policy createPolicy(PolicyStatus active, boolean activeCustomer) {
        Policy policy = new Policy();
        policy.setId(POLICY_ID);
        policy.setStatus(active);
        policy.setCustomer(createCustomer(activeCustomer));
        policy.setParentId(0L);
        policy.setCreated(LocalDateTime.now(FIXED));
        policy.setCreatorId(USER_ID);
        policy.setConfiguration(createConfiguration());
        return policy;

    }

    static CustomerPolicyConfiguration createConfiguration() {
        CustomerPolicyConfiguration customerPolicyConfiguration = new CustomerPolicyConfiguration();
        customerPolicyConfiguration.setId(1L);
        customerPolicyConfiguration.setPercentDiscount(new BigDecimal("15.00"));
        customerPolicyConfiguration.setAmountDiscount(new BigDecimal("10.00"));
        customerPolicyConfiguration.setPolicyConfiguration(createBaseConfiguration());
        return customerPolicyConfiguration;
    }

    static PolicyConfiguration createBaseConfiguration() {
        PolicyConfiguration configuration = new PolicyConfiguration();
        configuration.setId(1L);
        configuration.setPercentDiscount(new BigDecimal("15.00"));
        configuration.setAmountDiscount(new BigDecimal("10.00"));
        return configuration;
    }

    static PolicyConfiguration createNewBaseConfiguration() {
        PolicyConfiguration policyConfiguration = new PolicyConfiguration();
        policyConfiguration.setId(2L);
        policyConfiguration.setAmountDiscount(new BigDecimal("11.00"));
        policyConfiguration.setPercentDiscount(new BigDecimal("12.00"));
        return policyConfiguration;
    }

}
