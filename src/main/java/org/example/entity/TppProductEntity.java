package org.example.entity;


import lombok.*;

/*import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;*/
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jakarta.persistence.*;
import org.example.enums.ProductType;

@Table(name = "tpp_product", schema = "public", catalog = "postgres")
@Entity
@Data
@Getter
@Setter
@EqualsAndHashCode(exclude = { "id" })
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class TppProductEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Integer id;

    @Basic
    @Column(name = "product_code_id")
    private Long productCodeId;

    //@Getter @Setter
    //@ManyToOne(fetch = FetchType.LAZY) @JoinColumn(name = "product_code_id", referencedColumnName = "internal_id")
    //private TppRefProductClassEntity productCodeId;
    //private Long productCodeId;

    @Basic
    @Column(name = "client_id")
    private Long clientId;

    @Basic
    @Column(name = "type")
    private ProductType type;

    @Basic
    @Column(name = "number")
    private String number;

    @Basic
    @Column(name = "priority")
    private Integer priority;

    @Basic
    @Column(name = "date_of_conclusion")
    private Date dateOfConclusion;

    @Basic
    @Column(name = "start_date_time")
    private Date startDateTime;

    @Basic
    @Column(name = "end_date_time")
    private Date endDateTime;

    @Basic
    @Column(name = "days")
    private Long days;

    @Basic
    @Column(name = "penalty_rate")
    private BigDecimal penaltyRate;

    @Basic
    @Column(name = "nso")
    private BigDecimal nso;

    @Basic
    @Column(name = "threshold_amount")
    private BigDecimal thresholdAmount;

    @Basic
    @Column(name = "requisite_type")
    private String requisiteType;

    @Basic
    @Column(name = "interest_rate_type")
    private String interestRateType;

    @Basic
    @Column(name = "tax_rate")
    private BigDecimal taxRate;

    @Basic
    @Column(name = "reasone_close")
    private String reasoneClose;

    @Basic
    @Column(name = "state")
    private String state;

//    @OneToMany(mappedBy = "productId")
//    private List<AgreementEntity> agreements = new ArrayList<>();
//
//    public void addAgreement(AgreementEntity agreement){
//        agreement.setProductId(this); //setAgreementId(this);
//        //agreement.setGeneralAgreementId(this);
//        agreements.add(agreement);
//    }
//
//    public Iterator<AgreementEntity> getAgreements() {
//        return agreements.iterator();
//    }

}

/*

CREATE TABLE IF NOT EXISTS tpp_product
        (
                id serial PRIMARY KEY,
--	agreement_id BIGINT,
                product_code_id BIGINT,
                client_id BIGINT,
                type VARCHAR(50),
number VARCHAR(50),
priority BIGINT,
date_of_conclusion TIMESTAMP,
start_date_time TIMESTAMP,
end_date_time TIMESTAMP,
days BIGINT,
penalty_rate DECIMAL,
nso DECIMAL,
threshold_amount DECIMAL,
requisite_type VARCHAR(50),
interest_rate_type VARCHAR(50),
tax_rate DECIMAL,
reasone_close VARCHAR(100),
state VARCHAR(50)
);*/
