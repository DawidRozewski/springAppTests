package pl.nullpointerexception.minikurstesty.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import pl.nullpointerexception.minikurstesty.model.*;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PolicyCopyServiceTest {
    public static final long CUSTOMER_ID = 1L;
    public static final long POLICY_ID = 1L;
    public static final long USER_ID = 1L;
    private static final Clock FIXED = Clock.fixed(Instant.parse("2022-01-01T12:00:00.000Z"), ZoneId.systemDefault());

    @Mock
    private PolicyService policyService;
    @Mock
    private UserService userService;
    @Mock
    private PolicyConfigurationService policyConfigurationService;
    @Mock
    private Clock clock;

    @InjectMocks
    private PolicyCopyService policyCopyService;

    @Test
    void shouldCreatePolicy() {
        // given
        when(clock.instant()).thenReturn(FIXED.instant());
        when(clock.getZone()).thenReturn(FIXED.getZone());
        when(policyService.getPolicy(POLICY_ID)).thenReturn(createPolicy());
        when(policyService.getMostRecentPolicyForCustomer(CUSTOMER_ID)).thenReturn(createMostRecentPolicy());
        when(userService.getUserById(USER_ID)).thenReturn(createUser());
        when(policyConfigurationService.getMostRecentConfiguration()).thenReturn(createNewBaseConfiguration());
        when(policyService.addNewPolicy(any())).thenAnswer(invocation -> {
            Policy policy = invocation.getArgument(0, Policy.class);
            policy.setId(2L);
            policy.getConfiguration().setId(2L);
            return  policy;
        });
        // when
        Policy policy = policyCopyService.createNewPolicyFromExistingOne(POLICY_ID, USER_ID);
        // then
        assertThat(policy).isNotNull();
        assertThat(policy.getId()).isEqualTo(2L);
        assertThat(policy.getCreatorId()).isEqualTo(USER_ID);
        assertThat(policy.getCreated()).isEqualTo(LocalDateTime.now(FIXED));
        assertThat(policy.getConfiguration().getPercentDiscount()).isEqualByComparingTo(new BigDecimal("15.00"));
        assertThat(policy.getConfiguration().getAmountDiscount()).isEqualByComparingTo(new BigDecimal("10.00"));
        assertThat(policy.getConfiguration().getAmountDiscount()).isEqualByComparingTo(new BigDecimal("10.00"));
        PolicyConfiguration base = policy.getConfiguration().getPolicyConfiguration();
        assertThat(base.getPercentDiscount()).isEqualByComparingTo(new BigDecimal("12.00"));
        assertThat(base.getAmountDiscount()).isEqualByComparingTo(new BigDecimal("11.00"));
    }

    private PolicyConfiguration createNewBaseConfiguration() {
        PolicyConfiguration policyConfiguration = new PolicyConfiguration();
        policyConfiguration.setId(2L);
        policyConfiguration.setAmountDiscount(new BigDecimal("11.00"));
        policyConfiguration.setPercentDiscount(new BigDecimal("12.00"));
        return policyConfiguration;
    }

    private User createUser() {
        User user = new User();
        user.setId(USER_ID);
        user.setUserName("User");
        user.setPrivileges(createPrivilages());
        return user;
    }

    private List<Privileges> createPrivilages() {
        Privileges privileges = new Privileges();
        privileges.setName("createPolicy");
        privileges.setActive(true);
        return Arrays.asList(privileges);
    }

    private Policy createMostRecentPolicy() {
        Policy policy = new Policy();
        policy.setId(POLICY_ID);
        policy.setCreated(LocalDateTime.now(FIXED));
        return policy;
    }

    private Policy createPolicy() {
        Policy policy = new Policy();
        policy.setId(POLICY_ID);
        policy.setStatus(PolicyStatus.ACTIVE);
        policy.setCustomer(createCustomer());
        policy.setParentId(0L);
        policy.setCreated(LocalDateTime.now(FIXED));
        policy.setCreatorId(USER_ID);
        policy.setConfiguration(createConfiguration());
        return policy;

    }

    private CustomerPolicyConfiguration createConfiguration() {
        CustomerPolicyConfiguration customerPolicyConfiguration = new CustomerPolicyConfiguration();
        customerPolicyConfiguration.setId(1L);
        customerPolicyConfiguration.setPercentDiscount(new BigDecimal("15.00"));
        customerPolicyConfiguration.setAmountDiscount(new BigDecimal("10.00"));
        customerPolicyConfiguration.setPolicyConfiguration(createBaseConfiguration());
        return customerPolicyConfiguration;
    }

    private PolicyConfiguration createBaseConfiguration() {
        PolicyConfiguration configuration = new PolicyConfiguration();
        configuration.setId(1L);
        configuration.setPercentDiscount(new BigDecimal("15.00"));
        configuration.setAmountDiscount(new BigDecimal("10.00"));
        return configuration;
    }

    private Customer createCustomer() {
        Customer customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setName("Customer1");
        customer.setActive(true);
        return customer;
    }
}