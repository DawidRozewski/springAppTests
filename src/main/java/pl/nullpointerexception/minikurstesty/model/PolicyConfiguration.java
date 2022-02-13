package pl.nullpointerexception.minikurstesty.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class PolicyConfiguration {

    @Id
    private long id;
    private BigDecimal amountDiscount;
    private BigDecimal percentDiscount;

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
}
