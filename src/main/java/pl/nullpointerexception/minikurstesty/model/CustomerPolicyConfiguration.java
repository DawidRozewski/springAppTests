package pl.nullpointerexception.minikurstesty.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.math.BigDecimal;

@Entity
public class CustomerPolicyConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private BigDecimal amountDiscount;
    private BigDecimal percentDiscount;
    @OneToOne
    private PolicyConfiguration policyConfiguration;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getAmountDiscount() {
        return amountDiscount;
    }

    public void setAmountDiscount(BigDecimal amountDiscount) {
        this.amountDiscount = amountDiscount;
    }

    public BigDecimal getPercentDiscount() {
        return percentDiscount;
    }

    public void setPercentDiscount(BigDecimal percentDiscount) {
        this.percentDiscount = percentDiscount;
    }

    public PolicyConfiguration getPolicyConfiguration() {
        return policyConfiguration;
    }

    public void setPolicyConfiguration(PolicyConfiguration policyConfiguration) {
        this.policyConfiguration = policyConfiguration;
    }
}
