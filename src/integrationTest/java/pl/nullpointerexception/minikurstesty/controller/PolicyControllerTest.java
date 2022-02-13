package pl.nullpointerexception.minikurstesty.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.nullpointerexception.minikurstesty.model.Policy;
import pl.nullpointerexception.minikurstesty.model.PolicyStatus;
import pl.nullpointerexception.minikurstesty.repository.PolicyRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test-data.sql")
class PolicyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PolicyRepository policyRepository;

    @Test
    void shouldCopyPolicy() throws Exception {
        // given
        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/polices/copy")
                        .param("policyId", "1")
                        .param("userId", "1")
                ).andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        // then
        Policy policy = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Policy.class);
        assertThat(policy).isNotNull();
        assertThat(policy.getId()).isEqualTo(2L);

        Optional<Policy> byId = policyRepository.findById(2L);
        assertThat(byId).isPresent();
        assertThat(byId.get().getStatus()).isEqualTo(PolicyStatus.DRAFT);

    }
}