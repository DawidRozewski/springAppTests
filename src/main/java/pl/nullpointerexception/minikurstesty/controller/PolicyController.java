package pl.nullpointerexception.minikurstesty.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.nullpointerexception.minikurstesty.model.Policy;
import pl.nullpointerexception.minikurstesty.service.PolicyCopyService;

@RestController
public class PolicyController {

    private final PolicyCopyService policyCopyService;

    public PolicyController(PolicyCopyService policyCopyService) {
        this.policyCopyService = policyCopyService;
    }

    @GetMapping("/polices/copy")
    public Policy copyPolicy(@RequestParam Long policyId, @RequestParam Long userId) {
        return policyCopyService.createNewPolicyFromExistingOne(policyId, userId);
    }
}
